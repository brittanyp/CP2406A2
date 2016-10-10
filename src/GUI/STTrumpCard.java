package GUI;

/**
 * Created by Brit on 9/15/2016.
 */
//Class Super Trump Trump Card extends on GUI.STCard

public class STTrumpCard extends STCard {
    public String subtitle;
    public STTrumpCard(int id, String fileName, String imageName, String card_type, String title, String subtitle) {
        super(id,fileName,imageName,card_type,title);
        this.subtitle = subtitle;
    }

    public String getSubtitle(){
        return subtitle;
    }

    public void toPrntString(){
        //Prints information the card stores
        System.out.println("Title - " + title);
        System.out.println("Card Type - " + card_type);
        System.out.println("Subtitle - " + subtitle);
    }
}
