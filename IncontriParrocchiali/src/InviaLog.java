import com.thoughtworks.xstream.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.*;

class Info{
    public String nomeApplicazione;
    public String nomeEvento;
    public String indirizzoIPClient;
    public String momento;    
    public Info(String nA, String nE, String iC, String ora){
        nomeApplicazione = nA;
        nomeEvento = nE;
        indirizzoIPClient = iC;
        momento = ora;
    }
}

public class InviaLog{
    private String nomeEvento;
    
    private String ipClient;
    private int porta;
    
    
    public InviaLog(String nA, String nE, Date ora, String ipClient, int porta){
        this.ipClient = ipClient;
        this.porta = porta;
        nomeEvento = nE;
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Info newEvento = new Info(nA,nE,ipClient,formatter.format(ora));
        serializzaXML(newEvento);
    }
    
    public void serializzaXML(Info newEvento){
        XStream xs = new XStream();
        try(    Socket s = new Socket(ipClient,porta);
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            ){
                String x = (new XStream()).toXML(newEvento);
                System.out.println("Invio al server l'evento: "+nomeEvento);
                dos.writeUTF(x);
        }catch(IOException e){e.printStackTrace();}
    }
    
}