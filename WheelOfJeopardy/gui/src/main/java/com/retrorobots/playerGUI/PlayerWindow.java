/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.retrorobots.playerGUI;

import com.retrorobots.ServerConnectorFactory;
import com.retrorobots.mainGameGUI.MainWindow;
import com.retrorobots.playerGUI.gameBoard.MainGameBoard;
import com.retrorobots.playerGUI.gameWheel.MainWheel;
import com.retrorobots.playerGUI.gameWheel.Wheel;
import com.retrorobots.playerGUI.popups.CategorySelector;
import com.retrorobots.server.wofj.Player;
import org.apache.catalina.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author KeeganRiley
 */
public class PlayerWindow extends javax.swing.JFrame {
    private Logger LOGGER = LoggerFactory.getLogger(PlayerWindow.class );

    private MainWindow main;
    private MainWheel wheel;
    private MainGameBoard mgb;
    private boolean active = false;
    private boolean lastSpin = false;
    private QuestionPanel qp;
    private AnswerPanel ap;
    private Map<String, Integer> askQuestions = new HashMap<>();
    private List<String> categories = new ArrayList<>();
    private int wheelSides = 1000;

    /**
     * Creates new form PlayerWindow
     */
    public PlayerWindow(String playerName, List<JSONObject> cats) {
        this(null, playerName, cats);
    }

    public PlayerWindow(MainWindow main, String playerName, List<JSONObject> cats) {
        setPreferredSize(new Dimension(1250, 1250));
        setTitle(playerName);
        initComponents();
        init(cats);
        setVisible(true);
        this.main = main;
    }

    private void init(List<JSONObject> cats) {
        qp = new QuestionPanel(this);
        ap = new AnswerPanel(this);

        this.topPanel.add(qp);
        this.topPanel.revalidate();

        this.bottomPanel.add(ap);
        this.bottomPanel.revalidate();

        updateCategoryList(cats);

        try {
            wheel = new MainWheel(cats);
            wheel.hasBorders(true);
            wheel.setBounds(10, 10, wheelSides, wheelSides);
            java.awt.GridBagConstraints gridBagConstraints;
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.1;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
            this.wheelPanel.add(wheel, gridBagConstraints);
            this.wheelPanel.revalidate();

        } catch (Exception ex) {
            LOGGER.info(PlayerWindow.class.getName(), ex);
        }


        mgb  = new MainGameBoard(cats);
        this.mgb.revalidate();
        this.boardPanel.add(mgb, BorderLayout.CENTER);
        this.boardPanel.revalidate();
    }

    public void spinClicked() {
        spinButton.setEnabled(false);
        sendCategory.setEnabled(true);
    }

    public void setActive(boolean active) {
        this.active = active;
        this.jTabbedPane1.setSelectedIndex(0);
        spinButton.setEnabled(active);
    }

    public void spinAgain() {
        setActive(true);
        this.jTabbedPane1.setSelectedIndex(0);
    }

    private void spinAgain(String message) {
        this.spinButton.setEnabled(!lastSpin);
        this.sendCategory.setEnabled(false);
        JOptionPane.showMessageDialog(this, message);
        if (lastSpin) {
            String data = ServerConnectorFactory.queryServer("/endRoundByTurn");
            if (data.equalsIgnoreCase("end of Game")) {
                String d = ServerConnectorFactory.queryServer("/getActiveGame");
                this.endGame(new JSONObject(d));
            } else {
                this.endRound();
            }
        }
    }

    public void switchPlayers(String name) {
        this.setActive(false);
        this.sendCategory.setEnabled(false);
        this.main.updatePlayerFocus(name);
    }

    public void updatePlayers(JSONArray arr) {
        List<JSONObject> list = new ArrayList<>();
        for(int i = 0; i < arr.length(); i++) {
            JSONObject jo = arr.getJSONObject(i);
            list.add(jo);
        }
        this.main.updateCurrentStats(list);
    }

    public void updateMainGameBoard(JSONObject question) {
        this.mgb.updateBoard(question);
    }

    public void updateGame(JSONObject game) {
        this.main.updateGame(game);
    }

    public void endRound() {
        this.setActive(false);
        this.main.endRound();
    }

