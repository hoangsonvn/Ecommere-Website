package com.demo6.shop.service;

import com.demo6.shop.entity.Voucher;

import java.util.List;

public interface VoucherService {
    Voucher findOneByCode(String code);
    List<Voucher> findAll();
    void persist(Voucher voucher);
    void merge(Voucher voucher);
    void delete(String voucherId);
    Voucher findOneById(String voucherId);
    Voucher findOneByCodeActive(String code);
}
