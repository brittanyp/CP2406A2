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

public class ReadFile {

    public static void main(String[] args) throws Exception{
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
                addNormalDetails(cardElement, i, 0, 6);
                //ArrayDetail start
                Node theValueNode = cardElement.getElementsByTagName("array").item(0);
                Element valueElement = (Element) theValueNode;
                NodeList array = valueElement.getElementsByTagName("string");

                int amntinArray = array.getLength();
                //Todo: Delete this line below, testing
                //System.out.println("Amt in array " + amntinArray);
                for (int x = 0; x < array.getLength(); x++) {
                    System.out.println(array.item(x).getTextContent());
                }

                //ArrayDetail End
                addNormalDetails(cardElement, i, 7 + amntinArray, 14);
            }
            else{
                System.out.println("Trump card");
            }


        }

    }

    public static void addNormalDetails(Element cElement, int indexI, int start, int end){
        Element cardElement = cElement;
        int indexStart = start;
        int indexEnd = end;
        int i = indexI;
        for (int index = indexStart; index < indexEnd; index++) {
            //Normal Detail
            Node theValueNode = cardElement.getElementsByTagName("string").item(index);
            Element valueElement = (Element) theValueNode;
            System.out.println(i + "\n" + valueElement.getTextContent());
        }
    }
}

