package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import business.entity.Agente;
import integration.AgenteDAO;

public class TestAgenteDAO {

	Agente[] test;
	AgenteDAO ag=new AgenteDAO();
	
	@Before
	public void setUp() throws Exception {
		test=new Agente[4];
		int i=0;
		while(i<test.length){
			test[i]=new Agente();
			i++;
		}
		test[0].setAgenzia("CarloanBari");
		test[0].setCognome("dinoia");
		test[0].setNome("michele");
		test[0].setPassword("miguel94");
		test[0].setUser("Draven94");
		test[1].setAgenzia("CarloanTrani");
		test[1].setCognome("dinoia");
		test[1].setNome("michele");
		test[1].setPassword("miguel94");
		test[1].setUser("Ciccio");
		test[2].setAgenzia("CarloanTrani");
		test[2].setCognome("dinoia");
		test[2].setNome("michele");
		test[2].setPassword("miguel94");
		test[2].setUser("Ciccio");
		test[3].setAgenzia("CarloanTrani");
		test[3].setCognome("dinoia");
		test[3].setNome("michele");
		test[3].setPassword("miguel94");
		test[3].setUser(null);
		}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testCreateAgente() {
		boolean[] attesi={true,true,false,false};
		boolean[] risultati=new boolean[4];
		
		for(int i=0;i<4;i++){
			try {
				risultati[i]=ag.create(test[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
			}
			assertTrue("Caso metodo create : " + i +" atteso "+attesi[i] +" ottenuto " +risultati[i], risultati[i] == attesi[i]);
		}
		for(int i=0;i<2;i++){
			try {
				risultati[i]=ag.delete(test[i].getUser());
			} catch (SQLException e) {
				
			}
		}
	}
	
	@Test
	public void testDelete() {
		for(int i=0;i<2;i++){
			try {
				ag.create(test[i]);
			} catch (SQLException e) {
			}
		}
		boolean[] attesi={true,true};
		boolean[] risultati=new boolean[2];
		for(int i=0;i<2;i++){
			try {
				risultati[i]=ag.delete(test[i].getUser());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
			}
			assertTrue("Caso metodo delete : " + i +" atteso "+attesi[i] +" ottenuto " +risultati[i], risultati[i] == attesi[i]);
		}
	}


	@Test
	public void testReadString() throws SQLException {
		Agente prova[]=new Agente[3];
		test[0]=new Agente("CarloanTrani","Ruggiero","rizzi","ruja94","ruggierorizzi");
		prova[0]=ag.read("ruja94");
		test[1]=new Agente("CarloanBari","Paolo","bitta","paolobitta","piccolaketty");
		prova[1]=ag.read("paolobitta");
		test[2]=new Agente("CarloanBari","paolo","bitta","ciccio","piccolaketty");
		prova[2]=ag.read("ciccio");
		for(int i=0;i<3;i++){
			if(prova[i]!=null){
			assertTrue("Caso metodo read : " ,test[i].getAgenzia().compareToIgnoreCase(prova[i].getAgenzia())>=0);
			assertTrue("Caso metodo read : ",test[i].getCognome().compareToIgnoreCase(prova[i].getCognome())>=0);
			assertTrue("Caso metodo read : ",test[i].getNome().compareToIgnoreCase(prova[i].getNome())>=0);
			assertTrue("Caso metodo read : ",test[i].getPassword().compareToIgnoreCase(prova[i].getPassword())>=0);
			assertTrue("Caso metodo read : ",test[i].getUser().compareToIgnoreCase(prova[i].getUser())>=0);
		}
			else{
				assertTrue("Caso metodo read ",prova[2]==null);
			}
	}
	}

	@Test
	public void testGetAll() throws SQLException {
		
		List<Agente> list=new ArrayList<Agente>();
		test[0].setAgenzia("CarloanBarletta");
		test[0].setCognome("Dinoia");
		test[0].setNome("Michele");
		test[0].setPassword("miguel94");
		test[0].setUser("micheledinoia");
		test[1].setAgenzia("CarloanBari");
		test[1].setCognome("Filannino");
		test[1].setNome("Giovanni");
		test[1].setPassword("gianni94");
		test[1].setUser("gianiifilannino");
		test[2].setAgenzia("CarloanTrani");
		test[2].setCognome("Dicuonzo");
		test[2].setNome("Domenico");
		test[2].setPassword("mirko94");
		test[2].setUser("domenicodicuonzo");
		test[3].setAgenzia("CarloanTrani");
		test[3].setCognome("Rizzi");
		test[3].setNome("Ruggiero");
		test[3].setPassword("ruja94");
		test[3].setUser("ruggierorizzi");
		list=ag.getAll();
		boolean risultato = false;
		for(int j=0;j<3;j++){
			for(int i=0;i<list.size();i++)
				if(list.get(i).getAgenzia().compareToIgnoreCase(test[j].getAgenzia())==0)
					if(list.get(i).getCognome().compareToIgnoreCase(test[j].getCognome())==0)
						if(list.get(i).getNome().compareToIgnoreCase(test[j].getNome())==0)
							if(list.get(i).getPassword().compareToIgnoreCase(test[j].getPassword())==0)
								if(list.get(i).getUser().compareToIgnoreCase(test[j].getUser())==0)
									risultato=true;
			}
		assertTrue("Caso metodo getAll ",risultato==true);
		}

	@Test
	public void testCheckCredenziali() throws SQLException {
		boolean risultato;
		String[] username ={"carlottabuono","claudiospano","domenicodicuonzo","gianfrancescoverdi","carlottabuono","ruggierorizzi"};
		String[] password={"carol86","claudio45","mirko94","gianfry86","ciao","cavalca"};
		boolean[] atteso={true,true,true,true,false,false};
		for(int i=0;i<username.length;i++){
			risultato=ag.checkCredenziali(username[i], password[i]);
			assertTrue("Caso metodo testCredenziali "+username[i]+"dovrebbe essere "+ atteso[i]+ " ma è "+ risultato,risultato==atteso[i]);
		}
	}

}
