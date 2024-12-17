/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author alraya
 */
public class Sketch {
    private String name;
    private String path;
    private BufferedImage sketchImage;

    public Sketch(String name, String noteFolderPath) {
        // Initially, path is empty until the sketch is created and saved
        this.path = noteFolderPath + "/sketchs/" + name + ".png";
        this.name = name;
    }

    // Method to open drawing window and return the path where the sketch is saved
    public void drawSketch() {
        // Create a JFrame to open the drawing window
        JFrame drawingFrame = new JFrame("Draw Your Sketch");
        drawingFrame.setSize(500, 500);
        drawingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create an instance of the DrawingPanel
        DrawingPanel drawingPanel = new DrawingPanel();
        drawingFrame.add(drawingPanel);

        drawingFrame.setVisible(true); // Make the frame visible
        
        JButton saveButton = new JButton("Save Drawing");
        saveButton.setBounds(10, 10, 150, 30);  // Set the position and size of the button
        saveButton.addActionListener(e -> {
            // Save the sketch when the button is pressed
            sketchImage = drawingPanel.getImage();
            drawingFrame.dispose();
        });

        drawingPanel.add(saveButton);

        drawingFrame.setLayout(null);  // Disable the default layout manager
        drawingFrame.setVisible(true); // Make the frame visible

        // // Simulate waiting for the user to finish drawing (could be a button to save the drawing in real case)
        // try {
        //     Thread.sleep(10000); // Wait 10 seconds (simulate the drawing time)
        //     sketchImage = drawingPanel.getImage();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        

    }

    public void displaySketch() {
        System.out.println(path);
        JFrame frame = new JFrame("Image Viewer");
        ImageIcon imageIcon = new ImageIcon(path); // Create an image icon from the path
        JLabel label = new JLabel(imageIcon); // Create a label with the image icon
        frame.add(label); // Add label to the frame
        frame.setSize(500, 500); // Set frame size
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close
        frame.setVisible(true); // Make the frame visible
    }


    public void saveSketch() {
        FileManager.saveAsPNG(path, sketchImage);
    }

    // Getter for the sketch file path
    public String getPath() {
        return this.path;
    }

    public String getName() {
        return this.name;
    }
}
