/**
 * Created by Brit on 9/11/2016.
 */


public class CP2406A1 {

    private static final int NEW_GAME = 1;
    public static void main(String[] args) {
        showGreeting();
        showMenu();
        int selection = getUserChoiceMenu();
        STGame game;

        if (selection == NEW_GAME) {
            STDeck deck = null;
            try {
               deck = ReadFile.ReadTheFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
               game = beginNewGame(deck);
               game.playGame();
        }
    }

    private static STGame beginNewGame(STDeck deck) {
        int numOfPlayers = getNumOfPlayers();
        STGame game = new STGame(numOfPlayers, deck);
        //deck.getPCard(0).toPrntString();
        game.chooseDealer();
        game.dealRandomCardsToEachPlayer();
        game.selectPlayerPostion();
       STPlayer humanPlayer = game.getHumanPlayer();
        //Todo: remove line below
        //showPlayer(humanPlayer);
        return game;
    }

    private static void showPlayer(STPlayer humanPlayer) {
        System.out.println("Human Player: " + humanPlayer);
        //System.out.println("Id: " + humanPlayer.getID());
    }

    private static int getUserChoiceMenu() {
        //todo: Create menu choice
        return 1;
    }
    private static int getNumOfPlayers() {
        //todo: create function
        return 2;
    }

    private static void showMenu(){
        System.out.println("1. Start Game\n2. Exit");
    }

    private static void showGreeting(){
        System.out.println("Welcome to the Super Trump Game.");
    }

}
