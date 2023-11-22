import java.sql.*;
import java.util.*;
        
public class GestoreDB {
    private Connection co; 
    private PreparedStatement prenotazioniGiorno,recuperaPosti,inserisciPrenotazioneGiorno,rimuoviPrenotazioneGiorno; 

    private String indirizzoIP;
    private int porta;
    private String database;
    private String utente;
    private String password;
    
    private String connessione;
    
    public GestoreDB(Parametri p){
        indirizzoIP = p.pDB.indirizzoDB;
        porta = p.pDB.portaDB;
        database = p.pDB.databaseDB;
        utente = p.pDB.nomeUtenteDB;
        password = p.pDB.passwordDB;
        
        connessione = "jdbc:mysql://"+indirizzoIP+":"+porta+"/"+database;
        
        try {   
            co = DriverManager.getConnection(connessione, utente, password);   
            prenotazioniGiorno = co.prepareStatement("SELECT* FROM prenotazione WHERE giornoPrenotazione = ?");
            recuperaPosti = co.prepareStatement("SELECT numeroPosto FROM prenotazione WHERE giornoPrenotazione = ?");
            inserisciPrenotazioneGiorno = co.prepareStatement("INSERT INTO prenotazione SET idPrenotazione = DEFAULT, giornoPrenotazione = ?, numeroPosto = ?, nome = ?, cognome = ?, sesso = ?, classe = ?");
            rimuoviPrenotazioneGiorno = co.prepareStatement("DELETE P.* FROM prenotazione P WHERE giornoPrenotazione = ? AND numeroPosto = ? AND nome = ? AND cognome = ? AND sesso = ? AND classe = ?");
        } catch (SQLException e) {System.err.println(e.getMessage());} 
    }
    
    public List<Prenotazione> caricaPrenotazioniDB(java.sql.Date n) {
     List<Prenotazione> listaPrenotazioni = new ArrayList<>();
     try { 
           prenotazioniGiorno.setDate(1,n);
           ResultSet rs = prenotazioniGiorno.executeQuery();
           while (rs.next())
              listaPrenotazioni.add(new Prenotazione(rs.getInt("numeroPosto"), rs.getString("nome"), rs.getString("cognome"), rs.getString("sesso"), rs.getString("classe")));
      } catch (SQLException e) {System.err.println(e.getMessage());} 
     return listaPrenotazioni;
    }
    
    public List<Integer> recuperaPostiDB(java.sql.Date n){
        List<Integer> listaPosti = new ArrayList<>();
     try { 
           recuperaPosti.setDate(1,n);
           ResultSet rs = recuperaPosti.executeQuery();
           while (rs.next()){
              listaPosti.add(rs.getInt("numeroPosto"));
           }
      } catch (SQLException e) {System.err.println(e.getMessage());} 
     return listaPosti;
    }
    
    public void inserisciPrenotazioniDB(java.sql.Date n, long nP, String nm, String c, String s, String cl) {
        int x = (int)nP;
        try{
            inserisciPrenotazioneGiorno.setDate(1, n);
            inserisciPrenotazioneGiorno.setInt(2, x);
            inserisciPrenotazioneGiorno.setString(3,nm);
            inserisciPrenotazioneGiorno.setString(4,c);
            inserisciPrenotazioneGiorno.setString(5,s);
            inserisciPrenotazioneGiorno.setString(6,cl);
            inserisciPrenotazioneGiorno.execute();
        }catch(SQLException e){System.err.println(e.getMessage());}
    }
    
    public void rimuoviPrenotazioniDB(java.sql.Date n, long nP, String nm, String c, String s, String cl) {
        int x = (int)nP;
        try{
            rimuoviPrenotazioneGiorno.setDate(1, n);
            rimuoviPrenotazioneGiorno.setInt(2, x);
            rimuoviPrenotazioneGiorno.setString(3,nm);
            rimuoviPrenotazioneGiorno.setString(4,c);
            rimuoviPrenotazioneGiorno.setString(5,s);
            rimuoviPrenotazioneGiorno.setString(6,cl);
            rimuoviPrenotazioneGiorno.execute();
        }catch(SQLException e){System.err.println(e.getMessage());}
    }
}
