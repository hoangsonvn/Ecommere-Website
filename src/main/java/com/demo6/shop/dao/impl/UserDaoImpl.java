package com.demo6.shop.dao.impl;

import com.demo6.shop.dao.UserDao;
import com.demo6.shop.entity.User;
import com.graphbuilder.org.apache.harmony.awt.gl.Crossing;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User findByVoucherId(String voucherId) {
		String sql = "select * from user u join user_voucher uv on u.user_id = uv.user_id where uv.voucher_id = :voucherId ";
		NativeQuery<User> nativeQuery = sessionFactory.getCurrentSession().createNativeQuery(sql,User.class)
				.setParameter("voucherId",voucherId);
		return nativeQuery.uniqueResult();
	}
	@Override
	public User findOne(Long id) {
		return sessionFactory.getCurrentSession().find(User.class,id);
	}

	@Override
	public void merge(User user) {
		sessionFactory.getCurrentSession().merge(user);
	}

	@Override
	public void insert(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public void update(User user) {
		sessionFactory.getCurrentSession().merge(user);
	}

	@Override
	public void delete(long userId) {
		User user = findById(userId);
		sessionFactory.getCurrentSession().delete(user);
	}
	@Override
	public User findById(long userId) {
		return sessionFactory.getCurrentSession().find(User.class, userId);
	}
	@Override
	public List<User> findAll(int pageIndex, int pageSize) {
		int first = pageIndex * pageSize;
		String sql = "SELECT u FROM User u";
		TypedQuery<User> typedQuery = sessionFactory.getCurrentSession().createQuery(sql,User.class).setFirstResult(first).setMaxResults(pageSize);
		return typedQuery.getResultList();
	}

	@Override
	public User loadUserByUsername(String account) {
		String sql = "SELECT u FROM User u WHERE u.email =: email or (:email is null or u.phone = :email) and u.verify = true";
		TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(sql,User.class)
				.setParameter("email",account);
		return  query.getSingleResult();
	}
	@Override
	public int count() {
		String sql = "SELECT count(u) FROM User u";
		TypedQuery typedQuery = sessionFactory.getCurrentSession().createQuery(sql);
		long count = (long) typedQuery.getSingleResult();
		return (int) count;
	}

	@Override
	public User findByEmail(String email) {
		String sql = "SELECT u FROM User u WHERE u.email = :email";
		Query query = sessionFactory.getCurrentSession().createQuery(sql)
				.setParameter("email",email);
		return (User) query.uniqueResult();
	}

}
