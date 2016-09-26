import java.util.ArrayList;

/**
 * Created by Brit on 9/15/2016.
 */
//Todo: create comments, pretty it up

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

    public String getChemistry(){
        return chemistry;
    }

    public String getClassification(){
        return classification;
    }

    public String getCrystal_system(){
        return crystal_system;
    }

    public ArrayList<String> getOccurrence(){
        return occurrence;
    }

    public String getHardness(){
        return hardness;
    }

    public String getSpecific_gravity(){
        return specific_gravity;
    }

    public String getCleavage(){
        return cleavage;
    }

    public String getCrustal_abundance(){
        return crustal_abundance;
    }
    public String getEconomic_value(){
        return economic_value;
    }

//    public String getHardness(){
//        String str;
//        str=hardness.split("-")[1];
//        //double highestHardness;
//        return str;
//    }

    public void toPrntString(){
        System.out.println("Title: " + title);
        System.out.println("Card Type: " + card_type);
        System.out.println("Chemistry: " + chemistry);
        System.out.println("Classification: " + classification);
        System.out.println("Crystal System: " + crystal_system);
        System.out.println("Occurrence: " + occurrence.toString());
        System.out.println("Hardness: " + hardness);
        System.out.println("Specific Gravity: " + specific_gravity);
        System.out.println("Cleavage: " + cleavage);
        System.out.println("Crustal Abundance: " + crustal_abundance);
        System.out.println("Economic Value: " + economic_value);

    }
}
