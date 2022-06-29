package com.demo6.shop.service.impl;

import com.demo6.shop.convert.ItemConverter;
import com.demo6.shop.dao.ItemDao;
import com.demo6.shop.dto.ItemDTO;
import com.demo6.shop.entity.Item;
import com.demo6.shop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private ItemConverter itemConverter;

    @Override
    public int countVoteByProductId(long productId) {
      return  itemDao.countVotesByProductId(productId);
    }

    @Override
    public void saveOrUpdate(long vote, long itemId) {
            itemDao.update(vote,itemId);
    }

    @Override
    public List<ItemDTO> findByOrderId(long orderId) {
        List<Item> items = itemDao.findByOrderId(orderId);
        List<ItemDTO> itemDTOs = new ArrayList<>();
        for (Item item : items) {
            itemDTOs.add(itemConverter.toDTO(item));
        }
        return itemDTOs;
    }

}
