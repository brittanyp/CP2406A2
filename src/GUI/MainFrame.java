package GUI;

import javax.swing.*;
import java.awt.*;


/**
 * Created by Brit on 10/9/2016.
 */

public class MainFrame extends JFrame {
    //Frame for the welcoming screen

    private WelcomePanel welcomePanel;
    public MainFrame(String title, boolean validFile){
        super(title);
        //Define
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ST Welcome");

        //Set layout manager
        setLayout(new BorderLayout());
        //Create swing items
        welcomePanel = new WelcomePanel(this, validFile);


        // Add swing items to content pane
        Container mainPane = getContentPane();
        mainPane.add(welcomePanel, BorderLayout.NORTH);
    }
}
