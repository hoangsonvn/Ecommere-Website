package com.demo6.shop.dao;

import com.demo6.shop.entity.Item;

import java.util.List;

public interface ItemDao {
	int countVotesByProductId(long productId);
	Item findOne(long itemId);
	void update(long vote,long itemId);
	void delete(Long id);
	void insert(Item item);
	List<Item> findByOrderId(long orderId);
}
