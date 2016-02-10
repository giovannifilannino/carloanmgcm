package integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import business.entity.Azienda;
public class AziendaDAO extends DAOAB<Azienda> {
	
	private static final String INSERT_QUERY="INSERT INTO Agenzie VALUES(?,?)";
	private static final String GET_ALL_QUERY="SELECT * FROM Agenzie ";
	private static final String DELETE_QUERY="DELETE FROM Agenzie WHERE NomeAgenzia=?";
	private static final String READ_QUERY="SELECT * FROM Agenzie WHERE NomeAgenzia=?";
	@Override
	public boolean create(Azienda entity) throws SQLException {
		boolean b= false;
		Connection connessione=MySqlDaoFactory.connetti();
		PreparedStatement prepStat=connessione.prepareStatement(INSERT_QUERY);
		String nomeAgenzia=entity.getNome_azienda();
		prepStat.setString(1, nomeAgenzia);
		String cittaAgenzia=entity.getCitta();
		prepStat.setString(2, cittaAgenzia);
		return b=prepStat.executeUpdate()==1;
		
	}

	@Override
	public boolean update(Azienda entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String nomeAgenzia) throws SQLException {
		boolean b= false;
		Connection connessione=MySqlDaoFactory.connetti();
		PreparedStatement prepStat=connessione.prepareStatement(DELETE_QUERY);
		prepStat.setString(1, nomeAgenzia);
		return b=prepStat.executeUpdate()==1;
		
	}

	@Override
	public Azienda read(String ID) throws SQLException {
		Connection connessione=MySqlDaoFactory.connetti();
		PreparedStatement prepStat=connessione.prepareStatement(READ_QUERY);
		prepStat.setString(1,ID);
		ResultSet risultato =prepStat.executeQuery();
		List<Azienda> lista =getLista(risultato);
		return lista.get(FIRST);
	}

	@Override
	public List<Azienda> getAll() throws SQLException {
		Connection connessione=MySqlDaoFactory.connetti();
		PreparedStatement prepStat=connessione.prepareStatement(GET_ALL_QUERY);
		ResultSet risultato =prepStat.executeQuery();
		List<Azienda> lista =getLista(risultato);
		return lista;
	}
	
	private List<Azienda> getLista(ResultSet resultSet) throws SQLException{
		ArrayList<Azienda> aziende=new ArrayList<Azienda>();
		while(resultSet.next()){
			Azienda elemento =new Azienda();
			String NomeAgente=resultSet.getString("NomeAgenzia");
			elemento.setNome_azienda(NomeAgente);
			aziende.add(elemento);
		}
		return aziende;
	}

}
