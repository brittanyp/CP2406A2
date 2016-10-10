package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 * Created by Brit on 9/11/2016.
 */
//Class dedicated to Super Trump Game, constructed by number of players and deck

public class STGame {
    int WAITTIME = 3000;
    static final int NUM_OF_CARDS_TO_DEAL = 3;
    int numOfPlayers;
    STPlayer[] players;
    STDeck deck;
    boolean gameIsOn = true;
    int humanplayerID;
    String playingCategory;
    String playingCategoryValue;
    STCard playedCard;
    int dealerID;

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

    public void dealSingleCardToPlayer(ArrayList<Object> hand) {
        //Deals single cards to hand
        //Passed instance of hand
        if (deck.getDeckSize() != 0) {
            STCard card = deck.getRandomSingleCard();
            hand.add(card);
        }
    }

    public void playGame(JFrame topframe, DefaultGameLayout gameLayout ) {

        //Iterates through rounds until game winning conditions are met
        String category = "";
        boolean exit = false;
        //Inital start
        if (players[0].getID() == humanplayerID) {
            //Human
            //ArrayList<Object> hand = (ArrayList<Object>) players[0].getHand();
            //int x = 0;
            //Todo: fix this
            gameLayout.addHandPanel(players[0]);

        }
        else{
            System.out.println("Nah bruh");
        }
    }

    private boolean playHumanTurn(STPlayer player) {
        //Play turn for the human player
        //Returns win boolean
        int cardSelection;
        boolean magnetiteInHand = false;
        boolean geophysicistInHand = false;
        boolean win = false;
        boolean cardValid = false;

        STPlayer selectedPlayer = player;
        if (selectedPlayer.getPlayerSkip() == false) {
            ArrayList<Integer> validCards = new ArrayList<Integer>();
            ArrayList<Object> hand = (ArrayList<Object>) selectedPlayer.getHand();
            int x = 0;

            for (Object card : hand) {
                STCard transformedCard = (STCard) card;
                //Check if card is magnetite
                if (transformedCard.getTitle().equals("Magnetite")) {
                    magnetiteInHand = true;
                }
                //Check if card is geophysicist
                if (transformedCard.getTitle().equals("The Geophysicist")) {
                    geophysicistInHand = true;
                }

                if (transformedCard.getCard_type().equals("play")) {
                    cardValid = compareCategory(transformedCard, false);
                } else {
                    cardValid = true;
                }
                if (cardValid) {
                    validCards.add(x);
                }
                System.out.println("---------- Card " + x + " ----------");
                printCard(transformedCard);
                System.out.println("");
                x = x + 1;
            }
            printCurrentCategoryNValue();
            if (magnetiteInHand == true && geophysicistInHand == true) {
                //Winning condition
                System.out.println("You hold both the magnetite and geophysicist cards!");
                win = true;
            }
            else {
                if (validCards.size() > 0) {
                    cardSelection = getHumanCardSelection();
                    while (validCards.contains(cardSelection) == false) {
                        System.out.println("Invalid card entry");
                        cardSelection = getHumanCardSelection();
                    }
                    STCard cardSelected = (STCard) hand.get(cardSelection);
                    if (cardSelected.getCard_type().equals("play")) {
                        compareCategory(cardSelected, true);
                        System.out.println("---------- Card last Played ----------");
                        printCard(playedCard);
                        System.out.println("--------------------------");
                    } else {
                        STTrumpCard selectedTrumpCard = (STTrumpCard) cardSelected;
                        resetAllPlayerSkip();
                        if (selectedTrumpCard.getSubtitle().equals("Change to trumps category of your choice")) {
                            playGeologistCard(selectedPlayer);
                        } else {
                            resetPlayedCard(selectedTrumpCard.getSubtitle());
                        }
                        System.out.println("---------- Card last Played ----------");
                        printCard(selectedTrumpCard);
                        System.out.println("--------------------------");
                    }
                    hand.remove(cardSelection);
                } else {
                    System.out.println("* You have no valid cards to play");
                    boolean exitTrue = false;
                    exitTrue=askIfExit();
                    if (exitTrue== true) {
                        gameIsOn = false;
                    } else {
                        System.out.println("you will be skipped until a trump card is played *");
                        System.out.println("-----------------------------");
                        selectedPlayer.setPlayerSkip(true);
                        dealSingleCardToPlayer(hand);
                        if (checkResetPlayersSkip()) {
                            resetPlayedCard(getRandomCategory());
                            resetAllPlayerSkip();
                        }
                    }
                }
                if (hand.size() == 0) {
                    System.out.println("*** Congrats " + selectedPlayer.getID() + " You have won! ***");
                    win = true;
                }
            }
        }
        return win;
    }

