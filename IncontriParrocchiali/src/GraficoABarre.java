import java.time.*;
import java.util.*;
import javafx.scene.chart.*;

public class GraficoABarre{
    private LocalDate giornoConsiderato,massimoIndietro;
    private int n;
    GestoreDB db;
    List<Prenotazione> presenze;
    
    public GraficoABarre(Parametri p){
        db = new GestoreDB(p);
    } 
    public BarChart getGrafico(int n){
        
       
        giornoConsiderato = LocalDate.now();
        java.sql.Date dateSql = java.sql.Date.valueOf(giornoConsiderato);
        
        int[][] classiArray = new int[n][3];
        massimoIndietro = giornoConsiderato.minusDays(n-1);
        
        LocalDate giorniSeguenti[] = new LocalDate[n];
        for(int i=0;i<n;i++){
            giorniSeguenti[i] = massimoIndietro.plusDays(i);
        }
        
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setMaxWidth(740);
        bc.setMinWidth(740);
        bc.setTitle("Presenze totali");
        xAxis.setLabel("Giorni");       
        yAxis.setLabel("Presenze");
 
        XYChart.Series seriePrima = new XYChart.Series();
        XYChart.Series serieSeconda = new XYChart.Series();
        XYChart.Series seriesTerza = new XYChart.Series();
        for(int i=0;i<n;i++){
            java.sql.Date dateSqlGiornaliera = java.sql.Date.valueOf(giorniSeguenti[i]);
            presenze = db.caricaPrenotazioniDB(dateSqlGiornaliera);
            
            for(int j=0; j<presenze.size();j++){
            String classiProprie = presenze.get(j).getClasse();
            switch(classiProprie){
                case "Prima":
                    classiArray[i][0]++;
                    break;
                case "Seconda":
                    classiArray[i][1]++;
                    break;
                case "Terza":
                    classiArray[i][2]++;
                    break;
                }
            } 
            seriePrima.getData().add(new XYChart.Data(giorniSeguenti[i].toString(), classiArray[i][0]));
            serieSeconda.getData().add(new XYChart.Data(giorniSeguenti[i].toString(), classiArray[i][1]));
            seriesTerza.getData().add(new XYChart.Data(giorniSeguenti[i].toString(), classiArray[i][2]));
        }    
        
        seriePrima.setName("Prima");
        serieSeconda.setName("Seconda");
        seriesTerza.setName("Terza");
        bc.getData().addAll(seriePrima,serieSeconda,seriesTerza);
        return bc;
    }
    public BarChart getGraficoUtente(int n, String nome, String cognome){
        
        giornoConsiderato = LocalDate.now();
        java.sql.Date dateSql = java.sql.Date.valueOf(giornoConsiderato);
        
        int[] presenzeUtente = new int[n];
        massimoIndietro = giornoConsiderato.minusDays(n-1);
        
        LocalDate giorniSeguenti[] = new LocalDate[n];
        for(int i=0;i<n;i++){
            giorniSeguenti[i] = massimoIndietro.plusDays(i);
        }
        
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setMaxWidth(740);
        bc.setMinWidth(740);
        bc.setTitle("Presenze totali");
        xAxis.setLabel("Giorni");       
        yAxis.setLabel("Presenza");
 
        XYChart.Series seriePrima = new XYChart.Series();
        for(int i=0;i<n;i++){
            java.sql.Date dateSqlGiornaliera = java.sql.Date.valueOf(giorniSeguenti[i]);
            presenze = db.caricaPrenotazioniDB(dateSqlGiornaliera);
            
            for(int j=0; j<presenze.size();j++){
            if(presenze.get(j).getNome().equals(nome) && presenze.get(j).getCognome().equals(cognome))
                presenzeUtente[i]++;
            } 
            seriePrima.getData().add(new XYChart.Data(giorniSeguenti[i].toString(), presenzeUtente[i]));
        }    
        
        seriePrima.setName("Presente");
        bc.getData().addAll(seriePrima);
        return bc;
    }
}