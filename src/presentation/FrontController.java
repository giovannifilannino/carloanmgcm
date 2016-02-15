package presentation;

public class FrontController {
	
	private  Dispatcher dispatcher;
	private  boolean autenticato = false;
	
	private FrontController(){
		dispatcher = new Dispatcher();
	}
	
	private static FrontController front = new FrontController();
	
	public   void dispatchRequest(String request){
		if(checkAutentication()){
			dispatcher.dispatch(request);
		}
	}
	
	public void register(){
		dispatcher.dispatch("registerwindows");
	}
	
	public static FrontController getIstance(){
		return front;
	}
	
	private  boolean checkAutentication(){
		boolean valore_ritorno;
		if(autenticato){
			valore_ritorno = true;
		}else{
		valore_ritorno = false;
		}
		return valore_ritorno;
	}
	
	public  void setAutenticato(){
		autenticato = true;
	}
	
}
