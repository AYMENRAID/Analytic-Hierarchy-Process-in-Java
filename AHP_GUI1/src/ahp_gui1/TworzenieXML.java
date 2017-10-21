/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahp_gui1;

import java.io.File;
import java.io.StringWriter;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Un1kalny
 */

public class TworzenieXML {
    AHP ahp;
    NewJFrame frame;
    
    public TworzenieXML(AHP ahp, NewJFrame frame) {
        this.ahp = ahp;
        this.frame = frame;
    }
    
    void rekurencyjnePrzechodzenieKryteriow(Kryterium kr, Document doc, Element element) {
       
            Element kryterium = doc.createElement("kryterium");
            element.appendChild(kryterium);
            kryterium.setAttribute("name", kr.nazwa);
            if(kr.listaPodkryteriow != null) {
                String w = "";
            for(int k = 0; k < kr.listaPodkryteriow.size(); ++k) {
                for(int l = 0; l < kr.listaPodkryteriow.size(); ++l) {
                    w += ahp.wagi.get(kr)[k][l];
                    w += " ";
                }
            }
            w = w.substring(0, w.length() - 1);
            Element wagi = doc.createElement("wagi");
            kryterium.appendChild(wagi);
            wagi.appendChild(doc.createTextNode(w));
            }
            else {
                String p = "";
                        for(int k = 0; k < ahp.cele.size(); ++k) {
                            for(int l = 0; l < ahp.cele.size(); ++l) {
                                p += ahp.porownania.get(kr)[k][l];
                                p += " ";
                            }
                        }
                        p = p.substring(0, p.length() - 1);
                        Element porownanie = doc.createElement("porownanie");
                        kryterium.appendChild(porownanie);
                        porownanie.appendChild(doc.createTextNode(p));
            }
            
            if(kr.listaPodkryteriow != null)
                for(int i = 0; i < kr.listaPodkryteriow.size(); ++i) {
                    rekurencyjnePrzechodzenieKryteriow(kr.listaPodkryteriow.get(i), doc, kryterium);
                }
        
    }
    
    void stworzXML() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("ahp");
            doc.appendChild(rootElement);
            Element cele = doc.createElement("cele");
            rootElement.appendChild(cele);
            for(int i = 0; i < ahp.cele.size(); ++i) {
                Element cel = doc.createElement("cel");
                cele.appendChild(cel);
                cel.appendChild(doc.createTextNode(ahp.cele.get(i)));
            }
            Element kryteria = doc.createElement("kryteria");
            rootElement.appendChild(kryteria); 
            
            Iterator it = ahp.wagi.keySet().iterator();
            while(it.hasNext()) {
                Kryterium kryterium = (Kryterium) it.next();
                if(kryterium.nazwa.equalsIgnoreCase("NULL")) {
                    String w = "";
                    for(int k = 0; k < kryterium.listaPodkryteriow.size(); ++k) {
                        for(int l = 0; l < kryterium.listaPodkryteriow.size(); ++l) {
                            w += ahp.wagi.get(kryterium)[k][l];
                            w += " ";
                        }
                    }
                    w = w.substring(0, w.length() - 1);
                    Element wagi = doc.createElement("wagi");
                    kryteria.appendChild(wagi);
                    wagi.appendChild(doc.createTextNode(w));
                    break;
                }
            }
            
            
            for(int i = 0; i < ahp.kryteria.size(); ++i) {
                if (ahp.kryteria.get(i).parent == null)
                    rekurencyjnePrzechodzenieKryteriow(ahp.kryteria.get(i), doc, kryteria);
            }
   
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apatche.org/xslt}indent-amount","2");
            DOMSource source = new DOMSource(doc);
            StreamResult result1 = new StreamResult(new File("C:\\Users\\Un1kalny\\IdeaProjects\\BadaniaOperacyjne1\\xmlik123.xml"));
            StreamResult result2 = new StreamResult(System.out);
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));
            String output = writer.toString();
            transformer.transform(source, result1);   
            transformer.transform(source, result2);
            frame.p111.ustawXML(output);
            ahp.XML_CREATED = true;
            if(frame.p11.isVisible()) {
                frame.p111.setVisible(true);
                frame.p11.setVisible(false);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
