package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Brit on 10/10/2016.
 */
public class NumberFrame extends JFrame {
    //Frame to get number of players for the game

    private JPanel numberPanel;
    private int numOfplayers;
    public NumberFrame(String title){
        //Set frame look
        super(title);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setTitle("ST - Number of Players");

        //Set Panel
        numberPanel = new JPanel();
        numberPanel.setPreferredSize(new Dimension(300,80));
        numberPanel.setLayout(new FlowLayout());

        //Create JObjects
        JLabel lblNumPlayers = new JLabel("Enter Number of Players (3-5): ");
        JTextField txtNumPlayers = new JTextField(10);
        JButton btnConfirmNumPlayers = new JButton("Confirm");
        JLabel lblNote = new JLabel("Invalid Input");

        //Add JObjects to panel
        numberPanel.add(lblNumPlayers);
        numberPanel.add(txtNumPlayers);
        numberPanel.add(btnConfirmNumPlayers);
        numberPanel.add(lblNote);
        lblNote.setVisible(false);

        //Add frame function when close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                JFrame welcomeFrame = new MainFrame("ST - Welcome", true);
            }
        }
        );

        //Add btnConfirmNumPlayers click function
        btnConfirmNumPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Define variables
                boolean validinput = false;
                int userInputInt = 0;
                String userInput = txtNumPlayers.getText().trim();
                try{
                    //Check if integer
                    userInputInt = Integer.parseInt(userInput);
                    if (userInputInt >= 3 && userInputInt <=5){
                        //Check if valid integer
                        validinput = true;
                    }
                    else{
                        //Notify user
                        lblNote.setVisible(true);
                        //Reset textfield
                        txtNumPlayers.setText(null);
                    }
                }
                catch (Exception er){
                    //Notify user
                    lblNote.setVisible(true);
                    validinput = false;
                    //Reset textField
                    txtNumPlayers.setText(null);
                }

                if(validinput==true){
                    //If input is valid create game instance
                    STGame game;
                    STDeck deck = null;
                    try {
                        deck = ReadFile.ReadTheFile();
                        game = beginNewGame(deck, userInputInt);
                        //game.playGame();
                        numOfplayers = userInputInt;;
                        dispose();
                        JFrame playFrame = new GameFrame("ST - Game", numOfplayers, game);
                    } catch (Exception er) {
                        //If file is not found go back to welcome screen and disable playGame btn
                        System.out.println("Unable to read file");
                        JFrame welcomeFrame = new MainFrame("ST - Welcome", false);
                        dispose();
                    }
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
