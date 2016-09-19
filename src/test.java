import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Brit on 9/20/2016.
 */
public class test {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<Object> hey = new ArrayList<Object>();
        list.add("john");
        list.add("greg");
        list.add("miller");
        STPlayCard card1 = new STPlayCard(1, "Filename", "Image name", "title", "play", "Chem", "Classification", "Crystal System", list, "2",
                "4", "Cleavage", "Crystal Abundance", "Eco Value");

        hey.add(card1);
        STPlayCard test = (STPlayCard) hey.get(0);

        System.out.println(test.getChemistry());
        //Object x = STPlayCard.getFilename();
    }

}
