import com.thoughtworks.xstream.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;

public class ServerIncontriParrocchiali {
    public static void main(String[] args){
        System.out.println("Attivazione server ...");
        try (ServerSocket servs = new ServerSocket(8080)){
            System.out.println("Server in esecuzione!");
            while(true){
                try (Socket sock = servs.accept();
                    DataInputStream din = new DataInputStream(sock.getInputStream());){
                    XStream xs = new XStream();
                    String xml = din.readUTF();
                    System.out.println(xml);
                    
                    File tmp = new File("myfiles/tmp.xml");
                    tmp.createNewFile();
                    Files.write(Paths.get("myfiles/tmp.xml"), xml.getBytes()); //(00)
                    Info log = (Info)xs.fromXML(xml);
                    boolean result = ValidaXML.valida("myfiles/tmp.xml", "myfiles/ParametriInfoLog.xsd");
                    if(result)
                        System.out.println("Validazione riuscita!");
                    else
                        System.out.println("Validazione NON riuscita!");
                    tmp.delete(); 
                    Files.write(Paths.get("myfiles/datiLog.xml"), xml.getBytes(), StandardOpenOption.APPEND);
               } catch (IOException ex){
                    System.out.println(ex.getMessage());
               }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}