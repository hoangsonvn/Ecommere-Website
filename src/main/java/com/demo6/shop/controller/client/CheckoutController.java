package com.demo6.shop.controller.client;

import com.demo6.shop.common.ICommon;
import com.demo6.shop.constant.SystemConstant;
import com.demo6.shop.dao.ItemDao;
import com.demo6.shop.dto.CartDTO;
import com.demo6.shop.dto.OrderDTO;
import com.demo6.shop.dto.UserPrincipal;
import com.demo6.shop.entity.*;
import com.demo6.shop.service.*;
import com.demo6.shop.service.impl.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping(value = "/client")
public class CheckoutController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private ICommon iCommon;
    @Autowired
    private CartService cartService;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @GetMapping("/confirm")
    public String confirm(HttpServletRequest request, HttpSession session) {
        Voucher voucher = (Voucher) Optional.ofNullable(session.getAttribute("voucherCodeClient")).orElseGet(Voucher::new);
        HashMap<Long, CartDTO> cart = Optional.ofNullable((HashMap<Long, CartDTO>) session.getAttribute("cart")).orElseGet(() -> new HashMap<>());
        float totalPrice = cartService.totalPrice(cart);
        Float voucherPrice = voucher.getVoucherPercent() * totalPrice / 100;
        // voucher.getMaxVoucher().floatValue()=  voucherPrice;
        voucherPrice = voucherPrice > voucher.getMaxVoucher() && voucher.getMaxVoucher() != 0 ? voucher.getMaxVoucher() : voucherPrice;

        Float finalTotal = totalPrice - voucherPrice;
        session.setAttribute("voucherPrice", voucherPrice);
        session.setAttribute("cart", cart);
        session.setAttribute("TotalQuantyCart", cartService.totalQuanty(cart));
        session.setAttribute("TotalPriceCartSale", cartService.totalPrice(cart));
        session.setAttribute("finalTotal", finalTotal + SystemConstant.FEE);
        String message = request.getParameter("limit");
        if (message != null) {
            request.setAttribute("limit", message);
        }
        try {
            UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            session.setAttribute("user", userPrincipal);
        } catch (ClassCastException e) {
            logger.error("invalid details user");
        }
        iCommon.notificate(request);
        return "/client/confirm";
    }

    @PostMapping(value = "/vocherCodeRemove")
    public String remove(HttpSession session) {
        session.removeAttribute("voucherCodeClient");
        session.removeAttribute("voucherdescription");
        return "redirect:/client/confirm";

    }

    @PostMapping("/voucherCode")
    public String getVoucherCode(HttpSession session, @RequestParam(value = "vouchercode") String vouchercode) {
        Voucher voucher = voucherService.findOneByCode(vouchercode);
        if (voucher != null) {
            if (voucher.getEffectiveDate().compareTo(new Date(new Date().getTime())) > 0) {
                return "redirect:/client/confirm?noteffectivedate=notEffectiveDate";
            }
            if(userService.findByVoucherId(voucher.getVoucherId())!= null){
                return "redirect:/client/confirm?used=used";
            }
            session.setAttribute("voucherCodeClient", voucher);
            session.setAttribute("voucherdescription", voucher.getShortDescription());
            return "redirect:/client/confirm";
        } else {
            return "redirect:/client/confirm?notvoucher=notVoucher";
        }
    }

    @GetMapping(value = "/checkout")
    public String checkout(HttpSession session, HttpServletRequest request) {
        float voucherPrice = (float) Optional.ofNullable(session.getAttribute("voucherPrice")).orElse(0);
        UserPrincipal userPrincipal = (UserPrincipal) session.getAttribute("user");
        Map<Long, CartDTO> cart = (Map<Long, CartDTO>) session.getAttribute("cart");
        String paypal = request.getParameter("paypal");
        //  float priceVoucherOnceProduct = voucherPrice / cart.size();
        float priceVoucherOnceProduct = voucherPrice / cartService.totalPrice((HashMap<Long, CartDTO>) cart);


        for (
                Map.Entry<Long, CartDTO> entry : cart.entrySet()) {
            if (entry.getValue().getProductDTO().getQuantity() - entry.getValue().getQuantity() < 0) {
                String mess = "   Product name named :_ " + entry.getValue().getProductDTO().getProductName() + " _ is not enough #! ";
                return "redirect:/client/listcart?limit=" + mess;
            }
            int quanityInStock = entry.getValue().getProductDTO().getQuantity() - entry.getValue().getQuantity();// số lượng còn lại trong kho
            entry.getValue().getProductDTO().setQuantity(quanityInStock);
            productService.update(entry.getValue().getProductDTO());
            // compare quantity in stock
        }


        OrderDTO order1 = new OrderDTO();
        Order order = orderService.insert(session, order1);
        for (Map.Entry<Long, CartDTO> entry : cart.entrySet()) {
            Product product = new Product();
            product.setProductId(entry.getValue().getProductDTO().getProductId());
            Item item = new Item();
            item.setProduct(product);
            item.setQuantity(entry.getValue().getQuantity());
            // item.setUnitPrice((float) entry.getValue().getTotalPriceSale() - priceVoucherOnceProduct);
            item.setUnitPrice((float) ((float) entry.getValue().getTotalPriceSale() - entry.getValue().getTotalPriceSale() * priceVoucherOnceProduct));
            item.setOrder(order);
            item.setProductName(entry.getValue().getProductDTO().getProductName());
            itemDao.insert(item);
        }

        mailSender.send();

        sendEmail("myanhm55@gmail.com", userPrincipal.getEmail(), "PiFood!",
                "Hello, " + userPrincipal.getFullname() + "! We will deliver it to you soon" +
                        "  Order:  " +
                        "  \n  #OrderId: " + order.getOrderId() +
                        "  \n----PriceTotal:  " + order.getPriceTotal() +
                        "  \n----BuyDate: " + order.getBuyDate() +
                        "  \n----Customer: " + order.getBuyer().getFullname() +
                        "  \n----Adress: " + order.getBuyer().getAddress() +
                        "  \n----Phone: " + order.getBuyer().getPhone() +
                        "  \n ===== Have a nice day <3! =====");

        Voucher voucher = (Voucher) session.getAttribute("voucherCodeClient");
        if (voucher != null) {
           List<Voucher> voucherList = new ArrayList<>();
            voucherList.add(voucher);
            User user = userService.findOne(order.getBuyer().getUserId());
            user.setVouchers(voucherList);

            userService.merge(user);
        }

        cart.clear();
        session.removeAttribute("voucherCodeClient");
        session.removeAttribute("voucherdescription");
        session.removeAttribute("voucherPrice");
        session.removeAttribute("TotalQuantyCart");
        session.removeAttribute("TotalPriceCartSale");
        if (paypal == null) {
            return "redirect:/client/my-order?ordersuccess=orderSuccess";
        } else {
            return "redirect:/client/pay?price=" + order.getPriceTotal();
        }

    }

    public void sendEmail(String from, String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);

        mailSender.send(mailMessage);
    }
}