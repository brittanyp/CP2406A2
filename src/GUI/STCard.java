package GUI;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Brit on 9/11/2016.
 */
//Class for Super Trump Game, represents a card

public class STCard{
    public int id;
    public String fileName, imageName, card_type, title;


    public STCard(int id, String fileName, String imageName, String card_type, String title){
        this.id = id;
        this.fileName = fileName;
        this.imageName = imageName;
        this.card_type = card_type;
        this.title = title;
    }

    public int getID(){
        return id;
    }
    public String getFileName(){
        return fileName;
    }
    public String getImageName(){
        return imageName;
    }
    public String getCard_type(){
        return card_type;
    }
    public String getTitle(){
        return title;
    }

    public String toString(){
        return "Card: " + getTitle();
    }
}
