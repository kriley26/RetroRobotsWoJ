/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.retrorobots.playerGUI.gameWheel;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author KeeganRiley
 */
public class Wheel extends javax.swing.JPanel {
    
    public static enum Shape {
        CIRCLE
    }
    
    Image image = null;
    private boolean hasBorders = false;
    private double delta;
    private Point2D imagePosition;
    private Point2D rotationCenter;
    private double rotationAngle = 0;
    private double zoomFactor = 1;
    
    private List<Color> colors;
    private int colorCounter = 0;
    
    private Shape shape = Shape.CIRCLE;
    private final int BORDER = 10;
    private int radius;
    private Point2D center = new Point2D.Double();
    
    private List<String> stringList;
    private int noElem;
    private final int LIMIT = 100;
    private final int MAXFONTSIZE = 80, MINFONTSIZE = 10;
    private final Font DEFAULTFONT = new Font("TimesRoman", Font.PLAIN, 12);
    private Font font = DEFAULTFONT;
    
    private boolean spinOnOff = false;
    private double spinSpeed = 0;
    private double maxSpinSpeed = 360;
    private double spinDeceleration = -20;
    private Timer speedTimer;
    private long timeStart, timeEnd;
    private double rotationAngleStart, rotationAngleEnd;
    private int refreshRate = 100;
    private Point2D mouseDragPosition;
    
    

    /**
     * Creates new form Wheel
     */
    public Wheel() {
        initComponents();
    }
    
    public Wheel(List<String> list) throws Exception {
        this();
        setListOfStrings(list);
        
        TimerTask timerTask = new speedTimerTask();
        speedTimer = new Timer(true);
        speedTimer.schedule(timerTask, 0);
    }
    
    @Override
    public void setBounds(int x, int y, int width, int height) {
        image = null;
        super.setBounds(x, y, width, height);
    }
    
    public void hasBorders(boolean borders) {
        hasBorders = borders;
        image = null;
        spinOnOff = false;
        setRotationAngle(0);
        this.repaint();
    }
    
    public void setShape(Shape shape) {
        this.shape = shape;
        this.image = null;
        this.spinOnOff = false;
        setRotationAngle(0);
        this.repaint();
    }
    
