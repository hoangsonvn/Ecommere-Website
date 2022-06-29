package com.demo6.shop.common;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.demo6.shop.constant.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ResourceBundle;

@Component
public class Common implements ICommon {
    @Autowired
    private AmazonS3Client awsS3Client;
    ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    public int totalPage(int count, int pageSize) {
        return count % SystemConstant.PAGESIZE == 0 ? count / SystemConstant.PAGESIZE : (count / pageSize + 1);
    }

    public void notificate(HttpServletRequest request) {
        String messageSuccess = request.getParameter("messageSuccess");
        String messageCreate = request.getParameter("messagecreate");
        String messageDelete = request.getParameter("messagedelete");
        String messageUpdate = request.getParameter("messageupdate");
        String messageImage = request.getParameter("imagefile");
        String imageformat = request.getParameter("imageformat");
        String message = request.getParameter("message");// hanlder duplicate
        String messsagedate = request.getParameter("expireddate");
        String tick = request.getParameter("tick");
        String nothingchange = request.getParameter("nothingchange");
        String effectivedate = request.getParameter("effectivedate");
        String noteffectivedate = request.getParameter("noteffectivedate");
        String notvoucher = request.getParameter("notvoucher");
        String ordersuccess = request.getParameter("ordersuccess");
        String used = request.getParameter("used");
        String commentcheck = request.getParameter("commentcheck");
        if (messageCreate != null) {
            request.setAttribute("messagecreate", resourceBundle.getString(messageCreate));
        }
        if (messageDelete != null) {
            request.setAttribute("messagedelete", resourceBundle.getString(messageDelete));
        }
        if (messageUpdate != null) {
            request.setAttribute("messageupdate", resourceBundle.getString(messageUpdate));
        }
        if (tick != null) {
            request.setAttribute("tick", resourceBundle.getString(tick));
        }
        if (messageImage != null) {
            request.setAttribute("imagefile", resourceBundle.getString(messageImage));
        }
        if (imageformat != null) {
            request.setAttribute("imageformat", resourceBundle.getString(imageformat));
        }
        if (message != null) {
            request.setAttribute("message", resourceBundle.getString(message));
        }
        if (messsagedate != null) {
            request.setAttribute("expireddate", resourceBundle.getString(messsagedate));
        }
        if (messageSuccess != null) {
            request.setAttribute("messageSuccess", resourceBundle.getString(messageSuccess));
        }
        if (nothingchange != null) {
            request.setAttribute("nothingchange", resourceBundle.getString(nothingchange));
        }
        if (effectivedate != null) {
            request.setAttribute("effectivedate", resourceBundle.getString(effectivedate));
        }
        if (noteffectivedate != null) {
            request.setAttribute("noteffectivedate", resourceBundle.getString(noteffectivedate));
        }
        if (notvoucher != null) {
            request.setAttribute("notvoucher", resourceBundle.getString(notvoucher));
        }
        if (ordersuccess != null) {
            request.setAttribute("ordersuccess", resourceBundle.getString(ordersuccess));
        }
        if (used != null) {
            request.setAttribute("used", resourceBundle.getString(used));
        }
        if (commentcheck != null) {
            request.setAttribute("commentcheck", resourceBundle.getString(commentcheck));
        }

}

    @Override
    public String imageUpload(MultipartFile imageFile) {
        String filenameExtension = StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
        String key = System.currentTimeMillis() + "." + filenameExtension;
        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentLength(imageFile.getSize());
        metaData.setContentType(imageFile.getContentType());

        try {
            awsS3Client.putObject("bucketslhs", key, imageFile.getInputStream(), metaData);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An exception occured while uploading the file");
        }
        awsS3Client.setObjectAcl("bucketslhs", key, CannedAccessControlList.PublicRead);
        return key;
    }
}
