/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;

/**
 * كلاس يمثل صورة مع مسارها ويحتوي على وظائف لعرضها والحصول على اسمها.
 *
 * @author alraya
 */
public class Image implements Displayable {
    private String path; // المسار الخاص بالصورة

    // المُنشئ لتعيين مسار الصورة عند إنشاء الكائن
    public Image(String path) {
        this.path = path;
    }

    // دالة لإرجاع المسار الخاص بالصورة
    public String getPath() {
        return path;
    }

    // دالة لعرض الصورة مع وصف بسيط
    public void displayImage() {
        display(path);
    }

    // دالة للحصول على اسم الصورة بدون المسار وامتداد الملف
    public String getImageName() {
        String name = this.path; // نسخ المسار إلى متغير مؤقت
        int lastSlashIndex = name.lastIndexOf('/'); // الحصول على موقع آخر '/'
        if (lastSlashIndex != -1) {
            name = name.substring(lastSlashIndex + 1); // استخراج النص بعد آخر '/'
        }
        
        int dotIndex = name.lastIndexOf('.'); // الحصول على موقع آخر '.'
        if (dotIndex != -1) {
            name = name.substring(0, dotIndex); // إزالة الامتداد
        }
        
        return name; // إرجاع اسم الصورة
    }
}
