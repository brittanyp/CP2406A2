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
//Todo: Make player icons update with skip value
//Todo: Winning conditions
//Todo: Make it pretty
//Todo: Fix bug; no valid cards to play but still asks for button input

//Class dedicated to Super Trump Game, constructed by number of players and deck

public class STGame {

    int WAITTIME = 3000;
    static final int NUM_OF_CARDS_TO_DEAL = 8;
    int numOfPlayers;
    STPlayer[] players;
    STDeck deck;
    boolean gameIsOn = true;
    int humanplayerID;
    String playingCategory;
    String playingCategoryValue;
    STCard playedCard;
    int dealerID;
    int currentPlayer = 0;

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
        }
    }

    public void initiateGame(JFrame topframe, DefaultGameLayout gameLayout ) {
        //Inital Start
        //Set random start category
        String tempcategory=getRandomCategory();
        resetPlayedCard(tempcategory, "Slide66.jpg");
        //Update Layout
        gameLayout.updateLayout(playingCategory, playingCategoryValue, currentPlayer, "Slide66.jpg");
        gameLayout.addHandPanel(players[humanplayerID]);
        gameLayout.ableHandButtons(false);
    }

    public void confirmButtonAction(JFrame topFrame, DefaultGameLayout gameLayout,STCard card){
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
                int tempPlayerNum= currentPlayer + 1;
                if (tempPlayerNum <= numOfPlayers){
                    currentPlayer = tempPlayerNum;}
                else{
                    currentPlayer = 0;
                }

                //Update layout
                gameLayout.ableAllComponents(true);
                gameLayout.updateLayout(playingCategory, playingCategoryValue, currentPlayer, playedCard.getFileName());
                gameLayout.addHandPanel(getHumanPlayer());
                gameLayout.ableHandButtons(false);

                //Initate next round
                playRound(gameLayout);
            }
            else{
                topFrame.dispose();
                gameLayout.ableAllComponents(true);
                gameLayout.notifyUser("!! Invalid Card Selection: "+ card.toString() + " !!");
            }
        }
        else{
            topFrame.dispose();
            STTrumpCard tcard = (STTrumpCard) card;
            if(tcard.getSubtitle().equals("Change to trumps category of your choice")){
                //Special get user input I hate this card
            }
            else{
                resetPlayedCard(tcard.getSubtitle(), card.getFileName());
                //Remove card and Update currentPlayer
                removeCardFromHand(players[currentPlayer], card);
                int tempPlayerNum= currentPlayer + 1;
                if (tempPlayerNum < numOfPlayers){
                    currentPlayer = tempPlayerNum;}
                else{
                    currentPlayer = 0;
                }

                //Update layout
                gameLayout.ableAllComponents(true);
                gameLayout.updateLayout(playingCategory, playingCategoryValue, currentPlayer, playedCard.getFileName());
                gameLayout.addHandPanel(getHumanPlayer());
                gameLayout.ableHandButtons(false);

                //Initate next round
                playRound(gameLayout);
            }
        }
    }


    public void playRound(DefaultGameLayout gameLayout){
        if (checkResetPlayersSkip()) {
            resetPlayedCard(getRandomCategory(), playedCard.getFileName());
            resetAllPlayerSkip();
        }

        //Check if player is skipable
        if(players[currentPlayer].getPlayerSkip()==false) {
            System.out.println(currentPlayer);
            //Check If human player
            if (players[currentPlayer].getID() == humanplayerID) {
                if (checkValidHand() == true) {
                    ActionListener task = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            gameLayout.notifyUser("It is your turn to play a card");
                            gameLayout.ableHandButtons(true);
                        }
                    };
                    Timer timer = new Timer(100, task);
                    timer.setRepeats(false);
                    timer.start();

                    try {
                        Thread.sleep(WAITTIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    playHumanNoValidCards(gameLayout);
                }
            } else {
                gameLayout.notifyUser("Player " + (currentPlayer + 1) + " is deciding");
                //Simulate player deciding
                ActionListener task = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        playBotTurn(gameLayout);
                    }
                };
                Timer timer = new Timer(100, task);
                timer.setRepeats(false);
                timer.start();

                try {
                    Thread.sleep(WAITTIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            if(currentPlayer == humanplayerID) {
                gameLayout.notifyUser("You are still skipped!");
            }
            else{
                gameLayout.notifyUser("Player " + (currentPlayer + 1) + " was skipped!");
            }

            ActionListener task = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int tempPlayerNum= currentPlayer + 1;
                    if (tempPlayerNum <= numOfPlayers){
                        currentPlayer = tempPlayerNum;}
                    else{
                        currentPlayer = 0;
                    }
                    playRound(gameLayout);
                }
            };
            Timer timer = new Timer(100, task);
            timer.setRepeats(false);
            timer.start();

            try {
                Thread.sleep(WAITTIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        if (noSkipCounter >= players.length - 1) {
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

    private void playHumanNoValidCards(DefaultGameLayout gameLayout){
        System.out.println("No valid cards human");
        gameLayout.notifyUser("Oh no! You have no valid cards in your hand. You will be skipped " +
                "until a trump card is played");
        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                players[currentPlayer].setPlayerSkip(true);
                dealSingleCardToPlayer(players[currentPlayer].getHand());
                int tempPlayerNum= currentPlayer + 1;
                if (tempPlayerNum <= numOfPlayers){
                    currentPlayer = tempPlayerNum;}
                else{
                    currentPlayer = 0;
                }
                playRound(gameLayout);
            }
        };
        Timer timer = new Timer(100, task);
        timer.setRepeats(false);
        timer.start();

        try {
            Thread.sleep(WAITTIME+1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void playBotTurn(DefaultGameLayout gameLayout){
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
            gameLayout.notifyUser("Player: " + (currentPlayer+1) + " has played a card");
        }
        else{
            //No valid Cards
            //Set skip and deal card
            players[currentPlayer].setPlayerSkip(true);
            dealSingleCardToPlayer(players[currentPlayer].getHand());

            System.out.println("No valid cards");
            gameLayout.notifyUser("Player " + (currentPlayer + 1) + " has no valid cards to play and will be skipped");
        }


        //Update currentPlayer
        int tempPlayerNum= currentPlayer + 1;
        if (tempPlayerNum < numOfPlayers){
            currentPlayer = tempPlayerNum;}
        else{
            currentPlayer = 0;
        }

        //Update layout
        gameLayout.updateLayout(playingCategory, playingCategoryValue, currentPlayer, playedCard.getFileName());
        //Play next round
        playRound(gameLayout);
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