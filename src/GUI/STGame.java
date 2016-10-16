package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 * Created by Brit on 9/11/2016.
 */
//Todo: Make it pretty
//Todo: Add geologist card condition
//Todo: No deck to pull card thingy DONE
//Todo: User doesnt update icons (PLACE UPDATE LAYOUT IN RESET SKIP CHECK) DONE

//Class dedicated to Super Trump Game, constructed by number of players and deck

public class STGame {
    static final int NUM_OF_CARDS_TO_DEAL = 3;
    int WAITTIME = 2000;
    int numOfPlayers;
    STPlayer[] players;
    STDeck deck;
    int humanplayerID;
    String playingCategory;
    String playingCategoryValue;
    STCard playedCard;
    int dealerID;
    int currentPlayer = 0;
    DefaultGameLayout layout;

    public STGame(int numOfPlayers, STDeck deck) {
        this.numOfPlayers = numOfPlayers;
        this.deck = deck;
    }

    public void dealRandomCardsToEachPlayer() {
        //Create players and deal random cards to each player
        players = new STPlayer[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            players[i] = new STPlayer(i);
        }
        for (STPlayer player : players) {
            ArrayList<Object> hand = deck.dealCards(NUM_OF_CARDS_TO_DEAL);
            player.setHand(hand);
        }
    }

    public void removeCardFromHand(STPlayer player, STCard card){
        ArrayList<Object> hand = player.getHand();
        for (int i=0; i<hand.size() ;i++){
            Object handCard = hand.get(i);
            STCard tHandCard = (STCard) handCard;
            if(tHandCard == card){
                hand.remove(tHandCard);
            }
        }
    }

    public void dealSingleCardToPlayer(ArrayList<Object> hand) {
        //Deals single cards to hand
        //Passed instance of hand
        if (deck.getDeckSize() != 0) {
            STCard card = deck.getRandomSingleCard();
            hand.add(card);
            System.out.println(card.getTitle() + " Added to Hand");
        }

    }

    public void initiateGame(JFrame topframe, DefaultGameLayout gameLayout ) {
        //Inital Start
        layout=gameLayout;
        //Set random start category
        String tempcategory=getRandomCategory();
        resetPlayedCard(tempcategory, "Slide66.jpg");
        //Update Layout
        layout.updateLayout(playingCategory, playingCategoryValue, currentPlayer, "Slide66.jpg", players);
        layout.addHandPanel(players[humanplayerID]);
        layout.ableHandButtons(false);
    }

    public boolean checkWin(STPlayer player){
        boolean win = false;
        ArrayList <Object> hand = player.getHand();
        if (hand.size()==0){
            win = true;
        }
        return win;
    }

    public void confirmButtonAction(JFrame topFrame, STCard card){
        //Human action
        if(card.getCard_type().equals("play")){
            boolean valid;

            //Check valid card
            valid = compareCategory(card, false);
            if(valid==true){
                //Close view card window
                topFrame.dispose();
                //Set Card
                compareCategory(card, true);
                //Remove card and Update currentPlayer
                removeCardFromHand(players[currentPlayer], card);
                int selectedPlayer = currentPlayer;
                updatePlayer();



                if (checkWin(players[selectedPlayer])==true){
                    showWin(players[selectedPlayer]);
                }
                else{
                    //Update layout
                    //Here
                    layout.ableAllComponents(true);
                    layout.updateLayout(playingCategory, playingCategoryValue, currentPlayer, playedCard.getFileName(), players);
                    layout.addHandPanel(getHumanPlayer());
                    layout.ableHandButtons(false);
                    //Initate next round
                    playRound();}
            }
            else{
                topFrame.dispose();
                layout.ableAllComponents(true);
                layout.notifyUser("!! Invalid Card Selection: "+ card.toString() + " !!");
            }
        }
        else{
            topFrame.dispose();
            STTrumpCard tcard = (STTrumpCard) card;
            if(tcard.getSubtitle().equals("Change to trumps category of your choice")){
                //Freeze frame
                layout.ableHandButtons(false);
                layout.ableAllComponents(false);

                //The Geologist Case
                JFrame geologistMenuFrame = new geologistMenuFrame("ST - Geologist Choice", this);
                geologistMenuFrame.setSize(400,200);

            }
            else{
                resetPlayedCard(tcard.getSubtitle(), card.getFileName());
                //Remove card and Update currentPlayer
                removeCardFromHand(players[currentPlayer], card);
                //Set Player check int
                int selectedPlayer = currentPlayer;
                updatePlayer();

                //Update layout
                layout.ableAllComponents(true);
                layout.updateLayout(playingCategory, playingCategoryValue, currentPlayer, playedCard.getFileName(), players);
                layout.addHandPanel(getHumanPlayer());
                layout.ableHandButtons(false);

                if (checkWin(players[selectedPlayer])==true){
                    showWin(players[selectedPlayer]);
                }
                else{
                //Initate next round
                playRound();}
            }
        }
    }

