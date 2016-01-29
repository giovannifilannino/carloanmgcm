package presentation.controller;

import integration.CarLoanDB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ConfermaPrenotazioneController {

	@FXML
	Label tipo_chilometraggio;
	@FXML
	Label tipo_noleggio;
	@FXML
	Label data_inizio;
	@FXML
	Label data_fine;
	@FXML
	Label automobile;
	@FXML
	Label agenzia_restituzione;
	@FXML
	Label costo_previsto;
	
	CarLoanDB db = new CarLoanDB();
	
	
	@FXML
	public void initialize(){
		
		tipo_chilometraggio.setText((NoleggioController.nuovo_contratto.getChilometraggio_limitato()));
		tipo_noleggio.setText((NoleggioController.nuovo_contratto.getNoleggio()));
		data_inizio.setText(NoleggioController.nuovo_contratto.getData_inizio().toString());
		data_fine.setText(NoleggioController.nuovo_contratto.getData_fine().toString());
		automobile.setText(NoleggioController.nuovo_contratto.getAuto().toString());
		agenzia_restituzione.setText(NoleggioController.nuovo_contratto.getRestituzione().toString());
		costo_previsto.setText(String.valueOf(NoleggioController.nuovo_contratto.preventivo()));
		
	}
	
	@FXML
	public void indietro(ActionEvent e){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("fxmlclass/FinestraPrenotazioneAutomobile.fxml"));
		try {
			ClientiController.prenotazione1.setScene(new Scene(loader.load()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@FXML
	public void prenota(ActionEvent e) throws SQLException{
		System.out.println(NoleggioController.nuovo_contratto.getTarga() + " mlmlml");
		Alert conferma = new Alert(AlertType.CONFIRMATION);
		conferma.setTitle("Prenotazione noleggio");
		conferma.setHeaderText("Vuoi confermare i dati inseriti?");
		

		Optional<ButtonType> result = conferma.showAndWait();
		if (result.get() == ButtonType.OK){
			
		    
			if(OperatoreController.fromOperator){
				db.setNoleggio(NoleggioController.nuovo_contratto.getTarga(),null, NoleggioController.nuovo_contratto.getPrelievo().toString(), getKm(NoleggioController.nuovo_contratto.getChilometraggio_limitato()), getNoleggio(NoleggioController.nuovo_contratto.getNoleggio()), NoleggioController.nuovo_contratto.getData_inizio().toString(), NoleggioController.nuovo_contratto.getData_fine().toString(), NoleggioController.nuovo_contratto.getPrelievo().toString(), NoleggioController.nuovo_contratto.getRestituzione().toString(), NoleggioController.nuovo_contratto.getAcconto(), NoleggioController.nuovo_contratto.getAuto());
				OperatoreController.contratto.add(NoleggioController.nuovo_contratto);
				OperatoreController.fromOperator = false;
			} else {
				db.setNoleggio(NoleggioController.nuovo_contratto.getTarga(),LoginController.username, NoleggioController.nuovo_contratto.getPrelievo().toString(), getKm(NoleggioController.nuovo_contratto.getChilometraggio_limitato()), getNoleggio(NoleggioController.nuovo_contratto.getNoleggio()), NoleggioController.nuovo_contratto.getData_inizio().toString(), NoleggioController.nuovo_contratto.getData_fine().toString(), NoleggioController.nuovo_contratto.getPrelievo().toString(), NoleggioController.nuovo_contratto.getRestituzione().toString(), NoleggioController.nuovo_contratto.getAcconto(), NoleggioController.nuovo_contratto.getAuto());
				ClientiController.noleggi_cliente.add(NoleggioController.nuovo_contratto);
				ClientiController.fromClient = false;
			}
			NoleggioController.dati = false;
			PrenotazioneAutomobileController.dati = false;
			Stage ml = (Stage) ClientiController.prenotazione1.getScene().getWindow();
			ml.close();
		} else {
		    conferma.close();
		}
	}
	
	private int getKm(String s){
		int output = 0;
		if(s.equalsIgnoreCase("limitato")){
			output = 1;
		} 
		return output;
	}
	
	private int getNoleggio(String s){
		int output = 0;
		if(s.equalsIgnoreCase("giornaliero")){
			output = 1;
		} 
		return output;
	}

}
