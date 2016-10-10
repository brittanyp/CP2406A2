package GUI; /**
 * Created by Brit on 9/13/2016.
 */
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

//Class dedicated to reading xml file

public class ReadFile {
    public static STDeck ReadTheFile() throws Exception{
        //File dir
        File currentDir = new File("");
        String strCurrentDir = currentDir.toString();
        String filePath = "MstCards_151021.xml";
        //Path to file
        STDeck deck = new STDeck();
        ArrayList <Object> card = new ArrayList<Object> ();
        ArrayList<String> occurrenceArray = new ArrayList<String>();

        //Build document
        File xmlFile = new File(strCurrentDir + filePath);
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
            Node cardNode = deckNodes.item(i);
            Element cardElement = (Element) cardNode;

            //Check what card type
            Node cardTypeNode = cardElement.getElementsByTagName("key").item(3);
            Element cardTypeElement = (Element) cardTypeNode;
            String cardType = cardTypeElement.getTextContent();

            if (cardType.equals("play")) {

                //Adds first 6 NormalDetails
                addNormalDetails(cardElement, 0, 6, card);

                //ArrayDetail start
                Node theValueNode = cardElement.getElementsByTagName("array").item(0);
                Element valueElement = (Element) theValueNode;
                NodeList array = valueElement.getElementsByTagName("string");
                int amntinArray = array.getLength();
                //Iterate through array details
                for (int x = 0; x < array.getLength(); x++) {
                    occurrenceArray.add(array.item(x).getTextContent());
                }
                card.add(occurrenceArray);

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
                deck.addPlayCard(i, completeCard);
            }
            else if(cardType.equals("trump")){
                addNormalDetails(cardElement, 0, 4, card);
                STTrumpCard completeCard = new STTrumpCard(i, card.get(0).toString(), card.get(1).toString(),
                        cardType, card.get(2).toString(), card.get(3).toString());
                card = new ArrayList<Object> ();
                deck.addTrumpCard(i, completeCard);
            }
        }
        return deck;
    }

    public static void addNormalDetails(Element cElement, int start, int end, ArrayList<Object> cardArray){
        ArrayList<Object> card = cardArray;
        Element cardElement = cElement;
        int indexStart = start;
        int indexEnd = end;
        //Iterate through details
        for (int index = indexStart; index < indexEnd; index++) {
            //Normal Detail
            Node theValueNode = cardElement.getElementsByTagName("string").item(index);
            Element valueElement = (Element) theValueNode;
            card.add(index ,valueElement.getTextContent());
        }
    }
    public static void addLastNormalDetails(Element cElement, int start, int end, ArrayList<Object> cardArray){
        ArrayList<Object> card = cardArray;
        Element cardElement = cElement;
        int indexStart = start;
        int indexEnd = end;
        int placement = 7;
        //Iterate through details
        for (int index = indexStart; index < indexEnd; index++) {
            //Normal Detail
            Node theValueNode = cardElement.getElementsByTagName("string").item(index);
            Element valueElement = (Element) theValueNode;
            card.add(placement, valueElement.getTextContent());
            placement= placement + 1;
        }
    }

    public static String getFileString(String dir) {
        FileSystem fs = FileSystems.getDefault();
        Path path = fs.getPath(dir);
        String fileString = path.toString();
        System.out.println(fileString);
        return fileString;
    }
}

