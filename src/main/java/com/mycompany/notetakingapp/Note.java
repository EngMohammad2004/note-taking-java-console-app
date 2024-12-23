/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;
import java.util.ArrayList;
import java.util.List;

/**
 * كلاس يمثل ملاحظة تحتوي على عنوان، محتوى، صور، ورسومات.
 *
 * @author alraya
 */
public class Note {
    private String title, content, noteFolderPath; // عنوان الملاحظة، محتواها، ومسار المجلد الخاص بها
    private List<Image> images = new ArrayList<>(); // قائمة بالصور المرتبطة بالملاحظة
    private List<Sketch> sketchs = new ArrayList<>(); // قائمة بالرسومات المرتبطة بالملاحظة
    
    // المُنشئ لتعيين العنوان ومسار المجلد الخاص بالملاحظة
    public Note(String title, String noteFolderPath) {
        this.title = title;
        this.noteFolderPath = noteFolderPath;
        this.content = ""; // المحتوى الافتراضي فارغ
    }

    // دوال getter للحصول على قيم الخصائص
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getNoteFolderPath() {
        return noteFolderPath;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<Sketch> getSketchs() {
        return sketchs;
    }

    // دوال setter لتعيين قيم الخصائص
    public void setContent(String content) {
        this.content = content;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setSketch(List<Sketch> sketchs) {
        this.sketchs = sketchs;
    }
    
    // إضافة صورة جديدة إلى الملاحظة
    public void addImage(String path) {
        Image image = new Image(path); // إنشاء كائن صورة باستخدام المسار
        images.add(image); // إضافة الصورة إلى القائمة
    }
    
    // إضافة رسم جديد إلى الملاحظة
    public void addSketch(String name) {
        Sketch sketch = new Sketch(name, noteFolderPath); // إنشاء كائن رسم
        sketch.drawSketch(); // استدعاء الدالة لرسم التخطيط
        sketchs.add(sketch); // إضافة الرسم إلى القائمة
    }
    
    // حفظ الملاحظة إلى ملفات
    public void saveNote() {
        // إذا لم يكن المجلد موجودًا، يتم إنشاؤه
        if (!FileManager.isFileExists(noteFolderPath)) {
            FileManager.createFolder(noteFolderPath); // إنشاء المجلد الأساسي
            FileManager.createFolder(noteFolderPath + "/sketchs"); // إنشاء مجلد الرسومات
        }
        
        // حفظ المحتوى في ملف نصي
        FileManager.saveToTextFile(noteFolderPath + "/content.txt", content);
        
        // حفظ مسارات الصور في ملف نصي
        String imagesString = "";
        for (Image image : this.images) {
            imagesString += image.getPath() + "\n";
        }
        FileManager.saveToTextFile(noteFolderPath + "/images.txt", imagesString);
        
        // حفظ الرسومات
        for (Sketch sketch : this.sketchs) {
            sketch.saveSketch();
        }
    }
    
    // تحميل الملاحظة من الملفات
    public void loadNote() {
        try {
            this.content = FileManager.loadFileAsString(noteFolderPath + "/content.txt"); // تحميل المحتوى
            loadImages(noteFolderPath + "/images.txt"); // تحميل الصور
            loadSketchs(); // تحميل الرسومات
            
        } catch (Exception e) {
            System.out.println("Error Loading Note: " + e);
        }
    }
    
    // تحميل الصور من ملف النص
    public void loadImages(String imagePath) {
        try {
            List<String> lines = FileManager.loadFromTextFile(imagePath); // قراءة المسارات من الملف النصي
            
            for (String line : lines) {
                images.add(new Image(line)); // إضافة كل صورة إلى القائمة
            }
        } catch (Exception e) {
            System.out.println("Error Loading Images: " + e);
        }
    }
    
    // تحميل الرسومات من المجلد
    public void loadSketchs() {
        try {
            List<String> sketchList = FileManager.listFilesInFolder(noteFolderPath + "/sketchs"); // قراءة أسماء الملفات في مجلد الرسومات
            for (String sketch : sketchList) {
                String name = sketch.replace(".png", ""); // إزالة الامتداد من اسم الملف
                sketchs.add(new Sketch(name, noteFolderPath)); // إضافة الرسم إلى القائمة
            }
        } catch (Exception e) {
            System.out.println("Error Loading Sketchs: " + e);
        }
    }
}
