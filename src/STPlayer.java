
import java.util.ArrayList;

/**
 * Created by Brit on 9/11/2016.
 */
public class STPlayer {
    private ArrayList<STCard> cards;
    private String playerID;

    public STPlayer(String playerID) {
        this.playerID = playerID;
    }

    public void setCards(ArrayList<STCard> cards) {
        this.cards = cards;
    }
    public String toString(){
        return "PlayerID: " + playerID + "\n Cards: " + cards;
    }
}
