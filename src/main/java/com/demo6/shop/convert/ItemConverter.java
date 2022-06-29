package com.demo6.shop.convert;

import com.demo6.shop.dto.ItemDTO;
import com.demo6.shop.dto.OrderDTO;
import com.demo6.shop.dto.ProductDTO;
import com.demo6.shop.dto.SaleDTO;
import com.demo6.shop.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemConverter {
    public ItemDTO toDTO(Item item){

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(item.getOrder().getOrderId());

        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setSaleId(item.getProduct().getSale().getSaleId());
        saleDTO.setSalePercent(item.getProduct().getSale().getSalePercent());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(item.getProduct().getProductId());
        productDTO.setImage(item.getProduct().getImage());
        productDTO.setProductName(item.getProduct().getProductName());
        productDTO.setPrice(item.getProduct().getPrice());
        productDTO.setSaleDTO(saleDTO);

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemId(item.getItemId());
        itemDTO.setOrderDTO(orderDTO);
        itemDTO.setProductDTO(productDTO);
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setUnitPrice(item.getUnitPrice());
        itemDTO.setProductName(item.getProductName());
        itemDTO.setVote(item.getVote());
        return itemDTO;
    }
}
