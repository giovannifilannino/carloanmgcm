package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import integration.AutomobileDAO;
import integration.MySqlDaoFactory;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import business.entity.Automobile;
import business.entity.Azienda;


	public class testautomobiledao extends TestCase {
		private static final String DELETE_QUERY="DELETE FROM Auto WHERE targa=?";
		AutomobileDAO test;
		AutomobileDAO testCreate;
			
			@Before
			protected void setUp() throws Exception {
				super.setUp();
				test = new AutomobileDAO();
			}

			@After
			protected void tearDown() throws Exception {
				super.tearDown();
				test = null;
			}

			public  boolean deletetest(String ID) throws SQLException {
				boolean b= false;
				Connection connessione=MySqlDaoFactory.connetti();
				PreparedStatement prepStat=connessione.prepareStatement(DELETE_QUERY);
				prepStat.setString(1,ID);
				return b=prepStat.executeUpdate()==1;
				
			}
	@Test
	public void testCreateAutomobile() throws SQLException {
		testCreate = new AutomobileDAO();
		Automobile entity=new Automobile();
		entity.setTarga("BZ746PL");
		entity.setModello_auto("carlo");
		entity.setAgenzia("CarLoanTrani");
		entity.setCategoria("Economy");
		entity.setCilindrata(1200);
		testCreate.create(entity);
		assertTrue("caso testletturatutteagenzie",testCreate.read("BZ746PL")!=null );
		}

	@Test
	public void testReadString() throws SQLException {
		Automobile entity = test.read("BZ746PL");
		assertTrue("caso testletturatutteagenzie",entity!=null );
		entity = test.read("CarLoanTran");
		assertTrue("caso testletturatutteagenzie",entity==null );
		}

	@Test
	public void testGetAuto() throws SQLException {
		List auto=test.getAuto("CarLoanTrani");
		for(int i=0;i<auto.size();i++){
			System.out.println(auto.get(i));
			System.out.println(test.getAuto("CarloanTrani").get(i));
		//assertTrue("caso testletturatutteagenzie",auto.get(i)==test.getAuto("CarLoanTrani").get(i));
		}
		assertTrue("caso testletturatutteagenzie doveva essere:" + 0 + " invece è: " +test.getAuto("Carloan").size(),test.getAuto("CarLoan").size()==0 );

		}

	@Test
	public void testGetAutoDisponibili() throws SQLException {
		assertTrue("caso testletturatutteagenzie",test.getAutoDisponibili("Economy", "CarLoanTrani")!=null );
		assertTrue("caso testletturatutteagenzie",test.getAutoDisponibili("Economy", "CarLoan").size()==0 );
	}
}
