package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brit on 10/9/2016.
 */
public class GameFrame extends JFrame {
    private DefaultGameLayout defaultGameLayout;

    public GameFrame(String title, int playersNum, STGame game) {
        super(title);
        GameFrame currentFrame = this;
        //Define
        setSize(1300, 950);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ST - Game");

        //Set layout manager
        setLayout(new BorderLayout());
        Container mainPane = getContentPane();


        defaultGameLayout = new DefaultGameLayout(this, playersNum, game);
        mainPane.add(defaultGameLayout, BorderLayout.NORTH);
        pack();

        //Wait for user to start
        defaultGameLayout.ableAllComponents(false);
        defaultGameLayout.setStartnQuitButtonAble();
        game.playRound(defaultGameLayout);
    }

}
