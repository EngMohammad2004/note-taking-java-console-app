/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author alraya
 */
public class User {
    private String username;
    private String hashedPassword;
    private String userFolder;
    private HashMap<String, Note> notes;

    public User(String username, String hashedPassword, String rootDirectory) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        System.out.println("The Root: " + rootDirectory);
        this.userFolder = rootDirectory + "/" + username;
        notes = new HashMap<String, Note>();
    }

    public String getUsername() {
        return this.username;
    }
    
    public String getUserFolder() {
        return this.userFolder;
    }
    
    public void createUserFolder() {
        FileManager.createFolder(userFolder);
        FileManager.saveToTextFile(userFolder + "/hashPassword.txt", this.hashedPassword);
    }

    protected boolean verifyPassword(String enteredPassword){
        return this.hashedPassword.equals(SecurityUtils.hashPassword(enteredPassword));
    }
    
    int counter = 1;
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

    public Note getNote(String title) {
        return notes.get(title);
    }
    
    public SecureNote getSecureNote(String title) {
        return (SecureNote) notes.get(title);
    }

    public void loadNotes() {
        List<String> noteList = FileManager.listFoldersInDirectory(userFolder);
        for(String noteTitle : noteList) {
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

    public List<String> listAllNotes() {
        List<String> noteList = new ArrayList<String>();
        
        for (String noteTitle : notes.keySet()) {
            noteList.add(noteTitle);
        }

        return noteList;
    }

    public Note deleteNote(Note deletedNote) {
        FileManager.deleteFolder(deletedNote.getNoteFolderPath());
        return notes.remove(deletedNote.getTitle());
    }

}
