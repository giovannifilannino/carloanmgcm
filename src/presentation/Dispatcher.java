package presentation;

import presentation.controller.*;


public class Dispatcher {

	private NoleggioController noleggio;
	private ClientiController clienti;
	private OperatoreController operatore;
	private AmministratoreController amministratore;
	private PrenotazioneAutomobileController auto;
	private ConfermaPrenotazioneController conferma;
	private InserisciAgenteController agente;
	private InserimentoAutoController automobile;
	private RegisterController registrazione;
	private LoginController login;
	
	public Dispatcher(){
		noleggio = new NoleggioController();
		clienti = new ClientiController();
		operatore = new OperatoreController();
		amministratore = new AmministratoreController();
		auto = new PrenotazioneAutomobileController();
		conferma = new ConfermaPrenotazioneController();
		agente = new InserisciAgenteController();
		automobile = new InserimentoAutoController();
		registrazione = new RegisterController();
		login = new LoginController();
	}
	
	
	public void dispatch(String request){
		if(request.equalsIgnoreCase("FinestraPrenotazione")){
			noleggio.show();
		} else if(request.equalsIgnoreCase("FinestraClienti")){
			clienti.show();
		} else if(request.equalsIgnoreCase("FinestraOperatore")){
			operatore.show();
		} else if(request.equalsIgnoreCase("FinestraAmministrazione")){
			amministratore.show();
		} else if(request.equalsIgnoreCase("finestraprenotazioneautomobile")){
			auto.show();
		} else if(request.equalsIgnoreCase("finestraconfermaprenotazione")){
			conferma.show();
		} else if(request.equalsIgnoreCase("inserimentoagente")){
			agente.show();
		} else if(request.equalsIgnoreCase("inserimentoauto")){
			automobile.show();
		} else if(request.equalsIgnoreCase("registerwindows")){
			registrazione.show();
		} else if(request.equals("LoginWin")){
			login.show();
		}
	}
}
