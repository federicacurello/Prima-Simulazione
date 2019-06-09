/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CrimesController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	txtResult.clear();
    	if((boxAnno.getValue()==null )){
    		txtResult.setText("Selezionare un anno!\n");
    		}
    	else {
    	model.creaGrafo(boxAnno.getValue());
    	txtResult.appendText("Creato grafo con "+model.getGrafo().vertexSet().size()+
    			" vertici e "+model.getGrafo().edgeSet().size()+" archi");
    	txtResult.appendText(model.trovaTuttiVicini());
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {  //ATTENZIONE: NON FUNZIONA IL CONTROLLO SUI CAMPI VUOTI
    	txtResult.clear();
    	try {
    	int m= boxMese.getValue();
    	int g=boxGiorno.getValue();
    	if((boxAnno.getValue()==null || boxMese.getValue()==null || boxGiorno.getValue()==null )){
    		txtResult.setText("Selezionare giorno, mese, anno!");
    		return;
    		}
    	
    	else if(boxGiorno.getValue()==31 && (m==2 || m==4 || m==6 || m==9 || m==11 )) {
    		txtResult.setText("Non esiste il giorno 31 per il mese selezionato!");
    	return;}
    	
    	else if(m==2 && (g==29 || g==30) && boxAnno.getValue()!=2016) {
    		txtResult.setText("Febbraio ha solo 28 giorni!");
    		return;}
    	else if(boxAnno.getValue()==2016 && m==2 && g==30) {
    		txtResult.setText("Febbraio ha solo 29 giorni in questo anno!");
    		return;
    	}
    	if(txtN.equals("")|| txtN==null || (Integer.parseInt(""+txtN))<1|| Integer.parseInt(""+txtN)>10) {
    		txtResult.appendText("Inserire un numero di agenti compreso tra 1 e 10");
    		return;
    	}}
    	catch (NumberFormatException e) {
    		txtResult.setText("Inserire una data realmente esistente!");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxAnno.getItems().addAll(model.getAnni());
    	boxMese.getItems().addAll(model.getMesi());
    	boxGiorno.getItems().addAll(model.getGiorni());
    	
    }
}
