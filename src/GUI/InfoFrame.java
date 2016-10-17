package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Brit on 10/9/2016.
 */
public class InfoFrame extends JFrame {
    private InfoMenuPanel infoMenuPanel;

    public InfoFrame(String title) {
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
        infoMenuPanel = new InfoMenuPanel(this);

        // Add swing items to content pane
        Container mainPane = getContentPane();
        mainPane.add(infoMenuPanel, BorderLayout.NORTH);
    }
}
