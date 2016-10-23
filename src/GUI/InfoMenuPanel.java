package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brit on 10/9/2016.
 */
public class InfoMenuPanel extends  JPanel{
    //Panel for information menu
    InfoMenuPanel panel;
        public InfoMenuPanel(JFrame topFrame){
            //Create Panel
            panel = this;
            Dimension size = getPreferredSize();
            size.height = 250;
            setPreferredSize(size);

            //Create J objects
            JLabel lblTitle = new JLabel("Information");
            JButton btnRules = new JButton("Rules");
            JButton btnCategory = new JButton("Category Info");
            JButton btnGeneral = new JButton("General Info");
            JButton btnBack = new JButton("Back");

            //Create button functions
            //Back Button Pressed
            btnBack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    topFrame.dispose();
                    JFrame welcomeFrame = new MainFrame("ST - Welcome", true);
                }
            });

            btnRules.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    topFrame.dispose();
                    JFrame infoDisplayFrame = new InfoDisplayFrame("ST - Information", "Rules");
                }
            });

            btnCategory.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    topFrame.dispose();
                    JFrame infoDisplayFrame = new InfoDisplayFrame("ST - Information", "Categories");
                }
            });

            btnGeneral.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    topFrame.dispose();
                    JFrame infoDisplayFrame = new InfoDisplayFrame("ST - Information", "General");
                }
            });

            //Create layout
            setLayout(new GridBagLayout());
            GridBagConstraints infoLayout = new GridBagConstraints();
            infoLayout.weightx = 0.5;
            infoLayout.weighty = 0.5;

            //Top Row
            infoLayout.weighty = 4;
            infoLayout.gridx = 0;
            infoLayout.gridy = 0;
            add(lblTitle, infoLayout);


            //Second top row
            infoLayout.weighty = 20;
            infoLayout.gridx = 0;
            infoLayout.gridy = 2;
            add(getButtonPanel(btnRules, btnCategory, btnGeneral), infoLayout);

            //Bottom Row
            infoLayout.weighty = 8;
            infoLayout.anchor = GridBagConstraints.LAST_LINE_START;
            infoLayout.gridx = 0;
            infoLayout.gridy = 3;
            add(btnBack, infoLayout);

        }

    public JPanel getButtonPanel(JButton btn1, JButton btn2, JButton btn3) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btn1);
        buttonPanel.add(btn2);
        buttonPanel.add(btn3);
        return buttonPanel;
    }

    }
