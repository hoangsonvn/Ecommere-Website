package com.demo6.shop.service;

import com.demo6.shop.dto.SaleDTO;
import com.demo6.shop.entity.Sale;

import java.util.List;

public interface SaleService {
	void persist(SaleDTO saleDTO);
	void update(SaleDTO saleDTO);
	void delete(String id);
	SaleDTO findOne(String id);
	List<SaleDTO> findAll();
	Integer findBySalePercent(Integer salePercent);


}
