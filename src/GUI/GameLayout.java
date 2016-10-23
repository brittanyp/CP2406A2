package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

/**
 * Created by Brit on 10/9/2016.
 */
public class GameLayout extends JPanel {
    //Panel usually called by GameFrame
    //Displays Game to User

    //Panels
    JPanel handPanel = new JPanel();
    JPanel iconPanel = new JPanel();
    JLabel lblCategory;
    JLabel lblValue;
    JTextArea lblGuide;
    JLabel lblPlayedCardIcon;
    JLabel lblDeckCount;
    JLabel lblhand;
    //Buttons
    JButton btnQuit;
    JButton btnStart;

    //Constants
    STGame GAME;
    JFrame UPPERFRAME;
    GameLayout INSTANCEOFLAYOUT = this;
    public GameLayout(JFrame topFrame, int numOfPlayers, STGame passedGame){
        //Dynamic Resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        //Define
        UPPERFRAME = topFrame;
        GAME = passedGame;
        int deckSize = GAME.getDeckSize();

        //Set Panel Look
        Dimension size = getPreferredSize();
        size.height = 800;
        if (screenWidth < 1200){
            size.width = 950;
        }
        else{
            size.width = 1200;
        }
        setPreferredSize(size);

        //Create Fonts
        Font sensibleFont = new Font("Times New Roman", Font.BOLD, 15);
        Font guideFont = new Font("Courier New", Font.BOLD, 15);

        //Create J objects
        lblCategory = new JLabel("Category: ");
        lblCategory.setFont(sensibleFont);

        lblValue = new JLabel("Value: ");
        lblValue.setFont(sensibleFont);

        btnQuit = new JButton("Quit");
        btnQuit.setPreferredSize(new Dimension(100, 50));

        lblGuide = new JTextArea("Welcome");
        lblGuide.setEnabled(false);
        lblGuide.setMaximumSize(new Dimension(250,70));
        lblGuide.setDisabledTextColor(Color.black);
        lblGuide.setFont(guideFont);

        //PlayedCard Panel
        JLabel lblPlayedCard = new JLabel("Played Card");
        lblPlayedCardIcon = new JLabel();
        setPlayedCardImage(lblPlayedCardIcon, "Slide66.jpg");

        //Deck Panel
        lblDeckCount = new JLabel("Deck: " + deckSize);
        JLabel lblDeckIcon = new JLabel();
        ImageIcon DeckCardImage = new ImageIcon(new ImageIcon("images\\Slide66.jpg").
                getImage().getScaledInstance(240,336, Image.SCALE_DEFAULT));
        lblDeckIcon.setIcon(DeckCardImage);

        //Hand Panel
        lblhand = new JLabel("Position: " + GAME.getHumanPlayer());

        //Quit button Function on Press
        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Define game as over, close frame and open welcome screen
                passedGame.gameOn=false;
                topFrame.dispose();
                JFrame welcomeFrame = new MainFrame("ST - Welcome", true);
            }
        });

        //Set layout
        setLayout(new GridBagLayout());
        GridBagConstraints defaultLayout = new GridBagConstraints();
        defaultLayout.weightx = 0.5;
        defaultLayout.weighty = 0.5;

        //Row 0, Column 0
        //Value Display
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 0;
        defaultLayout.gridy = 0;
        defaultLayout.anchor = GridBagConstraints.FIRST_LINE_START;
        add(getValuesPanel(lblCategory, lblValue), defaultLayout);

        //Row 0, Column 1
        //Guide Message
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 1;
        defaultLayout.gridy = 0;
        defaultLayout.anchor = GridBagConstraints.NORTH;
        add(lblGuide, defaultLayout);

        //Row 1, Column 0
        //Player Icons
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 0;
        defaultLayout.gridy = 1;
        defaultLayout.anchor = GridBagConstraints.FIRST_LINE_START;
        add(getPlayersIcons(numOfPlayers), defaultLayout);

        //Row 1, Column 1
        //Played Card View
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 1;
        defaultLayout.gridy = 1;
        defaultLayout.anchor = GridBagConstraints.CENTER;
        add(getCardPanel(lblPlayedCard, lblPlayedCardIcon), defaultLayout);


        //Row 1, Column 2
        //Deck View
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 2;
        defaultLayout.gridy = 1;
        defaultLayout.anchor = GridBagConstraints.WEST;
        add(getDeckPanel(lblDeckCount, lblDeckIcon), defaultLayout);


        //Row 2, Column 1
        //Player Hand View
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 1;
        defaultLayout.gridy = 2;
        defaultLayout.anchor = GridBagConstraints.CENTER;
        handPanel.setLayout(new GridLayout(0,4));
        add(handPanel, defaultLayout);

        //Row 2, Column 2
        //Quit Btn
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 2;
        defaultLayout.gridy = 2;
        defaultLayout.anchor = GridBagConstraints.CENTER;
        add(btnQuit, defaultLayout);

    }

    public void setPlayedCardImage(JLabel lbl, String fileName){
        //Function that sets passed lbl's icon to "images\\ + passed fileName"
        //Passed JLabel and fileName String

        ImageIcon playedCardImage = new ImageIcon(new ImageIcon("images\\"+ fileName).
                getImage().getScaledInstance(240,336, Image.SCALE_DEFAULT));
        lbl.setIcon(playedCardImage);
    }

    public void addHandPanel(STPlayer player){
        //Function adds visual representation of players hand
        //Passed STPlayer

        //Ensure handPanel is empty
        handPanel.removeAll();

        //Gather each card and create a button for each
        String title;
        ArrayList<Object> hand = (ArrayList<Object>) player.getHand();
        for (Object card : hand){
            STCard tcard = (STCard) card;
            title = tcard.getTitle();
            JButton btncard = new JButton(title);

            //Add button click action
            btncard.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showCard(tcard);
                }
            });

            //Add the button to the panel
            handPanel.add(btncard);
        }

    }

    public void ableHandButtons(boolean enable){
        //Function sets components of handPanel to either true or false depending on 'enable'
        //Passed boolean enable

        Component[] components = handPanel.getComponents();
        for(Component component : components){
            component.setEnabled(enable);
        }
    }

    public void updateLayout(String category, String value, int playerNum, String fileName, STPlayer[] playerArray){
        //Updates GameLayout components which are dynamic each turn
        //Passed current category in play, current value to beat, number of players, playedCard
        //fileName and player Array

        //Updates Lbl Values
        lblCategory.setText("Category: " + category);
        lblValue.setText("Value: " + value);
        lblhand.setText("Position: " + GAME.getHumanPlayer());
        lblDeckCount.setText("Deck: " +  (Integer.toString(GAME.getDeckSize())));
        //Updates PlayedCard Image
        setPlayedCardImage(lblPlayedCardIcon, fileName);

        //Updates Player Icon panel
        updatePlayerIcons(playerArray, playerNum);
        UPPERFRAME.repaint();
    }

    public void showCard(STCard card){
        //Shows Human Player Selected Card
        //Passed relevant card

        //Disables Irrelevant Components
        this.setEnabled(false);
        ableAllComponents(false);

        //Creates new Card view frame
        JFrame cardViewFrame = new JFrame("ST - View Card");
        //Define looks
        cardViewFrame.setSize(400, 400);
        cardViewFrame.setLocationRelativeTo(null);
        cardViewFrame.setVisible(true);
        cardViewFrame.setTitle("ST - View Card");

        Container mainPane = cardViewFrame.getContentPane();

        //Creates new cardPanel
        CardViewPanel cardPanel = new CardViewPanel(cardViewFrame, card, GAME, this);
        mainPane.add(cardPanel, BorderLayout.NORTH);
        cardViewFrame.pack();

        cardViewFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                //Define Close Action
                ableAllComponents(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public void setStartnQuitBtnAble(){
        //Function enables btnStart and btnQuit
        btnStart.setEnabled(true);
        btnQuit.setEnabled(true);
    }

    public void ableAllComponents(boolean enable){
        //Function either enables or disables all components (but lblGuide) in this panel
        //Depending on boolean enable
        //Passed boolean enable

        Component[] components = this.getComponents();
        for(Component component : components){
            if (component!=lblGuide){
            component.setEnabled(enable);}
        }
        Component[] handpanelComponents = this.handPanel.getComponents();
        for(Component component: handpanelComponents){
            component.setEnabled(enable);
        }
    }

    public void notifyUser(String message){
        //Update lblGuide
        //Passed String message to inform user
        lblGuide.setText(message);
    }


    public JPanel getValuesPanel(JLabel lbl1, JLabel lbl2) {
        //Function to get valuePanel
        //Passed two JLabels, usually lblCategory and lblValue
        //Returns JPanel with labels added

        JPanel valuePanel = new JPanel();
        valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.PAGE_AXIS));
        valuePanel.add(lbl1);
        valuePanel.add(lbl2);
        return valuePanel;
    }
    public JPanel getPlayersIcons(int playernums){
        //Function to get PlayerIcons and btnStart
        //Passed number of players
        //Returns JPanel with all necessary labels added

        //Set JPanel look
        Font playerFont = new Font("Times New Roman", Font.PLAIN, 10);
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.PAGE_AXIS));

        //Iterate for number of players
        for (int i=0; i<playernums;i++){
            //Each iteration represents a player
            JLabel lblplayer = new JLabel("Player " + (i+1));
            JLabel lblplayerIcons = new JLabel();
            ImageIcon playerImage = new ImageIcon(new ImageIcon("images\\playerIcon.png").
                    getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT));
            lblplayerIcons.setIcon(playerImage);
            lblplayer.setFont(playerFont);

            //Add labels of icon and name
            iconPanel.add(lblplayerIcons);
            iconPanel.add(lblplayer);
        }

        //Create btnStart
        btnStart = new JButton("Start Game");
        btnStart.setPreferredSize(new Dimension(100,50));
        add(btnStart);
        //Add btnStart function on click which starts game
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStart.setVisible(false);
                GAME.initiateGame(INSTANCEOFLAYOUT);
                GAME.playRound();
            }
        });
        return iconPanel;
    }

    public JPanel getCardPanel(JLabel lbl1, JLabel lbl2){
        //Function to get playedCard panel
        //Passed two JLabels usually, label of 'playedCard' and icon of playedCard
        //Returns JPanel with necessary components

        JPanel playedCardPanel = new JPanel();
        playedCardPanel.setLayout(new BoxLayout(playedCardPanel, BoxLayout.PAGE_AXIS));
        playedCardPanel.add(lbl1);
        playedCardPanel.add(lbl2);
        return playedCardPanel;
    }

    public JPanel getDeckPanel(JLabel lbl1, JLabel lbl2){
        //Function to get deckCard panel
        //Passed two JLabels usually, label of 'deck' and icon of deck Card
        //Returns JPanel with necessary components

        JPanel deckPanel = new JPanel();
        deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.PAGE_AXIS));
        deckPanel.add(lbl1);
        deckPanel.add(lbl2);
        return deckPanel;
    }

    public void updatePlayerIcons(STPlayer[] playerArray, int currentPlayer){
        //Updates the playerIcons with each turn
        //Passed playerArray and the currentplayer

        String imageFile;
        //Ensure Icon Panel is empty
        iconPanel.removeAll();
        JLabel lblplayer;
        Font playerFont = new Font("Times New Roman", Font.PLAIN, 10);

        //Update each playerIcon
        for (int i=0; i<playerArray.length;i++){
            if (playerArray[i].getID()==currentPlayer){
                //For currentPlayer
                imageFile = "currentplayerIcon.png";
            }
            else {
                if (playerArray[i].getPlayerSkip()) {
                    //Player skip value = true
                    imageFile = "playSkipIcon.png";
                } else {
                    //Player skip value = false
                    imageFile = "playerIcon.png";
                }
            }
            if (playerArray[i].getID()== GAME.getHumanPlayer().getID()){
                //If human player
               lblplayer = new JLabel("You");
            }
            else {
                //If bot player
                lblplayer = new JLabel("Player " + (i + 1) + ", Hand Size: " + playerArray[i].getHand().size());
            }
            //Create Jobjects and add them to iconPanel
            JLabel lblplayerIcons = new JLabel();
            ImageIcon playerImage = new ImageIcon(new ImageIcon("images\\" + imageFile).
                    getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            lblplayerIcons.setIcon(playerImage);
            lblplayer.setFont(playerFont);
            iconPanel.add(lblplayerIcons);
            iconPanel.add(lblplayer);
        }

    }
}
