package com.demo6.shop.controller.client;

import com.demo6.shop.common.ICommon;
import com.demo6.shop.controller.admin.PermissionController;
import com.demo6.shop.service.CommentService;
import com.demo6.shop.service.ItemService;
import com.demo6.shop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/client")
public class ProductDetailsClientController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ICommon iCommon;
    @GetMapping(value = "/product-details")
    public String productDetails(HttpServletRequest request, @RequestParam(name = "productId") long productId) {
        Set<String> roles1 = new HashSet<>();
       try{
            roles1 =  AuthorityUtils.authorityListToSet((SecurityContextHolder.getContext()).getAuthentication().getAuthorities());
       }catch (Exception e){
       }
       if(roles1.contains("ROLE_EDITOR") || roles1.contains(("ROLE_MANAGER"))){
           request.setAttribute("comments",commentService.findAllByProductIdAutho(productId) );
       }else {
           request.setAttribute("comments", commentService.findAllByProductId(productId));
       }
        iCommon.notificate(request);
        request.setAttribute("reviews",itemService.countVoteByProductId(productId) );
        request.setAttribute("product", productService.findById(productId));
        return "client/product_details";
    }
}