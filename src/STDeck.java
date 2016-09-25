import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class STDeck {
    private static final int INIT_NUM_CARDS = 60;
    private ArrayList<Object> deck;

    public STDeck(){
       deck = new ArrayList<Object>();
        //for (int i = 0; i < INIT_NUM_CARDS; i++) {
             //cards.add(new STCard(i));

            //google how to create random array of integers (no repeats)

        //}
    }
   public ArrayList<Object> dealCards(int nCards) {
        ArrayList<Object> hand = new ArrayList<Object>();
        for (int i = 0; i < nCards; i++){
            int index = new Random().nextInt((deck.size()));
            STCard selectedCard = (STCard) deck.get(index);
            if (selectedCard.getCard_type().equals("play")) {
                STPlayCard card = (STPlayCard) deck.remove(index);
                //deck.remove(index);
                hand.add(card);
                //Todo: remove line below
                //card.toPrntString();
            }
            if (selectedCard.getCard_type().equals("trump")) {
                STTrumpCard card = (STTrumpCard) deck.remove(index);
                //deck.remove(index);
                hand.add(card);
                //Todo: remove line below
                //card.toPrntString();
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

    public STPlayCard getPCard(int index){
        STPlayCard card = (STPlayCard) deck.get(index);
        return card;
    }

    public STTrumpCard getTCard(int index){
        STTrumpCard card = (STTrumpCard) deck.get(index);
        return card;
    }

    public STCard getRandomSingleCard() {
        int randomInt = new Random().nextInt((deck.size()));
        STCard card = (STCard) deck.get(randomInt);
        deck.remove(randomInt);
        return card;
    }
}