    public double getRotationAngle() {
        return rotationAngle;
    }
    
    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle % 360;
        this.repaint();
    }
    
    public List<Color> getColorScheme() {
        return this.colors;
    }
    
    public void setColorScheme(List<Color> colors) {
        this.colors = colors;
        this.image = null;
        this.spinOnOff = false;
        setRotationAngle(0);
        this.repaint();
    }
    
    public void addColor(Color color) {
        if (this.colors == null)
            this.colors = new ArrayList<Color>();
        this.colors.add(color);
        this.image = null;
        this.spinOnOff = false;
        setRotationAngle(0);
        this.repaint();
    }
    
    public int getRadius() {
        return this.radius;
    }
    
    public List<String> getListOfStrings() {
        return this.stringList;
    }
    
    public void setListOfStrings(List<String> list) throws Exception {
        this.noElem = list.size();
        if (this.noElem > LIMIT)
            throw new Exception("String list is larger than limit (" + LIMIT + ")");
        this.delta = (double)360 / (double)this.noElem;
        this.stringList = list;
        this.image = null;
        this.spinOnOff = false;
        setRotationAngle(0);
        this.repaint();
    }
    
    @Override
    public Font getFont() {
        return this.font;
    }
    
    @Override
    public void setFont(Font font) {
        super.setFont(font);
        this.font = font;
        this.image = null;
        this.spinOnOff = false;
        setRotationAngle(0);
        this.repaint();
    }
    
    public double getSpinSpeed() {
        return spinOnOff ? spinSpeed : 0;
    }
    
    public double getMaxSpinSpeed() {
        return maxSpinSpeed;
    }
    
    public void setMaxSpinSpeed(double speed) {
        this.spinOnOff = false;
        this.maxSpinSpeed = speed;
    }
    
    public double getSpinDeceleration() {
        return spinDeceleration;
    }
    
    public void setSpinDeceleration(double deceleration) throws Exception {
        if (deceleration > 0)
            throw new Exception("Illegal parameter value: acceleration must be < 0");
        spinDeceleration = deceleration;
    }
    
    public boolean isSpinning() {
        return spinOnOff;
    }
    
    public String getSelectedString() {
        return stringList.get((int)Math.floor(noElem + (rotationAngle % 360) / delta) % noElem);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (image == null) {
            image = drawImage();
            rotationCenter = new Point2D.Double(this.getWidth() - image.getWidth(null) + center.getX(), this.getHeight() / 2);
            imagePosition = new Point2D.Double((int)(this.getWidth() - image.getWidth(null)), (int)(this.getHeight() / 2 - center.getY()));
        }
        
        Graphics2D gPanel = (Graphics2D) g;
        gPanel.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gPanel.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        gPanel.rotate(Math.toRadians(rotationAngle), rotationCenter.getX(), rotationCenter.getY());
        gPanel.drawImage(image, (int)imagePosition.getX(), (int)imagePosition.getY(), null);
    }
    
    private BufferedImage drawImage() {
        int width = this.getWidth(), height = this.getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        
        radius = Math.min(img.getWidth(), img.getHeight()) / 2 - BORDER;
        
        double stringDistanceFromEdge = 0.05 * radius;
        int fontSize, stringWidth, maxStringWidth;
        
        maxStringWidth = (int)(radius - 2 * stringDistanceFromEdge);
        fontSize = calcFontSize(g2d, stringDistanceFromEdge, maxStringWidth);
        g2d.setFont(new Font(font.getFamily(), font.getStyle(), fontSize));
        
        if(fontSize < MINFONTSIZE) {
            zoomFactor = (double)MINFONTSIZE / fontSize;
            width += (int) 2 * ((zoomFactor * radius) - radius);
            height += (int) 2 * ((zoomFactor * radius) - radius);
            radius = (int)(zoomFactor * radius);
            img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            g2d = (Graphics2D)img.getGraphics();
            maxStringWidth = (int)(radius - 2 * stringDistanceFromEdge);
            fontSize = calcFontSize(g2d, stringDistanceFromEdge, maxStringWidth);
        }
        
        center = new Point2D.Double((double)img.getWidth() / 2, (double)img.getHeight() / 2);
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.rotate(Math.toRadians(rotationAngle), center.getX(), center.getY());
        
        if (hasBorders) {
            g2d.setColor(Color.BLACK);
            g2d.fillArc((int)center.getX() - (int)Math.floor(Math.max(0.01*radius, 1)), 
                    (int)center.getY() - (int)Math.floor(Math.max(0.01*radius, 1)), 
                    (int)Math.floor(Math.max(0.01 * 2 *radius, radius)), 
                    (int)Math.floor(Math.max(0.01 * 2 * radius, 2)), 0, 360);

            
        }            
        
        FontMetrics fontMetrics;
        if (colors == null)
            colors = getDefaultColorList();
        colorCounter = 0;
        for (int i = noElem - 1; i >= 0; i--) {
            if (hasBorders) {
                g2d.setColor(Color.BLACK);
                g2d.drawLine((int)center.getX(), (int)center.getY(), (int)center.getX() + radius, (int)center.getY());   
            }
                
            g2d.setColor(colors.get(colorCounter++ % colors.size()));
            if (shape != null)
                fillArc(g2d);

            g2d.rotate(Math.toRadians(delta/2), center.getX(), center.getY());
            g2d.setColor(Color.BLACK);
            fontMetrics = g2d.getFontMetrics();
            stringWidth = fontMetrics.stringWidth(stringList.get(i));
            g2d.drawString(stringList.get(i), (int)(center.getX() + maxStringWidth - stringWidth + stringDistanceFromEdge), (int)(center.getY() + (double)fontMetrics.getHeight() / 2 - fontMetrics.getMaxDescent()));
            g2d.rotate(Math.toRadians(delta / 2), center.getX(), center.getY());
        }
        
        return img;
    }
    
    private int calcFontSize(Graphics g, double stringDistanceFromEdge, int maxStringWidth) {
        String tmpString = "";
        for (int i = noElem - 1; i >= 0; i--) {
            if (stringList.get(i).length() > tmpString.length())
                tmpString = stringList.get(i);
        }
        
        int fontSize = MAXFONTSIZE;
        g.setFont(new Font(font.getFamily(), font.getStyle(), fontSize));
        FontMetrics fontMetrics = g.getFontMetrics();
        Rectangle2D stringBounds = fontMetrics.getStringBounds(tmpString, g);
        
        int maxHeight = (int)Math.floor(2 * stringDistanceFromEdge * Math.sin(Math.toRadians(delta / 2)));
        
        if (stringBounds.getHeight() > maxHeight){
            fontSize = (int) Math.floor(fontSize * maxHeight / stringBounds.getHeight());
            g.setFont(new Font(font.getFamily(), font.getStyle(), fontSize));
            fontMetrics = g.getFontMetrics();
            stringBounds = fontMetrics.getStringBounds(tmpString, g);
        }
        
        double K = stringBounds.getWidth()  / stringBounds.getHeight();
        maxHeight = (int)Math.floor(2 * (radius - stringDistanceFromEdge) * Math.tan(Math.toRadians(delta / 2)) / (1 + 2 * K * Math.tan(Math.toRadians(delta / 2))));
        while (stringBounds.getWidth() > maxStringWidth) {
            g.setFont(new Font(font.getFamily(), font.getStyle(), ++fontSize));
            fontMetrics = g.getFontMetrics();
            stringBounds = fontMetrics.getStringBounds(tmpString, g);
        }
        
        while(stringBounds.getWidth() > maxStringWidth) {
            g.setFont(new Font(font.getFamily(), font.getStyle(), --fontSize));
            fontMetrics = g.getFontMetrics();
            stringBounds = fontMetrics.getStringBounds(tmpString, g);
        }
        
        return Math.min(fontSize, MAXFONTSIZE);
    }
    
    private void fillArc(Graphics g2d) {
        g2d.fillArc((int)center.getX() - radius, (int)center.getY()- radius, 2 * radius, 2 * radius, 0, (int)-Math.ceil(delta));
        if (hasBorders) {
            g2d.setColor(Color.BLACK);
            g2d.drawArc((int)center.getX() - radius, (int)center.getY() - radius, 2 * radius, 2 * radius, 0, (int)-Math.ceil(delta));
        }
    }
    
    private class SpinRunnable implements Runnable {
        
        private double spinSpeed;
        private int spinDirection;
        private double spinDeceleration;
        
        public SpinRunnable(double speed, int direction, double deceleration) {
            this.spinSpeed = speed;
            this.spinDirection = direction;
            this.spinDeceleration = deceleration;
        }
        
        public void run() {
            
            spinOnOff = true;
            int sleepTime = 1000 / refreshRate;
            double srDelta;
            while (spinOnOff && spinSpeed > 0) {
                srDelta = spinDirection * (spinSpeed / refreshRate);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setRotationAngle(getRotationAngle() + srDelta);
                spinSpeed += spinDeceleration / refreshRate;
            }
            spinOnOff = false;
            
        }
    }
    
    public void spinStartAsync(double speed, int direction, double deceleration) throws Exception {
        
        if (deceleration > 0)
            throw new Exception("Illegal parameter value: acceleration must be < 0");
        SpinRunnable spinRunnable = new SpinRunnable(speed, direction, deceleration);
        Thread t = new Thread(spinRunnable);
        t.start();
    }
    
    public void spinStop() {
        spinOnOff = false;
    }
    
    public class speedTimerTask extends TimerTask {
        @Override
        public void run() {
            double prevAngle, nowAngle;
            long sleepTime = 100;
            while(true) {
                prevAngle = getRotationAngle();
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                nowAngle = getRotationAngle();
                nowAngle = (nowAngle + Math.signum(nowAngle) * 360) % 360;
                spinSpeed = Math.abs(nowAngle - prevAngle) * (1000 / sleepTime);
            }
        }
    }
    
    private List<Color> getDefaultColorList() {
        List<Color> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.DARK_GRAY);
        colors.add(Color.GRAY);
        colors.add(Color.GREEN);
        colors.add(Color.LIGHT_GRAY);
        colors.add(Color.MAGENTA);
        colors.add(Color.ORANGE);
        colors.add(Color.PINK);
        colors.add(Color.RED);
        colors.add(Color.WHITE);
        colors.add(Color.YELLOW);
        return colors;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
