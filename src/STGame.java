import java.util.ArrayList;
import java.util.Scanner;

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
    String PlayingCategoryValue = "0";
    STCard playedCard;

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
        while (gameIsOn) {
            //todo: setup players in correct order
            for (int i = 0; i < players.length; i++) {
                //todo check if humanplayer
                // check if player.id has skip boolean true if so skip if not continue
                STPlayer selectedPlayer = players[i];
                PlayingCategory = "hardness";
                //if (selectedPlayer.getID() == humanplayerID){
                    ArrayList<Object> hand = (ArrayList<Object>) selectedPlayer.getHand();
                    int x = 0;

                    for (Object card: hand){
                        System.out.println("---------- Card " + x + " ----------");
                        STCard transformedCard = (STCard) card;
                        printCard(transformedCard);
                        x=x+1;
                        System.out.println("");
                    }
                    cardSelection = getHumanCardSelection();

                    while (cardSelection > hand.size()-1 || cardSelection<0 ){
                        System.out.println("Invalid card entry");
                        cardSelection = getHumanCardSelection();
                    }
                    if(i == 0){
                        ArrayList<String> originArray = new ArrayList<String>();
                        STPlayCard originCard = new STPlayCard(101, "OriginCard", "NA", "play",
                        "OriginCard", "NA", "NA", "NA", originArray, "0", "0", "none", "ultratrace","trivial");
                        playedCard = originCard;
                    }
                    compareCategory(hand.get(cardSelection));

                    STCard tempCard =(STCard) hand.get(cardSelection);
                    playedCard = tempCard;
                    hand.remove(cardSelection);

                    //Show last played Card
                    System.out.println("---------- Card last Played ----------");
                    printCard(playedCard);
                //}
                gameIsOn = askIfExit();
                //show current card and humans cards
                //getUserselection
                //Check it is valid (card.category.number> currentcard.category.number?
                //Check if player has valid card to put down
                    //if so:
                        //play card, remove card from hand
                        // print "You played " element name
                    //else:
                        //give one card from deck to players hand
                        // set skip boolean to true
                        // print "You had no valid cards to put down, you will be skipped
                        //until someone plays a trump card
                //show current card

                // else{
                        //Check it is valid (card.category.number> currentcard.category.number?
                    //Check if computer has valid card to put down
                    //if so:
                    //computer scans for valid card
                    //play card, remove card from hand
                    // print Player id " played " element name
                    //else:
                    //give one card from deck to players hand
                    // set skip boolean to true
                    // print player id " had no valid cards to put down, player id will be skipped
                    //until someone plays a trump card
                    //show current card
            }
        }
    }

    private boolean askIfExit() {
        System.out.println("Wanna Quit? Type Yes if so, No if you wish to continue");
        Scanner in = new Scanner(System.in);
        String answer=in.nextLine();
        if (answer.equals("Yes")){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean compareCategory(Object object) {
        double Level = 0;
        double currentLevel = 0;
        String tempString;
        boolean valid = false;
        STPlayCard playCard = (STPlayCard) object;
        STPlayCard playedCardTransformed = (STPlayCard) playedCard;
        switch(PlayingCategory){
            case "hardness":
                try{
                tempString = playCard.getHardness().split("-")[1];
                System.out.println(tempString);}
                catch (Exception e) {
                    try {
                        tempString = playCard.getHardness().split(" ")[1];
                    } catch (Exception etwo) {
                        tempString = playCard.getHardness();
                        System.out.println(tempString);
                    }
                }
                double hardnessValue = Double.parseDouble(tempString);
                double currentHardnessValue = Double.parseDouble(PlayingCategoryValue);
                if (hardnessValue > currentHardnessValue){
                    valid = true;
                    PlayingCategoryValue = Double.toString(hardnessValue);
                }

            case "specific gravity":
                try{
                    tempString = playCard.getHardness().split("-")[1];
                    System.out.println(tempString);}
                catch (Exception e){tempString = playCard.getHardness();
                    System.out.println(tempString);}
                double specificValue = Double.parseDouble(tempString);
                double currentSpecificValue = Double.parseDouble(PlayingCategoryValue);
                if (specificValue > currentSpecificValue){
                    valid = true;
                    PlayingCategoryValue = Double.toString(specificValue);
                }
            case "crustal abundance":
                switch (playCard.getCrustal_abundance()) {
                    case "ultratrace":
                        Level = 0;
                    case "trace":
                        Level = 1;
                    case "low":
                        Level = 2;
                    case "moderate":
                        Level = 3;
                    case "high":
                        Level = 4;
                    case "very high":
                        Level = 5;
                }
                switch (playedCardTransformed.getCrustal_abundance()) {
                    case "ultratrace":
                        currentLevel = 0;
                    case "trace":
                        currentLevel = 1;
                    case "low":
                        currentLevel = 2;
                    case "moderate":
                        currentLevel = 3;
                    case "high":
                        currentLevel = 4;
                    case "very high":
                        currentLevel = 5;
                }
                if (Level > currentLevel){
                    valid = true;
                    PlayingCategoryValue = Double.toString(Level);
                }

            case "economic value":
                switch (playCard.getEconomic_value()){
                    case "trivial":
                        Level = 0;
                    case "low":
                        Level = 1;
                    case "moderate":
                        Level = 2;
                    case "high":
                        Level = 3;
                    case "very high":
                        Level = 4;
                    case "I'm rich!":
                        Level = 5;}
                switch (playedCardTransformed.getEconomic_value()) {
                    case "trivial":
                        currentLevel = 0;
                    case "low":
                        currentLevel = 1;
                    case "moderate":
                        currentLevel = 2;
                    case "high":
                        currentLevel = 3;
                    case "very high":
                        currentLevel = 4;
                    case "I'm rich!":
                        currentLevel = 5;
                }
                if (Level > currentLevel){
                    valid = true;
                    PlayingCategoryValue = Double.toString(Level);
                }

            case "cleavage":
                switch (playCard.getCleavage()){
                    case "none":
                        Level = 0;
                    case "poor/none":
                        Level = 1;
                    case "1 poor":
                        Level = 2;
                    case "2 good":
                        Level = 3;
                    case "3 good":
                        Level = 4;
                    case "1 perfect":
                        Level = 5;
                    case "1 perfect, 1 good":
                        Level = 6;
                    case "1 perfect, 2 good":
                        Level = 7;
                    case "2 perfect, 1 good":
                        Level = 8;
                    case "3 perfect":
                        Level = 9;
                    case "4 perfect":
                        Level = 10;
                    case "6 perfect":
                        Level = 11;
                }
                switch (playedCardTransformed.getCleavage()){
                    case "none":
                        currentLevel = 0;
                    case "poor/none":
                        currentLevel = 1;
                    case "1 poor":
                        currentLevel = 2;
                    case "2 good":
                        currentLevel = 3;
                    case "3 good":
                        currentLevel = 4;
                    case "1 perfect":
                        currentLevel = 5;
                    case "1 perfect, 1 good":
                        currentLevel = 6;
                    case "1 perfect, 2 good":
                        currentLevel = 7;
                    case "2 perfect, 1 good":
                        currentLevel = 8;
                    case "3 perfect":
                        currentLevel = 9;
                    case "4 perfect":
                        currentLevel = 10;
                    case "6 perfect":
                        currentLevel = 11;
                }
                if (Level > currentLevel){
                    valid = true;
                    PlayingCategoryValue = Double.toString(Level);
                }
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
