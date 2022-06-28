/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import it.polito.tdp.nyc.model.Model;
import it.polito.tdp.nyc.model.QuartiereAdiacente;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<String> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="cmbQuartiere"
    private ComboBox<String> cmbQuartiere; // Value injected by FXMLLoader

    @FXML // fx:id="txtMemoria"
    private TextField txtMemoria; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML // fx:id="clQuartiere"
    private TableColumn<QuartiereAdiacente, String> clQuartiere; // Value injected by FXMLLoader
 
    @FXML // fx:id="clDistanza"
    private TableColumn<QuartiereAdiacente, Double> clDistanza; // Value injected by FXMLLoader
    
    @FXML // fx:id="tblQuartieri"
    private TableView<QuartiereAdiacente> tblQuartieri; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String provider=cmbProvider.getValue();
    	if(provider==null) {
    		txtResult.appendText("Devi inserire un provider");
    	}
    	String s=model.creaGrafo(provider);
    	cmbQuartiere.getItems().addAll(model.getQuartieri(provider));
    	txtResult.appendText(s);

    }

    @FXML
    void doQuartieriAdiacenti(ActionEvent event) {
    	txtResult.clear();
    	String q=cmbQuartiere.getValue();
    	if(q==null) {
    		txtResult.appendText("Devi inserire un quartiere");
    	}
    	
    	List<QuartiereAdiacente> vicini=model.getVicini(q);
    	tblQuartieri.setItems(FXCollections.observableArrayList(vicini));

    }

    @FXML
    void doSimula(ActionEvent event) {
        txtResult.clear();
    	
    	String provider=cmbProvider.getValue();
    	if(provider==null) {
    		txtResult.appendText("Devi inserire un provider");
    	}
    	String qPartenza=cmbQuartiere.getValue();
    	if(qPartenza==null) {
    		txtResult.appendText("Devi inserire un quartiere");
    	}
    	String n=txtMemoria.getText();
    	try {
    		int ntecnici=Integer.parseInt(n);
    		model.simula(ntecnici, provider, qPartenza);
    		
    		int durata=model.getDurataSimulazione();
    		txtResult.appendText("Durata simulazione: "+durata+"\n");
    		Map<Integer,Integer> HotspotTecnico=model.getHotspotPerTecnico();
    		for(Integer i: HotspotTecnico.keySet()) {
    			txtResult.appendText("Tecnico: "+i+" ");
    			txtResult.appendText("hotspot-> "+HotspotTecnico.get(i)+"\n");
    		}
    		
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Devi inserire un numero di tecnici");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbQuartiere != null : "fx:id=\"cmbQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMemoria != null : "fx:id=\"txtMemoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clDistanza != null : "fx:id=\"clDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clQuartiere != null : "fx:id=\"clQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";

        clQuartiere.setCellValueFactory(new PropertyValueFactory<QuartiereAdiacente, String>("vicino"));
		clDistanza.setCellValueFactory(new PropertyValueFactory<QuartiereAdiacente, Double>("distanza"));

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	cmbProvider.getItems().addAll(model.getProvider());
    }

}
