package business.entity;

import java.time.LocalDate;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Contratto {
	
	
	private SimpleStringProperty chilometraggio_limitato; //se è true è limitato, altrimenti illimitato
	private SimpleStringProperty noleggio; //se è true e di base giornaliera, se false in base settimanale
	private LocalDate data_inizio;
	private LocalDate data_fine;
	private SimpleStringProperty targa;
	private SimpleStringProperty cliente;
	private SimpleStringProperty agente;
	private SimpleStringProperty auto;
	private SimpleStringProperty categoria;
	private SimpleStringProperty prelievo;
	private SimpleStringProperty restituzione;
	private SimpleDoubleProperty acconto;
	private SimpleStringProperty stato;
	
	private float preventivo_value;
	
	
	
	public Contratto(String targa, String cliente,  String auto, boolean chilometraggio_limitato, boolean noleggio, String prelievo, String restituzione, double acconto){
		this.targa = new SimpleStringProperty(targa);
		this.cliente = new SimpleStringProperty(cliente);
		this.auto = new SimpleStringProperty(auto);
		this.chilometraggio_limitato = new SimpleStringProperty(setChilometraggioValue(chilometraggio_limitato));
		this.noleggio = new SimpleStringProperty(setNoleggioValue(noleggio));
		this.prelievo = new SimpleStringProperty(prelievo);
		this.restituzione = new SimpleStringProperty(restituzione);
		this.acconto = new SimpleDoubleProperty(acconto);
		this.stato = new SimpleStringProperty("non confermato");
	}

	public Contratto(){
		this.targa = new SimpleStringProperty("");
		this.cliente = new SimpleStringProperty("");
		this.auto = new SimpleStringProperty("");
		this.chilometraggio_limitato = new SimpleStringProperty("");
		this.noleggio = new SimpleStringProperty("");
		this.prelievo = new SimpleStringProperty("");
		this.restituzione = new SimpleStringProperty("");
		this.acconto = new SimpleDoubleProperty(0.0);
		this.stato = new SimpleStringProperty("non confermato");
		this.categoria = new SimpleStringProperty("");
	}
	
	private String setNoleggioValue(boolean noleggio){
		String output = "";
		if(noleggio){
			output =  "giornaliero";
		} else {
			output = "settimanale";
		}
		return output;
	}
	
	private String setChilometraggioValue(boolean chilometraggio){
		String output = "";
		if(chilometraggio){
			output = "limitato";
		} else {
			output = "illimitato";
		}
		return output;
	}
	
	public void setTarga(String targa){
		this.targa.set(targa);
	}
	
	public String getTarga(){
		return targa.get();
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente.set(cliente.toString());
	}

	public void setAgente(Agente agente) {
		this.agente.set(agente.getNome());
	}

	public void setAuto(Automobile auto) {
		this.auto.set(auto.getModello_auto());
	}

	public void setChilometraggio_limitato(boolean chilometraggio_limitato) {
		this.chilometraggio_limitato.set(setChilometraggioValue(chilometraggio_limitato));
	}

	public void setNoleggio(boolean noleggioscelto) {
		this.noleggio.set(setNoleggioValue(noleggioscelto));
	}

	public void setData_inizio(LocalDate localDate) {
		this.data_inizio = localDate;
	}

	public void setData_fine(LocalDate data_fine) {
		this.data_fine = data_fine;
	}

	public String getNoleggio() {
		return noleggio.get();
	}

	public LocalDate getData_inizio() {
		return data_inizio;
	}

	public LocalDate getData_fine() {
		return data_fine;
	}

	public void setPrelievo(Azienda azienda_da_prelievo) {
		this.prelievo.set(azienda_da_prelievo.getNome_azienda());
	}

	

	public String getCategoria() {
		return categoria.get();
	}

	public Azienda getRestituzione() {
		return new Azienda(restituzione.get());
	}

	public float getPreventivo() {
		return preventivo_value;
	}

	public void setRestituzione(Azienda restituzione) {
		this.restituzione.set(restituzione.getNome_azienda());
	}


	public Azienda getPrelievo() {
		return new Azienda(prelievo.get());
	}

	public void setPreventivo(float preventivo) {
		preventivo_value = preventivo;
	}

	public String getCliente() {
		return cliente.get();
	}

	public String getAgente() {
		return agente.get();
	}

	public String getAuto() {
		return auto.get();
	}

	public String getChilometraggio_limitato() {
		return chilometraggio_limitato.get();
	}
	
	public double preventivo(){
		double preventivo = 0;
		CategoriaAutomobile a = new CategoriaAutomobile(categoria.get());
		if(chilometraggio_limitato.get().equalsIgnoreCase("limitato")){
			if(noleggio.get().equalsIgnoreCase("giornaliero")){
				preventivo = a.getPrezzoBase();
			} else {
				preventivo = a.getPrezzoBase()*7;
			}
		} else {
			if(noleggio.get().equalsIgnoreCase("giornaliero")){
				preventivo = a.getPrezzoIllimitato();
			}
			else{
				preventivo = a.getPrezzoIllimitato()*7;
			}
		}
			
		return preventivo;
	}
	
	public void setCategoria(String categoria) {
		this.categoria.set(categoria);  
	}

	public String getStato(){
		return stato.get();
	}
	
	public void setConferma(){
		if(stato.get().equalsIgnoreCase("non confermato")){
			stato.set("confermato");
		}
	}
	
	public void chiudiContratto(){
		if(stato.get().equalsIgnoreCase("confermato")){
			stato.set("chiuso");
		}
	}
	
	public void setStato(String s){
		stato.set(s);
	}

	public Double getAcconto() {
		return acconto.get();
	}

	public void setAcconto(Double acconto) {
		this.acconto.set(acconto);
	}
	
	public boolean emptyContratto(){
		boolean esito = false;
		if(this.data_inizio == null)
			esito = true;
		return esito;
	}
	
	public void resetContratto(){
		this.targa = new SimpleStringProperty("");
		this.cliente = new SimpleStringProperty("");
		this.auto = new SimpleStringProperty("");
		this.chilometraggio_limitato = new SimpleStringProperty("");
		this.noleggio = new SimpleStringProperty("");
		this.prelievo = new SimpleStringProperty("");
		this.restituzione = new SimpleStringProperty("");
		this.acconto = new SimpleDoubleProperty(0.0);
		this.stato = new SimpleStringProperty("non confermato");
		this.categoria = new SimpleStringProperty("");
		this.data_inizio = null;
	}
}
