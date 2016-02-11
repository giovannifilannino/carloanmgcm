package business.entity;

import java.sql.SQLException;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import integration.AutomobileDAO;

public class Automobile {
	
	private SimpleStringProperty modello_auto;
	private SimpleStringProperty targa;
	private SimpleIntegerProperty cilindrata;
	private SimpleStringProperty agenzia;
	private SimpleStringProperty categoria;
	private SimpleStringProperty disponibile;
	private static AutomobileDAO dao;
	
	public Automobile(String modello_auto, String targa, int cilindrata, String agenzia, String categoria){
		this.modello_auto = new SimpleStringProperty(modello_auto);
		this.targa = new SimpleStringProperty(targa);
		this.cilindrata = new SimpleIntegerProperty(cilindrata);
		this.agenzia = new SimpleStringProperty(agenzia);
		this.categoria = new SimpleStringProperty(categoria);
		this.disponibile = new SimpleStringProperty("disponibile");
		dao = new AutomobileDAO();
	}
	public Automobile(){
		modello_auto=new SimpleStringProperty();
		targa=new SimpleStringProperty();
		cilindrata=new SimpleIntegerProperty();
		agenzia=new SimpleStringProperty();
		categoria=new SimpleStringProperty();
		disponibile=new SimpleStringProperty();
		dao = new AutomobileDAO();
	}
	
	public Automobile(String modello_auto){
		this.modello_auto = new SimpleStringProperty(modello_auto);
	}

	public String getModello_auto() {
		return modello_auto.get();
	}

	public String getTarga() {
		return targa.get();
	}

	public int getCilindrata() {
		return cilindrata.get();
	}
	
	public String getCategoria(){
		return categoria.get();
	}
	
	public String getDisponibile(){
		return disponibile.get();
	}
	public String getAgenzia(){
		return agenzia.get();
	}


	@Override
	public String toString() {
		return modello_auto.get();
	}

	public void setModello_auto(String modello_auto2) {
		this.modello_auto.set(modello_auto2);;
	}

	public void setTarga(String targa2) {
		this.targa.set(targa2);
	}

	public void setCilindrata(int cilindrata2) {
		this.cilindrata.set(cilindrata2);
	}

	public void setAgenzia(String agenzia) {
		this.agenzia.set(agenzia);
		
	}
	
	public void setStato(){
		if(disponibile.get().equals("disponibile")){
			disponibile.set("non disponibile");
		}
		else{
			disponibile.set("disponibile");
		}
	}
	
	public void setCategoria(String categoria){
		this.categoria.set(categoria);
	}
	
	
	public void create(Automobile entity) throws SQLException {
		dao.create(entity);
	}
	
	public void update(Automobile entity) {
		dao.update(entity);		
	}

	public void delete(String ID) throws SQLException {
		dao.delete(ID);
		
	}

	public Automobile read(String sede) throws SQLException {
		return dao.read(sede);
	}
	
	public List<Automobile> getAll() throws SQLException {
		return dao.getAll();
	}
	
	public static void setAutoFuori(String nomeAuto) throws SQLException{
		dao.setAutoFuori(nomeAuto);
	}
	
	public List<Automobile> getAuto(String sede) throws SQLException{
		return dao.getAuto(sede);
	}
	
	public List<Automobile> getAutoDisponibili(String nomeFascia,String sede) throws SQLException{
		return dao.getAutoDisponibili(nomeFascia, sede);
	}
	
	
}
