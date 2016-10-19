package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brit on 10/10/2016.
 */
public class CardViewPanel extends JPanel {
    public CardViewPanel(JFrame topFrame,STCard card, STGame game, GameLayout gameLayout){
        String fileName = card.getFileName().trim();

        Dimension size = getPreferredSize();
        size.height = 600;
        size.width = 400;
        setPreferredSize(size);
        setLayout(new FlowLayout());
        JLabel lblCardPic = new JLabel();
        JButton btnConfirmCard = new JButton("Confirm Card");
        ImageIcon cardImage = new ImageIcon(new ImageIcon("images\\" + fileName).
                getImage().getScaledInstance(360,504, Image.SCALE_DEFAULT));
        lblCardPic.setIcon(cardImage);
        add(lblCardPic);
        add(btnConfirmCard);
        btnConfirmCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.confirmButtonAction(topFrame, card);
            }
        });
    }
}
