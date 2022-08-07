/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.retrorobots.mainGameGUI;

import com.retrorobots.ServerConnectorFactory;
import com.retrorobots.playerGUI.PlayerWindow;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author KeeganRiley
 */
public class StartGamePanel extends javax.swing.JPanel {

    private JSONObject curPlayer;
    private MainWindow main;
    private List<String> categories;
    private List<JSONObject> playerList;
    private Map<String, PlayerWindow> windMap;
    
    /**
     * Creates new form NewGamePanel
     */
    public StartGamePanel(MainWindow main) {
        initComponents();
        this.main = main;
        this.categories = new ArrayList<>();
        this.playerList = new ArrayList<>();
        this.windMap = new HashMap<>();
    }
    
    public int getPlayerCount() {
        return this.playerList.size();
    }
    
    private void createPlayerMenus() {
        for (JSONObject obj : playerList) {
            PlayerWindow pw = new PlayerWindow(this.main, obj.getString("name"), categories);
            if (obj.getString("name").equals(curPlayer.getString("name"))) {
                pw.setActive(true);
            }
            this.windMap.put(obj.getString("name"), pw);
        }
    }

    public void updatePlayerWindow(String name) {
        if(windMap.containsKey(name)) {
            windMap.get(name).setActive(true);
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

        playerCountPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        initateGamePanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        playerCountPanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("How many players?");
        jLabel1.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        playerCountPanel.add(jLabel1, gridBagConstraints);

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 5, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        playerCountPanel.add(jSpinner1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(playerCountPanel, gridBagConstraints);

        initateGamePanel.setLayout(new java.awt.GridBagLayout());

        jButton1.setText("Start Game");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        initateGamePanel.add(jButton1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(initateGamePanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        int val = (int)this.jSpinner1.getModel().getValue();
        String data = ServerConnectorFactory.queryServer(ServerConnectorFactory.START_GAME_PATH+"?pCount="+val);
        System.out.println(data);
        parseGameData(data);
        createPlayerMenus();
        this.main.startGame();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void parseGameData(String data) {
        JSONObject game = new JSONObject(data);
        JSONArray catArr = game.getJSONArray("categoryList");
        for (int i = 0; i < catArr.length(); i++) {
            JSONObject cat = catArr.getJSONObject(i);
            String name = cat.getString("categoryName");
            System.out.println(name);
            categories.add(name);
        }

        curPlayer = game.getJSONObject("currPlayer");
        JSONArray list = game.getJSONArray("playerList");
        for (int i = 0; i < list.length(); i++) {
            JSONObject player = list.getJSONObject(i);
            playerList.add(player);
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel initateGamePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JPanel playerCountPanel;
    // End of variables declaration//GEN-END:variables
}
