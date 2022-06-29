package com.demo6.shop.service.impl;

import com.demo6.shop.common.ICommon;
import com.demo6.shop.convert.UserConverter;
import com.demo6.shop.dao.UserDao;
import com.demo6.shop.dto.RoleDTO;
import com.demo6.shop.dto.UserDTO;
import com.demo6.shop.dto.UserPrincipal;
import com.demo6.shop.entity.User;
import com.demo6.shop.entity.Voucher;
import com.demo6.shop.service.OrderService;
import com.demo6.shop.service.UserService;
import com.demo6.shop.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private ICommon iCommon;
    @Autowired
    private OrderService orderService;
    @Autowired
    private VoucherService voucherService;

    @Override
    public User findByVoucherId(String voucherId) {
        return userDao.findByVoucherId(voucherId);
    }

    @Override
    public User findOne(Long id) {
        return userDao.findOne(id);
    }

    @Override
    public void merge(User user) {
        userDao.merge(user);
    }

    @org.springframework.transaction.annotation.Transactional
    @Override
    public void userUpdate(HttpServletRequest request, String email, long userId, String fullName, boolean gender, String phone, String address, long roleId, String password, String repassword, MultipartFile avatarFile, String avatar) {
        Optional<UserDTO> userDTO = Optional.ofNullable(findById(userId));
        if (!userDTO.isPresent()) {
            throw new UsernameNotFoundException("not found");
        }
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleId(roleId);
        String avatarFilename = !avatarFile.isEmpty() ? iCommon.imageUpload(avatarFile) : avatar;
        UserDTO userUpdate = new UserDTO(email, userId, new BCryptPasswordEncoder().encode(repassword), fullName, phone, address, gender, roleDTO, avatarFilename, true);
        UserPrincipal userPrincipal = (UserPrincipal) request.getSession().getAttribute("user");

        if (userId == userPrincipal.getUserId()) {
            userPrincipal.setFullname(fullName);
            userPrincipal.setAvatar(avatarFilename);
        }

        this.update(userUpdate);
    }

    @Override
    public void userCreate(String email, String fullName, boolean gender, String phone, String address, long roleId, String password, String repassword, MultipartFile avatarFile) {
        String image = null;
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleId(roleId);
        if (!avatarFile.isEmpty()) {
            image = iCommon.imageUpload(avatarFile);
        }
        UserDTO userDTO = new UserDTO(email, new BCryptPasswordEncoder().encode(repassword), fullName, phone, address, gender, true, roleDTO, image);
        this.insert(userDTO);

    }

    @Override
    public UserDTO insert(UserDTO userDTO) {
        User user = userConverter.toEntity(userDTO);
        userDao.insert(user);
        return userConverter.toDTO(user);
    }

    @Override
    public void update(UserDTO userDTO) {
        userDao.update(userConverter.toEntity(userDTO));
    }

    @Override
    public void delete(long userId) {
        orderService.deleteByUserId(userId);
        userDao.delete(userId);
    }

    @Override
    public UserDTO findById(long userId) {
        User user = userDao.findById(userId);
        return userConverter.toDTO(user);
    }

    @Override
    public List<UserDTO> findAll(int pageIndex, int PageSize) {
        List<User> users = userDao.findAll(pageIndex, PageSize);
        return users.stream().map(s -> userConverter.toDTO(s)).collect(Collectors.toList());
    }


    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        System.out.println("0--------------");

        User user = userDao.loadUserByUsername(account);
        System.out.println("1---------------");


        List<SimpleGrantedAuthority> roleList = new ArrayList<>();
        user.getRole().getPermissions().forEach(s -> {
            roleList.add(new SimpleGrantedAuthority(s.getPermissionKey()));
        });
        roleList.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        return new UserPrincipal(user.getEmail(), user.getPassword(), roleList, user.getUserId(),
                user.getEmail(), user.getFullname(), user.getPhone(), user.getAddress(), user.isGender(), user.isVerify(), user.getRole(), user.getAvatar());
    }

    @Override
    public int count() {
        return userDao.count();
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user != null) {
            return userConverter.toDTO(user);
        }
        return null;
    }


}
