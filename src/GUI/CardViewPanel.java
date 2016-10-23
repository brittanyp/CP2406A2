package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brit on 10/10/2016.
 */
public class CardViewPanel extends JPanel {
    //Panel to view Human Selected Card

    public CardViewPanel(JFrame topFrame,STCard card, STGame game, GameLayout gameLayout){
        //Get Card
        String fileName = card.getFileName().trim();

        //Set Panel Looks
        Dimension size = getPreferredSize();
        size.height = 600;
        size.width = 400;
        setPreferredSize(size);
        setLayout(new FlowLayout());

        //Create JObjects
        JLabel lblCardPic = new JLabel();
        JButton btnConfirmCard = new JButton("Confirm Card");
        ImageIcon cardImage = new ImageIcon(new ImageIcon("images\\" + fileName).
                getImage().getScaledInstance(360,504, Image.SCALE_DEFAULT));
        lblCardPic.setIcon(cardImage);

        //Add Objects to Panel
        add(lblCardPic);
        add(btnConfirmCard);
        btnConfirmCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Run Confirm button Function
                game.confirmButtonAction(topFrame, card);
            }
        });
    }
}
