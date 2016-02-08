package integration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import business.entity.Agente;
import business.entity.Cliente;

public class ClienteDAO extends DAOAB<Cliente>{
	private static final String INSERT_QUERY="INSERT INTO Clienti VALUES(?,?,?,?,?,?)";
	private static final String READ_QUERY="SELECT * FROM Clienti WHERE UsernameC=?";
	private static final String CHECK_QUERY="SELECT UsernameC FROM Clienti WHERE UsernameC=? AND PasswordC=?";
	private static final String CHECK_VALIDAZIONE_CLIENTE_QUERY="SELECT UsernameA FROM Agenti WHERE UsernameA=? AND PasswordA=?";

	@Override
	public void create(Cliente entity) throws SQLException {
		PreparedStatement prepStat=connessione.prepareStatement(INSERT_QUERY);
		String usernameC=entity.getUsernameCliente();
		prepStat.setString(1, usernameC);
		String passwordC=entity.getPassowrdCliente();
		prepStat.setString(2, passwordC);
		String nomeC=entity.getNome();
		prepStat.setString(3, nomeC);
		String cognomeC=entity.getCognomeCliente();
		prepStat.setString(4, cognomeC);
		int neoPatentato=entity.getNeoPatentato();
		prepStat.setInt(5, neoPatentato);
		String numTelefono=entity.getNumTelefono();
		prepStat.setString(6, numTelefono);
		prepStat.executeUpdate();
		
	}

	@Override
	public void update(Cliente entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String ID) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cliente read(String usernameC) throws SQLException {
		PreparedStatement prepStat=connessione.prepareStatement(READ_QUERY);
		prepStat.setString(1, usernameC);
		ResultSet risultato=prepStat.executeQuery();
		List<Cliente> lista = getLista(risultato);
		return lista.get(FIRST);
	}

	@Override
	public List<Cliente> getAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean checkCredenzialiClienti(String usernameC,String passwordC) throws SQLException{
		PreparedStatement prepStat = connessione.prepareStatement(CHECK_QUERY);
		prepStat.setString(1, usernameC);
		prepStat.setString(2, passwordC);
		ResultSet ris=prepStat.executeQuery();
		if(ris.next()){
			return true;
		}
		else
			return false;
	}
	public boolean checkEsistenzaCliente(String usernameC) throws SQLException{
		Cliente c=read(usernameC);
		if(c==null)
			return false;
		else
			return true;
	}
	
	
	private List<Cliente> getLista(ResultSet resultSet) throws SQLException{
		List<Cliente> clienti=new LinkedList<Cliente>();
		
		while(resultSet.next()){
			Cliente elemento =new Cliente();
			String NomeCliente=resultSet.getString("NomeCliente");
			elemento.setNomeCliente(NomeCliente);
			String CognomeCliente=resultSet.getString("CognomeCliente");
			elemento.setCognomeCliente(CognomeCliente);
			String usernameCliente=resultSet.getString("UsernameC");
			elemento.setUsernameCliente(usernameCliente);
			String PasswordCliente=resultSet.getString("PasswordC");
			elemento.setPassowrdCliente(PasswordCliente);
			String NumTelefono=resultSet.getString("NumTelefono");
			elemento.setNumTelefono(NumTelefono);
			int neoPatentato=resultSet.getInt("NeoPatentato");
			elemento.setNeoPatentato(neoPatentato);
			clienti.add(elemento);
		}
		return clienti;
	}

}
