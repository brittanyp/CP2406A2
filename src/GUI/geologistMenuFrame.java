package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brit on 10/16/2016.
 */

public class geologistMenuFrame extends JFrame{
    JButton btnHardness;
    JButton btnSpecificGravity;
    JButton btnCleavage;
    JButton btnCrustalAbundance;
    JButton btnEconomicValue;


    public geologistMenuFrame(String title, STGame game)
            //DefaultGameLayout gameLayout
    {
        super(title);
        //Define
        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ST - Geologist Choice");

        JPanel geoMenuLayout = new JPanel();
        geoMenuLayout.setLayout(new BoxLayout(geoMenuLayout, BoxLayout.PAGE_AXIS));

        //Create Components
        JLabel lblexplain = new JLabel("You have chosen 'The Geologist' card; Select a category to play");
        btnHardness = new JButton("Hardness");
        btnSpecificGravity = new JButton("Specific Gravity");
        btnCleavage = new JButton("Cleavage");
        btnCrustalAbundance = new JButton("Crustal Abundance");
        btnEconomicValue = new JButton("Economic Value");

        //Add Components
        geoMenuLayout.add(lblexplain);
        geoMenuLayout.add(btnHardness);
        geoMenuLayout.add(btnSpecificGravity);
        geoMenuLayout.add(btnCleavage);
        geoMenuLayout.add(btnCrustalAbundance);
        geoMenuLayout.add(btnEconomicValue);

        Container mainPane = getContentPane();
        mainPane.add(geoMenuLayout, BorderLayout.NORTH);
        pack();

        btnHardness.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.playGeologist("hardness");
                //resetcard with hardness
            }
        });

    }
}
