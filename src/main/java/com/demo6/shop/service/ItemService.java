package com.demo6.shop.service;

import com.demo6.shop.dto.ItemDTO;

import java.util.List;

public interface ItemService {
	int countVoteByProductId(long productId);
	void saveOrUpdate(long vote,long itemId);
	List<ItemDTO> findByOrderId(long orderId);

}
