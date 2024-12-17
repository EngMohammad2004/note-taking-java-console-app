/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notetakingapp;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author alraya
 */
public class Note {
    private String title, content, noteFolderPath;
    private List<Image> images = new ArrayList<>();
    private List<Sketch> sketchs = new ArrayList<>();
    
    public Note(String title, String noteFolderPath) {
        this.title = title;
        this.noteFolderPath = noteFolderPath;

        this.content = "";
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getNoteFolderPath() {
        return noteFolderPath;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<Sketch> getSketchs() {
        return sketchs;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setSketch(List<Sketch> sketchs) {
        this.sketchs = sketchs;
    }
    
    public void addImage(String path) {
        Image image = new Image(path);
        images.addLast(image);
    }
    
    public void addSketch(String name) {
        Sketch sketch = new Sketch(name, noteFolderPath);
        sketch.drawSketch();
        sketchs.addLast(sketch);
    }
    
    public void saveNote(){
        if (!FileManager.isFileExists(noteFolderPath)) {
            FileManager.createFolder(noteFolderPath);
            FileManager.createFolder(noteFolderPath + "/sketchs");
        }
        
        FileManager.saveToTextFile(noteFolderPath + "/content.txt", content);
        
        String imagesString = "";
        for(Image image : this.images) {
            imagesString += image.getPath() + "\n";
        }
        FileManager.saveToTextFile(noteFolderPath + "/images.txt", imagesString);
        
        for(Sketch sketch : this.sketchs) {
            sketch.saveSketch();
        }
        
    }
    
    public void loadNote() {
        try {
            this.content = (FileManager.loadFileAsString(noteFolderPath + "/content.txt"));
            loadImages(noteFolderPath + "/images.txt");
            loadSketchs();
            
        } catch (Exception e) {
            System.out.println("Error Loading Note: " + e);
        }
    }
    
    public void loadImages(String imagePath) {
        try {
            List<String> lines = FileManager.loadFromTextFile(imagePath);
            
            for(String line : lines) {
                images.add(new Image(line));
            }
        } catch (Exception e) {
            System.out.println("Error Loading Images: " + e);
        }
    }
    
    public void loadSketchs() {
        try {
            List<String> sketchList = FileManager.listFilesInFolder(noteFolderPath + "/sketchs");
            for(String sketch : sketchList) {
                String name = sketch.replace(".png", "");
                sketchs.add(new Sketch(name, noteFolderPath));
            }
        } catch (Exception e) {
            System.out.println("Error Loading Sketchs: " + e);
        }
    }
    
    
}
