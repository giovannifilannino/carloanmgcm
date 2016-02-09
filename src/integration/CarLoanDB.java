package integration;

import java.sql.*;

public class CarLoanDB {
	static Connection connessione =null;
	static int contatore=0;
	public CarLoanDB(){	
		try{
			new com.mysql.jdbc.Driver();
			connessione=DriverManager.getConnection("jdbc:mysql://localhost/CarLoanDB", "root", "nicoletta94");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		try{
			String query="SELECT COUNT(*) FROM Noleggi";
			Statement istruzione = connessione.createStatement();
			ResultSet app=istruzione.executeQuery(query);
			app.next();
			contatore=app.getInt(1);
		}
		catch(Exception e){
			System.out.println("ERRORE NEL COSTRUTTORE");
		}
	}

	public  void setAuto(String targa,String marcaAuto,int cilindrata,String codAgenzia,String codFascia) throws SQLException{
		//controlloLunghezza(7,targa);
		String risultato="INSERT INTO Auto VALUES(?,?,?,?,?,1)";
		PreparedStatement prepStat = connessione.prepareStatement(risultato);
		prepStat.setString(1, targa);
		prepStat.setString(2, marcaAuto);
		prepStat.setInt(3, cilindrata);
		prepStat.setString(4, codAgenzia);
		prepStat.setString(5, codFascia);
		prepStat.executeUpdate();
		
	}

	//tipo kil 0=illimitato---- tipo noleggio  0=giornaliero
	public void setNoleggio(String targa,String usernameC,String nomeAgenzia,int tipoKm,int tipoNoleggio,String inizioNo,String fineNo,String cittaRitiro,String cittaConsegna,double acconto,String nomeAuto) throws SQLException{
		String risultato="INSERT INTO Noleggi VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement prepStat = connessione.prepareStatement(risultato);
		prepStat.setInt(1, contatore);
		prepStat.setString(2, targa);
		prepStat.setString(3, usernameC);
		prepStat.setString(4, nomeAgenzia);
		prepStat.setInt(5, tipoKm);
		prepStat.setInt(6, tipoNoleggio);
		prepStat.setString(7, inizioNo);
		prepStat.setString(8, fineNo);
		prepStat.setString(9, cittaRitiro);
		prepStat.setString(10, cittaConsegna);
		prepStat.setDouble(11, acconto);
		prepStat.setString(12, nomeAuto);
		prepStat.setString(13, "NON CONFERMATO");
		prepStat.executeUpdate();
		setAutoFuori(nomeAuto);
	}
	private void setAutoFuori(String nomeAuto) throws SQLException{
		String risultato="UPDATE Auto "
				+ "SET Disponibile=0 "
				+ "WHERE NomeAuto=?";
		PreparedStatement prepStat=connessione.prepareStatement(risultato);
		prepStat.setString(1, nomeAuto);
		prepStat.executeUpdate();
	}
	
	public void setAgente(String usernameA,String passwordA,String nome,String cognome,String codAgenzia) throws SQLException{
		String risultato="INSERT INTO Agenti VALUES(?,?,?,?,?)";
		PreparedStatement prepStat=connessione.prepareStatement(risultato);
		prepStat.setString(1, usernameA);
		prepStat.setString(2, passwordA);
		prepStat.setString(3, nome);
		prepStat.setString(4, cognome);
		prepStat.setString(5, codAgenzia);
		prepStat.executeUpdate();
	}
	
	public void setCliente(String usernameC,String passwordC,String nomeC,String cognomeC,int neoPatentato,String numTelefono) throws SQLException{
		String risultato="INSERT INTO Clienti VALUES(?,?,?,?,?,?)";
		PreparedStatement prepStat= connessione.prepareStatement(risultato);
		prepStat.setString(1, usernameC);
		prepStat.setString(2, passwordC);
		prepStat.setString(3, nomeC);
		prepStat.setString(4, cognomeC);
		prepStat.setInt(5, neoPatentato);
		prepStat.setString(6, numTelefono);
		prepStat.executeUpdate();
	}
	public void setAgenzia(String nomeAgenzia,String cittaAgenzia) throws SQLException{
		String query="INSERT INTO Agenzie VALUES(?,?)";
		PreparedStatement prepStat=connessione.prepareStatement(query);
		prepStat.setString(1, nomeAgenzia);
		prepStat.setString(2, cittaAgenzia);
		prepStat.executeUpdate();
	}
	
	//dato username nome e cognome
	public ResultSet getDatiUtenti(String username) throws SQLException{
	String query="SELECT NomeCliente,CognomeCliente "
			+ "FROM Clienti "
			+ "WHERE UsernameC=?";
	PreparedStatement prepStat=connessione.prepareStatement(query);
	prepStat.setString(1, username);
	ResultSet risultato=prepStat.executeQuery();
	return risultato;
	}
	public ResultSet getDatiAgente(String username) throws SQLException{
		String query="SELECT NomeAgente,CognomeAgente,CodAgenzia "
				+ "FROM Agenti "
				+ "WHERE UsernameA=?";
		PreparedStatement prepStat=connessione.prepareStatement(query);
		prepStat.setString(1, username);
		ResultSet risultato=prepStat.executeQuery();
		return risultato;
		}
	
	public ResultSet getAgenzie() throws SQLException{
		String query="SELECT * "
				+ "FROM Agenzie ";
		Statement istruzione=connessione.createStatement();
		ResultSet risultato =istruzione.executeQuery(query);
		return risultato;
	}
	public ResultSet getCategorie() throws SQLException{
		String query="SELECT * "
				+ "FROM Fasce ";
		Statement istruzione =connessione.createStatement();
		ResultSet risultato=istruzione.executeQuery(query);
		return risultato;
	}
	
	public ResultSet getAuto(String nomeFascia,String sede) throws SQLException{
		String query="SELECT NomeAuto,targa "
				+ "FROM Auto "
				+ "WHERE CodAgenzia=? AND CodFascia=? AND Disponibile=1";
		PreparedStatement prepStat=connessione.prepareStatement(query);
		prepStat.setString(1, sede);
		prepStat.setString(2, nomeFascia);
		ResultSet risultato=prepStat.executeQuery();
		return risultato;
		}
	
	public ResultSet getAuto(String sede) throws SQLException{
		String query="SELECT targa,cilindrata,codfascia,codagenzia,NomeAuto,Disponibile "
				+ "FROM Auto "
				+ "WHERE CodAgenzia=?";
		PreparedStatement prepStat=connessione.prepareStatement(query);
		prepStat.setString(1, sede);
		ResultSet risultato=prepStat.executeQuery();
		return risultato;
		}
	public ResultSet getAgenti() throws SQLException{
		String query="SELECT * FROM Agenti";
		Statement istruzione=connessione.createStatement();
		ResultSet risultato=istruzione.executeQuery(query);
		return risultato;
	}
	
	public ResultSet getNoleggiA(String nomeAgenzia) throws SQLException{
		String query="SELECT * FROM Noleggi "
				+ "WHERE NomeAgenzia=?";
		PreparedStatement prepStat=connessione.prepareStatement(query);
		prepStat.setString(1, nomeAgenzia);
		ResultSet risultato =prepStat.executeQuery();
		return risultato;
	}
	public ResultSet getNoleggiC(String usernameC) throws SQLException{
		String query="SELECT * FROM Noleggi "
				+ "WHERE UsernameC=?";
		PreparedStatement prepStat=connessione.prepareStatement(query);
		prepStat.setString(1, usernameC);
		ResultSet risultato=prepStat.executeQuery();
		return risultato;
	}

	public void removeAgente(String usernameA) throws SQLException{
		String query="DELETE FROM Agenti "
				+ "WHERE UsernameA=?";
		PreparedStatement prepStat=connessione.prepareStatement(query);
		prepStat.setString(1, usernameA);
		prepStat.executeUpdate();
	}
	public void removeAgenzia(String nomeAgenzia) throws SQLException{
		String query= "DELETE FROM Agenzie "
				+ "WHERE NomeAgenzia=?";
		PreparedStatement prepStat= connessione.prepareStatement(query);
		prepStat.setString(1, nomeAgenzia);
		prepStat.executeUpdate();
	}
	
	public void chiudiNoleggio(String targa) throws SQLException{
		String query="UPDATE Auto "
				+ "SET Disponibile=1 "
				+ "WHERE Targa=?";
		PreparedStatement prepStat=connessione.prepareStatement(query);
		prepStat.setString(1, targa);
		prepStat.executeUpdate();
		String query1="UPDATE Noleggi "
				+ "SET Chiuso='Chiuso' "
				+ "WHERE Targa=? AND Chiuso!='CHIUSO' AND Chiuso !='NON CONFERMATO'";
		PreparedStatement prepStat1=connessione.prepareStatement(query1);
		
		prepStat1.setString(1, targa);
		prepStat1.executeUpdate();
	}
	
	public void confermaNoleggio(String targa) throws SQLException{
		String query1="UPDATE Noleggi "
				+ "SET Chiuso='CONFERMATO' "
				+ "WHERE Targa=? AND Chiuso!='CHIUSO' AND Chiuso!='CONFERMATO'";
		PreparedStatement prepStat1=connessione.prepareStatement(query1);
		prepStat1.setString(1, targa);
		prepStat1.executeUpdate();
	}

	public boolean checkCliente(String username) throws SQLException{
		String query="SELECT UsernameC "
				+ "FROM Clienti "
				+ "WHERE UsernameC=?";
		PreparedStatement prepStat = connessione.prepareStatement(query);
		prepStat.setString(1, username);
		ResultSet ris=prepStat.executeQuery();
		if(ris.next()){
			return true;
		}
		else
			return false;
	}
	public boolean checkCredenzialiClienti(String usernameC,String passwordC) throws SQLException{
		String query="SELECT UsernameC "
				+ "FROM Clienti "
				+ "WHERE UsernameC=? AND PasswordC=?";
		PreparedStatement prepStat = connessione.prepareStatement(query);
		prepStat.setString(1, usernameC);
		prepStat.setString(2, passwordC);
		ResultSet ris=prepStat.executeQuery();
		if(ris.next()){
			return true;
		}
		else
			return false;
	}
	public boolean checkCredenzialiAgenti(String usernameA,String passwordA) throws SQLException{
		String query="SELECT UsernameA "
				+ "FROM Agenti "
				+ "WHERE UsernameA=? AND PasswordA=?";
		PreparedStatement prepStat = connessione.prepareStatement(query);
		prepStat.setString(1, usernameA);
		prepStat.setString(2, passwordA);
		ResultSet ris=prepStat.executeQuery();
		if(ris.next()){
			return true;
		}
		else
			return false;
	}
	
	

}
