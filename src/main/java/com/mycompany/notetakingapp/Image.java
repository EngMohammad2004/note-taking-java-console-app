/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;
import javax.swing.*;

/**
 *
 * @author alraya
 */
public class Image {
    private String path;

    public Image(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    // Method to display image and description
    public void displayImage() {
        System.out.println(path);
        JFrame frame = new JFrame("Image Viewer");
        ImageIcon imageIcon = new ImageIcon(path); // Create an image icon from the path
        JLabel label = new JLabel(imageIcon); // Create a label with the image icon
        frame.add(label); // Add label to the frame
        frame.setSize(500, 500); // Set frame size
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close
        frame.setVisible(true); // Make the frame visible
    }

    public String getImageName() {
        String name = this.path;
        int lastSlashIndex = name.lastIndexOf('/');
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
