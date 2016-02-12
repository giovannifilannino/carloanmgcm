package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import integration.AutomobileDAO;
import integration.MySqlDaoFactory;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import business.entity.Automobile;
import business.entity.Azienda;


	public class TestAutomobileDAO extends TestCase {
		AutomobileDAO test;
		Automobile[] auto;
			
			@Before
			protected void setUp() throws Exception {
				super.setUp();
				test = new AutomobileDAO();
				auto=new Automobile[4];
				for(int i =0;i<auto.length;i++){
					auto[i]=new Automobile();
				}
			}

			@After
			protected void tearDown() throws Exception {
				super.tearDown();
				test = null;
			}
			
			
	@Test
	public void testCreateAutomobile() throws SQLException {
		try{
			test.delete("RWERWR7");
		}
		catch(SQLException e){	
		}
		Automobile entity=new Automobile();
		entity.setTarga("RWERWR7");
		entity.setModello_auto("carlo");
		entity.setAgenzia("CarLoanTrani");
		entity.setCategoria("Economy");
		entity.setCilindrata(1200);
		test.create(entity);
		assertTrue("caso testCreaAuto",test.read("RWERWR7")!=null );
		}
	@Test
	public void testDelete() throws SQLException{
		Automobile entity=new Automobile();
		entity.setTarga("TYJHUII");
		entity.setModello_auto("mazda");
		entity.setAgenzia("CarLoanBari");
		entity.setCategoria("Economy");
		entity.setCilindrata(800);
		test.create(entity);
		test.delete("TYJHUII");
		assertTrue("caso testDelete",test.read("TYJHUII")==null);
	}

	@Test
	public void testReadString() throws SQLException {
		Automobile entity = test.read("BZ746PL");
		assertTrue("caso TestRead "+"Dodge Viper"+ "ma è "+entity.getModello_auto(),entity.getModello_auto().compareToIgnoreCase("Dodge Viper")==0);
		entity = test.read("CarLoanTran");
		assertTrue("caso TestRead",entity==null );
		}

	@Test
	public void testGetAuto() throws SQLException {
		
		List<Automobile> list=new ArrayList<Automobile>();
		auto[0].setTarga("pq456rl");
		auto[1].setTarga("cj746ga");
		auto[2].setTarga("ta365lm");
		list=test.getAuto("CarLoanTrani");
		for(int j=0;j<3;j++){
			boolean risultato=false;
			for(int i=0;i<list.size();i++){
				if(list.get(i).getTarga().compareToIgnoreCase(auto[j].getTarga())==0)
					risultato=true;
			}
			assertTrue("caso getAuto + "+j,risultato==true );
		}
		assertTrue("caso getAuto",list!=null );
		assertTrue("caso getAuto",test.getAuto("CarLoan").size()==0 );

		}

	@Test
	public void testGetAutoDisponibili() throws SQLException {
		int m=0;
		List<Automobile> list=new ArrayList<Automobile>();
		list=test.getAuto("CarLoanTrani");
		auto[0].setTarga("cd546cm");
		auto[1].setTarga("dm123am");
		auto[2].setTarga("pd522sm");
		auto[3].setTarga("RWERWR7");
		for(int j=0;j<4;j++){
			boolean risultato=false;
			for(int i=0;i<list.size();i++){
				if(list.get(i).getTarga().compareToIgnoreCase(auto[j].getTarga())==0)
					m=i;
					risultato=true;
			}
			assertTrue("caso getAutoDisponibili + "+list.get(m).getTarga()+"ma "+auto[j].getTarga(),risultato==true );
		}
		assertTrue("caso getAutoDisponibili",test.getAutoDisponibili("Economy", "CarLoanTrani")!=null );
		assertTrue("caso getAutoDisponibili",test.getAutoDisponibili("Economy", "CarLoan").size()==0 );
	}
	
	@Test
	public void testSetAutoFuori() throws SQLException{
		test.setAutoFuori("cd546cm");
		Automobile a=new Automobile();
		a =test.read("cd546cm");
		assertTrue("caso testAutoFuori",a.getDisponibile()==0 );
		test.setAutoFuori("dm123am");
		a=test.read("dm123am");
		assertTrue("caso testAutoFuori",a.getDisponibile()==0 );
	}
}