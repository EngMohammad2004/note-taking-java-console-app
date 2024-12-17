/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author alraya
 */
public class UserManager {
    private String rootDirectory;
    private HashMap<String, User> users;
    
    public UserManager(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        users = new HashMap<String, User>();
    }
    
    public boolean createUser(String username, String password) {
        if(!users.containsKey(username) && FileManager.isFileExists(rootDirectory)) {
            String hashedPassword = SecurityUtils.hashPassword(password);
            User newUser = new User(username, hashedPassword, rootDirectory);
            users.put(username, newUser);
            newUser.createUserFolder();
            return true;
        } else {
            return false;
        }
    }
    
    public User getUser(String username) {
        return users.get(username);
    }
    
    public void loadUsers() {
        List<String> usersFolder = FileManager.listFoldersInDirectory(rootDirectory);
        for(String username : usersFolder) {
            String hashedPassword = FileManager.loadFromTextFile(rootDirectory + "/" + username + "/hashPassword.txt").get(0);
            users.put(username, new User(username, hashedPassword, rootDirectory));
            users.get(username).loadNotes();
        }
    }
    
    public boolean login(String enteredUsername, String enteredPassword) {
        if(!users.containsKey(enteredUsername))
            return false;
        else {
            return users.get(enteredUsername).verifyPassword(enteredPassword);
        }
    }

}
