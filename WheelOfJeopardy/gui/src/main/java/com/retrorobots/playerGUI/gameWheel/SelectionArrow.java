/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.retrorobots.playerGUI.gameWheel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

/**
 *
 * @author KeeganRiley
 */
public class SelectionArrow extends javax.swing.JPanel {
    
    private Polygon polygon_orig = null;
    private Polygon polygon = null;
    
    private int arrowWidth = 20;
    private int arrowHeight = 20;

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public int getArrowWidth() {
        return arrowWidth;
    }

    public void setArrowWidth(int arrowWidth) {
        this.arrowWidth = arrowWidth;
    }

    public int getArrowHeight() {
        return arrowHeight;
    }

    public void setArrowHeight(int arrowHeight) {
        this.arrowHeight = arrowHeight;
    }

    /**
     * Creates new form SelectionArrow
     */
    public SelectionArrow() {
        initComponents();
    }
    
    private void adjustPolygon() {
        int i;
        int xMax = Integer.MIN_VALUE, xMin = Integer.MAX_VALUE;
        int yMax = xMax, yMin = xMin;
        for (i = 0; i < polygon.xpoints.length; i++) {
            if (polygon.xpoints[i]>xMax) xMax = polygon.xpoints[i];
            if (polygon.xpoints[i]<xMin) xMin = polygon.xpoints[i];
        }
        for (i = 0; i < polygon.ypoints.length; i++) {
            if (polygon.ypoints[i]>yMax) yMax = polygon.ypoints[i];
            if (polygon.ypoints[i]<yMin) yMin = polygon.ypoints[i];
        }
        int width  = xMax - xMin;
        
        double factor = (double)this.getWidth() / width;
        for (i = 0; i < polygon.xpoints.length; i++) {
            polygon.xpoints[i] *= factor;
            polygon.ypoints[i] *= factor;
        }
        
        int centerX = 0, centerY = 0;
        for (i = 0; i < polygon.ypoints.length; i++) {
            centerX += polygon.xpoints[i];
        }
        centerX /= polygon.xpoints.length;
        for (i = 0; i < polygon.ypoints.length; i++) {
            centerY += polygon.ypoints[i];
        }
        centerY /= polygon.ypoints.length;
        polygon.translate(this.getWidth() / 2 - centerX, this.getHeight() / 2 - centerY);
    }
    
    private Polygon getTriangle() {
        Polygon polygon = new Polygon();
        polygon.addPoint(0, this.getHeight() / 2);
        polygon.addPoint(this.getWidth(), (int)(this.getHeight() / 2 - this.getWidth() * Math.tan(Math.toRadians(30))));
        polygon.addPoint(this.getWidth(), (int)(this.getHeight() / 2 + this.getWidth() * Math.tan(Math.toRadians(30))));
        return polygon;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(rh);
        
        if(polygon_orig == null) {
            polygon = getTriangle();
        } else {
            adjustPolygon();
        }
        g2d.fillPolygon(polygon);
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