    public void endGame(JSONObject game) {
        this.setActive(false);
        List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < game.getJSONArray("playerList").length(); i++) {
            list.add(game.getJSONArray("playerList").getJSONObject(i));
        }
        this.main.updateCurrentStats(list);
        this.main.endGame();

    }

    public void newRound(List<JSONObject> categories) {
        this.mgb.setCategoryList(categories);
        this.mgb.newRound();
        this.updateCategoryList(categories);
        this.wheelPanel.remove(wheel);
        try {
            wheel = new MainWheel(categories);
            wheel.hasBorders(true);
            wheel.setBounds(10, 10, wheelSides, wheelSides);
            this.wheel.revalidate();
            java.awt.GridBagConstraints gridBagConstraints;
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.1;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
            this.wheelPanel.add(wheel, gridBagConstraints);
            this.wheelPanel.revalidate();
            this.wheel.repaint();
            this.main.updateTabs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.jTabbedPane1.setSelectedIndex(0);
    }

    private void updateCategoryList(List<JSONObject> cats) {
        if (categories.size() > 0)
            categories.clear();
        for (JSONObject jo : cats) {
            categories.add(jo.getString("categoryName"));
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        wheelPanel = new javax.swing.JPanel();
        controlPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        spinButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        sendCategory = new javax.swing.JButton();
        boardPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        bottomPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        wheelPanel.setLayout(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(wheelPanel, gridBagConstraints);

        controlPanel.setLayout(new java.awt.GridBagLayout());

        jPanel3.setLayout(new java.awt.GridBagLayout());

        spinButton.setText("Spin");
        spinButton.setEnabled(false);
        spinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spinButtonActionPerformed(evt);
            }
        });
        jPanel3.add(spinButton, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        controlPanel.add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        sendCategory.setText("Request Question");
        sendCategory.setEnabled(false);
        sendCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendCategoryActionPerformed(evt);
            }
        });
        jPanel4.add(sendCategory, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        controlPanel.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(controlPanel, gridBagConstraints);

        jTabbedPane1.addTab("Game Wheel", jPanel2);

        boardPanel.setLayout(new java.awt.BorderLayout());
        jTabbedPane1.addTab("Game Board", boardPanel);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        topPanel.setLayout(new javax.swing.BoxLayout(topPanel, javax.swing.BoxLayout.LINE_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(topPanel, gridBagConstraints);

        bottomPanel.setLayout(new javax.swing.BoxLayout(bottomPanel, javax.swing.BoxLayout.LINE_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(bottomPanel, gridBagConstraints);

        jTabbedPane1.addTab("Question", jPanel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void spinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spinButtonActionPerformed

        double base = 180;
        Random rand = new Random();
        int upperbound = 50;
        int int_random = rand.nextInt(upperbound);
        double initialSpeed = base + (int_random*15);
        initialSpeed = (int)Math.signum(initialSpeed) * Math.min(Math.abs(initialSpeed), wheel.getMaxSpinSpeed());
        String data = ServerConnectorFactory.queryServer("/takeTurn");
        JSONObject game = new JSONObject(data);
        this.updateGame(game);
        if (game.getInt("spinCount") <= 0) {
            this.lastSpin = true;
        }
        try {
            wheel.startSpinAsync(Math.abs(initialSpeed), (int)Math.signum(initialSpeed), -100);
            spinClicked();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_spinButtonActionPerformed

    private void sendCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendCategoryActionPerformed

        if (wheel.isSpinning()) {
            JOptionPane.showMessageDialog(this, "Wheel still spinning. Please wait.");
            return;
        }

        String cat = wheel.getSelectedString();
        String data;
        String hasFreeToken;
        String message;
        CategorySelector cs;
        boolean validChoice;
        switch (cat.toLowerCase()) {
            case ("lose turn"):
                hasFreeToken = ServerConnectorFactory.queryServer(ServerConnectorFactory.CHECK_PLAYER_FREE_TOKEN);
                if (Boolean.parseBoolean(hasFreeToken)) {
                    int choice = JOptionPane.showConfirmDialog(this, "You lost your turn. Would you like to use your Free Turn Token now?");
                    if (choice == JOptionPane.YES_OPTION) {
                        ServerConnectorFactory.queryServer(ServerConnectorFactory.USE_FREE_TOKEN);
                        spinAgain("Please Spin again.");
                        break;
                    }
                }
                data = ServerConnectorFactory.queryServer(ServerConnectorFactory.LOSE_TURN_PATH);
                JSONObject game = new JSONObject(data);
                JSONObject currPlayer = game.getJSONObject("currPlayer");
                switchPlayers(currPlayer.getString("name"));
                break;
            case ("spin again"):
                spinAgain("Please spin again.");
                break;
            case ("free turn"):
                data = ServerConnectorFactory.queryServer(ServerConnectorFactory.FREE_TURN_PATH);
                if (Boolean.parseBoolean(data)) {
                    spinAgain("Free Token added. Please spin again.");
                } else {
                    spinAgain("You already have a Free Token. Please spin again");
                }
                break;
            case ("bankrupt"):
                data = ServerConnectorFactory.queryServer(ServerConnectorFactory.BANKRUPT_PATH);
                JOptionPane.showMessageDialog(this, "Sorry you are now bankrupt!");
                hasFreeToken = ServerConnectorFactory.queryServer(ServerConnectorFactory.CHECK_PLAYER_FREE_TOKEN);
                this.main.updateGame(new JSONObject(data));
                if (Boolean.parseBoolean(hasFreeToken)) {
                    int choice = JOptionPane.showConfirmDialog(this, "You landed on Bankrupt but you may " +
                            "continue your turn. Would you like to use your Free Turn Token now?");
                    if (choice == JOptionPane.YES_OPTION) {
                        ServerConnectorFactory.queryServer(ServerConnectorFactory.USE_FREE_TOKEN);
                        spinAgain("Please continue your turn.");
                        break;
                    }
                }
                data = ServerConnectorFactory.queryServer(ServerConnectorFactory.LOSE_TURN_PATH);
                JSONObject game1 = new JSONObject(data);
                JSONObject currPlayer1 = game1.getJSONObject("currPlayer");
                switchPlayers(currPlayer1.getString("name"));
                break;
            case ("player's choice"):
                validChoice = false;
                while (!validChoice) {
                    message = "Please select the category you wish to answer.";
                    cs = new CategorySelector(this, true, message, categories);
                    cs.setVisible(true);
                    while (cs.isVisible()) {
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    data = cs.getSelectedCat();
                    validChoice = sendCategory(data,"Invalid category. Please select another.");
                }
                break;
            case ("opponent's choice"):
                data = ServerConnectorFactory.queryServer(ServerConnectorFactory.OPPONENTS_CHOICE_PLAYER_PATH);
                message = "Please select the category for " + getTitle() + ".";
                validChoice = false;

                // Query category from next player with new Popup GUI
                while (!validChoice) {
                    PlayerWindow pw = this.main.getPlayerWindow(data);
                    cs = new CategorySelector(pw, true, message, categories);
                    cs.setVisible(true);
                    while (cs.isVisible()) {
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    data = cs.getSelectedCat();

                    validChoice = sendCategory(data, "Invalid category. Please select another.");
                }
                break;
            default:
                sendCategory(cat, "No question available for this category. Please spin again.");
                break;
        }
    }//GEN-LAST:event_sendCategoryActionPerformed

    private boolean sendCategory(String cat, String message) {
        String continueProcess = ServerConnectorFactory.queryServer("/availableQuestion?cat="+cat);
        boolean con = Boolean.parseBoolean(continueProcess);
        if (!con) {
            spinAgain(message);
            return false;
        }
        String data = ServerConnectorFactory.queryServer(ServerConnectorFactory.GET_QUESTION_PATH+"?cat="+cat);
        try {
            JSONObject jsonObject = new JSONObject(data);
            LOGGER.info(data);

            this.qp.updateQuestion(jsonObject.getString("question"));
            this.ap.updateAnswers(jsonObject.getString("answer"), jsonObject.getJSONArray("wrongAns"));
        } catch (JSONException e) {
            LOGGER.error(data);
            e.printStackTrace();
        }
        sendCategory.setEnabled(false);
        this.main.updateGameBoards(new JSONObject(data));
        this.jTabbedPane1.setSelectedIndex(2);
        this.ap.enableAnswer();
        return true;
    }

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
            java.util.logging.Logger.getLogger(PlayerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlayerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlayerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlayerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PlayerWindow("0", new ArrayList<>()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel boardPanel;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton sendCategory;
    private javax.swing.JButton spinButton;
    private javax.swing.JPanel topPanel;
    private javax.swing.JPanel wheelPanel;
    // End of variables declaration//GEN-END:variables
}
