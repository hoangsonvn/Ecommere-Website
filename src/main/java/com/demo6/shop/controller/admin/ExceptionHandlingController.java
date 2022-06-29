package com.demo6.shop.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);

 /*   @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllException(Exception ex, WebRequest request) {
        //do something
        //   return new ErrorMessage(10000, ex.getLocalizedMessage());
        return "error";
    }
*/

    /*  @ExceptionHandler(NoResultException.class)
      @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
      public ErrorMessage handNoResultException(Exception ex, WebRequest request) {
          //do something
          return new ErrorMessage(10000, ex.getLocalizedMessage());
      }*/
    @ExceptionHandler(MultipartException.class)
    //@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String MultipartException(
            MultipartException exc,
            HttpServletRequest request,
            HttpServletResponse response) {
        return "redirect:" + request.getHeader("Referer") + "?imagefile=toolarge";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    //@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response) {
        String header = request.getHeader("Referer");
        String str = (header.split("&")[0]);
       /* StringBuilder sb = new StringBuilder();
        for (String s : stringArray) {
            sb.append(s);
        }
        String str = sb.toString();
        if (str.contains("profile")) {
            String[] array = (str.split("?"));
            StringBuilder sbr = new StringBuilder();
            for (String s : array) {
                sbr.append(s);
            }
            String stb = sb.toString();
            return "redirect:" + stb + "?imagefile=toolarge";

        }*/
        if (str.contains("profile")) {
             str = (header.split("\\?")[0]);

            return "redirect:" + str + "?imagefile=toolarge";
        }

      //  if (str.contains("userId") || str.contains("productId")) {
            return "redirect:" + str + "&imagefile=toolarge";
      //  }
    }
   // java.lang.IllegalStateException: Ambiguous @ExceptionHandler method mapped for neu trong class co  2 ngoai le giong nhau dien ra nen mih thay bang MultipartException.class de khac voi product create

}
