package com.demo6.shop.dao.impl;

import com.demo6.shop.dao.SaleDao;
import com.demo6.shop.entity.Sale;
import lombok.Builder;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
@Transactional
public class SaleDaoImpl implements SaleDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Sale> findAll() {
        String sql = "SELECT s FROM Sale s";
        TypedQuery<Sale> typedQuery = sessionFactory.getCurrentSession().createQuery(sql, Sale.class);
        return typedQuery.getResultList();
    }

    @Override
    public void persist(Sale sale) {
        sale.setSaleId(UUID.randomUUID().toString());
        sale.setCreateDate(new Date(new java.util.Date().getTime()));
        sessionFactory.getCurrentSession().save(sale);
    }

    @Override
    public void update(Sale sale) {
        sessionFactory.getCurrentSession().merge(sale);
    }

    @Override
    public void delete(String id) {
        sessionFactory.getCurrentSession().remove(sessionFactory.getCurrentSession().find(Sale.class, id));
    }

    @Override
    public Sale findOne(String id) {
        String sql = "SELECT s from Sale s where s.saleId = :id";
        Query<Sale> query= sessionFactory.getCurrentSession().createQuery(sql,Sale.class)
                .setParameter("id",id);
        return  query.uniqueResult();
    }

    @Override
    public Integer findBySalePercent(Integer salePercent) {
        String sql = "SELECT s.salePercent FROM Sale s WHERE s.salePercent = : salePercent ";
        Query<Integer> query = sessionFactory.getCurrentSession().createQuery(sql,Integer.class)
                .setParameter("salePercent",salePercent);
        return query.uniqueResult();
    }



}
