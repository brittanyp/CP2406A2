package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Brit on 10/9/2016.
 */
public class DefaultGameLayout extends JPanel {
    JPanel handPanel = new JPanel();

    public DefaultGameLayout(JFrame topFrame, int numOfPlayers, STGame game){
        int decksize = game.getDeckSize();

        //Create Panel
        Dimension size = getPreferredSize();
        size.height = 950;
        size.width = 1300;
        setPreferredSize(size);

        //Create J objects
        JLabel lblCategory = new JLabel("Category: " + game.playingCategory);
        JLabel lblValue = new JLabel("Value: " + game.playingCategoryValue);

        JButton btnQuit = new JButton("Quit");
        JLabel lblGuide = new JLabel("Welcome");

        //PlayedCard Panel
        JLabel lblPlayedCard = new JLabel("Played Card");
        JLabel lblPlayedCardIcon = new JLabel();
        ImageIcon playedCardImage = new ImageIcon(new ImageIcon("images\\Slide66.jpg").
                getImage().getScaledInstance(240,336, Image.SCALE_DEFAULT));
        lblPlayedCardIcon.setIcon(playedCardImage);

        //Deck Panel
        JLabel lblDeckCount = new JLabel("Deck: " + decksize);
        JLabel lblDeckIcon = new JLabel();
        ImageIcon DeckCardImage = new ImageIcon(new ImageIcon("images\\Slide66.jpg").
                getImage().getScaledInstance(240,336, Image.SCALE_DEFAULT));
        lblDeckIcon.setIcon(DeckCardImage);

        //Hand Panel
        JLabel lblhand = new JLabel("Your Hand --->");


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
        /*
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 2;
        defaultLayout.gridy = 0;
        add(btnConfirmCat, defaultLayout);
        add(getCategorySelect(cmbCategory, btnConfirmCat), defaultLayout);
        */

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
        defaultLayout.anchor = GridBagConstraints.FIRST_LINE_START;
        add(getPlayedCardPanel(lblPlayedCard, lblPlayedCardIcon), defaultLayout);

        //Row 1, Column 2
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 2;
        defaultLayout.gridy = 1;
        defaultLayout.anchor = GridBagConstraints.FIRST_LINE_START;
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
        handPanel.setLayout(new FlowLayout());
        add(handPanel, defaultLayout);

        //Row 2, Column 2
        defaultLayout.weighty = 4;
        defaultLayout.gridx = 2;
        defaultLayout.gridy = 2;
        defaultLayout.anchor = GridBagConstraints.EAST;
        add(btnQuit, defaultLayout);

    }
    public void addHandPanel(STPlayer player){
        String fileName, title;
        ArrayList<Object> hand = (ArrayList<Object>) player.getHand();
        for (Object card : hand){
            STCard tcard = (STCard) card;
            title = tcard.getTitle();
            fileName = tcard.getFileName();
            JButton btncard = new JButton(title);
            btncard.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showCard(tcard);
                }
            });
            /*
            ImageIcon cardImage = new ImageIcon(new ImageIcon("F:\\Uni\\SP_3_IT\\CP2406\\A2\\CP2406A2\\images\\" + fileName).
                    getImage().getScaledInstance(192,269, Image.SCALE_DEFAULT));
            lblcard.setIcon(cardImage);
            */
            handPanel.add(btncard);
        }

    }

    public void showCard(STCard card){
        JPanel panel = this;
        panel.setEnabled(false);
        JFrame cardViewFrame = new JFrame("ST - View Card");
        cardViewFrame.setSize(400, 400);
        cardViewFrame.setLocationRelativeTo(null);
        cardViewFrame.setVisible(true);
        cardViewFrame.setTitle("ST - View Card");
        Container mainPane = cardViewFrame.getContentPane();
        CardViewPanel cardPanel = new CardViewPanel(panel, card);
        mainPane.add(cardPanel, BorderLayout.NORTH);
        cardViewFrame.pack();
        cardViewFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                panel.setEnabled(true);
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


    public JPanel getValuesPanel(JLabel lbl1, JLabel lbl2) {
        JPanel valuePanel = new JPanel();
        valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.PAGE_AXIS));
        valuePanel.add(lbl1);
        valuePanel.add(lbl2);
        return valuePanel;
    }
    public JPanel getPlayersIcons(int playernums){
        JPanel playersIcons = new JPanel();
        playersIcons.setLayout(new BoxLayout(playersIcons, BoxLayout.PAGE_AXIS));
        for (int i=0; i<playernums;i++){
            JLabel lblplayer = new JLabel("Player " + (i+1));
            JLabel lblplayerIcons = new JLabel();
            ImageIcon playerImage = new ImageIcon(new ImageIcon("images\\playerIcon.png").
                    getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
            lblplayerIcons.setIcon(playerImage);
            playersIcons.add(lblplayerIcons);
            playersIcons.add(lblplayer);
        }
        return playersIcons;
    }

    public JPanel getPlayedCardPanel(JLabel lbl1, JLabel lbl2){
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
/*

    public JPanel getCategorySelect(JComboBox[] cb1, JButton btn1){
        JPanel selectionCategoryPanel = new JPanel();
        selectionCategoryPanel.setLayout(new FlowLayout());
        selectionCategoryPanel.add(cb1);
        selectionCategoryPanel.add(btn1);
        return selectionCategoryPanel;
    }
    */
}
