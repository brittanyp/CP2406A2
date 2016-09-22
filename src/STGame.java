import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
//todo: add breaks to each switches
//Todo: create if robot is playing
//Todo: add card from deck to hand if skip is true

/**
 * Created by Brit on 9/11/2016.
 */
public class STGame {
    private static final int NUM_OF_CARDS_TO_DEAL = 8;
    private int numOfPlayers;
    private int dealerId;
    private STPlayer[] players;
    private STDeck deck;
    int humanplayerID;
    private String PlayingCategory;
    String PlayingCategoryValue;
    STCard playedCard;
    ArrayList<STPlayer> playersSkipped = new ArrayList<STPlayer>();

    public STGame(int numOfPlayers, STDeck deck) {
        this.numOfPlayers = numOfPlayers;
        this.deck = deck;
    }

    public void chooseDealer() {
        //todo: get random int
        dealerId = 1;
    }

    public void dealRandomCardsToEachPlayer() {
        players = new STPlayer[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++){
            players[i] = new STPlayer(i);

        }
        for (STPlayer player : players) {
            //todo: undo comments on 2 lines below
            ArrayList<Object> hand = deck.dealCards(NUM_OF_CARDS_TO_DEAL);
            player.setHand(hand);
        }
    }

    public void playGame() {
        int cardSelection;
        boolean gameIsOn = true;
        boolean cardValid = false;


        resetPlayedCard(getRandomCategory());


        while (gameIsOn) {
            //todo: setup players in correct order
            //Todo: define when to reset player skip
            for (int i = 0; i < players.length; i++) {
                System.out.println("Category is : " + PlayingCategory + "\nValue to beat is " + PlayingCategoryValue);
                //todo check if humanplayer
                // check if player.id has skip boolean true if so skip if not continue
                STPlayer selectedPlayer = players[i];
                //if (selectedPlayer.getID() == humanplayerID) {
                    ArrayList<Integer> validCards = new ArrayList<Integer>();
                    ArrayList<Object> hand = (ArrayList<Object>) selectedPlayer.getHand();
                    int x = 0;

                    for (Object card : hand) {
                        STCard transformedCard = (STCard) card;

                        if (transformedCard.getCard_type().equals("play")) {
                            cardValid = compareCategory(transformedCard, false);
                        }
                        else{
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
                        }
                        else {
                            STTrumpCard selectedTrumpCard = (STTrumpCard) cardSelected;
                            resetAllPlayerSkip();
                            if (selectedTrumpCard.getSubtitle().equals("Change to trumps category of your choice")) {
                                System.out.println("You have played a trump card!\n" +
                                        "Choose the category you want to play:\n" + "1. Hardness\n2. Specific Gravity\n3. Cleavage\n" +
                                        "4. Crustal abundance\n5. Economic Value");
                                Scanner in = new Scanner(System.in);
                                int userChoice = in.nextInt();
                                System.out.println(userChoice);
                                while (userChoice > 5 || userChoice < 1) {
                                    System.out.println("Invalid Choice");
                                    userChoice = in.nextInt();
                                }
                                String category = "";
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
                                resetPlayedCard(category);

                            }
                            else{
                                resetPlayedCard(selectedTrumpCard.getSubtitle());
                            }
                            System.out.println("---------- Card last Played ----------");
                            printCard(selectedTrumpCard);
                        }
                        hand.remove(cardSelection);
                    } else {
                        System.out.println("You have no valid cards to play, you will be skipped until a trump card is played");
                        selectedPlayer.setPlayerSkip(true);
                    }
                    //}
                    //gameIsOn = askIfExit();

            }
        }
    }

    private void resetAllPlayerSkip() {
        for (STPlayer player : players) {
            player.setPlayerSkip(false);
        }
    }

