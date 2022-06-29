package com.demo6.shop.dao.impl;

import com.demo6.shop.dao.VoucherDao;
import com.demo6.shop.entity.User;
import com.demo6.shop.entity.Voucher;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Repository
public class VoucheurDaoImpl implements VoucherDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    @Override
    public Voucher findOneByCode(String code) {
        String sql = "select v from Voucher v where v.code = :code";
        Query<Voucher> query = sessionFactory.getCurrentSession().createQuery(sql, Voucher.class).setParameter("code", code);
        return query.uniqueResult();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Voucher> findAll() {
        String sql = " SELECT v FROM Voucher v ";
        TypedQuery<Voucher> typedQuery = sessionFactory.getCurrentSession().createQuery(sql, Voucher.class);
        return typedQuery.getResultList();
    }

    @Transactional
    @Override
    public void persist(Voucher voucher) {
        voucher.setVoucherId(UUID.randomUUID().toString());
        sessionFactory.getCurrentSession().persist(voucher);
    }

    @Transactional
    @Override
    public void merge(Voucher voucher) {
        sessionFactory.getCurrentSession().merge(voucher);
    }

    @Transactional
    @Override
    public void delete(String voucherId) {
        sessionFactory.getCurrentSession().remove(this.findOneById(voucherId));
    }

    @Transactional(readOnly = true)
    @Override
    public Voucher findOneById(String voucherId) {
        return sessionFactory.getCurrentSession().get(Voucher.class, voucherId);
    }

    @Transactional(readOnly = true)
    @Override
    public Voucher findOneByCodeActive(String code) {
        String sql = "SELECT v from Voucher v where day(v.effectiveDate) > day(current_date) and v.code = :code";
        Query query = sessionFactory.getCurrentSession().createQuery(sql).setParameter("code", code);
        return (Voucher) query.uniqueResult();
    }

}
