package integration;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import business.entity.Agente;

public class test {
	public static void main(String[] args) throws SQLException{
		AgenteDAO a=new AgenteDAO();
		List<Agente> agenzie=a.getAll();
		for(int i=0;i<1;i++){
			System.out.println(agenzie.get(i).getNome());
		}
	}
	

}