    private boolean playBotTurn(STPlayer player) {
        //Play bots turn
        //returns win boolean
        int cardSelection;
        boolean magnetiteInHand = false;
        boolean geophysicistInHand = false;
        boolean win = false;
        boolean cardValid = false;

        STPlayer selectedPlayer = player;
        if (selectedPlayer.getPlayerSkip() == false) {

            ArrayList<Integer> validCards = new ArrayList<Integer>();
            ArrayList<Object> hand = (ArrayList<Object>) selectedPlayer.getHand();
            int x = 0;

            for (Object card : hand) {
                STCard transformedCard = (STCard) card;
                //Check if card is Magnetite
                if (transformedCard.getTitle().equals("Magnetite")) {
                    magnetiteInHand = true;
                }
                //Check if card is geophysicist
                if (transformedCard.getTitle().equals("The Geophysicist")) {
                    geophysicistInHand = true;
                }
                if (transformedCard.getCard_type().equals("play")) {
                    cardValid = compareCategory(transformedCard, false);
                } else {
                    cardValid = true;
                }
                if (cardValid) {
                    //Add card to validCard array if it is deemed valid
                    validCards.add(x);
                }
                x = x + 1;
            }
            if (magnetiteInHand == true && geophysicistInHand == true) {
                System.out.println("Player " + selectedPlayer.getID() + " holds both the magnetite and geophysicist cards!");
                System.out.println("Player " + selectedPlayer.getID() + " has won! Accept defeat with grace.");
                win = true;
            } else {
                if (validCards.size() > 0) {
                    cardSelection = getBotCardSelection(hand.size());
                    while (validCards.contains(cardSelection) == false) {
                        cardSelection = getBotCardSelection(hand.size());
                    }
                    STCard cardSelected = (STCard) hand.get(cardSelection);
                    if (cardSelected.getCard_type().equals("play")) {
                        compareCategory(cardSelected, true);
                        System.out.println("------Player " + player.getID() + " played " + cardSelected.getTitle() + " ------");
                        printCard(playedCard);
                        System.out.println("--------------------------");

                    } else {
                        STTrumpCard selectedTrumpCard = (STTrumpCard) cardSelected;
                        resetAllPlayerSkip();
                        if (selectedTrumpCard.getSubtitle().equals("Change to trumps category of your choice")) {
                            playGeologistCard(selectedPlayer);
                        } else {
                            resetPlayedCard(selectedTrumpCard.getSubtitle());
                        }
                        System.out.println("------Player " + player.getID() + " played " + cardSelected.getTitle() + " ------");
                        printCard(selectedTrumpCard);
                        System.out.println("--------------------------");
                    }
                    hand.remove(cardSelection);
                } else {
                    System.out.println("* Player " + selectedPlayer.getID() + " has no valid cards to play, and will be skipped until a trump card is played *");
                    System.out.println("-----------------------------");
                    selectedPlayer.setPlayerSkip(true);
                    dealSingleCardToPlayer(hand);
                    if (checkResetPlayersSkip()) {
                        resetPlayedCard(getRandomCategory());
                        resetAllPlayerSkip();
                    }
                }
                if (hand.size() == 0) {
                    System.out.println("Player " + selectedPlayer.getID() + " has won! Accept defeat with grace.");
                    win = true;
                }
            }
        }
        return win;
    }

