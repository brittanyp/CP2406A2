package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

/**
 * Created by Brit on 10/10/2016.
 */
public class CardViewPanel extends JPanel {
    public CardViewPanel(JPanel previousPanel,STCard card){
        String fileName = card.getFileName().trim();

        Dimension size = getPreferredSize();
        size.height = 400;
        size.width = 400;
        setPreferredSize(size);
        setLayout(new FlowLayout());
        JLabel lblCardPic = new JLabel();
        System.out.println("\\images\\" + fileName);
        ImageIcon cardImage = new ImageIcon(new ImageIcon("\\images\\" + fileName).
                getImage().getScaledInstance(240,336, Image.SCALE_DEFAULT));
        lblCardPic.setIcon(cardImage);
        add(lblCardPic);


    }
}
