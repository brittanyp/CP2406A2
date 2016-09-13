import java.util.ArrayList;
import java.util.Random;

public class STDeck {
    private static final int INIT_NUM_CARDS = 60;
    private ArrayList<STCard> cards;

    public STDeck(){
       cards = new ArrayList<STCard>();
        for (int i = 0; i < INIT_NUM_CARDS; i++) {
             cards.add(new STCard(i));

            //google how to create random array of integers (no repeats)

        }
    }
    public ArrayList<STCard> dealCards(int nCards) {
        ArrayList<STCard> ret = new ArrayList<STCard>();
        for (int i = 0; i < nCards; i++){
            int index = new Random().nextInt((cards.size()));
            STCard card = cards.remove(index);
            ret.add(card);
            System.out.println(card.toString());
        }
        return ret;

    }
}
