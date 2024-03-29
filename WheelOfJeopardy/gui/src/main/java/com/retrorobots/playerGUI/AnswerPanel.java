/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retrorobots.playerGUI;

import com.retrorobots.ServerConnectorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author KeeganRiley
 */
public class AnswerPanel extends javax.swing.JPanel {
    private Logger LOGGER = LoggerFactory.getLogger(AnswerPanel.class);

    private PlayerWindow pw;
    private List<JButton> jbuts;

    /**
     * Creates new form AnswerPanel
     */
    public AnswerPanel() {
        this(null);
    }
    
    public AnswerPanel(PlayerWindow pw) {
        initComponents();
        this.pw = pw;
        init();
        enableAllButtons(false);  
    }
    
    public void init() {
        jbuts = new ArrayList<>();
        jbuts.add(answerOneButton);
        jbuts.add(answerTwoButton);
        jbuts.add(answerThreeButton);
    }

    public void updateAnswers(String correctAns, JSONArray incorrectAns) {
        Random rand = new Random();
        int pos = rand.nextInt(jbuts.size());
        JButton correctButton = jbuts.get(pos);
        correctButton.setText(correctAns);

        JButton wrongButton1 = pos-1 >= 0 ? jbuts.get(pos-1) : jbuts.get(jbuts.size()-1);
        JButton wrongButton2 = pos+1 <= jbuts.size()-1 ? jbuts.get(pos+1) : jbuts.get(0);

        wrongButton1.setText(incorrectAns.getString(0));
        wrongButton2.setText(incorrectAns.getString(1));
    }

    public void enableAnswer() {
        enableAllButtons(true);
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

        answerOneButton = new javax.swing.JButton();
        answerTwoButton = new javax.swing.JButton();
        answerThreeButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        answerOneButton.setText("Answer 1");
        answerOneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answerOneButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        add(answerOneButton, gridBagConstraints);

        answerTwoButton.setText("jButton1");
        answerTwoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answerTwoButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        add(answerTwoButton, gridBagConstraints);

        answerThreeButton.setText("jButton2");
        answerThreeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answerThreeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        add(answerThreeButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void answerOneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answerOneButtonActionPerformed

        String data = this.answerOneButton.getText();
        sendAnswer(data);

    }//GEN-LAST:event_answerOneButtonActionPerformed

    private void answerTwoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answerTwoButtonActionPerformed
        String data = this.answerTwoButton.getText();
        sendAnswer(data);
    }//GEN-LAST:event_answerTwoButtonActionPerformed

    private void answerThreeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answerThreeButtonActionPerformed
        
        String data = this.answerThreeButton.getText();
        sendAnswer(data);
    }//GEN-LAST:event_answerThreeButtonActionPerformed

    
    
    private void sendAnswer(String data) {
        String returnData = ServerConnectorFactory.queryServer(ServerConnectorFactory.VERIFY_ANSWER_PATH+"?ans="+data);
        JSONObject anRes = new JSONObject(returnData);
        boolean correct = anRes.getBoolean("result");

        JSONObject game = anRes.getJSONObject("game");
        JSONArray players = game.getJSONArray("playerList");

        int spinsLeft = game.getInt("spinCount");
        int quesLeft = game.getInt("quesCount");

        if (spinsLeft <= 0 || quesLeft <= 0) {
            String end = ServerConnectorFactory.queryServer("/checkRound");
            enableAllButtons(false);
            if (end.equalsIgnoreCase("end of Game")) {
                this.pw.endGame(game);
                return;
            }
            this.pw.endRound();
            return;
        }

        String hasFreeToken = ServerConnectorFactory.queryServer(ServerConnectorFactory.CHECK_PLAYER_FREE_TOKEN);

        if (correct) {
            this.pw.spinAgain();
        } else {
            int choice = 1;
            if (Boolean.parseBoolean(hasFreeToken)) {
                choice = JOptionPane.showConfirmDialog(this, "Would you like to use your Free Turn Token now?");
            }
            if (choice == JOptionPane.YES_OPTION) {
                this.pw.spinAgain();
                ServerConnectorFactory.queryServer(ServerConnectorFactory.USE_FREE_TOKEN);
            } else {
                LOGGER.info("Choice: {}", choice);
                returnData = ServerConnectorFactory.queryServer(ServerConnectorFactory.LOSE_TURN_PATH);
                game = new JSONObject(returnData);
                players = game.getJSONArray("playerList");

                enableAllButtons(false);
                JSONObject currP = game.getJSONObject("currPlayer");
                this.pw.switchPlayers(currP.getString("name"));
            }
        }
        this.pw.updatePlayers(players);
        this.pw.updateGame(game);
    }
    
    private void enableAllButtons(boolean enable) {
        this.answerOneButton.setEnabled(enable);
        this.answerTwoButton.setEnabled(enable);
        this.answerThreeButton.setEnabled(enable);
    }
    
    private void resetButtons() {
        this.answerOneButton.setText("Answer 1");
        this.answerTwoButton.setText("Answer 2");
        this.answerThreeButton.setText("Answer 3");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton answerOneButton;
    private javax.swing.JButton answerThreeButton;
    private javax.swing.JButton answerTwoButton;
    // End of variables declaration//GEN-END:variables
}
