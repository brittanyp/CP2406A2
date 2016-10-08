package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Brit on 10/9/2016.
 */
public class GameFrame extends JFrame {
    private GameDeckPanel gameDeckPanel;
    private DefaultGameLayout defaultGameLayout;

    public GameFrame(String title){
        super(title);
        //Define
        setSize(1000, 850);
        setLocationRelativeTo(null);

        //setLocationRelativeTo(null);
        setSize(1000, 850);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ST - Game");

        //Set layout manager
        setLayout(new BorderLayout());
        //Create swing items
        defaultGameLayout = new DefaultGameLayout(this);


        // Add swing items to content pane
        Container mainPane = getContentPane();
        mainPane.add(defaultGameLayout, BorderLayout.NORTH);

    }
}
