package business.entity;

public class CategoriaAutomobile {
	private double prezzoBase;
	private double prezzoIllimitato;
	private Categoria categoria;
	
	public CategoriaAutomobile(Categoria c){
		setPrezzo(c);
		categoria = c;
	}
	public CategoriaAutomobile(){}
	
	public CategoriaAutomobile(String c){
		switch(c){
		case "Economy":
			categoria = Categoria.ECONOMY;
			setPrezzo(categoria);
			break;
		case "Ecology":
			categoria = Categoria.ECOLOGY;
			setPrezzo(categoria);
			break;
		case "Sportiva":
			categoria = Categoria.SPORTIVA;
			setPrezzo(categoria);
			break;
		case "Lusso":
			categoria = Categoria.LUSSO;
			setPrezzo(categoria);
			break;
		case "Familiare":
			categoria = Categoria.FAMILIARE;
			setPrezzo(categoria);
			break;
		}
			
	}
	
	private void setPrezzo(Categoria c){
		switch(c){
		case ECONOMY:
			prezzoBase = 0.25;
			prezzoIllimitato = 80;
			break;
		case SPORTIVA:
			prezzoBase = 0.75;
			prezzoIllimitato = 250;
			break;
		case LUSSO:
			prezzoBase = 1;
			prezzoIllimitato = 350;
			break;
		case FAMILIARE:
			prezzoBase = 0.5;
			prezzoIllimitato = 150;
			break;
		case ECOLOGY:
			prezzoBase = 0.5;
			prezzoIllimitato = 150;
			break;
			
		}	
	}

	public double getPrezzoBase() {
		return prezzoBase;
	}

	public double getPrezzoIllimitato() {
		return prezzoIllimitato;
	}
	
	public Categoria getCategoria(){
		return categoria;
	}
	
	@Override
	public String toString(){
		String output = "";
		
		switch(categoria){
		case ECOLOGY:
			output =  "Ecology";
			break;
		case ECONOMY:
			output =  "Economy";
			break;
		case FAMILIARE:
			output =  "Familiare";
			break;
			
		case LUSSO:
			output =  "Lusso";
			break;
			
		case SPORTIVA:
			output =  "Sportiva";
			break;
		}
		return output;
	}
}
