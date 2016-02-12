package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Random;

import junit.framework.TestCase;
import integration.AutomobileDAO;
import integration.ClienteDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import business.entity.Automobile;
import business.entity.Cliente;

public class testclientedao extends TestCase {

	ClienteDAO test;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		test = new ClienteDAO();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
		test = null;
	}

	@Test
	public void testCreateCliente() throws SQLException {
		Cliente entity=new Cliente();
		Random ran = new Random();
		int boom = ran.nextInt();
		entity.setNomeCliente("armando");
		entity.setCognomeCliente("brunelleschi");
		entity.setNumTelefono("3483436010");
		entity.setNeoPatentato(0);
		entity.setUsernameCliente("username" + boom);
		entity.setPassowrdCliente("password");
		test.create(entity);
		assertTrue("caso testCreaCliente",test.read("username" + boom)!=null );
		}

	@Test
	public void testReadString() throws SQLException {
		Cliente expected=test.read("username");
		Cliente entity = test.read("username");
		System.out.println(expected.getCognomeCliente());
		assertTrue("caso testletturatutteagenzie",entity.getCognomeCliente().compareTo(expected.getCognomeCliente())==0);
		entity = test.read("caramba");
		assertTrue("caso testletturaCLiente",entity==null);
		}

	@Test
	public void testCheckCredenzialiClienti() throws SQLException {
		assertTrue("caso testCheckCredenziali",test.checkCredenzialiClienti("username","password")==true );
		assertTrue("caso testCheckCredenziali",test.checkCredenzialiClienti("User", "password")==false );
		assertTrue("caso testCheckCredenziali",test.checkCredenzialiClienti("username", "Pass")==false );
		assertTrue("caso testCheckCredenziali",test.checkCredenzialiClienti("username", "PASS")==false );
	}

	@Test
	public void testCheckEsistenzaCliente() throws SQLException {
		assertTrue("caso testCheckEsistenzaCliente",test.checkEsistenzaCliente("username")==true );
		assertTrue("caso testCheckEsistenzaCliente",test.checkEsistenzaCliente("user")==false );
		assertTrue("caso testCheckEsistenzaCliente",test.checkEsistenzaCliente("usa")==false);
		assertTrue("caso testCheckEsistenzaCliente",test.checkEsistenzaCliente("Miguel")==true);
		}
	}


