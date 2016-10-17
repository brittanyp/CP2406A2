package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brit on 10/10/2016.
 */
public class NumberFrame extends JFrame {
    private JPanel numberPanel;
    private int numOfplayers;
    public NumberFrame(String title){
        super(title);
        setSize(1000, 850);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ST - Number of Players");

        numberPanel = new JPanel();
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
                }
                catch (Exception er){
                    lblNote.setVisible(true);
                    validinput = false;
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
        game.selectPlayerPostion();
        STPlayer humanPlayer = game.getHumanPlayer();

        return game;
    }
}
