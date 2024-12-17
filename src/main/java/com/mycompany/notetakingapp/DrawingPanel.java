package com.mycompany.notetakingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class DrawingPanel extends JPanel {
    private int x, y; // Coordinates for drawing
    private BufferedImage image; // The image being drawn on
    private Graphics2D g2d; // Graphics2D object to draw on the image

    public DrawingPanel() {
        setBackground(Color.WHITE); // Set the background to white

        // Initialize the image with size 500x500
        image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.setColor(Color.BLACK); // Default color for drawing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Smoothing the drawing

        // Add mouse listeners for drawing
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                // Store the coordinates when mouse is pressed
                x = evt.getX();
                y = evt.getY();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent evt) {
                // Draw a line from the previous point to the new point while dragging
                g2d.drawLine(x, y, evt.getX(), evt.getY());
                x = evt.getX();
                y = evt.getY();
                repaint(); // Redraw the panel to show the updated image
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // Draw the current image on the panel
    }

    // Method to return the image object itself (if needed elsewhere)
    public BufferedImage getImage() {
        return image;
    }
}
