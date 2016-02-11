package test;

import static org.junit.Assert.assertTrue;
import integration.AziendaDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import business.entity.Azienda;
import junit.framework.TestCase;

public class testaziendadao extends TestCase {
	AziendaDAO test;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		test = new AziendaDAO();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
		test = null;
	}

	@Test
	public void testNuovaAzienda() throws SQLException {
		boolean expected= true;
		Azienda entity=new Azienda();
		entity.setNome_azienda("test3");
		test.create(entity);
		Azienda result = test.read("test3");
		List<Azienda> lista =entity.getAll();
		//System.out.println(entity.getAll());
		assertTrue("caso testletturatutteagenzie",lista.get(0)!=null );
		//System.out.println(test.getAll());
		
	}
	
	@Test
	public void testEliminaAzienda() throws SQLException {
		boolean result;
		boolean expected= true;
		Azienda entity=new Azienda();
		result = test.delete("test3");
		assertTrue("caso testnuovaazienda",result == expected);
	}
	
	@Test
	public void testLetturaAzienda() throws SQLException{
		Azienda entity = test.read("CarLoanTrani");
		List<Azienda> lista =entity.getAll();
		assertTrue("caso testletturatutteagenzie",lista.size()>=1 );
		entity = test.read("CarLoanTran");
		assertTrue("caso testletturatutteagenzie",entity==null );
		//System.out.println(entity.getAll());
		
		
	}
	
	@Test
	public void testGetAll() throws SQLException{
		ArrayList<Azienda> aziende =new ArrayList<Azienda>();
		aziende = (ArrayList<Azienda>) test.getAll();
		assertTrue("caso testletturatutteagenzie",aziende.size()==4 );
	}
	
	
}
