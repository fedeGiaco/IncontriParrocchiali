
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import javafx.collections.*;
import javafx.scene.control.*;

public class Cache{
    String fileName = "./config/cache.bin";
    private String[] datiDaSalvare,datiDaRecuperare;
    private int salva;
    private int conta;
    private long numeroPosto;
    private String numeroPostoS;
    private String nome;
    private String cognome;
    private String sesso;
    private String classe;
    
    public void salvaCache(List<Prenotazione> ultimaRiga, LocalDate giornoPrenotazione){
        datiDaSalvare = new String[6];
        salva = 0;
        conta = 0;
        numeroPosto = ultimaRiga.get(0).getNumeroPosto();
        numeroPostoS = Long.toString(numeroPosto);
        nome = ultimaRiga.get(0).getNome();
        cognome = ultimaRiga.get(0).getCognome();
        sesso = ultimaRiga.get(0).getSesso();
        classe = ultimaRiga.get(0).getClasse();
        
        datiDaSalvare[0] = giornoPrenotazione.toString();
        datiDaSalvare[1] = numeroPostoS;
        datiDaSalvare[2] = nome;
        datiDaSalvare[3] = cognome;
        datiDaSalvare[4] = sesso;
        datiDaSalvare[5] = classe;
        
        String a = "...";
        if(numeroPosto!=0)
            salva++;
        for(int i=0;i<6;i++){
            if(datiDaSalvare[i].equals(a)) {
                conta++;
            }
        }
        
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            if(salva!=0 || conta<4){
              for (String n : datiDaSalvare) {
                out.writeUTF(n);
              }
            }
            else{
                for (String n : datiDaSalvare) {
                    out.writeUTF("Vuota");
              }
            }
        }catch(IOException e){}
    }
    
    public String[] recuperaCache(){
        datiDaRecuperare = new String[6];
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
          for (int i = 0; i < datiDaRecuperare.length; i++) {
            datiDaRecuperare[i] = in.readUTF();
          }
        }catch(IOException e){}
        return datiDaRecuperare;
    }
}