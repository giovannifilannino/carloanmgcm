package integration;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import business.entity.Agente;
import business.entity.Azienda;

public class test {
	public static void main(String[] args) throws SQLException{
		AziendaDAO a=new AziendaDAO();
		List<Azienda> azienda=a.getAll();
		for(int i=0;i<1;i++){
			System.out.println(azienda.get(i).getCitta());
		}
	}
}