    private String getRandomCategory() {
        String selectedCategory = "";
        int randomInt = new Random().nextInt(5);
        switch(randomInt){
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

    private void resetPlayedCard(String category){

        category=category.toLowerCase();
        ArrayList<String> originArray = new ArrayList<String>();
        STPlayCard originCard = new STPlayCard(101, "OriginCard", "NA", "play",
                "OriginCard", "NA", "NA", "NA", originArray, "0", "0", "none",
                "ultratrace", "trivial");

        playedCard = originCard;
        PlayingCategory = category;
        switch(category){
            case "hardness":
                PlayingCategoryValue = originCard.getHardness();
                break;
            case "specific gravity":
                PlayingCategoryValue =originCard.getSpecific_gravity();
                break;
            case "cleavage":
                PlayingCategoryValue =originCard.getCleavage();
                break;
            case "crustal abundance":
                PlayingCategoryValue = originCard.getCrustal_abundance();
                break;
            case "economic value":
                PlayingCategoryValue = originCard.getEconomic_value();
                break;
        }
    }

    private boolean askIfExit() {
        System.out.println("---------- Quit? ----------");
        System.out.println("Wanna Quit? Type 'Yes' if so\nType 'No' if you wish to continue");
        Scanner in = new Scanner(System.in);
        String answer=in.nextLine().trim().toUpperCase();
        if (answer.equals("YES")){
            return false;
        }
        else{
            return true;
        }
    }

    private boolean compareCategory(Object object, boolean setValue) {
        int Level = 0;
        int currentLevel = 0;
        String tempString;
        String newValue = "";
        boolean valid = false;
        STPlayCard playCard = (STPlayCard) object;
        STPlayCard playedCardTransformed = (STPlayCard) playedCard;
        switch(PlayingCategory){
            case "hardness":
                try{
                tempString = playCard.getHardness().split("-")[1];

                    }
                catch (Exception e) {
                    try {
                        tempString = playCard.getHardness().split(" ")[1];
                    } catch (Exception etwo) {
                        tempString = playCard.getHardness();

                    }
                }
                double hardnessValue = Double.parseDouble(tempString);
                double currentHardnessValue = Double.parseDouble(PlayingCategoryValue);
                if (hardnessValue > currentHardnessValue){
                    valid = true;
                    newValue = Double.toString(hardnessValue);
                }
                break;

            case "specific gravity":
                try{
                    tempString = playCard.getHardness().split("-")[1];
                    //System.out.println(tempString);
                    }
                catch (Exception e){tempString = playCard.getHardness();
                    //System.out.println(tempString);
                }
                double specificValue = Double.parseDouble(tempString);
                double currentSpecificValue = Double.parseDouble(PlayingCategoryValue);
                if (specificValue > currentSpecificValue){
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
                if (Level > currentLevel){
                    valid = true;
                    newValue = playCard.getCrustal_abundance();
                }
                break;

            case "economic value":
                switch (playCard.getEconomic_value()){
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
                        break;}

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
                if (Level > currentLevel){
                    valid = true;
                    newValue = playCard.getEconomic_value();
                }
                break;

            case "cleavage":
                switch (playCard.getCleavage()){
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
                switch (playedCardTransformed.getCleavage()){
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
                if (Level > currentLevel){
                    valid = true;
                    newValue = playCard.getCleavage();
                }
                break;
        }
        if (setValue){
            playedCard = playCard;
            PlayingCategoryValue = newValue;
        }
        return valid;
    }



    public void selectPlayerPostion() {
        //Todo: randomly select
        humanplayerID = 0;
    }

    public void printCard(STCard transformedCard){
        if (transformedCard.getCard_type().equals("play")){
            STPlayCard playCard = (STPlayCard) transformedCard;

            playCard.toPrntString();
        }
        if (transformedCard.getCard_type().equals("trump")){
            STTrumpCard trumpCard = (STTrumpCard) transformedCard;
            trumpCard.toPrntString();

        }
    }

    public STPlayer getHumanPlayer() {
        //todo:
        return players[humanplayerID];
    }

    public int getHumanCardSelection() {
        System.out.print("Enter Card you want to put down\n>>>>>");
        Scanner in = new Scanner(System.in);
        int humanCardSelection = in.nextInt();
        return humanCardSelection;
    }
}
