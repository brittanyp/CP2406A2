import java.util.ArrayList;
import java.util.Random;
//Todo: See if playTCard and playPCard are needed

//Class for Super Trump game deck

public class STDeck {
    //Define cards in deck
    private ArrayList<Object> deck;

    public STDeck(){
        //Create ArrayList deck
       deck = new ArrayList<Object>();
    }
   public ArrayList<Object> dealCards(int nCards) {
       //Deals nCards to a hand
       //Returns arrayList hand
       //Passed nCards; number of cards
        ArrayList<Object> hand = new ArrayList<Object>();
        for (int i = 0; i < nCards; i++){
            //Generate a random int
            int index = new Random().nextInt((deck.size()));
            STCard selectedCard = (STCard) deck.get(index);
            if (selectedCard.getCard_type().equals("play")) {
                STPlayCard card = (STPlayCard) deck.remove(index);
                hand.add(card);
            }
            if (selectedCard.getCard_type().equals("trump")) {
                STTrumpCard card = (STTrumpCard) deck.remove(index);
                hand.add(card);
            }
        }
        return hand;
    }

    public void addPlayCard(int index, STPlayCard card){
        deck.add(index, card);
    }

    public void addTrumpCard(int index, STTrumpCard card){
        deck.add(index, card);
    }

    public STCard getRandomSingleCard() {
        //
        int randomInt = new Random().nextInt((deck.size()));
        STCard card = (STCard) deck.get(randomInt);
        deck.remove(randomInt);
        return card;
    }
}
