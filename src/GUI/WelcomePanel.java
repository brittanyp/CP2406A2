package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brit on 10/9/2016.
 */
public class WelcomePanel extends JPanel {
    //Panel for welcome screen

    JButton btnPlayGame;
    JButton btnInfo;
    public WelcomePanel(JFrame topFrame, boolean validFile){
        //Create Panel
        Dimension size = getPreferredSize();
        size.height = 250;
        setPreferredSize(size);

        //Create J objects
        JLabel lblTitle = new JLabel("Super Trump!");
        JLabel lblSubTitle = new JLabel("#1 Game for Mineral Nerds! Probably!");
        btnPlayGame = new JButton("Play Game");
        btnInfo = new JButton("Information");
        JButton btnQuit = new JButton("Quit");

        //Create button functions
        //Quit button Pressed
        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //Info Button Pressed
        btnInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topFrame.dispose();
                JFrame infoFrame = new InfoFrame("ST - Information");

            }
        });

        btnPlayGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topFrame.dispose();
                JFrame NumberFrame = new NumberFrame("ST - Number of Players");
            }
        });

        //Create layout
        setLayout(new GridBagLayout());
        GridBagConstraints welcomeLayout = new GridBagConstraints();
        welcomeLayout.weightx = 0.5;
        welcomeLayout.weighty = 0.5;

        //Top Row
        welcomeLayout.weighty = 4;
        welcomeLayout.gridx = 0;
        welcomeLayout.gridy = 0;
        add(lblTitle, welcomeLayout);

        //Second top row
        welcomeLayout.weighty = 2;
        welcomeLayout.gridx = 0;
        welcomeLayout.gridy = 1;
        add(lblSubTitle, welcomeLayout);

        //Third top row
        welcomeLayout.weighty = 20;
        welcomeLayout.gridx = 0;
        welcomeLayout.gridy = 2;
        add(getButtonPanel(btnPlayGame, btnInfo), welcomeLayout);

        //Bottom Row
        welcomeLayout.weighty = 8;
        welcomeLayout.anchor = GridBagConstraints.SOUTH;
        welcomeLayout.gridx = 0;
        welcomeLayout.gridy = 3;
        add(btnQuit, welcomeLayout);

        if (validFile==false){
            btnPlayGame.setEnabled(false);
            btnInfo.setEnabled(false);
            btnPlayGame.setText("No File, No Game");
        }
    }

    public JPanel getButtonPanel(JButton btn1, JButton btn2) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btn1);
        buttonPanel.add(btn2);
        return buttonPanel;
    }

}
