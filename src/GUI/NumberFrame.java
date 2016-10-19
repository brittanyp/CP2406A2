package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Brit on 10/10/2016.
 */
public class NumberFrame extends JFrame {
    private JPanel numberPanel;
    private int numOfplayers;
    public NumberFrame(String title){
        super(title);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setTitle("ST - Number of Players");

        numberPanel = new JPanel();
        numberPanel.setPreferredSize(new Dimension(300,80));
        numberPanel.setLayout(new FlowLayout());
        JLabel lblNumPlayers = new JLabel("Enter Number of Players (3-5): ");
        JTextField txtNumPlayers = new JTextField(10);
        JButton btnConfirmNumPlayers = new JButton("Confirm");
        JLabel lblNote = new JLabel("Invalid Input");
        numberPanel.add(lblNumPlayers);
        numberPanel.add(txtNumPlayers);
        numberPanel.add(btnConfirmNumPlayers);
        numberPanel.add(lblNote);
        lblNote.setVisible(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                JFrame welcomeFrame = new MainFrame("ST - Welcome");
            }
        }
        );

        btnConfirmNumPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean validinput = false;
                int userInputInt = 0;
                String userInput = txtNumPlayers.getText().trim();
                try{
                    userInputInt = Integer.parseInt(userInput);
                    if (userInputInt >= 3 && userInputInt <=5){
                        validinput = true;
                    }
                    else{
                        lblNote.setVisible(true);
                        txtNumPlayers.setText(null);
                    }
                }
                catch (Exception er){
                    lblNote.setVisible(true);
                    validinput = false;
                    txtNumPlayers.setText(null);
                }

                if(validinput==true){
                    STGame game;
                    STDeck deck = null;
                    try {
                        deck = ReadFile.ReadTheFile();
                    } catch (Exception er) {
                        System.out.print("Unable to read file, exiting program.");
                    }
                    game = beginNewGame(deck, userInputInt);
                    //game.playGame();
                    numOfplayers = userInputInt;;
                    dispose();
                    JFrame playFrame = new GameFrame("ST - Game", numOfplayers, game);
                }
            }
        });

        Container mainPane = getContentPane();
        mainPane.add(numberPanel, BorderLayout.NORTH);
        pack();
    }


    private STGame beginNewGame(STDeck deck, int numofPlayers) {
        //Get number of players
        //Create new game
        STGame game = new STGame(numofPlayers, deck);
        //Assign dealer for game
        game.chooseDealer();
        game.dealRandomCardsToEachPlayer();
        game.selectPlayerPosition();
        //STPlayer humanPlayer = game.getHumanPlayer();

        return game;
    }
}
