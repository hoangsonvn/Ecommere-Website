package com.demo6.shop.controller.client;


import com.demo6.shop.common.ICommon;
import com.demo6.shop.constant.SystemConstant;
import com.demo6.shop.controller.admin.PermissionController;
import com.demo6.shop.dto.ItemDTO;
import com.demo6.shop.dto.UserPrincipal;
import com.demo6.shop.service.ItemService;
import com.demo6.shop.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;

@Controller
@RequestMapping(value = "/client")
public class MyOrderClientController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ICommon iCommon;

    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);
    ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    @GetMapping(value = "/my-order")
    public String myOrder(HttpServletRequest request) {
        iCommon.notificate(request);
        String message = request.getParameter("message");
        if (message != null) {
            request.setAttribute("message", resourceBundle.getString(message));
        }
        try {
            UserPrincipal userPrincipal = (UserPrincipal) request.getSession().getAttribute("user");
            long userId = userPrincipal.getUserId();
            request.setAttribute("orders", orderService.findAllOrderCLientById(userId));
        } catch (NullPointerException e) {
            logger.error("not found user");
        }
        return "client/my_order";
    }

    @GetMapping(value = "order-details")
    public String orderDetails(HttpServletRequest request, @RequestParam(name = "orderId") long orderId) {
        try {
            List<ItemDTO> itemDTOs = itemService.findByOrderId(orderId);
            float subTotal = 0;
            for (ItemDTO itemDTO : itemDTOs) {
                subTotal += itemDTO.getUnitPrice();
            }
            double grandTotal = subTotal + SystemConstant.FEE;

            request.setAttribute("subTotal", subTotal);
            request.setAttribute("grandTotal", grandTotal);
            request.setAttribute("items", itemDTOs);

        } catch (EntityNotFoundException e) {
            logger.error("chi tiet san pham da removed" + e);
            return "redirect:/client/my-order?message=detailsRemoved";
        }
        String reviewsuccess = request.getParameter("reviewsuccess");
        if (reviewsuccess != null) {
            request.setAttribute("reviewsuccess", resourceBundle.getString(reviewsuccess));
        }
        return "client/order_details";
    }

    @PostMapping("/order-details")
    public String pOrderDetails(HttpServletRequest request, @RequestParam(value = "vote") long vote, @RequestParam("itemId") long itemId) {
        itemService.saveOrUpdate(vote, itemId);
        return "redirect:" + request.getHeader("Referer") + "&reviewsuccess=reviewSuccess";
    }

    @GetMapping("/deleteorder/{id}")
    public String deleteOrder(@PathVariable(value = "id") Long id) {
        orderService.deleteOrderClientById(id);
        return "redirect:/client/my-order?messagedelete=orderDelete";
    }
}
