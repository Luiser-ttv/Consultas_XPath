
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rioja
 */
public class Gestionar_XPath {

    Document doc;
    XPath xpath;
    
    public int abrir_XML(File fichero) {
       
        doc = null;

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            doc = builder.parse(fichero);

            
            xpath = (XPath) XPathFactory.newInstance().newXPath();
            return 0;

        } catch (Exception e) {
            return -1;
        }
    }
    
    private String[] procesarLibro(Node n) {
        String datos[] = new String[3];
        Node ntemp = null;
        int contador = 1;

        datos[0] = n.getAttributes().item(0).getNodeValue();

        NodeList nodos = n.getChildNodes();

        for (int i = 0; i < nodos.getLength(); i++) {

            ntemp = nodos.item(i);

            if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                //datos[contador] = ntemp.getChildNodes().item(0).getNodeValue();
                datos[contador] = ntemp.getFirstChild().getNodeValue();
                contador++;
            }
        }
        return datos;
    }

    

   public String Ejecutar_XPath(String consulta) {
       
       String salida = "";
       
       try {

           XPathExpression exp = xpath.compile(consulta);
           
           Object result = exp.evaluate(doc, XPathConstants.NODESET);
           NodeList listaNodos = (NodeList) result;
           
           
           for(int i = 0; i<listaNodos.getLength(); i++){
               //salida = salida + "\n" + listaNodos.item(i).getFirstChild().getNodeValue();
               if(listaNodos.item(i).getNodeName().equals("Libro")){
               
                  String datos_nodo[] = procesarLibro(listaNodos.item(i));
                salida = salida + "\n " + "Publicado en: " + datos_nodo[0];
                salida = salida + "\n " + "El titulo es: " + datos_nodo[1];
                salida = salida + "\n " + "El autor es: " + datos_nodo[2];
                salida = salida + "\n ---------------------";
                
               }else{
                   salida = salida + "\n" + listaNodos.item(i).getFirstChild().getNodeValue();
               }
               
           }
           
            return salida;

        } catch (Exception e) {
            return "Error en la consulta";
        }
       
    }
    
}
