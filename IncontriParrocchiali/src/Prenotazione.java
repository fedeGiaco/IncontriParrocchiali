import java.io.*;
import javafx.beans.property.*;

public class Prenotazione implements Serializable{
    protected SimpleLongProperty numeroPosto;
    protected SimpleStringProperty nome;
    protected SimpleStringProperty cognome;
    protected SimpleStringProperty sesso;
    protected SimpleStringProperty classe;
    
    public Prenotazione(){}
    public Prenotazione(long nP, String n, String c, String s, String cl){
        numeroPosto = new SimpleLongProperty(nP);
        nome = new SimpleStringProperty(n);
        cognome = new SimpleStringProperty(c);
        sesso = new SimpleStringProperty(s);
        classe = new SimpleStringProperty(cl);
    }
    
    public void setNumeroPosto(long fName) {numeroPosto.set(fName);}
    public void setNome(String fName) {nome.set(fName);}
    public void setCognome(String fName) {cognome.set(fName);}
    public void setSesso(String fName) {sesso.set(fName);}
    public void setClasse(String fName) {classe.set(fName);}
    
    public long getNumeroPosto() {return numeroPosto.get();}
    public String getNome() {return nome.get();}
    public String getCognome() {return cognome.get();}
    public String getSesso() {return sesso.get();}
    public String getClasse() {return classe.get();}
    
}