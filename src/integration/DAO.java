package integration;

import java.sql.SQLException;
import java.util.List;

public interface DAO<Entity> {
	public void create(Entity entity) throws SQLException;
	public void update(Entity entity);
	public void delete(String ID) throws SQLException;
	public Entity read(String ID) throws SQLException;
	public List<Entity> getAll() throws SQLException;
	

}
