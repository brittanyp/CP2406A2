package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Brit on 10/9/2016.
 */
public class GameFrame extends JFrame {
    //Main Game Frame
    private GameLayout defaultGameLayout;

    public GameFrame(String title, int playersNum, STGame game) {
        super(title);
        //Set Frame looks and function
        setSize(1300, 950);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ST - Game");

        //Set layout manager
        setLayout(new BorderLayout());
        Container mainPane = getContentPane();

        //Create Panel
        defaultGameLayout = new GameLayout(this, playersNum, game);
        mainPane.add(defaultGameLayout, BorderLayout.NORTH);
        pack();

        //Wait for user to start
        defaultGameLayout.ableAllComponents(false);
        defaultGameLayout.setStartnQuitBtnAble();

    }

}
