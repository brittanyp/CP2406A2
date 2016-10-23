package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brit on 10/16/2016.
 */

public class geologistMenuFrame extends JFrame implements ActionListener{
    //Frame triggered when human player plays geologist card

    //Define Jbuttons and STGame and STCard
    JButton btnHardness;
    JButton btnSpecificGravity;
    JButton btnCleavage;
    JButton btnCrustalAbundance;
    JButton btnEconomicValue;
    STGame currentGame;
    STCard currentCard;

    public geologistMenuFrame(String title, STGame game, STCard card)
    {
        super(title);
        currentGame = game;
        currentCard = card;
        //Set Frame look
        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

        //Add functions to buttons
        btnHardness.addActionListener(this);
        btnSpecificGravity.addActionListener(this);
        btnCleavage.addActionListener(this);
        btnCrustalAbundance.addActionListener(this);
        btnEconomicValue.addActionListener(this);

        Container mainPane = getContentPane();
        mainPane.add(geoMenuLayout, BorderLayout.NORTH);
        pack();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String category;
        if(e.getSource()==btnHardness){
            category = "hardness";
        }
        else if(e.getSource()==btnSpecificGravity){
            category = "specific gravity";
        }
        else if(e.getSource()==btnCleavage){
            category = "cleavage";
        }
        else if(e.getSource()==btnCrustalAbundance){
            category = "crustal abundance";
        }
        else{
            category = "economic value";
        }
        dispose();
        currentGame.playGeologist(category, currentCard);
    }
}
