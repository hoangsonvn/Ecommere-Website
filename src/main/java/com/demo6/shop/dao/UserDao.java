package com.demo6.shop.dao;

import com.demo6.shop.entity.User;

import java.util.List;

public interface UserDao {
	User findByVoucherId(String voucherId);
	User findOne(Long id);
	void merge(User user);

	void insert(User user);
	
	void update(User user);
	
	void delete(long userId);
	
	User findById(long userId);
	
	List<User> findAll(int pageIndex, int pageSize);


	User loadUserByUsername(String account);
	
	User findByEmail(String email);
	
	int count();
}
