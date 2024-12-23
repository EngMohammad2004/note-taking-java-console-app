/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;

/**
 *
 * @author alraya
 */
public class Image implements Displayable{
    private final String path;

    public Image(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    // Method to display image
    public void displayImage() {
        display(path);
    }

    public String getImageName() {
        String name = this.path;
        // Handle both forward and backward slashes
        int lastSlashIndex = Math.max(name.lastIndexOf('/'), name.lastIndexOf('\\'));
        if (lastSlashIndex != -1) {
            name = name.substring(lastSlashIndex + 1);
        }

        int dotIndex = name.lastIndexOf('.');
        if (dotIndex != -1) {
            name = name.substring(0, dotIndex);
        }

        return name;
    }

}
