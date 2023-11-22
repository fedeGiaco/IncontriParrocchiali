import java.io.*;
import java.time.*;
import java.util.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.scene.control.cell.*;
import javafx.scene.text.*;
import javafx.util.*;
import javafx.util.converter.*;

public class TabellaPrenotazioni extends TableView<Prenotazione> implements Serializable{
    private ObservableList<Prenotazione> data;
    private Label titoloTabella_la;
    GestoreDB db;
    
    public TabellaPrenotazioni(Parametri p){
        db = new GestoreDB(p);
        
        titoloTabella_la = new Label("Prenotazioni");
        titoloTabella_la.setFont(new Font("Arial", 20));
 
        setEditable(true);
        Callback<TableColumn, TableCell> cellFactory =
             new Callback<TableColumn, TableCell>() {
                 public TableCell call(TableColumn p) {
                    return new EditingCell();
                 }
             };
 
        TableColumn numeroPosto = new TableColumn("Posto");
        numeroPosto.setMinWidth(100);
        numeroPosto.setCellValueFactory(
            new PropertyValueFactory<Prenotazione, Long>("numeroPosto"));
        numeroPosto.setCellFactory(cellFactory);
        numeroPosto.setCellFactory(TextFieldTableCell.<Prenotazione, Number>forTableColumn(new NumberStringConverter()));
        numeroPosto.setOnEditCommit(
            new EventHandler<CellEditEvent<Prenotazione, Long>>() {
                @Override
                public void handle(CellEditEvent<Prenotazione, Long> t) {
                    ((Prenotazione) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNumeroPosto(t.getNewValue());
                }
            }
        );
        
        TableColumn nome = new TableColumn("Nome");
        nome.setMinWidth(100);
        nome.setCellValueFactory(
            new PropertyValueFactory<Prenotazione, String>("nome"));
        nome.setCellFactory(cellFactory);
        nome.setOnEditCommit(
            new EventHandler<CellEditEvent<Prenotazione, String>>() {
                @Override
                public void handle(CellEditEvent<Prenotazione, String> t) {
                    ((Prenotazione) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNome(t.getNewValue());
                }
            }
        );
        
        TableColumn cognome = new TableColumn("Cognome");
        cognome.setMinWidth(100);
        cognome.setCellValueFactory(
            new PropertyValueFactory<Prenotazione, String>("cognome"));
        cognome.setCellFactory(cellFactory);
        cognome.setOnEditCommit(
            new EventHandler<CellEditEvent<Prenotazione, String>>() {
                @Override
                public void handle(CellEditEvent<Prenotazione, String> t) {
                    ((Prenotazione) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCognome(t.getNewValue());
                }
            }
        );
        
        TableColumn sesso = new TableColumn("Sesso");
        sesso.setMinWidth(100);
        sesso.setCellValueFactory(
            new PropertyValueFactory<Prenotazione, String>("sesso"));
        sesso.setCellFactory(cellFactory);
        sesso.setOnEditCommit(
            new EventHandler<CellEditEvent<Prenotazione, String>>() {
                @Override
                public void handle(CellEditEvent<Prenotazione, String> t) {
                    ((Prenotazione) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSesso(t.getNewValue());
                }
            }
        );
        
        TableColumn classe = new TableColumn("Classe");
        classe.setMinWidth(100);
        classe.setCellValueFactory(
            new PropertyValueFactory<Prenotazione, String>("classe"));
        classe.setCellFactory(cellFactory);
        classe.setOnEditCommit(
            new EventHandler<CellEditEvent<Prenotazione, String>>() {
                @Override
                public void handle(CellEditEvent<Prenotazione, String> t) {
                    ((Prenotazione) t.getTableView().getItems().get(t.getTablePosition().getRow())).setClasse(t.getNewValue());
                }
            }
        );
        
        data = FXCollections.observableArrayList();
        setItems(data);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setMaxHeight(p.pPrenotazioni.altezzaTabella);
        getColumns().addAll(numeroPosto, nome, cognome, sesso, classe);
    }
    public void aggiornaPrenotazioniTableView(List<Prenotazione> prenotazioni, String[] datiDaRecuperare, LocalDate giornoComparabile) {
        data.clear();
        data.addAll(prenotazioni);
        if(datiDaRecuperare != null){
            String a = giornoComparabile.toString();
            String b = datiDaRecuperare[0].toString();
               if(a.equals(b)){
                List<Prenotazione> oldPrenotazione = new ArrayList<>();
                Long numeroPosto = Long.parseLong(datiDaRecuperare[1]);
                String nome = datiDaRecuperare[2];
                String cognome = datiDaRecuperare[3];
                String sesso = datiDaRecuperare[4];
                String classe = datiDaRecuperare[5];
                oldPrenotazione.add(new Prenotazione(numeroPosto,nome,cognome,sesso,classe));
                data.addAll(oldPrenotazione);
                return;
            }
        }
        List<Prenotazione> newPrenotazione = new ArrayList<>();
        newPrenotazione.add(new Prenotazione(0,"...","...","...","..."));
        data.addAll(newPrenotazione);
    }
    public boolean inserisciPrenotazioneTableView(java.sql.Date giornoPrenotazione){ 
        Prenotazione product = new Prenotazione();
        String[] newDati = new String[4];
     
        int dim = getItems().size() - 1;
        
        long numeroPosto = getItems().get(dim).getNumeroPosto();
        String nome = getItems().get(dim).getNome();
        String cognome = getItems().get(dim).getCognome();
        String sesso = getItems().get(dim).getSesso();
        String classe = getItems().get(dim).getClasse();
        
        boolean verificaDati = false;
        String confronta = "...";
        if(nome.equals(confronta) || cognome.equals(confronta) || sesso.equals(confronta) || classe.equals(confronta))
            verificaDati = true;
        
        if (numeroPosto>10 || numeroPosto==0)
            verificaDati = true;
        
        List<Integer> postiPrenotati = new ArrayList<>();
        
        postiPrenotati = db.recuperaPostiDB(giornoPrenotazione);
        for(int i=0;i<postiPrenotati.size();i++){
            int verifica = postiPrenotati.get(i);
            if(verifica == numeroPosto)
                verificaDati = true;
        }
        
        if (verificaDati == false){
            List<Prenotazione> newPrenotazione = new ArrayList<>();
            db.inserisciPrenotazioniDB(giornoPrenotazione,numeroPosto,nome,cognome,sesso,classe);
            newPrenotazione.add(new Prenotazione(numeroPosto,nome,cognome,sesso,classe));

            List<Prenotazione> newPrenotazioneDB = new ArrayList<>();
            newPrenotazioneDB.add(new Prenotazione(0,"...","...","...","..."));
            data.addAll(newPrenotazioneDB); 
            
            return true;
        }
        
        return false;
    }
    public boolean rimuoviPrenotazioneTableView(java.sql.Date giornoPrenotazione){ 
        Prenotazione selectedItem = getSelectionModel().getSelectedItem();
        
        long numeroRimuovi = getSelectionModel().getSelectedItem().getNumeroPosto();
        String nome = getSelectionModel().getSelectedItem().getNome();
        String cognome = getSelectionModel().getSelectedItem().getCognome();
        String sesso = getSelectionModel().getSelectedItem().getSesso();
        String classe = getSelectionModel().getSelectedItem().getClasse();
        
        boolean verificaDati = false;
        String confronta = "...";
        if(numeroRimuovi == 0 || nome.equals(confronta) || cognome.equals(confronta) || sesso.equals(confronta) || classe.equals(confronta))
            verificaDati = true;
        
        if(verificaDati == false){
            getItems().remove(selectedItem);
            db.rimuoviPrenotazioniDB(giornoPrenotazione,selectedItem.getNumeroPosto(),selectedItem.getNome(),selectedItem.getCognome(),selectedItem.getSesso(),selectedItem.getClasse());
            return true;
        }
        
        return false;
    }   
    public List<Prenotazione> recuperaDatiTableView(){
        int ultimo = data.size() - 1;
        List<Prenotazione> ultimaRiga = new ArrayList<>();
        Long numeroPosto = data.get(ultimo).getNumeroPosto();
        String nome = data.get(ultimo).getNome();
        String cognome = data.get(ultimo).getCognome();
        String sesso = data.get(ultimo).getSesso();
        String classe = data.get(ultimo).getClasse();
        ultimaRiga.add(new Prenotazione(numeroPosto,nome,cognome,sesso,classe));
        return ultimaRiga;
    }
}