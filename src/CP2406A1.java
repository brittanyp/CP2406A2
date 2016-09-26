import java.util.Scanner;
/**
 * Created by Brittany Peever on 9/11/2016.
 */

public class CP2406A1 {
    //Define menuchoices
    private static final int NEW_GAME = 1;
    private static final int INFO = 2;
    private static final int QUIT = 3;

    public static void main(String[] args) {
        boolean programIsOn = true;
        showGreeting();
        while (programIsOn) {
            showMenu();
            //Get user input
            int menuChoice = getUserChoiceMenu();

            switch (menuChoice) {
                case 1:
                    //Play game
                    STGame game;
                    STDeck deck = null;
                    try {
                        deck = ReadFile.ReadTheFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    game = beginNewGame(deck);
                    game.playGame();
                    break;

                case 2:
                    //Display Information about ST Game
                    showInformation();

                    break;
                case 3:
                    //Exit program
                    programIsOn = false;
                    break;
            }
        }
    }

    private static void showInformation(){
        //Function that prints information about the Super Trump Game
        System.out.println("Mineral Supertrumps is a game designed to help players learn about " +
                "the properies and uses of common rockforming minerals.");
        System.out.println("Objective: To be the first player to lose all of your cards");
        System.out.println("How to play: Each player is dealt 8 cards. Each normal card has 5 categories; " +
                "Hardness, Specific Gravity, Cleavage, Crustal Abundance, Economic Value. Each round " +
                "each player plays a card to beat the last value for the selected category" +
                "Super Trump Cards are special cards that change the selected category.");
    }

    private static STGame beginNewGame(STDeck deck) {
        //Prepare variables and create game in prep for playing game
        //Returns instance game
        //Passed instance deck

        //Get number of players
        int numOfPlayers = getNumOfPlayers();
        //Create new game
        STGame game = new STGame(numOfPlayers, deck);
        //Assign dealer for game
        game.chooseDealer();
        game.dealRandomCardsToEachPlayer();
        game.selectPlayerPostion();

       STPlayer humanPlayer = game.getHumanPlayer();
        showPlayer(humanPlayer);
        return game;
    }

    private static void showPlayer(STPlayer humanPlayer) {
        //Show players position and hand size
        //Passed instance of STPlayer
        System.out.println("Your position is: " + humanPlayer);
    }

    private static int getUserChoiceMenu() {
        //Get user choice for menu
        //Returns integer choice
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();

        while (choice != 1 && choice !=2 && choice !=3) {
            System.out.println("Please enter a valid number>>>>>");
            choice = in.nextInt();
        }
        return choice;
        }
    private static int getNumOfPlayers() {
        //Get user input for number of players
        //Return integer num; selected number of players
        System.out.print("Please enter the number of players. Remember this game is made for 3-5 Players.\n>>>>>");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        while (num < 3 || num > 5){
            System.out.print("Please enter the number of players. Remember this game is made for 3-5 Players.\n>>>>>");
            num = in.nextInt();
        }
        return num;
    }

    private static void showMenu(){
        //Show user menu choices
        System.out.println("1. Play Game\n2. Info\n3. Exit");
        System.out.print(">>>>>");
    }

    private static void showGreeting(){
        //Show greeting to user
        System.out.println("Welcome to the Super Trump Game.");
    }

}
