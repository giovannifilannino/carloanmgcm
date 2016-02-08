package business.entity;

import integration.AgenteDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class Agente {
	
	private SimpleStringProperty agenzia;
	private SimpleStringProperty nome;
	private SimpleStringProperty cognome;
	private SimpleStringProperty user;
	private SimpleStringProperty password;
	private AgenteDAO dao;
	
	
	public Agente(String agenzia, String nome, String cognome, String username){
		this.agenzia = new SimpleStringProperty(agenzia);
		this.nome = new SimpleStringProperty(nome);
		this.cognome = new SimpleStringProperty(cognome);
		this.user = new SimpleStringProperty(username);
	}
	
	public Agente(){
		
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

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password2) {
		password.set(password2);
	}
	//TRANSFER OBJ
	
	public void create(Agente Entity) throws SQLException{
		dao.create(Entity);
	}
	
	public void update(Agente entity) {
		dao.update(entity);
	}
	
	public void delete(String usernameAgente) throws SQLException {
		dao.delete(usernameAgente);
	}

	public List<Agente> getAll() throws SQLException{
		return dao.getAll();
		
	}
	
	public Agente read(String usernameAgente) throws SQLException{
		return dao.read(usernameAgente);
	}
	
	public boolean checkcredenziali(String usernameA,String passwordA) throws SQLException{
		return dao.checkCredenziali(usernameA, passwordA);
	}
	
}
