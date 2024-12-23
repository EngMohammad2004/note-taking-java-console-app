/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;
import java.util.HashMap;
import java.util.List;

/**
 * كلاس مسؤول عن إدارة المستخدمين في النظام.
 * يتضمن إنشاء المستخدمين، تسجيل الدخول، وتحميل بيانات المستخدمين.
 */
public class UserManager {
    private String rootDirectory; // المسار الجذر لجميع المستخدمين
    private HashMap<String, User> users; // خريطة تحتوي على المستخدمين بأسمائهم

    /**
     * المُنشئ لإنشاء مدير المستخدمين.
     *
     * @param rootDirectory المسار الجذر للمجلد الرئيسي.
     */
    public UserManager(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        users = new HashMap<>();
    }

    /**
     * إنشاء مستخدم جديد.
     *
     * @param username اسم المستخدم الجديد.
     * @param password كلمة المرور.
     * @return true إذا تم إنشاء المستخدم بنجاح، وإلا false.
     */
    public boolean createUser(String username, String password) {
        // تحقق إذا كان اسم المستخدم موجودًا مسبقًا والمسار الجذر موجود.
        if (!users.containsKey(username) && FileManager.isFileExists(rootDirectory)) {
            String hashedPassword = SecurityUtils.hashPassword(password);
            User newUser = new User(username, hashedPassword, rootDirectory);
            users.put(username, newUser); // إضافة المستخدم إلى القائمة
            newUser.createUserFolder(); // إنشاء المجلد الخاص بالمستخدم
            return true;
        } else {
            return false; // إرجاع false إذا كان اسم المستخدم موجودًا مسبقًا.
        }
    }

    /**
     * استرجاع مستخدم بناءً على اسم المستخدم.
     *
     * @param username اسم المستخدم.
     * @return كائن المستخدم إذا كان موجودًا، وإلا null.
     */
    public User getUser(String username) {
        return users.get(username);
    }

    /**
     * تحميل بيانات جميع المستخدمين من المجلد الجذر.
     */
    public void loadUsers() {
        // الحصول على قائمة المجلدات الخاصة بالمستخدمين.
        List<String> usersFolder = FileManager.listFoldersInDirectory(rootDirectory);
        for (String username : usersFolder) {
            String hashedPassword = FileManager.loadFromTextFile(rootDirectory + "/" + username + "/hashPassword.txt").get(0);
            User user = new User(username, hashedPassword, rootDirectory);
            users.put(username, user); // إضافة المستخدم إلى الخريطة
            user.loadNotes(); // تحميل ملاحظات المستخدم
        }
    }

    /**
     * التحقق من بيانات تسجيل الدخول.
     *
     * @param enteredUsername اسم المستخدم المُدخل.
     * @param enteredPassword كلمة المرور المُدخلة.
     * @return true إذا كانت البيانات صحيحة، وإلا false.
     */
    public boolean login(String enteredUsername, String enteredPassword) {
        // تحقق إذا كان اسم المستخدم موجودًا.
        if (!users.containsKey(enteredUsername))
            return false;
        else {
            // تحقق من كلمة المرور.
            return users.get(enteredUsername).verifyPassword(enteredPassword);
        }
    }
}
