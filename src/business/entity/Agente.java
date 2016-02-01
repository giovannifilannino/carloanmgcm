package business.entity;

import javafx.beans.property.SimpleStringProperty;

public class Agente {
	
	private final SimpleStringProperty agenzia;
	private final SimpleStringProperty nome;
	private final SimpleStringProperty cognome;
	private final SimpleStringProperty user;
	
	
	public Agente(String agenzia, String nome, String cognome, String username){
		this.agenzia = new SimpleStringProperty(agenzia);
		this.nome = new SimpleStringProperty(nome);
		this.cognome = new SimpleStringProperty(cognome);
		this.user = new SimpleStringProperty(username);
	}
	
	public String getAgenzia() {
		return agenzia.get();
	}

	public String getNome() {
		return nome.get();
	}

	public String getCognome() {
		return cognome.get();
	}
	
	public void setAgenzia(String agenzia2){
		agenzia.set(agenzia2);
	}
	
	public void setNome(String nome2){
		nome.set(nome2);
	}
	
	public void setCognome(String cognome2){
		cognome.set(cognome2);
	}

	public String getUser() {
		return user.get();
	}
	
	public void setUser(String username){
		user.set(username);
	}
	
}
