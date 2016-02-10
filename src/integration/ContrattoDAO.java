package integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import business.entity.Azienda;
import business.entity.Contratto;

public class ContrattoDAO extends DAOAB<Contratto> {
	private final static String INSERT_QUERY="INSERT INTO Noleggi VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private final static String GET_NOLEGGIOA_QUERY="SELECT * FROM Noleggi WHERE NomeAgenzia=?";
	private final static String CONFERMA_QUERY="UPDATE Noleggi SET Chiuso='CONFERMATO' WHERE Targa=? AND Chiuso!='CHIUSO' AND Chiuso!='CONFERMATO'";
	private final static String CHIUDI_QUERY1="UPDATE Auto SET Disponibile=1 WHERE Targa=?";
	private final static String CHIUDI_QUERY2="UPDATE Noleggi SET Chiuso='Chiuso' WHERE Targa=? AND Chiuso!='CHIUSO' AND Chiuso !='NON CONFERMATO'";
	private final static String GET_NOLEGGIOC_QUERY="SELECT * FROM Noleggi WHERE UsernameC=?";
	
	@Override
	public boolean create(Contratto entity) throws SQLException {
		Connection connessione=MySqlDaoFactory.connetti();
		PreparedStatement prepStat = connessione.prepareStatement(INSERT_QUERY);
		prepStat.setInt(1, contatore);
		String targa=entity.getTarga();
		prepStat.setString(2, targa);
		String usernameC=entity.getCliente();
		prepStat.setString(3, usernameC);
		String nomeAgenzia=entity.getAgenzia();
		prepStat.setString(4, nomeAgenzia);
		String tipoKm=entity.getChilometraggio_limitato();
		prepStat.setString(5, tipoKm);
		String tipoNoleggio=entity.getNoleggio();
		prepStat.setString(6, tipoNoleggio);
		LocalDate inizioNo=entity.getData_inizio();
		prepStat.setString(7, inizioNo.toString());
		LocalDate fineNo=entity.getData_fine();
		prepStat.setString(8, fineNo.toString());
		Azienda cittaRitiro=entity.getPrelievo();
		prepStat.setString(9, cittaRitiro.getNome_azienda());
		Azienda cittaConsegna=entity.getRestituzione();
		prepStat.setString(10, cittaConsegna.getNome_azienda());
		Double acconto=entity.getAcconto();
		prepStat.setDouble(11, acconto);
		String nomeAuto=entity.getAuto();
		prepStat.setString(12, nomeAuto);
		prepStat.setString(13, "NON CONFERMATO");
		prepStat.executeUpdate();
		AutomobileDAO.setAutoFuori(nomeAuto);
		return false;
	}

	@Override
	public boolean update(Contratto entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String ID) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Contratto read(String ID) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contratto> getAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Contratto> getNoleggiA(String nomeAgenzia) throws SQLException{
		Connection connessione=MySqlDaoFactory.connetti();
		PreparedStatement prepStat=connessione.prepareStatement(GET_NOLEGGIOA_QUERY);
		prepStat.setString(1, nomeAgenzia);
		ResultSet risultato =prepStat.executeQuery();
		List<Contratto> lista=getLista(risultato);
		return lista;
	}
	public List<Contratto> getNoleggiC(String usernameC) throws SQLException{
		Connection connessione=MySqlDaoFactory.connetti();
		PreparedStatement prepStat=connessione.prepareStatement(GET_NOLEGGIOC_QUERY);
		prepStat.setString(1, usernameC);
		ResultSet risultato=prepStat.executeQuery();
		List<Contratto> lista=getLista(risultato);
		return lista;
	}
	
	public void confermaNoleggio(String targa) throws SQLException{
		Connection connessione=MySqlDaoFactory.connetti();
		PreparedStatement prepStat1=connessione.prepareStatement(CONFERMA_QUERY);
		prepStat1.setString(1, targa);
		prepStat1.executeUpdate();
	}
	public void chiudiNoleggio(String targa) throws SQLException{
		Connection connessione=MySqlDaoFactory.connetti();
		PreparedStatement prepStat=connessione.prepareStatement(CHIUDI_QUERY1);
		prepStat.setString(1, targa);
		prepStat.executeUpdate();
		PreparedStatement prepStat1=connessione.prepareStatement(CHIUDI_QUERY2);
		prepStat1.setString(1, targa);
		prepStat1.executeUpdate();
	}
	
	private List<Contratto> getLista(ResultSet resultSet) throws SQLException{
		ArrayList<Contratto> contratti=new ArrayList<Contratto>();
		while(resultSet.next()){
			Contratto elemento =new Contratto();
			int CodNoleggio=resultSet.getInt("CodNoleggio");
			elemento.setCodNoleggio(CodNoleggio);
			String Targa=resultSet.getString("Targa");
			elemento.setTarga(Targa);
			String UsernameC=resultSet.getString("UsernameC");
			ClienteDAO e=new ClienteDAO();
			elemento.setCliente(e.read(UsernameC));
			String NomeAgenzia=resultSet.getString("NomeAgenzia");
			Azienda a=new Azienda();
			a.setNome_azienda(NomeAgenzia);
			elemento.setAgenzia(a);
			String tipoKilometraggio=resultSet.getString("TipoKilometraggio");
			elemento.setChilometraggio_limitato(tipoKilometraggio.contentEquals("true"));
			String TipoNoleggio=resultSet.getString("TipoNoleggio");
			elemento.setNoleggio(TipoNoleggio.contentEquals("true"));
			String inizioNoleggio=resultSet.getString("InizioNoleggio");
			elemento.setData_inizio(LocalDate.parse(inizioNoleggio));
			String fineNoleggio=resultSet.getString("FineNoleggio");
			elemento.setData_inizio(LocalDate.parse(fineNoleggio));
			String cittaRitiro=resultSet.getString("CittaRitiro");
			Azienda ritiro=new Azienda();
			ritiro.setNome_azienda(cittaRitiro);
			elemento.setPrelievo(ritiro);
			String cittaConsegna=resultSet.getString("CittaConsegna");
			Azienda consegna=new Azienda();
			consegna.setNome_azienda(cittaConsegna);
			elemento.setPrelievo(consegna);
			elemento.setRestituzione(consegna);
			Double acconto=resultSet.getDouble("Acconto");
			elemento.setAcconto(acconto);
			AutomobileDAO auto=new AutomobileDAO();
			elemento.setAuto(auto.read(Targa));
			String chiuso=resultSet.getString("chiuso");
			elemento.setConferma(chiuso);
			contratti.add(elemento);
		}
		return contratti;
	}

}
