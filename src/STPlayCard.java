import java.util.ArrayList;

/**
 * Created by Brit on 9/15/2016.
 */
public class STPlayCard extends STCard {
    public String specific_gravity, hardness, chemistry, classification,crystal_system, cleavage, crustal_abundance, economic_value;
    public ArrayList<String> occurrence;
    public STPlayCard (int id, String fileName, String imageName, String card_type, String title, String chemistry,
                       String classification, String crystal_system, ArrayList occurrence, String hardness, String specific_gravity,
                       String cleavage, String crustal_abundance, String economic_value){
        super(id, fileName, imageName, card_type, title);

        this.chemistry = chemistry;
        this.classification = classification;
        this.crystal_system = crystal_system;
        this.occurrence = occurrence;
        this.hardness = hardness;
        this.specific_gravity = specific_gravity;
        this.cleavage = cleavage;
        this.crustal_abundance = crustal_abundance;
        this.economic_value = economic_value;
    }
    public void toPrntString(){
        System.out.println("Title - " + title);
        System.out.println("Card Type - " + card_type);
        System.out.println("Chemistry - " + chemistry);
        System.out.println("Classification - " + classification);
        System.out.println("Crystal System  - " + crystal_system);
        System.out.println("Occurrence  - " + occurrence.toString());
        System.out.println("Hardness  - " + hardness);
        System.out.println("Specific Gravity  - " + specific_gravity);
        System.out.println("Cleavage  - " + cleavage);
        System.out.println("Crustal Abundance  - " + crustal_abundance);
        System.out.println("Economic Value  - " + economic_value);

    }
}
