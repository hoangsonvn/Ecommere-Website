package com.demo6.shop.service.impl;

import com.demo6.shop.convert.SaleConverter;
import com.demo6.shop.dao.SaleDao;
import com.demo6.shop.dto.SaleDTO;
import com.demo6.shop.entity.Sale;
import com.demo6.shop.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {
    @Autowired
    private SaleConverter saleConverter;
    @Autowired
    private SaleDao saleDao;

    @Override
    public void persist(SaleDTO saleDTO) {
        saleDao.persist(saleConverter.toEntity(saleDTO));
    }

    @Override
    public void update(SaleDTO saleDTO) {
        saleDao.update(saleConverter.toEntity(saleDTO));
    }

    @Override
    public void delete(String id) {
        saleDao.delete(id);
    }

    @Override
    public SaleDTO findOne(String id) {
        return saleConverter.toDTO(saleDao.findOne(id));
    }

    @Override
    public List<SaleDTO> findAll() {
        List<Sale> sales = saleDao.findAll();
        List<SaleDTO> saleDTOs = new ArrayList<>();
        for (Sale sale : sales) {
            saleDTOs.add(saleConverter.toDTO(sale));
        }
        return saleDTOs;
    }

    @Override
    public Integer findBySalePercent(Integer salePercent) {
        return saleDao.findBySalePercent(salePercent);
    }


}
