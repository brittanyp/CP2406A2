
import java.util.ArrayList;

/**
 * Created by Brit on 9/11/2016.
 */
public class STPlayer {
    private ArrayList<Object> hand;
    private int playerID;

    public STPlayer(int playerID) {
        this.playerID = playerID;
    }

    public void setHand(ArrayList<Object> hand) {
        this.hand = hand;
    }

    public Object getHand(){
        return hand;
    }

    public int getID(){
        return playerID;
    }

    public String toString(){
        return "PlayerID: " + playerID + "\n Cards: " + hand;
    }
}
