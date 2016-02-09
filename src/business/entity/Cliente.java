package business.entity;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import integration.ClienteDAO;

public class Cliente {
	
	private String nomeCliente;
	private String cognomeCliente;
	private String usernameCliente;
	private String passowrdCliente;
	private int neoPatentato;
	private String numTelefono;
	private ClienteDAO clientedao;
	
	
	public Cliente(){
		
	}
	
	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCognomeCliente() {
		return cognomeCliente;
	}

	public void setCognomeCliente(String cognomeCliente) {
		this.cognomeCliente = cognomeCliente;
	}

	public String getUsernameCliente() {
		return usernameCliente;
	}

	public void setUsernameCliente(String usernameCliente) {
		this.usernameCliente = usernameCliente;
	}

	public String getPassowrdCliente() {
		return passowrdCliente;
	}

	public void setPassowrdCliente(String passowrdCliente) {
		this.passowrdCliente = passowrdCliente;
	}

	public int getNeoPatentato() {
		return neoPatentato;
	}

	public void setNeoPatentato(int neoPatentato) {
		this.neoPatentato = neoPatentato;
	}

	public String getNumTelefono() {
		return numTelefono;
	}

	public void setNumTelefono(String numTelefono) {
		this.numTelefono = numTelefono;
	}

	public Cliente(String nome){
		this.nomeCliente = nome;
	}
	
	public String getNome(){
		return nomeCliente;
	}
	
	//TRANSFER OBJ
	
	public void create(Cliente entity) throws SQLException {
		clientedao.create(entity);
		}
	public void update(Cliente entity) {
		clientedao.update(entity);
		
	}

	public void delete(String ID) throws SQLException {
		clientedao.delete(ID);
		
	}
	public Cliente read(String usernameC) throws SQLException {
		return clientedao.read(usernameC);
	}
	
	public boolean checkCredenzialiClienti(String usernameC,String passwordC) throws SQLException{
		return clientedao.checkCredenzialiClienti(usernameC, passwordC);
	}
	
	public boolean checkEsistenzaCliente(String usernameC) throws SQLException{
		return clientedao.checkEsistenzaCliente(usernameC);
	}
}
