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

public class ReadFileold {

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
            Node cardNode = deckNodes.item(i);
            Element cardElement = (Element) cardNode;

            //Check what card type
            Node cardTypeNode = cardElement.getElementsByTagName("key").item(3);
            Element cardTypeElement = (Element) cardTypeNode;
            String cardType = cardTypeElement.getTextContent();
            //Todo: Delete this line below, testing
            //System.out.println(cardType);

            if (cardType.equals("play")) {
                //Shows key
                //Node theKey = cardElement.getElementsByTagName("key").item(0);
                //Element keyElement = (Element) theKey;
                //System.out.println("Key: " + keyElement.getTextContent());
                //Create array, sort key. Should this only be done once or not at all?

                //Shows Value
                
                for (int x = 0; x <= 11; x++) {
                    if (x!=7) {
                        Node theValueNode = cardElement.getElementsByTagName("string").item(x);
                        Element valueElement = (Element) theValueNode;
                        System.out.println(i + "\n" + valueElement.getTextContent());
                        //Todo: Delete this line below, testing
                        //System.out.println("Play card");
                        //Add "play" in last array position
                    }
                    else {
                        Node theValueArrayNode = cardElement.getElementsByTagName("array").item(0);
                        Element valueArrayElement = (Element) theValueArrayNode;
                        String ugh = valueArrayElement.getTextContent();
                        ugh.trim();
                        System.out.println(i + "\n" + ugh);
                        //System.out.println(i + "\n" + valueArrayElement.getTextContent());
                    }
                }
            }
            else{
                System.out.println("Trump card");
            }


        }

    }
}
