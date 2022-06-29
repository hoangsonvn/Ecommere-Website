package com.demo6.shop.convert;

import com.demo6.shop.dto.SaleDTO;
import com.demo6.shop.entity.Sale;
import org.springframework.stereotype.Component;

@Component
public class SaleConverter {
    public SaleDTO toDTO(Sale sale){
        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setSaleId(sale.getSaleId());
        saleDTO.setSalePercent(sale.getSalePercent());
        saleDTO.setTitle(sale.getTitle());
        saleDTO.setShortDescription(sale.getShortDescription());
        saleDTO.setCreateDate(sale.getCreateDate());
        saleDTO.setExpirationDate(sale.getExpirationDate());
        return saleDTO;
    }

    public Sale toEntity(SaleDTO saleDTO){
        Sale sale = new Sale();
        sale.setSaleId(saleDTO.getSaleId());
        sale.setSalePercent(saleDTO.getSalePercent());
        sale.setTitle(saleDTO.getTitle());
        sale.setShortDescription(saleDTO.getShortDescription());
        sale.setCreateDate(saleDTO.getCreateDate());
        sale.setExpirationDate(saleDTO.getExpirationDate());
        return sale;
    }
}
