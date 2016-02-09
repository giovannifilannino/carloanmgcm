package integration;

import java.sql.SQLException;
import java.util.List;

public interface DAO<Entity> {
	public boolean create(Entity entity) throws SQLException;
	public boolean update(Entity entity);
	public boolean delete(String ID) throws SQLException;
	public Entity read(String ID) throws SQLException;
	public List<Entity> getAll() throws SQLException;
	

}
