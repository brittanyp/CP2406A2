package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Brit on 10/9/2016.
 */
public class InfoDisplayFrame extends JFrame{
    private InfoDisplayPanel infoDisplayPanel;

    public InfoDisplayFrame(String title, String infoType){
        super(title);
        //Define
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ST - Information");

        //Set layout manager
        setLayout(new BorderLayout());
        //Create swing items
        infoDisplayPanel = new InfoDisplayPanel(this, infoType);

        // Add swing items to content pane
        Container mainPane = getContentPane();
        mainPane.add(infoDisplayPanel, BorderLayout.NORTH);

    }
}
