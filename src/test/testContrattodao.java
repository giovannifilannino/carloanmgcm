package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import junit.framework.TestCase;
import integration.AutomobileDAO;
import integration.AziendaDAO;
import integration.ClienteDAO;
import integration.ContrattoDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import business.entity.Agente;
import business.entity.Automobile;
import business.entity.Azienda;
import business.entity.Cliente;
import business.entity.Contratto;

public class testContrattodao extends TestCase{

	ContrattoDAO test;
	private Cliente loadcliente;
	private Azienda loadagenzia;
	private Automobile loadautomobile;
	private Agente loadagente;
	@Before
	public void setUp() throws Exception {
		super.setUp();
		test=new ContrattoDAO();
		}

	@After
	public void tearDown() throws Exception {
		test=null;
	}

	@Test
	public void testCreateContratto() throws SQLException {
		//Contratto entity=new Contratto("BZ746PL","Miguel","CarloanTrani","true","true",LocalDate.now().toString(),LocalDate.of(2016, 2, 28).toString(),"CarloanTrani","CarloanTrani",0.15,"mirko");
		Contratto entity=new Contratto("BP890PX","Miguel","CarloanTrani","true","true",LocalDate.now().toString(),LocalDate.of(2016, 2, 28).toString(),"CarloanTrani","CarloanTrani",0.15,"mirko");
		boolean risultato=test.create(entity);
		assertTrue("caso testlCreateContratto",risultato==true);
		}

	@Test
	public void testGetNoleggiA() throws SQLException {
		test.confermaNoleggio("BP890PX");
		assertTrue("caso testNoleggiA",test.getNoleggiA("CarLoanTrani").size()>=1);
		assertTrue("caso testNoleggiA",test.getNoleggiA("Carlona").size()==0 );
	}
	@Test
	public void testGetNoleggiC() throws SQLException {
		test.chiudiNoleggio("BP890PX");
		assertTrue("caso testNoleggiC",test.getNoleggiC("Miguel").size()>=1);
		assertTrue("caso testNoleggiC",test.getNoleggiC("Ruggierorizzi").size()==0 );
	}
}
