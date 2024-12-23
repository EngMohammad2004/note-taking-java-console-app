/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;

/**
 *
 * @author alraya
 */
/**
 * كلاس يمثل ملاحظة آمنة تحتوي على حماية بكلمة مرور.
 * يمتد من الكلاس الأساسي Note.
 */
public class SecureNote extends Note {
    private String hashedPassword; // كلمة المرور المشفرة للملاحظة

    // المُنشئ لتعيين العنوان، مسار المجلد، وكلمة المرور المشفرة
    public SecureNote(String title, String folderPath, String hashedPassword) {
        super(title, folderPath); // استدعاء مُنشئ الكلاس الأب Note
        this.hashedPassword = hashedPassword;
    }
    
    // دالة للتحقق من صحة كلمة المرور
    protected boolean verifyPassword(String password) {
        // مقارنة كلمة المرور المشفرة المُخزنة مع كلمة المرور المشفرة المُدخلة
        return this.hashedPassword.equals(SecurityUtils.hashPassword(password));
    }
    
    @Override
    public void saveNote() {
        super.saveNote(); // استدعاء الدالة الأصلية لحفظ الملاحظة
        
        // حفظ كلمة المرور المشفرة في ملف نصي
        FileManager.saveToTextFile(super.getNoteFolderPath() + "/hashPassword.txt", this.hashedPassword);
    }
}

