package integration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class DAOAB<Entity> implements DAO<Entity>{
	CarLoanDB DB=new CarLoanDB();
	protected static final int FIRST = 0;
	static int contatore=0;
	
	public DAOAB(){
		try{
			String query="SELECT COUNT(*) FROM Noleggi";
			Connection connessione=MySqlDaoFactory.connetti();
			Statement istruzione = connessione.createStatement();
			ResultSet app=istruzione.executeQuery(query);
			app.next();
			contatore=app.getInt(1);
		}
		catch(Exception e){
			System.out.println("ERRORE NEL COSTRUTTORE");
		}
	}
	@Override
    public abstract boolean create(Entity entity) throws SQLException;

    @Override
    public abstract void update(Entity entity);

    @Override
    public abstract void delete(String ID) throws SQLException;

    @Override
    public abstract Entity read(String ID)  throws SQLException;

    @Override
    public abstract List<Entity> getAll()  throws SQLException;
}
