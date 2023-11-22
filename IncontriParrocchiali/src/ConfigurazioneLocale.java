import com.thoughtworks.xstream.XStream;
import java.io.*;
import java.nio.file.*;

class ParametriPrenotazioni implements Serializable{
    public int altezzaTabella;
    public int giorniPrecedentiN;
    
    public ParametriPrenotazioni(int aT, int gPn){
        altezzaTabella = aT;
        giorniPrecedentiN = gPn;
    }
}

class ParametriNet implements Serializable{
    public String indirizzoClient,indirizzoServer;
    public int portaLog;
    public ParametriNet(String iC, String iS, int pL){
        indirizzoClient = iC;
        indirizzoServer = iS;
        portaLog = pL;
    }
}

class ParametriDB implements Serializable{
    public String nomeUtenteDB;
    public String passwordDB;
    public String indirizzoDB;
    public String databaseDB;
    public int portaDB;
    
    public ParametriDB(String nDB, String pDB, String iDB, String dDB, int poDB){
        nomeUtenteDB = nDB;
        passwordDB = pDB;
        indirizzoDB = iDB;
        databaseDB = dDB;
        portaDB = poDB;
    }
}

class Parametri implements Serializable{
    ParametriPrenotazioni pPrenotazioni;
    ParametriNet pNet;
    ParametriDB pDB;
    public Parametri(ParametriPrenotazioni pPrenotazioni, ParametriNet pNet, ParametriDB pDB){
        this.pPrenotazioni = pPrenotazioni;
        this.pNet = pNet;
        this.pDB = pDB;
    }
}


public class ConfigurazioneLocale implements Serializable{
    Parametri p;
    XStream xs = new XStream();
    String x;
    
    public ConfigurazioneLocale(){
        //salvaParametri();
    }
    
    public void salvaParametri(){
        try{
            p = new Parametri(
                    new ParametriPrenotazioni(180,7),
                    new ParametriNet("localhost","localhost",8080),
                    new ParametriDB("root","","localhost","incontriparrocchiali",3306)
            );
        }catch(Exception e){}
        String x = xs.toXML(p);
        try { Files.write(Paths.get("config/cfg.xml"), x.getBytes());}catch(IOException e){}
    }
    
    public Parametri recuperaParametri(){
        try{
            String fileXSD = "myfiles/ParametriConfigurazioneLocale.xsd";
            String fileXML = "config/cfg.xml";
            x = new String(Files.readAllBytes(Paths.get("config/cfg.xml")));
            boolean esito = ValidaXML.valida(fileXML, fileXSD);
            if(esito){
                System.out.println("Validazione riuscita!");
            }
            else{
                System.out.println("Validazione NON riuscita!");
            }
        }catch(IOException e){e.printStackTrace();}
        Parametri p = (Parametri)xs.fromXML(x);
        return p;
    }
}