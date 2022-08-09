/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.retrorobots.playerGUI.gameBoard;

import org.json.JSONObject;

import java.awt.*;

/**
 *
 * @author KeeganRiley
 */
public class ValueCard extends javax.swing.JPanel {

    private int value;
    private boolean answered;

    /**
     * Creates new form ValueCard
     */
    public ValueCard() {
        this(null);
    }

    public ValueCard(JSONObject obj) {
        setPreferredSize(new Dimension(100, 100));
        initComponents();
        this.answered = false;
        init(obj);
    }

    private void init(JSONObject obj) {
        this.jLabel1.setText(obj.getString("value"));
        this.jLabel1.revalidate();
        if (!this.answered) {
            setBackground(Color.GREEN);
        }
    }

    public void setAnswered(boolean ans) {
        this.answered = ans;
        if (this.answered) {
            setBackground(Color.RED);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("number");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
