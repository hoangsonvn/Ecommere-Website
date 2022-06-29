package com.demo6.shop.service.impl;

import com.demo6.shop.constant.SystemConstant;
import com.demo6.shop.controller.admin.StatsController;
import com.demo6.shop.convert.OrderConverter;
import com.demo6.shop.dao.ItemDao;
import com.demo6.shop.dao.OrderDao;
import com.demo6.shop.dao.ProductDao;
import com.demo6.shop.dto.OrderDTO;
import com.demo6.shop.dto.UserDTO;
import com.demo6.shop.dto.UserPrincipal;
import com.demo6.shop.entity.Item;
import com.demo6.shop.entity.Order;
import com.demo6.shop.entity.Product;
import com.demo6.shop.service.ItemService;
import com.demo6.shop.service.OrderService;
import com.demo6.shop.utils.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.sql.Date;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderConverter orderConverter;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private ProductDao productDao;
    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public void deleteOrderClientById(Long id) {
        OrderDTO orderDTO = orderConverter.toDto(orderDao.findById(id));
        if (orderDTO.getStatus().equals("PENDING")) {
            List<Item> items = itemDao.findByOrderId(id);

            items.forEach(s -> {
                Product product = productDao.findById(s.getProduct().getProductId());
                product.setQuantity(s.getQuantity() + s.getProduct().getQuantity());
                productDao.update(product);
            });
            logger.debug("hoan tra so luong khi o trang thai remove PENDING");

            orderDao.delete(id);

        } else {
            orderDao.deleteOrderClientById(id);
        }
    }

    @Override
    public List<OrderDTO> findAllOrderCLientById(Long id) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        List<Order> orders = orderDao.findAllOrderCLientById(id);
        orders.forEach(s -> orderDTOS.add(orderConverter.toDto(s)));
        return orderDTOS;
    }

    @Override
    public void deleteByUserId(Long id) {
        orderDao.deleteByUserId(id);
    }

    @Override
    public void updateHome(long orderId) {
        OrderDTO orderDTO = this.findById(orderId);
        orderDTO.setStatus("SUCCESS");
        this.update(orderDTO);
    }

    @Override
    public Double totalPriceByCurrentMonth() {
        return orderDao.totalPriceByCurrentMonth();
    }

    @Override
    public Double totalPrice() {
        return orderDao.totalPrice();
    }

    @Override
    public Order insert(HttpSession session, OrderDTO orderDTO) {
        UserPrincipal userPrincipal = (UserPrincipal) session.getAttribute("user");
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userPrincipal.getUserId());
        userDTO.setPhone(userPrincipal.getPhone());
        userDTO.setAddress(userPrincipal.getAddress());
        userDTO.setFullname(userPrincipal.getFullname());

        orderDTO.setUserDTO(userDTO);
        orderDTO.setBuyDate(new Date(new java.util.Date().getTime()));
        orderDTO.setStatus("PENDING");
        orderDTO.setPriceTotal(SessionUtils.finalTotal(session));
        Order order = orderConverter.toEntity(orderDTO);
        orderDao.insert(order);
        return order;

    }

    @Override
    public void update(OrderDTO orderDTO) {

        orderDao.update(orderConverter.toEntity(orderDTO));
    }

    @Override
    public void delete(long orderId) {
        orderDao.delete(orderId);
    }

    @Override
    public List<OrderDTO> findAll(int pageIndex, int pageSize) {
        List<Order> orders = orderDao.findAll(pageIndex, pageSize);
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            orderDTOs.add(orderConverter.toDto(order));
        }
        return orderDTOs;
    }

    @Override
    public List<OrderDTO> findByBuyer(long userId) {
        List<Order> orders = orderDao.findByBuyer(userId);
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            orderDTOs.add(orderConverter.toDto(order));
        }
        return orderDTOs;
    }

    @Override
    public int count() {
        return orderDao.count();
    }

    @Override
    public OrderDTO findById(long orderId) {
        Order order = orderDao.findById(orderId);
        return orderConverter.toDto(order);
    }

}
