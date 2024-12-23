/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.notetakingapp;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author 10
 */
public interface Displayable {
    default void display(String path) {
        System.out.println(path);
        JFrame frame = new JFrame("Image Viewer");
        ImageIcon imageIcon = new ImageIcon(path); // Create an image icon from the path
        JLabel label = new JLabel(imageIcon); // Create a label with the image icon
        frame.add(label); // Add label to the frame
        frame.setSize(500, 500); // Set frame size
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close
        frame.setVisible(true); // Make the frame visible
    }
}
