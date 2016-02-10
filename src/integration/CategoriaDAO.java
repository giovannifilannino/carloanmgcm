package integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import business.entity.Categoria;
import business.entity.CategoriaAutomobile;

public class CategoriaDAO extends DAOAB<CategoriaAutomobile>{
	private static final String GET_ALL_QUERY="SELECT * FROM Fasce ";

	@Override
	public boolean create(CategoriaAutomobile entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(CategoriaAutomobile entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String ID) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CategoriaAutomobile read(String ID) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoriaAutomobile> getAll() throws SQLException {
		Connection connessione=MySqlDaoFactory.connetti();
		PreparedStatement prepStat=connessione.prepareStatement(GET_ALL_QUERY);
		ResultSet risultato=prepStat.executeQuery();
		List<CategoriaAutomobile> lista=getLista(risultato);
		return lista;
	}
	private List<CategoriaAutomobile> getLista(ResultSet resultSet) throws SQLException{
		List<CategoriaAutomobile> categorie=new LinkedList<CategoriaAutomobile>();
		while(resultSet.next()){
			CategoriaAutomobile elemento =new CategoriaAutomobile();
			String categoria=resultSet.getString("NomeFascia");
			elemento.setCategoria(CategoriaAutomobile(categoria));
			Double PrezzoL=resultSet.getDouble("PrezzoL");
			elemento.setPrezzoBase(PrezzoL);
			Double PrezzoI=resultSet.getDouble("PrezzoI");
			elemento.setPrezzoIllimitato(PrezzoI);
			categorie.add(elemento);
		}
		return categorie;
	}
	private Categoria CategoriaAutomobile(String c){
		Categoria categoria = null;
		switch(c){
		case "Economy":
			categoria = Categoria.ECONOMY;
			break;
		case "Ecology":
			categoria = Categoria.ECOLOGY;
			break;
		case "Sportiva":
			categoria = Categoria.SPORTIVA;
			break;
		case "Lusso":
			categoria = Categoria.LUSSO;
			break;
		case "Familiare":
			categoria = Categoria.FAMILIARE;
			break;
		}
			return categoria;
	}

}
