/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;

/**
 *
 * @author alraya
 */
public class SecureNote extends Note {
    private String hashedPassword;

    public SecureNote(String title, String folderPath, String hashedPassword) {
        super(title, folderPath);
        this.hashedPassword = hashedPassword;
    }
    
    protected boolean verifyPassword(String password){
        return this.hashedPassword.equals(SecurityUtils.hashPassword(password));
    }
    
    @Override
    public void saveNote() {
        super.saveNote();
        
        FileManager.saveToTextFile(super.getNoteFolderPath() + "/hashPassword.txt", this.hashedPassword);
    }
    
    
}
