package com.demo6.shop.service.impl;

import com.demo6.shop.dao.VoucherDao;
import com.demo6.shop.entity.Voucher;
import com.demo6.shop.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherDao voucherDao;

    @Override
    public Voucher findOneByCode(String code) {
        return voucherDao.findOneByCode(code);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherDao.findAll();
    }

    @Override
    public void persist(Voucher voucher) {
        voucherDao.persist(voucher);
    }

    @Override
    public void merge(Voucher voucher) {
        voucher.setCreateDate(voucherDao.findOneById(voucher.getVoucherId()).getCreateDate());
        voucherDao.merge(voucher);
    }

    @Override
    public void delete(String voucherId) {
        voucherDao.delete(voucherId);
    }

    @Override
    public Voucher findOneById(String voucherId) {
        return voucherDao.findOneById(voucherId);
    }

    @Override
    public Voucher findOneByCodeActive(String code) {
        return voucherDao.findOneByCodeActive(code);
    }
}
