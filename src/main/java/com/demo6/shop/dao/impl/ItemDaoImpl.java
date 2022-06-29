package com.demo6.shop.dao.impl;

import com.demo6.shop.dao.ItemDao;
import com.demo6.shop.entity.Item;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional
public class ItemDaoImpl implements ItemDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int countVotesByProductId(long productId) {
        String sql= "select count(i.vote) from item i where i.vote > 0 and i.product_id = :id";
        NativeQuery nativeQuery = sessionFactory.getCurrentSession().createNativeQuery(sql)
                .setParameter("id", productId);
        return ((BigInteger) nativeQuery.getSingleResult()).intValue();
    }

    @Override
    public Item findOne(long itemId) {
        return sessionFactory.getCurrentSession().get(Item.class, itemId);
    }

    @Override
    public void update(long vote, long itemId) {
        String sql = "update item set item.vote = :vote where item.item_id= :itemId";
        sessionFactory.getCurrentSession().createNativeQuery(sql)
                .setParameter("vote", vote)
                .setParameter("itemId", itemId).executeUpdate();

    }


    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM item  WHERE item.order_id= :id";
        sessionFactory.getCurrentSession().createNativeQuery(sql)
                .setParameter("id", id).executeUpdate();
    }

    @Override
    public void insert(Item item) {
        sessionFactory.getCurrentSession().persist(item);
    }

    @Override
    public List<Item> findByOrderId(long orderId) {
        String sql = "SELECT i FROM Item i WHERE i.order.orderId =: orderId";
        TypedQuery<Item> query = sessionFactory.getCurrentSession().createQuery(sql, Item.class)
                .setParameter("orderId", orderId);
        return query.getResultList();
    }
}
