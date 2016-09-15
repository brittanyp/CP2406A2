/**
 * Created by Brit on 9/13/2016.
 */
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class ReadFile {

    public static void main(String[] args) throws Exception{
        ArrayList<STCard> deck = new ArrayList<STCard>();
        ArrayList <Object> card = new ArrayList<Object> ();
        ArrayList<String> occurrenceArray = new ArrayList<String>();

        //Build document
        File xmlFile = new File("F:\\Uni\\SP_3_IT\\CP2406\\Assignment1\\CP2406A1\\CP2406A1\\MstCards_151021v3.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

        //Investigate node hierarchy
        NodeList fileNodes = document.getElementsByTagName("dict");
        Node fileNode = fileNodes.item(0);
        Element deckElement = (Element) fileNode;
        NodeList deckNodes = deckElement.getElementsByTagName("dict");

        //Iterate through cards
        for(int i = 0; i < deckNodes.getLength(); i++){
            //Todo: Delete this line below, testing
            //int i = 0;
            Node cardNode = deckNodes.item(i);
            Element cardElement = (Element) cardNode;

            //Check what card type
            Node cardTypeNode = cardElement.getElementsByTagName("key").item(3);
            Element cardTypeElement = (Element) cardTypeNode;
            String cardType = cardTypeElement.getTextContent();
            //Todo: Delete this line below, testing
            //System.out.println(cardType);

            if (cardType.equals("play")) {
                System.out.println("---------" + i + "---------");
                //Adds first 6 NormalDetails
                addNormalDetails(cardElement, 0, 6, card);

                //ArrayDetail start
                Node theValueNode = cardElement.getElementsByTagName("array").item(0);
                Element valueElement = (Element) theValueNode;
                NodeList array = valueElement.getElementsByTagName("string");
                int amntinArray = array.getLength();
                //Todo: Delete this line below, testing
                //System.out.println("    Amt in array: " + amntinArray + "\n     " + (amntinArray + 6));
                //Iterate through array details
                for (int x = 0; x < array.getLength(); x++) {
                    System.out.println(array.item(x).getTextContent());
                    occurrenceArray.add(array.item(x).getTextContent());
                }
                card.add(occurrenceArray);
                //int amntinArray = addArrayDetail(cardElement);

                //Adds the rest of NormalDetails
                addLastNormalDetails(cardElement, 6 + amntinArray, (14 - 3 + amntinArray), card);

                //Create card in class Card
                STPlayCard completeCard = new STPlayCard(i, card.get(0).toString(), card.get(1).toString(),
                        cardType,card.get(2).toString(),card.get(3).toString(),card.get(4).toString(),
                        card.get(5).toString(),occurrenceArray, card.get(7).toString(),
                        card.get(8).toString(), card.get(9).toString(), card.get(10).toString(),
                        card.get(11).toString());
                occurrenceArray = new ArrayList<String>();
                card = new ArrayList<Object> ();
                deck.add(i, completeCard);
                completeCard.toPrntString();


            }
            else if(cardType.equals("trump")){
                System.out.println("Trump card");
                addNormalDetails(cardElement, 0, 4, card);
                STTrumpCard completeCard = new STTrumpCard(i, card.get(0).toString(), card.get(1).toString(),
                        cardType, card.get(2).toString(), card.get(3).toString());
                card = new ArrayList<Object> ();
                deck.add(i, completeCard);
                completeCard.toPrntString();
            }
        }
    }

    public static void addNormalDetails(Element cElement, int start, int end, ArrayList<Object> cardArray){
        ArrayList<Object> card = cardArray;
        Element cardElement = cElement;
        int indexStart = start;
        int indexEnd = end;
        //int i = indexI;
        //Iterate through details
        for (int index = indexStart; index < indexEnd; index++) {
            //Normal Detail
            Node theValueNode = cardElement.getElementsByTagName("string").item(index);
            Element valueElement = (Element) theValueNode;
            card.add(index ,valueElement.getTextContent());
            System.out.println(valueElement.getTextContent());
        }
    }
    public static void addLastNormalDetails(Element cElement, int start, int end, ArrayList<Object> cardArray){
        ArrayList<Object> card = cardArray;
        Element cardElement = cElement;
        int indexStart = start;
        int indexEnd = end;
        int okay = 7;
        //int i = indexI;
        //Iterate through details
        for (int index = indexStart; index < indexEnd; index++) {
            //Normal Detail
            Node theValueNode = cardElement.getElementsByTagName("string").item(index);
            Element valueElement = (Element) theValueNode;
            card.add(okay, valueElement.getTextContent());
            System.out.println(valueElement.getTextContent());
            okay= okay + 1;
        }
    }
}

