package integration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import business.entity.Azienda;
import business.entity.CategoriaAutomobile;

public class CategoriaDAO extends DAOAB<CategoriaAutomobile>{
	private static final String GET_ALL_QUERY="SELECT * FROM Fasce ";

	@Override
	public void create(CategoriaAutomobile entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CategoriaAutomobile entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String ID) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CategoriaAutomobile read(String ID) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoriaAutomobile> getAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	private List<CategoriaAutomobile> getLista(ResultSet resultSet) throws SQLException{
		List<CategoriaAutomobile> aziende=new LinkedList<CategoriaAutomobile>();
		while(resultSet.next()){
			CategoriaAutomobile elemento =new CategoriaAutomobile();
			String NomeAgente=resultSet.getString("NomeAgenzia");
			elemento.setNome_azienda(NomeAgente);
			String CittaAgenzia=resultSet.getString("CittaAgenzia");
			elemento.setCitta(CittaAgenzia);
			aziende.add(elemento);
		}
		return aziende;
	}

}
