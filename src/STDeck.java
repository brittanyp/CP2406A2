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
/*    public ArrayList<STCard> dealCards(int nCards) {
        ArrayList<STCard> ret = new ArrayList<STCard>();
        for (int i = 0; i < nCards; i++){
            int index = new Random().nextInt((deck.size()));
            STCard card = deck.remove(index);
            ret.add(card);
            System.out.println(card.toString());
        }
        return ret;
    }
*/
    public void addPlayCard(int index, STPlayCard card){
        deck.add(index, card);
    }

    public void addTrumpCard(int index, STTrumpCard card){
        deck.add(index, card);
    }

    public STPlayCard getCard(int index){
        STPlayCard card = (STPlayCard) deck.get(index);
        return card;
    }

}
