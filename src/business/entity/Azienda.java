package business.entity;

import javafx.beans.property.SimpleStringProperty;

public class Azienda {
	
	private SimpleStringProperty nome_azienda;
	private String nome;
	private String citta;
	
	
	public Azienda(String nome_azienda2){
		nome_azienda = new SimpleStringProperty(nome_azienda2);
		nome = nome_azienda2;
	}
	public Azienda(){
		
	}
	
	
	public String toString(){
		return nome;
	}


	public String getNome_azienda() {
		return nome_azienda.get();
	}


	public void setNome_azienda(String nome_azienda) {
		this.nome_azienda.set(nome_azienda);;
	}


	public String getCitta() {
		return citta;
	}


	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	
}
