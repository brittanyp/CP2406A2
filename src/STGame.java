import java.util.ArrayList;

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


    public STGame(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        deck = new STDeck();
    }

    public void chooseDealer() {
        //todo: get random int
        dealerId = 1;
    }

    public void dealRandomCardsToEachPlayer() {
        players = new STPlayer[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++){
            players[i] = new STPlayer("playerId= "+ i);

        }
        for (STPlayer player : players) {
            ArrayList<STCard> cards = deck.dealCards(NUM_OF_CARDS_TO_DEAL);
            player.setCards(cards);
        }
    }

    public void playGame() {
        boolean gameIsOn = true;
        while (gameIsOn) {
            //todo: setup players in correct order
            for (int i = 0; i < players.length; i++) {

                //todo check if humanplayer
                // check if player.id has skip boolean true if so skip if not continue
                //if player.id = humanplayerID
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


    public void selectPlayerPostion() {
        //Todo: randomly select
        humanplayerID = 0;
    }

    public STPlayer getHumanPlayer() {
        //todo:
        return players[humanplayerID];
    }
}
