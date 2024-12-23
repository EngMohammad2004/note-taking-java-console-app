package com.mycompany.notetakingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class DrawingPanel extends JPanel {
    private int x, y; // إحداثيات الرسم
    private BufferedImage image; // الصورة التي يتم الرسم عليها
    private Graphics2D g2d; // كائن للرسم ثنائي الأبعاد على الصورة

    public DrawingPanel() {
        setBackground(Color.WHITE); // ضبط خلفية اللوحة باللون الأبيض

        // تهيئة الصورة بحجم 1920x1080
        image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.setColor(Color.BLACK); // اللون الافتراضي للرسم
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // تحسين جودة الرسم

        // إضافة مستمع للنقر بالماوس لتحديد نقطة البداية
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                x = evt.getX(); // حفظ إحداثيات النقطة عند الضغط بالماوس
                y = evt.getY();
            }
        });

        // إضافة مستمع للسحب بالماوس للرسم على اللوحة
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent evt) {
                g2d.drawLine(x, y, evt.getX(), evt.getY()); // رسم خط بين النقطة السابقة والنقطة الجديدة
                x = evt.getX(); // تحديث الإحداثيات للنقطة الجديدة
                y = evt.getY();
                repaint(); // إعادة رسم اللوحة لتحديث الصورة
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // استدعاء الدالة الأصلية لرسم العناصر الافتراضية
        g.drawImage(image, 0, 0, null); // رسم الصورة الحالية على اللوحة
    }

    // دالة لإرجاع الكائن الخاص بالصورة (إذا تم الاحتياج له في مكان آخر)
    public BufferedImage getImage() {
        return image;
    }
}
