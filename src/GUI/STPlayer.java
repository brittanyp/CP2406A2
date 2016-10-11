package GUI;

import java.util.ArrayList;
/**
 * Created by Brit on 9/11/2016.
 */
//Class Super Trump players
public class STPlayer {
    private ArrayList<Object> hand;
    private int playerID;
    boolean playerSkip = false;

    public STPlayer(int playerID) {
        this.playerID = playerID;
    }

    public void setHand(ArrayList<Object> hand) {
        this.hand = hand;
    }

    public ArrayList<Object> getHand(){
        return hand;
    }

    public int getID(){
        return playerID;
    }

    public boolean getPlayerSkip(){
        return playerSkip;
    }

    public void setPlayerSkip(boolean skipSelection){
        playerSkip = skipSelection;
    }

    public String toString(){
        return (playerID+1) + "\n Cards in Hand: " + hand.size();
    }
}
