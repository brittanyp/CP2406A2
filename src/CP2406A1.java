import java.util.Scanner;

/**
 * Created by Brit on 9/11/2016.
 */


public class CP2406A1 {

    private static final int NEW_GAME = 1;
    private static final int INFO = 2;
    private static final int QUIT = 3;

    public static void main(String[] args) {
        showGreeting();
        showMenu();
        int menuChoice = getUserChoiceMenu();
        boolean programIsOn = true;

        while (programIsOn) {

            switch (menuChoice) {
                case 1:
                    STGame game;
                    STDeck deck = null;
                    try {
                        deck = ReadFile.ReadTheFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    game = beginNewGame(deck);
                    game.playGame();

                case 2:
                    System.out.println("Mineral Supertrumps is a game designed to help players learn about " +
                            "the properies and uses of common rockforming minerals.");
                    System.out.println("Objective: To be the first player to lose all of your cards");
                    System.out.println("How to play: Each player is dealt 8 cards. Each normal card has 5 categories; " +
                            "Hardness, Specific Gravity, Cleavage, Crustal Abundance, Economic Value. Each round " +
                            "each player plays a card to beat the last value for the selected category" +
                            "Super Trump Cards are special cards that change the selected category.");
                case 3:
                    programIsOn = false;
            }
        }
    }

    private static STGame beginNewGame(STDeck deck) {
        int numOfPlayers = getNumOfPlayers();
        STGame game = new STGame(numOfPlayers, deck);
        game.chooseDealer();
        game.dealRandomCardsToEachPlayer();
        game.selectPlayerPostion();

       STPlayer humanPlayer = game.getHumanPlayer();
        showPlayer(humanPlayer);
        return game;
    }

    private static void showPlayer(STPlayer humanPlayer) {
        System.out.println("Your ID is: " + humanPlayer);
    }

    private static int getUserChoiceMenu() {
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt(3);
        while (choice > 3 && choice < 1){
            choice = in.nextInt(3);
        }
        return choice;
    }
    private static int getNumOfPlayers() {
        Scanner in = new Scanner(System.in);
        int num = in.nextInt(2);
        while (num > 3 && num < 6){
            num = in.nextInt(2);
        }
        return num;
    }

    private static void showMenu(){
        System.out.println("1. Start Game\n2. Info\n3.Exit");
        int choice = getUserChoiceMenu();
    }

    private static void showGreeting(){
        System.out.println("Welcome to the Super Trump Game.");
    }

}
