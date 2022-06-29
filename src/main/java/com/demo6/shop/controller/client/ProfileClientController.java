package com.demo6.shop.controller.client;

import com.demo6.shop.common.ICommon;
import com.demo6.shop.controller.admin.StatsController;
import com.demo6.shop.dto.RoleDTO;
import com.demo6.shop.dto.UserDTO;
import com.demo6.shop.dto.UserPrincipal;
import com.demo6.shop.service.UserService;
import com.demo6.shop.utils.SecurityUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Objects;

@Controller
@RequestMapping(value = "/client")
public class ProfileClientController {

    @Autowired
    private UserService userService;
    @Autowired
    private ICommon iCommon;
    private static final Logger logger = LoggerFactory.getLogger(StatsController.class);

    @GetMapping(value = "/profile")
    public String profile(HttpServletRequest request, HttpSession session) {
        iCommon.notificate(request);
        try {
            UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            session.setAttribute("user", userPrincipal);
        } catch (Exception e) {
            logger.error("khong co thong tin");
        }

        return "client/profile";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @PostMapping(value = "/profile-update")
    public String profileUpdate(HttpServletRequest request, @RequestParam(name = "fullname", required = false) String fullname, @RequestParam(name = "phone") String phone,
                                @RequestParam(name = "address") String address, @RequestParam(name = "gender") boolean gender, @RequestParam(name = "avatarFile") MultipartFile avatarFile, @RequestParam(value = "image", required = false) String image) {
        UserPrincipal userPrincipal = (UserPrincipal) request.getSession().getAttribute("user");
        if(userPrincipal.isGender()==gender && Objects.equals(userPrincipal.getFullname(), fullname) && Objects.equals(userPrincipal.getPhone(), phone) && Objects.equals(userPrincipal.getAddress(), address) && avatarFile.isEmpty()){
            return "redirect:/client/profile?nothingchange=nothingChange";
        }

        userPrincipal.setFullname(fullname);
        userPrincipal.setPhone(phone);
        userPrincipal.setAddress(address);
        userPrincipal.setGender(gender);
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleId(userPrincipal.getRole().getRoleId());
        roleDTO.setRoleName(userPrincipal.getRole().getRoleName());


        UserDTO userDTO = UserDTO.builder().userId(userPrincipal.getUserId())
                .email(userPrincipal.getEmail())
                .phone(userPrincipal.getPhone())
                .address(userPrincipal.getAddress())
                .avatar(userPrincipal.getAvatar())
                .fullname(userPrincipal.getFullname())
                .verify(userPrincipal.isVerify())
                .gender(userPrincipal.isGender())
                .password(userPrincipal.getPassword())
                .roleDTO(roleDTO).build();
        if (avatarFile.getSize() > 0) {
            if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_PNG.getMimeType(), ContentType.IMAGE_GIF.getMimeType()).contains(avatarFile.getContentType())) {
                return "redirect:/client/profile?imageformat=imagefileformat";
            }
        }

        String avatarFileName = !avatarFile.isEmpty() ? iCommon.imageUpload(avatarFile) : image;
        userDTO.setAvatar(avatarFileName);
        userPrincipal.setAvatar(avatarFileName);

        userService.update(userDTO);
        return "redirect:/client/profile?messageSuccess=messageSuccess";
    }

    @PostMapping(value = "/change-password")
    public String changePassword(HttpServletRequest request) {
        String oldpassword = request.getParameter("oldpassword");
        String newpassword = request.getParameter("newpassword");
        String repassword = request.getParameter("repassword");
        UserPrincipal userPrincipal = SecurityUtils.getPrincipal();
        UserDTO userDTO = userService.findByEmail(userPrincipal.getEmail());
        if (!new BCryptPasswordEncoder().matches(oldpassword, userDTO.getPassword())) {
            request.setAttribute("messageinvalid", "invalid password");
        } else if (!newpassword.equals(repassword)) {
            request.setAttribute("messageinvalid", "password and repassword not match!");
        } else {
            userDTO.setPassword(new BCryptPasswordEncoder().encode(newpassword));
            userService.update(userDTO);
            request.setAttribute("messageSuccess", "Change Password successful: ");
        }
        return "client/profile";
    }
}
