package integration;

import java.sql.Connection;
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
	public boolean create(Agente entity) throws SQLException {
		boolean risultato;
		Connection connessione=MySqlDaoFactory.connetti();
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
		risultato=prepStat.executeUpdate()==1;
		return risultato;
	}

	@Override
	public boolean update(Agente entity) {
		return false;
	}

	@Override
	public boolean delete(String usernameAgente) throws SQLException {
		boolean b=false;
		Connection connessione=MySqlDaoFactory.connetti();
		PreparedStatement prepStat=connessione.prepareStatement(DELETE_QUERY);
		prepStat.setString(1, usernameAgente);
		return b=prepStat.executeUpdate()==1;
		
	}

	@Override
	public Agente read(String usernameAgente) throws SQLException {
		Connection connessione=MySqlDaoFactory.connetti();
		PreparedStatement prepStat=connessione.prepareStatement(READ_QUERY);
		prepStat.setString(1, usernameAgente);
		ResultSet risultato=prepStat.executeQuery();
		List<Agente> lista = new LinkedList<Agente>();
		lista=getLista(risultato);
		if(lista.size()>0)
			return lista.get(FIRST);
		else
			return null;
	}

	@Override
	public List<Agente> getAll() throws SQLException {
		Connection connessione=MySqlDaoFactory.connetti();
		PreparedStatement prepStat=connessione.prepareStatement(GET_ALL_QUERY);
		ResultSet risultato=prepStat.executeQuery();
		List<Agente> lista=getLista(risultato);
		return lista;
	}
	
	public boolean checkCredenziali(String usernameA,String passwordA) throws SQLException{
		Connection connessione=MySqlDaoFactory.connetti();
		PreparedStatement prepStat=connessione.prepareStatement(CHECK_QUERY);
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
