package com.demo6.shop.controller.admin;

import com.demo6.shop.dto.ProductDTO;
import com.demo6.shop.dto.SaleDTO;
import com.demo6.shop.entity.Voucher;
import com.demo6.shop.service.ProductService;
import com.demo6.shop.service.SaleService;
import com.demo6.shop.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ScheduleController {
    @Autowired
    private ProductService productService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private VoucherService voucherService;

    @Scheduled(cron = " 0 0 0 * * ? ")
    public void scheduleFixedDelayTask() {
        Date newDate = new Date(new Date().getTime());
        List<ProductDTO> productDTOS = productService.findAll();
        productDTOS.stream().filter(s -> newDate.compareTo(s.getExpirationDate()) > 0)
                .forEach(s -> {
                    s.setQuantity(0);
                    productService.update(s);
                });
    }

    @Scheduled(cron = " 0 0 0 * * ? ")
    public void scheduleTaskSale() {
        Date newDate = new Date(new Date().getTime());
        List<SaleDTO> saleDTOS = saleService.findAll();
        saleDTOS.stream().filter(s -> newDate.compareTo(s.getExpirationDate()) > 0)
                .forEach(s -> {
                    saleService.delete(s.getSaleId());
                });
    }

    @Scheduled(cron = " 0 0 0 * * ? ")
    public void scheduleTaskVoucher() {
        Date newDate = new Date(new Date().getTime());
        List<Voucher> vouchers = voucherService.findAll();
        vouchers.stream().filter(s -> newDate.compareTo(s.getExpirationDate()) > 0)
                .forEach(s -> {
                    voucherService.delete(s.getVoucherId());
                });
    }
}
