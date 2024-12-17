/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;

import java.awt.image.BufferedImage;
/**
 *
 * @author alraya
 */
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class FileManager {

    // Create a folder if it does not exist
    public static void createFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    // Delete a folder and all its contents
    public static void deleteFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists()) {
            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    deleteFolder(file.getAbsolutePath());
                } else {
                    file.delete();
                }
            }
            folder.delete();
        }
    }

    // Save text content to a file
    public static void saveToTextFile(String filePath, String content) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + filePath);
        }
    }

    // Load text content from a file
    // Load text content from a file and return it as a list of lines
    public static List<String> loadFromTextFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            System.out.println("Error reading from file: " + filePath);
            return new ArrayList<>(); // Return an empty list in case of error
        }
    }

    // List all files in a folder
    public static List<String> listFilesInFolder(String folderPath) {
        File file = new File(folderPath);
        String[] directories = file.list(new FilenameFilter() {
        @Override
        public boolean accept(File current, String name) {
          return !new File(current, name).isDirectory();
        }
        });
        return Arrays.asList(directories);
    }

    // List all folders in a directory
    public static List<String> listFoldersInDirectory(String directoryPath) {
        File file = new File(directoryPath);
        
        if (!file.exists() || !file.isDirectory()) {
            System.out.println("Invalid directory path: " + directoryPath);
            return new ArrayList<>(); // Return an empty list if the directory is invalid
        }
    
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
    
        // Handle case where directories is null
        if (directories == null) {
            System.out.println("No folders found or unable to access the directory.");
            return new ArrayList<>();
        }
    
        return Arrays.asList(directories);
    }

    public static boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static String loadFileAsString(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            System.out.println("Error reading from file: " + filePath);
            return ""; // Return an empty string in case of error
        }
    }

    public static String saveAsPNG(String path, BufferedImage image) {
        try {
            // التحقق من أن الصورة مش null
            if (image == null) {
                throw new IllegalArgumentException("Image is null, cannot save.");
            }

            File outputFile = new File(path);
            boolean isSaved = ImageIO.write(image, "PNG", outputFile);

            if (isSaved) {
                return outputFile.getAbsolutePath();
            } else {
                throw new IOException("Failed to save image.");
            }
        } catch (IllegalArgumentException e) {
            // في حالة الصورة null
            System.out.println("Error: " + e.getMessage());
        } catch (IOException e) {
            // في حالة وجود مشاكل أثناء الحفظ
            System.out.println("Error: Failed to save the image. " + e.getMessage());
        } catch (Exception e) {
            // في حالة وجود أخطاء غير متوقعة
            System.out.println("Unexpected error: " + e.getMessage());
        }
        return null; // لو فشل الحفظ، نرجع null
    }
}