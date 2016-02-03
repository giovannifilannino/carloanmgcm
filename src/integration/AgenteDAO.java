package integration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import business.entity.Agente;

public class AgenteDAO extends DAOAB<Agente>{
	
	private static final String INSERT_QUERY="INSERT INTO Agenti VALUES(?,?,?,?,?)";
	private static final String DELETE_QUERY="DELETE FROM Agenti WHERE UsernameA=?";
	private static final String READ_QUERY="SELECT * FROM Agenti WHERE UsernameA=?";
	private static final String GET_ALL_QUERY="SELECT * FROM Agenti";
	private static final String CHECK_QUERY="SELECT UsernameA FROM Agenti WHERE UsernameA=? AND PasswordA=?";
	
	@Override
	public void create(Agente entity) throws SQLException {
		PreparedStatement prepStat=connessione.prepareStatement(INSERT_QUERY);
		String usernameAgente=entity.getNome();
		prepStat.setString(1, usernameAgente);
		String passwordAgente=entity.getCognome();
		prepStat.setString(2, passwordAgente);
		String nomeAgente=entity.getCognome();
		prepStat.setString(3, nomeAgente);
		String cognomeAgente=entity.getCognome();
		prepStat.setString(4, cognomeAgente);
		String codAgenzia=entity.getCognome();
		prepStat.setString(5, codAgenzia);
		prepStat.executeUpdate();
	}

	@Override
	public void update(Agente entity) {
		//to do
		
	}

	@Override
	public void delete(String usernameAgente) throws SQLException {
		PreparedStatement prepStat=DB.connessione.prepareStatement(DELETE_QUERY);
		prepStat.setString(1, usernameAgente);
		prepStat.executeUpdate();
		
	}

	@Override
	public Agente read(String usernameAgente) throws SQLException {
		PreparedStatement prepStat=DB.connessione.prepareStatement(READ_QUERY);
		prepStat.setString(1, usernameAgente);
		ResultSet risultato=prepStat.executeQuery();
		List<Agente> lista = new LinkedList<Agente>();
		lista=getLista(risultato);
		return lista.get(FIRST);
	}

	@Override
	public List<Agente> getAll() throws SQLException {
		PreparedStatement prepStat=DB.connessione.prepareStatement(GET_ALL_QUERY);
		ResultSet risultato=prepStat.executeQuery();
		List<Agente> lista=getLista(risultato);
		return lista;
	}
	
	public boolean checkCredenziali(String usernameA,String passwordA) throws SQLException{
		PreparedStatement prepStat=DB.connessione.prepareStatement(CHECK_QUERY);
		prepStat.setString(1, usernameA);
		prepStat.setString(2, passwordA);
		ResultSet risultato=prepStat.executeQuery();
		if(risultato.next()){
			return true;
		}
		else{
			return false;
		}
	}
	private List<Agente> getLista(ResultSet resultSet) throws SQLException{
		List<Agente> agenti=new LinkedList<Agente>();
		
		while(resultSet.next()){
			Agente elemento =new Agente();
			String NomeAgente=resultSet.getString("NomeAgente");
			elemento.setNome(NomeAgente);
			String CognomeAgente=resultSet.getString("CognomeAgente");
			elemento.setCognome(CognomeAgente);
			String CodAgenzia=resultSet.getString("CodAgenzia");
			elemento.setAgenzia(CodAgenzia);
			String UsernameAgente=resultSet.getString("UsernameA");
			elemento.setUser(UsernameAgente);
			String PasswordAgente=resultSet.getString("PasswordA");
			elemento.setPassword(PasswordAgente);
			agenti.add(elemento);
		}
		return agenti;
	}

}