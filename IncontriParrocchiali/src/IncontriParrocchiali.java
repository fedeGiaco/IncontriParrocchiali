import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;


public class IncontriParrocchiali extends Application{
    
    private TabellaPrenotazioni tP;
    private DatePicker sceltaGiorno_dp;
    private Button aggiungiPrenotazione_bt,rimuoviPrenotazione_bt;
    private GraficoABarre presenze_gb;
    private Label titolo_la,sceltaGiorno_la,testoGrafico_la,sceltaGiorniIndietro_la,postiDisponibili_la, esitoPrenotazione_la;
    private TextField giorniIndietro_tf;
    private static LocalDate giornoPrenotazioneScelta_ld;
    private static Date nuovaData;
    private Group contenitoreGrafico_gr;
    private static LocalDate dataSalvataLocalDate;
    
    private static String nome,cognome,sesso,classe;
    private static long numeroPosto;
    int presente = 0;
    
    InviaLog iL;
    
    public void start(Stage stage){
        //Prelievo dati locali
        ConfigurazioneLocale cfg = new ConfigurazioneLocale();
        Parametri p = cfg.recuperaParametri(); 
        GestoreDB db = new GestoreDB(p);
        
        nuovaData = new Date(System.currentTimeMillis());
        iL = new InviaLog("IncontriParrocchiali","AVVIO",nuovaData,"localhost",8080);
    
        //Prelievo cache
        Cache datiCache = new Cache();
        String[] datiInCache = datiCache.recuperaCache();
        String testCache = "Vuota";
        
        //Titolo programma
        titolo_la = new Label("Incontri parrocchiali");
        postiDisponibili_la = new Label("");
        giornoPrenotazioneScelta_ld = LocalDate.now();
        //Scelta giorno
        sceltaGiorno_la = new Label("Scegli il giorno");
        //Scelta giorno tramite DatePicker
        if(testCache.equals(datiInCache[0]))
            sceltaGiorno_dp = new DatePicker(LocalDate.now());
        else{ 
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            sceltaGiorno_dp = new DatePicker(LocalDate.parse(datiInCache[0],format)); 
        }
        sceltaGiorno_dp.setOnAction(event -> {
            giornoPrenotazioneScelta_ld = sceltaGiorno_dp.getValue();
            iL = new InviaLog("IncontriParrocchiali","AggiornoGiornoPrenotazione",new Date(System.currentTimeMillis()),"localhost",8080);
            nuovaData = Date.valueOf(giornoPrenotazioneScelta_ld);
            List<Integer> postiOccupati = db.recuperaPostiDB(nuovaData);
            int quantiSono = 10 - postiOccupati.size();
            postiDisponibili_la.setText("Posti disponibili: "+quantiSono);
            tP.aggiornaPrenotazioniTableView(db.caricaPrenotazioniDB(nuovaData),datiInCache,giornoPrenotazioneScelta_ld);
        });
        
        
        //Tabella prenotazioni
        tP = new TabellaPrenotazioni(p);
        Date dateSql = nuovaData;
        tP.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            numeroPosto = tP.getSelectionModel().getSelectedItem().getNumeroPosto();
            nome = tP.getSelectionModel().getSelectedItem().getNome();
            cognome = tP.getSelectionModel().getSelectedItem().getCognome();
            sesso = tP.getSelectionModel().getSelectedItem().getSesso();
            classe = tP.getSelectionModel().getSelectedItem().getClasse();
            if(numeroPosto==0){
                contenitoreGrafico_gr.getChildren().clear();
                presenze_gb = new GraficoABarre(p);
                BarChart grafico = presenze_gb.getGrafico(p.pPrenotazioni.giorniPrecedentiN);
                contenitoreGrafico_gr.getChildren().add(grafico);
                testoGrafico_la.setText("Presenze giornaliere degli ultimi "+p.pPrenotazioni.giorniPrecedentiN+" giorni");
                presente = 0;
            }
            else{
                String confronta = "...";
                if(numeroPosto==0 || nome.equals(confronta) || cognome.equals(confronta) || sesso.equals(confronta) || classe.equals(confronta)){
                    contenitoreGrafico_gr.getChildren().clear();
                    presenze_gb = new GraficoABarre(p);
                    BarChart grafico = presenze_gb.getGrafico(p.pPrenotazioni.giorniPrecedentiN);
                    contenitoreGrafico_gr.getChildren().add(grafico);
                    testoGrafico_la.setText("Presenze giornaliere degli ultimi "+p.pPrenotazioni.giorniPrecedentiN+" giorni");
                    presente = 0;
                }
                else{
                    contenitoreGrafico_gr.getChildren().clear();
                    presenze_gb = new GraficoABarre(p);
                    BarChart grafico = presenze_gb.getGraficoUtente(p.pPrenotazioni.giorniPrecedentiN,nome,cognome);
                    contenitoreGrafico_gr.getChildren().add(grafico);
                    testoGrafico_la.setText("Presenze di "+nome+" "+cognome+" degli ultimi "+p.pPrenotazioni.giorniPrecedentiN+" giorni");
                    presente = 1;
                }
            }
            
        }
        });
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(datiInCache[0].equals(testCache))
            dataSalvataLocalDate = LocalDate.now();
        else
            dataSalvataLocalDate = LocalDate.parse(datiInCache[0],format); 
        Date dataSalvataDate = Date.valueOf(dataSalvataLocalDate);
        tP.aggiornaPrenotazioniTableView(db.caricaPrenotazioniDB(dataSalvataDate),datiInCache,dataSalvataLocalDate);
        tP.recuperaDatiTableView();
        
        
        
        esitoPrenotazione_la = new Label("Esito dell'operazione");
        esitoPrenotazione_la.setStyle("-fx-text-fill: blue");
        // Button modifica prenotazione
        aggiungiPrenotazione_bt = new Button("Aggiungi");
        aggiungiPrenotazione_bt.setOnAction((ActionEvent ev)->{
            iL = new InviaLog("IncontriParrocchiali","Inserita prenotazione",new Date(System.currentTimeMillis()),"localhost",8080); 
            boolean esitoInserimento = tP.inserisciPrenotazioneTableView(nuovaData);
            if(esitoInserimento){
                esitoPrenotazione_la.setText("Inserimento avvenuto!");
                esitoPrenotazione_la.setStyle("-fx-text-fill: green");
            }
            else{
                esitoPrenotazione_la.setText("Inserimento fallito!");
                esitoPrenotazione_la.setStyle("-fx-text-fill: red");
            }
            contenitoreGrafico_gr.getChildren().clear();
            presenze_gb = new GraficoABarre(p);
            BarChart grafico = presenze_gb.getGrafico(p.pPrenotazioni.giorniPrecedentiN);
            contenitoreGrafico_gr.getChildren().add(grafico);
        });
        rimuoviPrenotazione_bt = new Button("Rimuovi");
        rimuoviPrenotazione_bt.setOnAction((ActionEvent ev)->{
            iL = new InviaLog("IncontriParrocchiali","Rimozione prenotazione",new Date(System.currentTimeMillis()),"localhost",8080); 
            boolean esitoRimozione = tP.rimuoviPrenotazioneTableView(nuovaData);
            if(esitoRimozione){
                esitoPrenotazione_la.setText("Rimozione avvenuta!");
                esitoPrenotazione_la.setStyle("-fx-text-fill: green");
            }
            else{
                esitoPrenotazione_la.setText("Rimozione fallita!");
                esitoPrenotazione_la.setStyle("-fx-text-fill: red");
            }
            contenitoreGrafico_gr.getChildren().clear();
            presenze_gb = new GraficoABarre(p);
            BarChart grafico = presenze_gb.getGrafico(p.pPrenotazioni.giorniPrecedentiN);
            contenitoreGrafico_gr.getChildren().add(grafico);
        });
        
        List<Integer> postiOccupati = db.recuperaPostiDB(nuovaData);
        int quantiSono = 10 - postiOccupati.size();
        postiDisponibili_la.setText("Posti disponibili: "+quantiSono);
        
        
        // Scelta giorni indietro
        sceltaGiorniIndietro_la = new Label("Inserisci il numero di giorni all'indietro:");
        giorniIndietro_tf = new TextField();
        giorniIndietro_tf.setText(Integer.toString(p.pPrenotazioni.giorniPrecedentiN));
        HBox hb = new HBox();
        hb.getChildren().addAll(sceltaGiorniIndietro_la, giorniIndietro_tf);
        hb.setSpacing(10);
        
        //Gestore grafico
        contenitoreGrafico_gr = new Group();
        presenze_gb = new GraficoABarre(p);
        BarChart grafico = presenze_gb.getGrafico(p.pPrenotazioni.giorniPrecedentiN);
        contenitoreGrafico_gr.getChildren().add(grafico);
        
        giorniIndietro_tf.textProperty().addListener((observable, oldValue, newValue) -> {
            int newN;
            String a = "";
            if(giorniIndietro_tf.getText().equals(a))
                newN = 0;
            else
                newN = Integer.parseInt(giorniIndietro_tf.getText());
            iL = new InviaLog("IncontriParrocchiali","NuovoN",new Date(System.currentTimeMillis()),"localhost",8080);
            contenitoreGrafico_gr.getChildren().clear();
            if (presente==0){
                BarChart graficoNew = presenze_gb.getGrafico(newN);
                contenitoreGrafico_gr.getChildren().add(graficoNew);
                testoGrafico_la.setText("Presenze di tutti degli ultimi "+newN+" giorni");
            }
            else{
                String confronta = "...";
                boolean verificaDati = false;
                if(numeroPosto==0 || nome.equals(confronta) || cognome.equals(confronta) || sesso.equals(confronta) || classe.equals(confronta)){
                    verificaDati = true;
                }
                if(!verificaDati){
                    BarChart graficoNew = presenze_gb.getGraficoUtente(newN,nome,cognome);
                    contenitoreGrafico_gr.getChildren().add(graficoNew);
                    testoGrafico_la.setText("Presenze di "+nome+" "+cognome+" degli ultimi "+newN+" giorni");
                }
            }
        });
        
        //Scritta post-grafico
        testoGrafico_la = new Label("Presenze giornaliere degli ultimi "+p.pPrenotazioni.giorniPrecedentiN+" giorni");
        testoGrafico_la.setLayoutY(200);

        //Modifiche estetiche
        titolo_la.setFont(new Font("Arial", 25));
        titolo_la.setLayoutX(270);
        titolo_la.setLayoutY(15);
        tP.setLayoutX(40);
        tP.setLayoutY(60);
        sceltaGiorno_la.setLayoutX(615);
        sceltaGiorno_la.setLayoutY(50);
        sceltaGiorno_la.setFont(new Font("Arial", 16));
        sceltaGiorno_dp.setLayoutX(580);
        sceltaGiorno_dp.setLayoutY(80);
        postiDisponibili_la.setLayoutX(610);
        postiDisponibili_la.setLayoutY(120);
        postiDisponibili_la.setFont(new Font("Arial", 15));
        aggiungiPrenotazione_bt.setLayoutX(600);
        aggiungiPrenotazione_bt.setLayoutY(170);
        rimuoviPrenotazione_bt.setLayoutX(680);
        rimuoviPrenotazione_bt.setLayoutY(170);
        
        esitoPrenotazione_la.setLayoutX(605);
        esitoPrenotazione_la.setLayoutY(210);
        esitoPrenotazione_la.setFont(new Font("Arial", 15));
        sceltaGiorniIndietro_la.setFont(new Font("Arial", 16));
        giorniIndietro_tf.setMinWidth(50);
        hb.setLayoutX(160);
        hb.setLayoutY(270);
        sceltaGiorniIndietro_la.setLayoutY(20);
        contenitoreGrafico_gr.setLayoutX(20);
        contenitoreGrafico_gr.setLayoutY(300);
        testoGrafico_la.setFont(new Font("Arial", 16));
        testoGrafico_la.setLayoutX(230);
        testoGrafico_la.setLayoutY(710);
        stage.setOnCloseRequest((WindowEvent we) -> {datiCache.salvaCache(tP.recuperaDatiTableView(),sceltaGiorno_dp.getValue());iL = new InviaLog("IncontriParrocchiali","CHIUSURA",new Date(System.currentTimeMillis()),"localhost",8080);});
        Group root;
        root = new Group(titolo_la,tP,testoGrafico_la,aggiungiPrenotazione_bt,rimuoviPrenotazione_bt,esitoPrenotazione_la,postiDisponibili_la,contenitoreGrafico_gr,hb,sceltaGiorno_dp,sceltaGiorno_la);
        Scene scene = new Scene(root,800,760);
        stage.setTitle("IncontriParrocchiali");
        stage.setScene(scene);
        stage.show();
    }
}