    private void printCurrentCategoryNValue() {
        System.out.println("Category: " + playingCategory + "\nValue: " + playingCategoryValue);
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

    private void playGeologistCard(STPlayer player) {
        String category = "";
        //Function called for special card which lets user choose category
        if (player.getID() == humanplayerID) {
            //If its a humans turn
            System.out.println("You played 'The Geologist' card!\n" +
                    "Choose the category you want to play:\n" + "1. Hardness\n2. Specific Gravity\n3. Cleavage\n" +
                    "4. Crustal abundance\n5. Economic Value");
            int userChoice = getUserInputInt();
            while (userChoice > 5 || userChoice < 1) {
                System.out.println("Invalid Choice");
                userChoice = getUserInputInt();
            }

            switch (userChoice) {
                case 1:
                    category = "hardness";
                    break;
                case 2:
                    category = "specific gravity";
                    break;
                case 3:
                    category = "cleavage";
                    break;
                case 4:
                    category = "crustal abundance";
                    break;
                case 5:
                    category = "economic value";
                    break;
            }
        } else {
            //If its a bots turn
            category = getRandomCategory();
        }
        resetPlayedCard(category);
    }

    private void resetAllPlayerSkip() {
        //Iterates through players and sets each players skip value to false
        for (STPlayer player : players) {
            player.setPlayerSkip(false);
        }
        System.out.println("* Skip Status has been removed from all players *");
    }

    private String getRandomCategory() {
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

    private void resetPlayedCard(String category) {
        //Resets the variables playedCard and playingCategory to a origin card that holds the smallest playering
        // values for each category
        category = category.toLowerCase();
        ArrayList<String> originArray = new ArrayList<String>();
        STPlayCard originCard = new STPlayCard(101, "OriginCard", "NA", "play",
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
    }

    private boolean askIfExit() {
        //Method that asks user if they wish to end the game
        //Returns boolean true or false depending on if user writes YES
        System.out.println("---------- Quit? ----------");
        System.out.println("Wanna Quit? Type 'Yes' if so\nType 'No' if you wish to continue");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine().trim().toLowerCase();
        if (answer.equals("yes")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean compareCategory(Object object, boolean setValue) {
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
                    //System.out.println(tempString);
                } catch (Exception e) {
                    tempString = playCard.getSpecific_gravity();
                    //System.out.println(tempString);
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
            playingCategoryValue = newValue;
        }
        return valid;
    }

    public void selectPlayerPostion() {
        //Assigns human player to random position
        int playerPostion = new Random().nextInt(players.length);
        humanplayerID = playerPostion;
        //Todo: remove this, testing code
        humanplayerID = 0;
    }

    public void printCard(STCard transformedCard) {
        //Prints card depending on what play type
        //Passed GUI.STCard
        if (transformedCard.getCard_type().equals("play")) {
            STPlayCard playCard = (STPlayCard) transformedCard;

            playCard.toPrntString();
        }
        if (transformedCard.getCard_type().equals("trump")) {
            STTrumpCard trumpCard = (STTrumpCard) transformedCard;
            trumpCard.toPrntString();
        }
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

    public int getHumanCardSelection() {
        //Prompts user to enter a integer
        //Returns integer
        System.out.print("Enter Card you want to put down\n>>>>>");
        int humanCardSelection = getUserInputInt();
        return humanCardSelection;
    }

    private static int getUserInputInt() {
        Scanner in = new Scanner(System.in);
        while (!in.hasNextInt()) {
            in.next();
            System.out.print("Enter Valid numbers>>>>>>");

        }
        String inputChoice = in.next();
        return Integer.parseInt(inputChoice);
    }
}