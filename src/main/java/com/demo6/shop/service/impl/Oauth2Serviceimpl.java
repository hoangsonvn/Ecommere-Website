package com.demo6.shop.service.impl;

import com.demo6.shop.convert.RoleConverter;
import com.demo6.shop.dto.RoleDTO;
import com.demo6.shop.dto.UserDTO;
import com.demo6.shop.dto.UserPrincipal;
import com.demo6.shop.entity.Role;
import com.demo6.shop.service.Oauth2Service;
import com.demo6.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class Oauth2Serviceimpl implements Oauth2Service {

    @Autowired
    private RoleConverter roleConverter;
    @Autowired
    private UserService userService;
    @Override
    public UserPrincipal getInfo(String name,String nameEmail) {
        UserPrincipal userPrincipal;
        RoleDTO roleDTO = new RoleDTO(5,"ROLE_USER");
        Role role = roleConverter.toEntity(roleDTO);
        UserDTO userDTO = userService.findByEmail(nameEmail);
        if (userDTO==null) {
            UserDTO newUserDTO = new UserDTO(nameEmail,name,true,roleDTO);
                 newUserDTO.setAvatar("1653941986997.png");
            UserDTO insertDto = userService.insert(newUserDTO);
            userPrincipal = new UserPrincipal(insertDto.getFullname(),"", SecurityContextHolder.getContext().getAuthentication().getAuthorities(),insertDto.getUserId(),insertDto.getEmail(),insertDto.getFullname(), insertDto.isVerify(),role);
            userPrincipal.setAvatar("1653941986997.png");
        } else {
             userPrincipal = new UserPrincipal(userDTO.getFullname(), "", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            userPrincipal.setFullname(userDTO.getFullname());
            userPrincipal.setEmail(userDTO.getEmail());
            userPrincipal.setAvatar(userDTO.getAvatar());
            userPrincipal.setRole(role);
            userPrincipal.setUserId(userDTO.getUserId());
            userPrincipal.setAddress(userDTO.getAddress());
            userPrincipal.setPhone(userDTO.getPhone());
            userPrincipal.setVerify(userDTO.isVerify());

        }
        return userPrincipal;
    }
}
