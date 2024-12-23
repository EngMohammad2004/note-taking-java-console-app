/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;

import java.awt.image.BufferedImage;
/**
 *
 * @author alraya
 */
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class FileManager {

    // إنشاء مجلد إذا لم يكن موجوداً
    public static void createFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) { // إذا المجلد مش موجود
            folder.mkdir(); // إنشاء المجلد
        }
    }

    // حذف مجلد وكل الملفات الموجودة بداخله
    public static void deleteFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists()) { // إذا المجلد موجود
            // المرور على جميع الملفات داخل المجلد
            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    // إذا كان الملف مجلد نعيد استدعاء الدالة لحذفه
                    deleteFolder(file.getAbsolutePath());
                } else {
                    // إذا كان الملف ملف عادي نزيله
                    file.delete();
                }
            }
            folder.delete(); // حذف المجلد بعد حذف الملفات داخله
        }
    }

    // حفظ محتوى نصي في ملف
    public static void saveToTextFile(String filePath, String content) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content); // كتابة المحتوى في الملف
        } catch (IOException e) {
            System.out.println("خطأ في الكتابة إلى الملف: " + filePath);
        }
    }

    // تحميل محتوى نصي من ملف وإرجاعه كقائمة من الأسطر
    public static List<String> loadFromTextFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath)); // قراءة كل الأسطر من الملف
        } catch (IOException e) {
            System.out.println("خطأ في قراءة الملف: " + filePath);
            return new ArrayList<>(); // إرجاع قائمة فارغة في حالة حدوث خطأ
        }
    }

    // عرض جميع الملفات داخل مجلد معين
    public static List<String> listFilesInFolder(String folderPath) {
        File file = new File(folderPath);
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return !new File(current, name).isDirectory(); // تصفية الملفات فقط (ليس مجلدات)
            }
        });
        return Arrays.asList(directories); // إرجاع قائمة بالملفات
    }

    // عرض جميع المجلدات داخل مجلد معين
    public static List<String> listFoldersInDirectory(String directoryPath) {
        File file = new File(directoryPath);

        if (!file.exists() || !file.isDirectory()) { // إذا كان المسار غير صحيح أو ليس مجلداً
            System.out.println("مسار المجلد غير صالح: " + directoryPath);
            return new ArrayList<>(); // إرجاع قائمة فارغة
        }

        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory(); // تصفية المجلدات فقط
            }
        });

        // التحقق من حالة وجود المجلدات
        if (directories == null) {
            System.out.println("لا توجد مجلدات أو لا يمكن الوصول إلى المجلد.");
            return new ArrayList<>();
        }

        return Arrays.asList(directories); // إرجاع قائمة بالمجلدات
    }

    // التحقق من وجود ملف في المسار المحدد
    public static boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists(); // إرجاع ما إذا كان الملف موجوداً
    }

    // تحميل محتوى الملف كـ String
    public static String loadFileAsString(String filePath) {
        try {
            return Files.readString(Path.of(filePath)); // قراءة محتوى الملف كـ String
        } catch (IOException e) {
            System.out.println("خطأ في قراءة الملف: " + filePath);
            return ""; // إرجاع سلسلة فارغة في حالة حدوث خطأ
        }
    }

    // حفظ صورة بصيغة PNG
    public static String saveAsPNG(String path, BufferedImage image) {
        try {
            // التحقق من أن الصورة ليست null
            if (image == null) {
                throw new IllegalArgumentException("الصورة فارغة (null)، لا يمكن حفظها.");
            }

            File outputFile = new File(path);
            boolean isSaved = ImageIO.write(image, "PNG", outputFile); // محاولة حفظ الصورة

            if (isSaved) {
                return outputFile.getAbsolutePath(); // إرجاع المسار إذا تم الحفظ بنجاح
            } else {
                throw new IOException("فشل في حفظ الصورة.");
            }
        } catch (IllegalArgumentException e) {
            // في حالة الصورة null
            System.out.println("خطأ: " + e.getMessage());
        } catch (IOException e) {
            // في حالة وجود مشاكل أثناء الحفظ
            System.out.println("خطأ: فشل في حفظ الصورة. " + e.getMessage());
        } catch (Exception e) {
            // في حالة وجود أخطاء غير متوقعة
            System.out.println("خطأ غير متوقع: " + e.getMessage());
        }
        return null; // في حالة فشل الحفظ، إرجاع null
    }
}
