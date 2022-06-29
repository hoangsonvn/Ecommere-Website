package com.demo6.shop.common;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface ICommon {
     int totalPage(int count,int pageSize);
     void notificate(HttpServletRequest request);
     String imageUpload(MultipartFile imageFile);
}
