package com.mycompany.notetakingapp;

import java.security.MessageDigest;

/**
 * كلاس يحتوي على أدوات للأمان، مثل تشفير كلمات المرور.
 */
public class SecurityUtils {

    /**
     * دالة لتشفير كلمة المرور باستخدام خوارزمية SHA-256.
     *
     * @param password كلمة المرور النصية التي سيتم تشفيرها.
     * @return النص المشفر لكلمة المرور (hex string)، أو null في حالة وجود خطأ.
     */
    public static String hashPassword(String password) {
        try {
            // إنشاء كائن MessageDigest لخوارزمية SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            // تحويل كلمة المرور إلى مصفوفة بايت وتشفيرها
            byte[] hashedBytes = md.digest(password.getBytes());
            
            // تحويل البايتات المشفرة إلى سلسلة نصية بنظام الـ Hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString(); // إرجاع النص المشفر
        } catch (Exception e) {
            System.out.println("Error hashing password: " + e.getMessage()); // طباعة رسالة الخطأ
            return null; // إرجاع null إذا حدث خطأ
        }
    }
}
