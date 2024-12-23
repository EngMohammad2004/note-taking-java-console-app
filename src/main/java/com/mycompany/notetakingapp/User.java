/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * كلاس يمثل المستخدم، يحتوي على معلومات المستخدم وملاحظاته.
 */
public class User {
    private String username; // اسم المستخدم
    private String hashedPassword; // كلمة المرور المشتقة (hashed)
    private String userFolder; // مسار المجلد الخاص بالمستخدم
    private HashMap<String, Note> notes; // خريطة تحتوي على الملاحظات بعنوانها

    /**
     * المُنشئ لإنشاء كائن User.
     *
     * @param username اسم المستخدم.
     * @param hashedPassword كلمة المرور المشتقة.
     * @param rootDirectory المسار الجذر للمجلدات.
     */
    public User(String username, String hashedPassword, String rootDirectory) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        System.out.println("The Root: " + rootDirectory);
        this.userFolder = rootDirectory + "/" + username;
        notes = new HashMap<String, Note>();
    }

    /**
     * استرجاع اسم المستخدم.
     *
     * @return اسم المستخدم.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * استرجاع مسار مجلد المستخدم.
     *
     * @return مسار المجلد.
     */
    public String getUserFolder() {
        return this.userFolder;
    }

    /**
     * إنشاء مجلد خاص بالمستخدم.
     */
    public void createUserFolder() {
        FileManager.createFolder(userFolder); // إنشاء مجلد المستخدم
        FileManager.saveToTextFile(userFolder + "/hashPassword.txt", this.hashedPassword); // حفظ كلمة المرور المشتقة
    }

    /**
     * التحقق من كلمة المرور المدخلة.
     *
     * @param enteredPassword كلمة المرور المدخلة.
     * @return true إذا كانت كلمة المرور صحيحة، وإلا false.
     */
    protected boolean verifyPassword(String enteredPassword) {
        return this.hashedPassword.equals(SecurityUtils.hashPassword(enteredPassword));
    }

    private int counter = 1; // عداد لتجنب تكرار عناوين الملاحظات

    /**
     * إنشاء ملاحظة جديدة.
     *
     * @param title عنوان الملاحظة.
     * @return الملاحظة الجديدة.
     */
    public Note createNote(String title) {
        String newTitle = title;
        while (notes.containsKey(newTitle)) {
            newTitle = title + " " + counter;
            counter++;
        }
        Note newNote = new Note(newTitle, userFolder + "/" + newTitle);
        notes.put(newTitle, newNote);
        newNote.saveNote();
        return newNote;
    }

    /**
     * إنشاء ملاحظة آمنة جديدة.
     *
     * @param title عنوان الملاحظة.
     * @param password كلمة المرور للملاحظة.
     * @return الملاحظة الآمنة الجديدة.
     */
    public SecureNote createSecureNote(String title, String password) {
        String newTitle = title;
        while (notes.containsKey(newTitle)) {
            newTitle = title + " " + counter;
            counter++;
        }
        String hashedPassword = SecurityUtils.hashPassword(password);
        SecureNote newNote = new SecureNote(newTitle, userFolder + "/" + newTitle, hashedPassword);
        notes.put(newTitle, newNote);
        newNote.saveNote();
        return newNote;
    }

    /**
     * استرجاع ملاحظة غير آمنة بناءً على العنوان.
     *
     * @param title عنوان الملاحظة.
     * @return الملاحظة إذا كانت موجودة، وإلا null.
     */
    public Note getNote(String title) {
        return notes.get(title);
    }

    /**
     * استرجاع ملاحظة آمنة بناءً على العنوان.
     *
     * @param title عنوان الملاحظة.
     * @return الملاحظة الآمنة إذا كانت موجودة، وإلا null.
     */
    public SecureNote getSecureNote(String title) {
        return (SecureNote) notes.get(title);
    }

    /**
     * تحميل الملاحظات من مجلد المستخدم.
     */
    public void loadNotes() {
        List<String> noteList = FileManager.listFoldersInDirectory(userFolder);
        for (String noteTitle : noteList) {
            String noteFolderPath = userFolder + "/" + noteTitle;
            if (FileManager.isFileExists(noteFolderPath + "/hashPassword.txt")) {
                String password = FileManager.loadFromTextFile(noteFolderPath + "/hashPassword.txt").get(0);
                String hashedPassword = SecurityUtils.hashPassword(password);
                notes.put(noteTitle, new SecureNote(noteTitle, noteFolderPath, hashedPassword));
            } else {
                notes.put(noteTitle, new Note(noteTitle, userFolder + "/" + noteTitle));
            }
            notes.get(noteTitle).loadNote();
        }
    }

    /**
     * استرجاع قائمة تحتوي على عناوين جميع الملاحظات.
     *
     * @return قائمة عناوين الملاحظات.
     */
    public List<String> listAllNotes() {
        return new ArrayList<>(notes.keySet());
    }

    /**
     * حذف ملاحظة من النظام.
     *
     * @param deletedNote الملاحظة المراد حذفها.
     * @return الملاحظة التي تم حذفها.
     */
    public Note deleteNote(Note deletedNote) {
        FileManager.deleteFolder(deletedNote.getNoteFolderPath());
        return notes.remove(deletedNote.getTitle());
    }
}
