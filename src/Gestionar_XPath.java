
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
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

   public String Ejecutar_XPath(String consulta) {
       
       String salida = "";
       
       try {

           XPathExpression exp = xpath.compile(consulta);
           
           Object result = exp.evaluate(doc, XPathConstants.NODESET);
           NodeList listaNodos = (NodeList) result;
           
           for(int i = 0; i<listaNodos.getLength(); i++){
               salida = salida + "\n" + listaNodos.item(i).getFirstChild().getNodeValue();
           }
           
            return salida;

        } catch (Exception e) {
            return "Error en la consulta";
        }
       
    }
    
}
