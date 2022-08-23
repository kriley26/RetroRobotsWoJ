/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retrorobots.mainGameGUI;

import com.retrorobots.ServerConnectorFactory;
import com.retrorobots.playerGUI.AnswerPanel;
import com.retrorobots.playerGUI.PlayerWindow;
import com.retrorobots.playerGUI.QuestionPanel;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author KeeganRiley
 */
public class MainWindow extends javax.swing.JFrame {

    private StartGamePanel sgp;
    private CurrentGameStats cgs;

    /**
     * Creates new form MainFrame
     */
    public MainWindow() {
        setPreferredSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        initComponents();
        init();
        setVisible(true);
    }
    
    private void init() {
        this.sgp = new StartGamePanel(this);
        this.setupInterior.add(sgp);
        this.setupInterior.revalidate();
        
        this.cgs = new CurrentGameStats(this);
        this.currentGameInterior.add(cgs);
        this.currentGameInterior.revalidate();
    }

    public void updateGame(JSONObject game) {
        this.cgs.updateGame(game);
        JSONArray players = game.getJSONArray("playerList");
        List<JSONObject> list = new ArrayList<>();
        for(int i = 0; i < players.length(); i++) {
            JSONObject jo = players.getJSONObject(i);
            list.add(jo);
        }
        updateCurrentStats(list);
    }
    
    public void updateCurrentStats(List<JSONObject> players) {
        this.cgs.updatePlayers(players);
    }

    public void updateGameBoards(JSONObject question) {
        this.sgp.updateGameBoards(question);
    }
    
    public void startGame() {
        this.jTabbedPane1.setSelectedIndex(1);
    }

    public void updatePlayerFocus(String name) {
        this.sgp.updatePlayerWindow(name);
    }

    public void endRound() {
        this.sgp.endRound();
        this.jTabbedPane1.setSelectedIndex(0);
    }

    public void updateTabs() {
        this.jTabbedPane1.setSelectedIndex(1);
        this.jTabbedPane1.setSelectedIndex(0);
    }

    public void endGame() {
        this.sgp.disableSgp();
        String data = ServerConnectorFactory.queryServer("/endGame");
        System.out.println(data);
        JSONObject player = new JSONObject(data);
        JOptionPane.showMessageDialog(this, player.getString("name")
                + " won the game with " + (player.getInt("roundOneScore")+player.getInt("roundTwoScore")));
    }

    public PlayerWindow getPlayerWindow(String player) {
        return this.sgp.getPlayerWindow(player);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        setupPanel = new javax.swing.JPanel();
        setupInterior = new javax.swing.JPanel();
        currentGamePanel = new javax.swing.JPanel();
        currentGameInterior = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        setupPanel.setLayout(new java.awt.GridBagLayout());

        setupInterior.setLayout(new javax.swing.BoxLayout(setupInterior, javax.swing.BoxLayout.LINE_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        setupPanel.add(setupInterior, gridBagConstraints);

        jTabbedPane1.addTab("Set-Up", null, setupPanel, "Set up WOJ Game");

        currentGamePanel.setLayout(new java.awt.GridBagLayout());

        currentGameInterior.setLayout(new javax.swing.BoxLayout(currentGameInterior, javax.swing.BoxLayout.LINE_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        currentGamePanel.add(currentGameInterior, gridBagConstraints);

        jTabbedPane1.addTab("Current Game", currentGamePanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel currentGameInterior;
    private javax.swing.JPanel currentGamePanel;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel setupInterior;
    private javax.swing.JPanel setupPanel;
    // End of variables declaration//GEN-END:variables
}
