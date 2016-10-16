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
public class DefaultGameLayout extends JPanel {
    JPanel handPanel = new JPanel();
    JPanel iconPanel = new JPanel();
    JLabel lblPlayerValue;
    JLabel lblPlayer;
    JLabel lblCategory;
    JLabel lblValue;

    JLabel lblGuide;
    JLabel lblPlayedCardIcon;
    JLabel lblDeckCount;

    JLabel lblhand;

    JButton btnQuit;
    JButton btnStart;

    STGame game;
    JFrame upperFrame;
    DefaultGameLayout instanceOfLayout = this;
    public DefaultGameLayout(JFrame topFrame, int numOfPlayers, STGame passedgame){

        upperFrame = topFrame;
        game = passedgame;
        int decksize = game.getDeckSize();

        //Create Panel
        Dimension size = getPreferredSize();
        size.height = 950;
        size.width = 1300;
        setPreferredSize(size);

        //Create J objects
        lblPlayer = new JLabel("Player: ");
        lblPlayerValue = new JLabel("0");
        lblCategory = new JLabel("Category: ");
        lblValue = new JLabel("Value: ");

        btnQuit = new JButton("Quit");
        lblGuide = new JLabel("Welcome");

        //PlayedCard Panel
        JLabel lblPlayedCard = new JLabel("Played Card");
        lblPlayedCardIcon = new JLabel();
        setPlayedCardImage(lblPlayedCardIcon, "Slide66.jpg");


        //Deck Panel
        lblDeckCount = new JLabel("Deck: " + decksize);
        JLabel lblDeckIcon = new JLabel();
        ImageIcon DeckCardImage = new ImageIcon(new ImageIcon("images\\Slide66.jpg").
                getImage().getScaledInstance(240,336, Image.SCALE_DEFAULT));
        lblDeckIcon.setIcon(DeckCardImage);

        //Hand Panel
        lblhand = new JLabel("Position: " + game.getHumanPlayer());


        //Create button functions
        //Quit button Pressed
        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topFrame.dispose();
                JFrame welcomeFrame = new MainFrame("ST - Welcome");
            }
        });

        //Create layout
        setLayout(new GridBagLayout());
        GridBagConstraints defaultLayout = new GridBagConstraints();
        defaultLayout.weightx = 0.5;
        defaultLayout.weighty = 0.5;

        //Row 0, Column 0
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 0;
        defaultLayout.gridy = 0;
        defaultLayout.anchor = GridBagConstraints.FIRST_LINE_START;
        add(getValuesPanel(lblCategory, lblValue), defaultLayout);

        //Row 0, Column 1
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 1;
        defaultLayout.gridy = 0;
        defaultLayout.anchor = GridBagConstraints.FIRST_LINE_START;
        add(lblGuide, defaultLayout);

        //Row 0, Column 2
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 2;
        defaultLayout.gridy = 0;
        add(getPlayer(lblPlayer, lblPlayerValue), defaultLayout);

        //Row 1, Column 0
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 0;
        defaultLayout.gridy = 1;
        defaultLayout.anchor = GridBagConstraints.FIRST_LINE_START;
        add(getPlayersIcons(numOfPlayers), defaultLayout);

        //Row 1, Column 1
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 1;
        defaultLayout.gridy = 1;
        defaultLayout.anchor = GridBagConstraints.CENTER;
        add(getCardPanel(lblPlayedCard, lblPlayedCardIcon), defaultLayout);


        //Row 1, Column 2
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 2;
        defaultLayout.gridy = 1;
        defaultLayout.anchor = GridBagConstraints.CENTER;
        add(getDeckPanel(lblDeckCount, lblDeckIcon), defaultLayout);



        //Row 2, Column 0
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 0;
        defaultLayout.gridy = 2;
        defaultLayout.anchor = GridBagConstraints.CENTER;
        add(lblhand, defaultLayout);

        //Row 2, Column 1
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 1;
        defaultLayout.gridy = 2;
        defaultLayout.anchor = GridBagConstraints.CENTER;
        handPanel.setLayout(new GridLayout(0,4));
        add(handPanel, defaultLayout);

        //Row 2, Column 2
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 2;
        defaultLayout.gridy = 2;
        defaultLayout.anchor = GridBagConstraints.EAST;
        add(btnQuit, defaultLayout);

    }

    public void setPlayedCardImage(JLabel lbl, String fileName){
        ImageIcon playedCardImage = new ImageIcon(new ImageIcon("images\\"+ fileName).
                getImage().getScaledInstance(240,336, Image.SCALE_DEFAULT));
        lbl.setIcon(playedCardImage);
    }

    public void addHandPanel(STPlayer player){
        handPanel.removeAll();
        String title;
        ArrayList<Object> hand = (ArrayList<Object>) player.getHand();
        for (Object card : hand){
            STCard tcard = (STCard) card;
            title = tcard.getTitle();
            JButton btncard = new JButton(title);
            btncard.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showCard(tcard);
                }
            });
            handPanel.add(btncard);
        }

    }

    public void ableHandButtons(boolean enable){
        Component[] components = handPanel.getComponents();
        for(Component component : components){
            component.setEnabled(enable);
        }
    }

    public void updateLayout(String category, String value, int playerNum, String fileName, STPlayer[] playerArray){
        lblCategory.setText("Category: " + category);
        lblValue.setText("Value: " + value);
        lblhand.setText("Position: " + game.getHumanPlayer());
        String strPlayerNum = Integer.toString(playerNum +1);
        lblPlayerValue.setText(strPlayerNum);
        setPlayedCardImage(lblPlayedCardIcon, fileName);
        lblDeckCount.setText("Deck: " +  (Integer.toString(game.getDeckSize())));
        updatePlayerIcons(playerArray);
        upperFrame.repaint();

    }


    public void showCard(STCard card){
        this.setEnabled(false);
        ableAllComponents(false);
        JFrame cardViewFrame = new JFrame("ST - View Card");
        cardViewFrame.setSize(400, 400);
        cardViewFrame.setLocationRelativeTo(null);
        cardViewFrame.setVisible(true);
        cardViewFrame.setTitle("ST - View Card");
        Container mainPane = cardViewFrame.getContentPane();
        CardViewPanel cardPanel = new CardViewPanel(cardViewFrame, card, game, this);
        mainPane.add(cardPanel, BorderLayout.NORTH);
        cardViewFrame.pack();
        cardViewFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
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

    public void setStartnQuitButtonAble(){
        btnStart.setEnabled(true);
        btnQuit.setEnabled(true);
    }

    public void ableAllComponents(boolean enable){
        Component[] components = this.getComponents();
        for(Component component : components){
            component.setEnabled(enable);
        }
        Component[] handpanelComponents = this.handPanel.getComponents();
        for(Component component: handpanelComponents){
            component.setEnabled(enable);
        }
    }

    public void notifyUser(String message){
        lblGuide.setText(message);
    }


    public JPanel getValuesPanel(JLabel lbl1, JLabel lbl2) {
        JPanel valuePanel = new JPanel();
        valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.PAGE_AXIS));
        valuePanel.add(lbl1);
        valuePanel.add(lbl2);
        return valuePanel;
    }
    public JPanel getPlayersIcons(int playernums){
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.PAGE_AXIS));
        for (int i=0; i<playernums;i++){
            JLabel lblplayer = new JLabel("Player " + (i+1));
            JLabel lblplayerIcons = new JLabel();
            ImageIcon playerImage = new ImageIcon(new ImageIcon("images\\playerIcon.png").
                    getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
            lblplayerIcons.setIcon(playerImage);
            iconPanel.add(lblplayerIcons);
            iconPanel.add(lblplayer);
        }
        btnStart = new JButton("Start Game");
        add(btnStart);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStart.setVisible(false);
                game.initiateGame(upperFrame, instanceOfLayout);
                game.playRound();
            }
        });
        return iconPanel;
    }

    public JPanel getCardPanel(JLabel lbl1, JLabel lbl2){
        JPanel playedCardPanel = new JPanel();
        playedCardPanel.setLayout(new BoxLayout(playedCardPanel, BoxLayout.PAGE_AXIS));
        playedCardPanel.add(lbl1);
        playedCardPanel.add(lbl2);
        return playedCardPanel;
    }

    public JPanel getDeckPanel(JLabel lbl1, JLabel lbl2){
        JPanel deckPanel = new JPanel();
        deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.PAGE_AXIS));
        deckPanel.add(lbl1);
        deckPanel.add(lbl2);
        return deckPanel;
    }

    public void updatePlayerIcons(STPlayer[] playerArray){
        String imageFile;
        iconPanel.removeAll();
        for (int i=0; i<playerArray.length;i++){
            if(playerArray[i].getPlayerSkip()){
                imageFile = "playSkipIcon.gif";
            }
            else{
                imageFile = "playerIcon.png";
            }

            JLabel lblplayer = new JLabel("Player " + (i+1));
            JLabel lblplayerIcons = new JLabel();
            ImageIcon playerImage = new ImageIcon(new ImageIcon("images\\" + imageFile).
                    getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
            lblplayerIcons.setIcon(playerImage);
            iconPanel.add(lblplayerIcons);
            iconPanel.add(lblplayer);
        }

    }


    public JPanel getPlayer(JLabel lbl1, JLabel lbl2){
        JPanel selectionCategoryPanel = new JPanel();
        selectionCategoryPanel.setLayout(new FlowLayout());
        selectionCategoryPanel.add(lbl1);
        selectionCategoryPanel.add(lbl2);
        return selectionCategoryPanel;
    }
}
