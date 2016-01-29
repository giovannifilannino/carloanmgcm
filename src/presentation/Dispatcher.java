package presentation;

import presentation.controller.NoleggioController;

public class Dispatcher {

	private NoleggioController noleggio;
	
	public Dispatcher(){
		noleggio = new NoleggioController();
	}
	
	
	public void dispatch(String request){
		if(request.equalsIgnoreCase("FinestraPrenotazione")){
			noleggio.show();
		}
	}
}
