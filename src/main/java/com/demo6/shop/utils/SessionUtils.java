package com.demo6.shop.utils;


import javax.servlet.http.HttpSession;

public class SessionUtils {
    public static float finalTotal(HttpSession session) {
        float finalTotal = (float) session.getAttribute("finalTotal");
        return finalTotal;
    }

}
