package com.demo6.shop.dao;

import com.demo6.shop.entity.Sale;

import java.util.List;
import java.util.Set;

public interface SaleDao {

	List<Sale> findAll();
	void persist(Sale sale);
	void update(Sale sale);
	void delete(String id);
	Sale findOne(String id);
	Integer findBySalePercent(Integer salePercent);
	
}
