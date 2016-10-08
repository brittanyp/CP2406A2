package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brit on 10/9/2016.
 */
public class InfoDisplayPanel extends JPanel {
    String strRules = "Number of Players: 3-5\n" +
            " \n" +
            "Objective: Be the first player to lose all of your cards\n" +
            " \n" +
            "Play a card that can trump the current card by category value!\n" +
            " \n" +
            "Trump cards can trump any card!\n" +
            " \n" +
            "However if you have no valid cards you will be skipped until the category is changed!";
    String strGeneral = "Mineral supertrumps is a game designed to help players learn about the properties" +
            "\n and uses of common rock-forming minerals. The pack consists of 54 mineral " +
            "\ncards and 6 supertrump cards. Each mineral card includes formula, the classification," +
            "\n crystal system, the geological enviroment" +
            ", as well as information in the five playing \ncategories (or trumps) of Hardness, " +
            "Specific Gravity, Cleavage, Crustal Abundance \nand Economic Value. ";
    String strCategories = "Hardness: 1 to 10, Highest Value is best\n" +
            "Specific Gravity: Highest Value is best\n" +
            "Cleavage: none -> poor/none -> 1 poor -> 2 poor -> 1 good -> 1 good, 1 poor -> 2 good" +
            "\n -> 3 good -> 1 perfect -> 1 perfect, 1 good -> 1 perfect, 2 good -> 2 perfect," +
            "1 good \n-> 3 perfect -> 4 perfect -> 6 perfect\n" +
            "Crustal Abundance: ultratrace -> trace -> low -> moderate -> high -> very high\n" +
            "Economic Value: trivial -> low -> moderate -> high -> very high -> I'm rich!";

    public InfoDisplayPanel(JFrame topFrame, String selectedInfo){
        //Create Panel
        Dimension size = getPreferredSize();
        size.height = 250;
        setPreferredSize(size);

        String info = "";
        switch (selectedInfo) {
            case "Rules":
                info = strRules;
                break;
            case "General":
                info = strGeneral;
                break;
            case "Categories":
                info = strCategories;
                break;
        }

        //Create J objects
        JLabel lblTitle = new JLabel("Information - " + selectedInfo);
        JTextArea lblInfo = new JTextArea(info);
        JButton btnBack = new JButton("Back");

        //Create button functions
        //Back Button Pressed
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topFrame.dispose();
                JFrame infoFrame = new InfoFrame("ST - Information");
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

        infoLayout.weighty = 20;
        infoLayout.gridx = 0;
        infoLayout.gridy = 2;
        add(lblInfo, infoLayout);


        //Bottom Row
        infoLayout.weighty = 8;
        infoLayout.anchor = GridBagConstraints.LAST_LINE_START;
        infoLayout.gridx = 0;
        infoLayout.gridy = 3;
        add(btnBack, infoLayout);

    }
}
