package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

/**
 * Created by Brit on 10/9/2016.
 */
public class DefaultGameLayout extends JPanel {
    public DefaultGameLayout(JFrame topFrame){
        //(JFrame topFrame, String category, String value, int numOfPlayers, int deckSize)
        //Create Panel
        Dimension size = getPreferredSize();
        size.height = 850;
        size.width = 1000;
        setPreferredSize(size);

        //Create J objects
        JLabel lblCategory = new JLabel("Category: NA");
        JLabel lblValue = new JLabel("Value: NA");

        JButton btnQuit = new JButton("Quit");
        JLabel lblGuide = new JLabel("Welcome");

//        String[] categories = {"Hardness", "Cleavage", "Crustal Abundance", "Economic Value"};
//        JComboBox cmbCategory = new JComboBox(categories);
 //       cmbCategory.setSelectedIndex(0);

        JButton btnConfirmCat = new JButton("Confirm");


        //Create button functions
        //Quit button Pressed
        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topFrame.dispose();
                JFrame welcomeFrame = new MainFrame("ST - Welcome");
            }
        });

        //Create layout
        setLayout(new GridBagLayout());
        GridBagConstraints defaultLayout = new GridBagConstraints();
        defaultLayout.weightx = 0.5;
        defaultLayout.weighty = 0.5;

        //Row 0, Column 0
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 0;
        defaultLayout.gridy = 0;
        defaultLayout.anchor = GridBagConstraints.FIRST_LINE_START;
        add(getValuesPanel(lblCategory, lblValue), defaultLayout);

        //Row 0, Column 1
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 1;
        defaultLayout.gridy = 0;
        defaultLayout.anchor = GridBagConstraints.FIRST_LINE_START;
        add(lblGuide, defaultLayout);

        //Row 0, Column 2
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 2;
        defaultLayout.gridy = 0;
        add(btnConfirmCat, defaultLayout);
//        add(getCategorySelect(cmbCategory, btnConfirmCat), defaultLayout);

        //Row 1

        //Row 2



    }

    public JPanel getValuesPanel(JLabel lbl1, JLabel lbl2) {
        JPanel valuePanel = new JPanel();
        valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.PAGE_AXIS));
        valuePanel.add(lbl1);
        valuePanel.add(lbl2);
        return valuePanel;
    }
    /*
    public JPanel getCategorySelect(JComboBox cb1, JButton btn1){
        JPanel selectionCategoryPanel = new JPanel();
        selectionCategoryPanel.setLayout(new FlowLayout());
        selectionCategoryPanel.add(cb1);
        selectionCategoryPanel.add(btn1);
        return selectionCategoryPanel;
    }
    */
}
