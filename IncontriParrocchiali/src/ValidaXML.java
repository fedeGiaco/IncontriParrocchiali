import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;

public class ValidaXML {
    public static boolean valida(String fileXML, String fileXSD){ //(00)
          System.out.println("Validazione file: " + fileXML);
          try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document d = db.parse(new File(fileXML));
            Schema s = sf.newSchema(new StreamSource(new File(fileXSD)));
            s.newValidator().validate(new DOMSource(d)); 
            return true;
         } catch (IOException | ParserConfigurationException | org.xml.sax.SAXException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
}