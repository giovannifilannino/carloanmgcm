package business.entity;
import java.sql.SQLException;
import java.util.List;

import integration.AziendaDAO;
import javafx.beans.property.SimpleStringProperty;

public class Azienda {
	
	private SimpleStringProperty nome_azienda;
	private SimpleStringProperty nome;
	private SimpleStringProperty citta;
	private AziendaDAO aziendadao;
	
	
	public Azienda(String nome_azienda2){
		nome_azienda = new SimpleStringProperty(nome_azienda2);
		nome.set(nome_azienda2);
	}
	public Azienda(){
		nome_azienda=new SimpleStringProperty();
		nome=new SimpleStringProperty();
		citta=new SimpleStringProperty();
	}
	
	
	public String toString(){
		return nome.get();
	}


	public String getNome_azienda() {
		return nome_azienda.get();
	}

	public String getCitta() {
		return citta.get();
	}


	public void setCitta(String citta) {
		this.citta.set(citta);;
	}
	
	public void setNome_azienda(String nome_azienda) {
		this.nome_azienda.set(nome_azienda);;
	}
	//TRANSFER OBJ

	public void create(Azienda entity) throws SQLException {
		aziendadao.create(entity);
	}
	public void delete(String nomeAgenzia) throws SQLException {
		aziendadao.delete(nomeAgenzia);
	}
	
	public void update(Azienda entity) {
		aziendadao.update(entity);
		}
	
	public Azienda read(String ID) throws SQLException {
		return aziendadao.read(ID);
	}
	
	public List<Azienda> getAll() throws SQLException {
		return aziendadao.getAll();
	}
	
	
}
