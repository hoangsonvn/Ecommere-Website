package com.demo6.shop.service;

import com.demo6.shop.dto.UserDTO;
import com.demo6.shop.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface UserService {
	User findByVoucherId(String voucherId);
	User findOne(Long id);
	void merge(User user);
	void userUpdate(HttpServletRequest request, String email, long userId, String fullName, boolean gender, String phone, String address, long roleId, String password, String repassword, MultipartFile avatarFile, String avatar);

	void userCreate(String email, String fullName, boolean gender, String phone, String address, long roleId, String password, String repassword, MultipartFile avatarFile);

	UserDTO insert(UserDTO userDTO);
	
	void update(UserDTO userDTO);
	
	void delete(long userId);
	
	UserDTO findById(long userId);
	
	List<UserDTO> findAll(int pageIndex, int PageSize);


	UserDTO findByEmail(String email);
	int count();
	
}
