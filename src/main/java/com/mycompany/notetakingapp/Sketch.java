/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * كلاس يمثل رسمًا (Sketch) يمكن إنشاؤه وعرضه وحفظه.
 */
public class Sketch implements Displayable {
    private String name; // اسم الرسم
    private String path; // المسار الذي سيتم حفظ الرسم فيه
    private BufferedImage sketchImage; // صورة الرسم

    /**
     * المُنشئ لإنشاء كائن Sketch.
     *
     * @param name اسم الرسم.
     * @param noteFolderPath مسار المجلد الخاص بالملاحظة.
     */
    public Sketch(String name, String noteFolderPath) {
        // تحديد مسار حفظ الرسم
        this.path = noteFolderPath + "/sketchs/" + name + ".png";
        this.name = name;
    }

    /**
     * دالة لفتح نافذة رسم جديدة والسماح للمستخدم بالرسم.
     * يتم حفظ الرسم عند الضغط على زر الحفظ.
     */
    public void drawSketch() {
        // إنشاء نافذة JFrame لفتح واجهة الرسم
        JFrame drawingFrame = new JFrame("ارسم الرسم الخاص بك");
        drawingFrame.setSize(500, 500);
        drawingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // إنشاء لوحة الرسم DrawingPanel
        DrawingPanel drawingPanel = new DrawingPanel();
        drawingFrame.add(drawingPanel);

        // زر لحفظ الرسم
        JButton saveButton = new JButton("حفظ الرسم");
        saveButton.setBounds(10, 10, 150, 30); // تحديد موضع وحجم الزر
        saveButton.addActionListener(_ -> {
            // حفظ الرسم عند الضغط على الزر
            sketchImage = drawingPanel.getImage(); // الحصول على الصورة من اللوحة
            drawingFrame.dispose(); // إغلاق نافذة الرسم
        });

        drawingPanel.add(saveButton); // إضافة الزر إلى لوحة الرسم

        drawingFrame.setLayout(null); // تعطيل مدير التخطيط الافتراضي
        drawingFrame.setVisible(true); // جعل النافذة مرئية
    }

    /**
     * دالة لعرض الرسم المحفوظ باستخدام نافذة عرض.
     */
    public void displaySketch() {
        display(path);
    }

    /**
     * دالة لحفظ الرسم كملف صورة PNG.
     */
    public void saveSketch() {
        FileManager.saveAsPNG(path, sketchImage); // استخدام FileManager لحفظ الصورة
    }

    /**
     * دالة لاسترجاع مسار ملف الرسم.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * دالة لاسترجاع اسم الرسم.
     */
    public String getName() {
        return this.name;
    }
}