    public void showWin(STPlayer player){
        //Update layout
        layout.ableAllComponents(true);
        layout.updateLayout(playingCategory, playingCategoryValue, currentPlayer, playedCard.getFileName(), players);
        layout.addHandPanel(getHumanPlayer());
        layout.ableHandButtons(false);

        if (player.getID()==humanplayerID){
            layout.notifyUser("Human, you have won! Quit when you want");
        }
        else{
            layout.notifyUser("Player: " + player.getID() + " has won! Accept defeat gracefully.");
        }
    }

    private void updatePlayer(){
        int tempPlayerNum= currentPlayer + 1;
        if (tempPlayerNum < numOfPlayers){
            currentPlayer = tempPlayerNum;}
        else{
            currentPlayer = 0;
        }
    }


    public void playRound(){

        layout.ableHandButtons(false);

        //Check if One Player is left without skip true
        if (checkResetPlayersSkip()==true) {
            resetPlayedCard(getRandomCategory(), playedCard.getFileName());
            resetAllPlayerSkip();
        }

        layout.updatePlayerIcons(players, currentPlayer);

        //Check if player is skipable
        if(players[currentPlayer].getPlayerSkip()==false) {
            System.out.println("Player Turn: " + (currentPlayer +1));
            //Check If human player
            if (players[currentPlayer].getID() == humanplayerID) {
                //Human Path
                if (checkValidHand() == true) {
                    //Valid Hand
                    //Pause Game
                    ActionListener task = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("PAUSE");
                            layout.notifyUser("It is your turn to play a card");
                            layout.ableHandButtons(true);
                        }
                    };
                    Timer timer = new Timer(5000, task);
                    timer.setRepeats(false);
                    timer.start();

                    try {
                        Thread.sleep(WAITTIME);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }


                } else {
                    //No valid hand
                    playHumanNoValidCards();
                }
            } else {
                //Bot path
                layout.notifyUser("Player " + (currentPlayer + 1) + " is deciding");
                //Simulate player deciding
                //Pause Game
                ActionListener task = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("PAUSE");
                        playBotTurn();
                    }
                };
                Timer timer = new Timer(5000, task);
                timer.setRepeats(false);
                timer.start();

                try {
                    Thread.sleep(WAITTIME);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }

            }
        }
        else{
            if(currentPlayer == humanplayerID) {
                layout.notifyUser("You are still skipped!");
            }
            else{
                layout.notifyUser("Player " + (currentPlayer + 1) + " was skipped!");
            }
            //Pause Game
            ActionListener task = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("PAUSE");
                    updatePlayer();
                    playRound();
                }
            };
            Timer timer = new Timer(5000, task);
            timer.setRepeats(false);
            timer.start();

            try {
                Thread.sleep(WAITTIME);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

        }

    }

    private boolean checkResetPlayersSkip() {
        //Method that iterates through players to check the players skip value
        //Returns boolean answer which stores if there is only one person without skip value true
        boolean answer = false;
        int noSkipCounter = 0;
        for (STPlayer player : players) {
            if (player.getPlayerSkip() == true) {
                noSkipCounter = noSkipCounter + 1;
            }
        }
        if (noSkipCounter == players.length - 1) {
            answer = true;
        }
        return answer;
    }


    private boolean checkValidHand() {
        boolean result = false;
        //Get hand
        Object hand = players[currentPlayer].getHand();
        ArrayList<STCard> tHand = (ArrayList<STCard>) hand;

        //Make array of valid selection
        ArrayList<Integer> validCards = new ArrayList<Integer>();
        for (STCard card : tHand) {
            if (card.getCard_type().equals("play")) {
                //Play Cards
                boolean validCard;
                validCard = compareCategory(card, false);
                if(validCard==true){
                    result=true;
                }
            } else {
                //Trump Cards
                result = true;
            }
        }
        return result;
    }

    private void playHumanNoValidCards(){
        System.out.println("No valid cards human");
        layout.notifyUser("Oh no! You have no valid cards in your hand. You will be skipped " +
                "until a trump card is played");
        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PAUSE");
                players[currentPlayer].setPlayerSkip(true);
                dealSingleCardToPlayer(players[currentPlayer].getHand());

                updatePlayer();
                layout.updateLayout(playingCategory, playingCategoryValue, currentPlayer, playedCard.getFileName(), players);
                layout.addHandPanel(getHumanPlayer());
                layout.ableHandButtons(false);

                playRound();
            }
        };
        Timer timer = new Timer(5000, task);
        timer.setRepeats(false);
        timer.start();


        try {
            Thread.sleep(WAITTIME);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }

    private void playBotTurn(){
        //Define Hand
        Object hand = players[currentPlayer].getHand();
        ArrayList<STCard> tHand = (ArrayList<STCard>) hand;
        int x = 0;

        //Make array of valid selection
        ArrayList<Integer> validCards = new ArrayList<Integer>();
        for (STCard card : tHand){
            boolean cardValid=false;

            if(card.getCard_type().equals("play")){
                //Play Cards
                cardValid=compareCategory(card, false);
            }

            else{
                //Trump Cards
                cardValid = true;
            }

            if(cardValid){
                validCards.add(x);
            }
            x=x+1;
        }

        if (validCards.size()!=0) {
            int randomInt = new Random().nextInt(tHand.size());
            while (validCards.contains(randomInt) == false) {
                randomInt = new Random().nextInt(tHand.size());
            }
            STCard selectedCard = tHand.get(randomInt);
            if (selectedCard.getCard_type().equals("play")){
            //Set card as playedCard
            boolean testing = compareCategory(selectedCard, true);
            //Remove selected card
            removeCardFromHand(players[currentPlayer], (STCard) selectedCard);
            //Notify user
            }
            else{
                STTrumpCard tcard = (STTrumpCard) selectedCard;
                if(tcard.getSubtitle().equals("Change to trumps category of your choice")){
                    //Special get user input I hate this card
                }
                else{
                    //Trump Cards
                    resetPlayedCard(tcard.getSubtitle(), selectedCard.getFileName());
                    //Remove card and Update currentPlayer
                    removeCardFromHand(players[currentPlayer], selectedCard);
                }
            }
            layout.notifyUser("Player: " + (currentPlayer+1) + " has played a card");
        }
        else{
            //No valid Cards
            //Set skip and deal card
            players[currentPlayer].setPlayerSkip(true);
            dealSingleCardToPlayer(players[currentPlayer].getHand());

            System.out.println("No valid cards");
            layout.notifyUser("Player " + (currentPlayer + 1) + " has no valid cards to play and will be skipped");
        }


        //Update currentPlayer
        int selectedPlayer = currentPlayer;
        updatePlayer();

        //Update layout
        layout.updateLayout(playingCategory, playingCategoryValue, currentPlayer, playedCard.getFileName(), players);

        //Pause Game
        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PAUSE");
                if (checkWin(players[selectedPlayer])==true){
                    showWin(players[selectedPlayer]);
                }
                else{
                    //Initate next round
                    playRound();}
            }
        };
        Timer timer = new Timer(5000, task);
        timer.setRepeats(false);
        timer.start();

        try {
            Thread.sleep(WAITTIME);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }


    }


    private void resetAllPlayerSkip() {
        //Iterates through players and sets each players skip value to false
        for (STPlayer player : players) {
            player.setPlayerSkip(false);
        }
        System.out.println("* Skip Status has been removed from all players *");
    }

    public String getRandomCategory() {
        //Uses a random integer as a switch value and selects a category based on the random number
        //Returns the selected category as a string
        String selectedCategory = "";
        int randomInt = new Random().nextInt(5);
        switch (randomInt) {
            case 0:
                selectedCategory = "hardness";
                break;
            case 1:
                selectedCategory = "specific gravity";
                break;
            case 2:
                selectedCategory = "cleavage";
                break;
            case 3:
                selectedCategory = "crustal abundance";
                break;
            case 4:
                selectedCategory = "economic value";
                break;
        }
        return selectedCategory;
    }

    public void resetPlayedCard(String category, String fileName) {
        //Resets the variables playedCard and playingCategory to a origin card that holds the smallest playering
        // values for each category
        category = category.toLowerCase();
        ArrayList<String> originArray = new ArrayList<String>();
        STPlayCard originCard = new STPlayCard(101, fileName, "NA", "play",
                "OriginCard", "NA", "NA", "NA", originArray, "0", "0", "none",
                "ultratrace", "trivial");

        playedCard = originCard;
        playingCategory = category;
        switch (category) {
            case "hardness":
                playingCategoryValue = originCard.getHardness();
                break;
            case "specific gravity":
                playingCategoryValue = originCard.getSpecific_gravity();
                break;
            case "cleavage":
                playingCategoryValue = originCard.getCleavage();
                break;
            case "crustal abundance":
                playingCategoryValue = originCard.getCrustal_abundance();
                break;
            case "economic value":
                playingCategoryValue = originCard.getEconomic_value();
                break;
        }
        resetAllPlayerSkip();
    }

    public void playGeologist(String category){
        resetPlayedCard(category, "Slide60.jpg");

        int selectedPlayer = currentPlayer;
        updatePlayer();

        if (checkWin(players[selectedPlayer])==true){
            showWin(players[selectedPlayer]);
        }
        else{
            //Update layout
            layout.ableAllComponents(true);
            layout.updateLayout(playingCategory, playingCategoryValue, currentPlayer, playedCard.getFileName(), players);
            layout.addHandPanel(getHumanPlayer());
            layout.ableHandButtons(false);
            //Initate next round
            playRound();}
    }

    public boolean compareCategory(STCard object, boolean setValue) {
        //Function that compares a card with the current playing category and playing category value
        //Passed Card and setValue which determines if the playingcategory and playingcategoryvalue are
        //updated
        int Level = 0;
        int currentLevel = 0;
        String tempString;
        String newValue = "";
        boolean valid = false;
        STPlayCard playCard = (STPlayCard) object;
        STPlayCard playedCardTransformed = (STPlayCard) playedCard;
        //Todo: Fix this
        switch (playingCategory) {
            case "hardness":
                try {
                    tempString = playCard.getHardness().split("-")[1];

                } catch (Exception e) {
                    try {
                        tempString = playCard.getHardness().split(" ")[1];
                    } catch (Exception etwo) {
                        tempString = playCard.getHardness();

                    }
                }
                double hardnessValue = Double.parseDouble(tempString);
                double currentHardnessValue = Double.parseDouble(playingCategoryValue);
                if (hardnessValue > currentHardnessValue) {
                    valid = true;
                    newValue = Double.toString(hardnessValue);
                }
                break;

            case "specific gravity":
                try {
                    tempString = playCard.getSpecific_gravity().split("-")[1];
                } catch (Exception e) {
                    tempString = playCard.getSpecific_gravity();
                }
                double specificValue = Double.parseDouble(tempString);
                double currentSpecificValue = Double.parseDouble(playingCategoryValue);
                if (specificValue > currentSpecificValue) {
                    valid = true;
                    newValue = Double.toString(specificValue);
                }
                break;
            case "crustal abundance":
                switch (playCard.getCrustal_abundance()) {
                    case "ultratrace":
                        Level = 0;
                        break;
                    case "trace":
                        Level = 1;
                        break;
                    case "low":
                        Level = 2;
                        break;
                    case "moderate":
                        Level = 3;
                        break;
                    case "high":
                        Level = 4;
                        break;
                    case "very high":
                        Level = 5;
                        break;
                }
                switch (playedCardTransformed.getCrustal_abundance()) {
                    case "ultratrace":
                        currentLevel = 0;
                        break;
                    case "trace":
                        currentLevel = 1;
                        break;
                    case "low":
                        currentLevel = 2;
                        break;
                    case "moderate":
                        currentLevel = 3;
                        break;
                    case "high":
                        currentLevel = 4;
                        break;
                    case "very high":
                        currentLevel = 5;
                        break;
                }
                if (Level > currentLevel) {
                    valid = true;
                    newValue = playCard.getCrustal_abundance();
                }
                break;

            case "economic value":
                switch (playCard.getEconomic_value()) {
                    case "trivial":
                        Level = 0;
                        break;
                    case "low":
                        Level = 1;
                        break;
                    case "moderate":
                        Level = 2;
                        break;
                    case "high":
                        Level = 3;
                        break;
                    case "very high":
                        Level = 4;
                        break;
                    case "I'm rich!":
                        Level = 5;
                        break;
                }

                switch (playedCardTransformed.getEconomic_value()) {
                    case "trivial":
                        currentLevel = 0;
                        break;
                    case "low":
                        currentLevel = 1;
                        break;
                    case "moderate":
                        currentLevel = 2;
                        break;
                    case "high":
                        currentLevel = 3;
                        break;
                    case "very high":
                        currentLevel = 4;
                        break;
                    case "I'm rich!":
                        currentLevel = 5;
                        break;
                }
                if (Level > currentLevel) {
                    valid = true;
                    newValue = playCard.getEconomic_value();
                }
                break;

            case "cleavage":
                switch (playCard.getCleavage()) {
                    case "none":
                        Level = 0;
                        break;
                    case "poor/none":
                        Level = 1;
                        break;
                    case "1 poor":
                        Level = 2;
                        break;
                    case "2 poor":
                        Level = 3;
                        break;
                    case "1 good":
                        Level = 4;
                        break;
                    case "1 good, 1 poor":
                        Level = 5;
                        break;
                    case "2 good":
                        Level = 6;
                        break;
                    case "3 good":
                        Level = 7;
                        break;
                    case "1 perfect":
                        Level = 8;
                        break;
                    case "1 perfect, 1 good":
                        Level = 9;
                        break;
                    case "1 perfect, 2 good":
                        Level = 10;
                        break;
                    case "2 perfect, 1 good":
                        Level = 11;
                        break;
                    case "3 perfect":
                        Level = 12;
                        break;
                    case "4 perfect":
                        Level = 13;
                        break;
                    case "6 perfect":
                        Level = 14;
                        break;
                }
                switch (playedCardTransformed.getCleavage()) {
                    case "none":
                        currentLevel = 0;
                        break;
                    case "poor/none":
                        currentLevel = 1;
                        break;
                    case "1 poor":
                        currentLevel = 2;
                        break;
                    case "2 poor":
                        currentLevel = 3;
                        break;
                    case "1 good":
                        currentLevel = 4;
                        break;
                    case "1 good, 1 poor":
                        currentLevel = 5;
                        break;
                    case "2 good":
                        currentLevel = 6;
                        break;
                    case "3 good":
                        currentLevel = 7;
                        break;
                    case "1 perfect":
                        currentLevel = 8;
                        break;
                    case "1 perfect, 1 good":
                        currentLevel = 9;
                        break;
                    case "1 perfect, 2 good":
                        currentLevel = 10;
                        break;
                    case "2 perfect, 1 good":
                        currentLevel = 11;
                        break;
                    case "3 perfect":
                        currentLevel = 12;
                        break;
                    case "4 perfect":
                        currentLevel = 13;
                        break;
                    case "6 perfect":
                        currentLevel = 14;
                        break;
                }
                if (Level > currentLevel) {
                    valid = true;
                    newValue = playCard.getCleavage();
                }
                break;
        }
        if (setValue) {

            playedCard = playCard;

            System.out.println(playedCard.toString());
            playingCategoryValue = newValue;
        }
        return valid;
    }

    public void selectPlayerPostion() {
        //Assigns human player to random position
        int playerPostion = new Random().nextInt(players.length);
        humanplayerID = playerPostion;
        //Todo: remove this, testing code
        humanplayerID = 1;
    }

    public STPlayer getHumanPlayer() {
        //Returns human player ID
        return players[humanplayerID];
    }

    public int getDeckSize(){
        return deck.getDeckSize();
    }


    public int getBotCardSelection(int handSize) {
        //returns a random integer in the range 0-handsize (exclusive max)
        int randomInt = new Random().nextInt(handSize);
        return randomInt;
    }

    public void chooseDealer() {
        dealerID = 1;
    }

}