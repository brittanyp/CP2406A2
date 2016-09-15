/**
 * Created by Brit on 9/15/2016.
 */
public class STTrumpCard extends STCard {
    public String subtitle;
    public STTrumpCard(int id, String fileName, String imageName, String card_type, String title, String subtitle) {
        super(id,fileName,imageName,card_type,title);
        this.subtitle = subtitle;
    }

    public void toPrntString(){
        System.out.println("Title - " + title);
        System.out.println("Card Type - " + card_type);
        System.out.println("Subtitle - " + subtitle);
    }
}
