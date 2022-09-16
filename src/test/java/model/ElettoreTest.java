package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import database.CandidatoDAO;
import database.CandidatoDAOImpl;
import database.ElettoreDAO;
import database.ElettoreDAOImpl;

class ElettoreTest {

	@Test
	void creaElettoreNonEsiste() {
		Elettore e = new Elettore("test@test.it", "TTTTTTTTTTTTTTTT", "Palermo");
		System.out.println(e.getEmail());
		e.crea("");
		ElettoreDAO eDAO = new ElettoreDAOImpl();
		eDAO.crea(e);
		assertTrue(e.registrato());
		e.elimina();
	}

	
	@Test
	void creaElettoreEsiste() {
		Elettore e = new Elettore("test@test.it", "TTTTTTTTTTTTTTTT", "Palermo");
		e.crea("");
		e.crea("");
		assertTrue(e.registrato());
		e.elimina();
	}
	
	
	@Test
	void eliminaElettoreNonEsiste() {
		Elettore e = new Elettore("test@test.it", "TTTTTTTTTTTTTTTT", "Palermo");
		e.elimina();
		assertFalse(e.registrato());
	}

	
	@Test
	void eliminaElettoreEsiste() {
		Elettore e = new Elettore("test@test.it", "TTTTTTTTTTTTTTTT", "Palermo");
		e.crea("");
		assertTrue(e.registrato());
		e.elimina();
	}
	
	
	@Test
	void esprimiPreferenza() {
		Elettore e = new Elettore("test@test.it", "TTTTTTTTTTTTTTTT", "Palermo");
		e.crea("");
		InformazioneScheda i = new InformazioneScheda();
		Quesito q = new Quesito("test");
		q.crea();
		i.add(q);
		SchedaElettorale s = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		s.crea();
		Preferenza p = new Preferenza();
		p.add(q, 1);
		e.esprimiPreferenza(s, p);
		assertTrue(e.preferenzaEspressa(s));
		e.elimina();
		q.elimina();
		s.elimina();
	}
	
	@Test
	void getSessioniInCorsoInizioPrimaFineDopoOggi() {
		Elettore e = new Elettore("test@test.it", "TTTTTTTTTTTTTTTT", "Palermo");
		e.crea("");
		InformazioneScheda i = new InformazioneScheda();
		SchedaElettorale sch = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<SchedaElettorale> schede = new HashSet<>();
		schede.add(sch);
				
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.DATE, -1);
		Date inizio = c.getTime();
		
		c = Calendar.getInstance(); 
		c.add(Calendar.DATE, 1);
		Date fine = c.getTime();
				
		Sessione s = new Sessione("test sessione", inizio, fine, schede, "Palermo");
		s.crea();
		assertTrue(e.getSessioniInCorso().contains(s));
		sch.elimina();
		s.elimina();
		e.elimina();
	}
	
	@Test
	void getSessioniInCorsoInizioDopoFineDopoOggi() {
		Elettore e = new Elettore("test@test.it", "TTTTTTTTTTTTTTTT", "Palermo");
		e.crea("");
		InformazioneScheda i = new InformazioneScheda();
		SchedaElettorale sch = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<SchedaElettorale> schede = new HashSet<>();
		schede.add(sch);
				
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.DATE, 1);
		Date inizio = c.getTime();
		
		c = Calendar.getInstance(); 
		c.add(Calendar.DATE, 2);
		Date fine = c.getTime();
				
		Sessione s = new Sessione("test sessione", inizio, fine, schede, "Palermo");
		s.crea();
		assertFalse(e.getSessioniInCorso().contains(s));
		sch.elimina();
		s.elimina();
		e.elimina();
	}
	
	@Test
	void getSessioniInCorsoInizioPrimaFinePrimaOggi() {
		Elettore e = new Elettore("test@test.it", "TTTTTTTTTTTTTTTT", "Palermo");
		e.crea("");
		InformazioneScheda i = new InformazioneScheda();
		SchedaElettorale sch = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<SchedaElettorale> schede = new HashSet<>();
		schede.add(sch);
				
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.DATE, -2);
		Date inizio = c.getTime();
		
		c = Calendar.getInstance(); 
		c.add(Calendar.DATE, -1);
		Date fine = c.getTime();
				
		Sessione s = new Sessione("test sessione", inizio, fine, schede, "Palermo");
		s.crea();
		assertFalse(e.getSessioniInCorso().contains(s));
		sch.elimina();
		s.elimina();
		e.elimina();
	}
	
	@Test
	void getSchedeElettoraliNonAutorizzato() {
		Elettore e = new Elettore("test@test.it", "TTTTTT10A01TTTTT", "Palermo");
		e.crea("");
		InformazioneScheda i = new InformazioneScheda();
		SchedaElettorale sch = new SchedaElettorale("", i, 18, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<SchedaElettorale> schede = new HashSet<>();
		schede.add(sch);
				
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.DATE, -1);
		Date inizio = c.getTime();
		
		c = Calendar.getInstance(); 
		c.add(Calendar.DATE, 1);
		Date fine = c.getTime();
				
		Sessione s = new Sessione("test sessione", inizio, fine, schede, "Palermo");
		s.crea();
		assertFalse(e.getSchedeElettorali().contains(s));
		sch.elimina();
		s.elimina();
		e.elimina();
	}
	
	@Test
	void getSchedeElettoraliAutorizzato() {
		Elettore e = new Elettore("test@test.it", "TTTTTT90A01TTTTT", "Palermo");
		e.crea("");
		InformazioneScheda i = new InformazioneScheda();
		SchedaElettorale sch = new SchedaElettorale("", i, 18, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<SchedaElettorale> schede = new HashSet<>();
		schede.add(sch);
				
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.DATE, -1);
		Date inizio = c.getTime();
		
		c = Calendar.getInstance(); 
		c.add(Calendar.DATE, 1);
		Date fine = c.getTime();
				
		Sessione s = new Sessione("test sessione", inizio, fine, schede, "Palermo");
		s.crea();
		assertTrue(e.getSchedeElettorali().contains(sch));
		sch.elimina();
		s.elimina();
		e.elimina();
	}
	
	@Test
	void getSchedeElettoraliSenzaPreferenzaAutorizzatoSìPreferenzaNo() {
		Elettore e = new Elettore("test@test.it", "TTTTTT90A01TTTTT", "Palermo");
		e.crea("");
		InformazioneScheda i = new InformazioneScheda();
		Quesito q = new Quesito("test");
		q.crea();
		i.add(q);
		SchedaElettorale sch = new SchedaElettorale("", i, 18, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<SchedaElettorale> schede = new HashSet<>();
		schede.add(sch);
				
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.DATE, -1);
		Date inizio = c.getTime();
		
		c = Calendar.getInstance(); 
		c.add(Calendar.DATE, 1);
		Date fine = c.getTime();
				
		Sessione s = new Sessione("test sessione", inizio, fine, schede, "Palermo");
		s.crea();
		assertTrue(e.getSchedeElettoraliSenzaPreferenza().contains(sch));
		q.elimina();
		sch.elimina();
		s.elimina();
		e.elimina();
	}
	
	@Test
	void getSchedeElettoraliSenzaPreferenzaAutorizzatoNoPreferenzaSì() {
		Elettore e = new Elettore("test@test.it", "TTTTTT10A01TTTTT", "Palermo");
		e.crea("");
		InformazioneScheda i = new InformazioneScheda();
		Quesito q = new Quesito("test");
		q.crea();
		i.add(q);
		SchedaElettorale sch = new SchedaElettorale("", i, 18, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<SchedaElettorale> schede = new HashSet<>();
		schede.add(sch);
				
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.DATE, -1);
		Date inizio = c.getTime();
		
		c = Calendar.getInstance(); 
		c.add(Calendar.DATE, 1);
		Date fine = c.getTime();
				
		Sessione s = new Sessione("test sessione", inizio, fine, schede, "Palermo");
		s.crea();
		Preferenza p = new Preferenza();
		p.add(q, 1);
		e.esprimiPreferenza(sch, p);
		assertFalse(e.getSchedeElettoraliSenzaPreferenza().contains(sch));
		q.elimina();
		sch.elimina();
		s.elimina();
		e.elimina();
	}
	
	@Test
	void getSchedeElettoraliSenzaPreferenzaAutorizzatoSìPreferenzaSì() {
		Elettore e = new Elettore("test@test.it", "TTTTTT90A01TTTTT", "Palermo");
		e.crea("");
		InformazioneScheda i = new InformazioneScheda();
		Quesito q = new Quesito("test");
		q.crea();
		i.add(q);
		SchedaElettorale sch = new SchedaElettorale("", i, 18, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<SchedaElettorale> schede = new HashSet<>();
		schede.add(sch);
				
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.DATE, -1);
		Date inizio = c.getTime();
		
		c = Calendar.getInstance(); 
		c.add(Calendar.DATE, 1);
		Date fine = c.getTime();
				
		Sessione s = new Sessione("test sessione", inizio, fine, schede, "Palermo");
		s.crea();
		Preferenza p = new Preferenza();
		p.add(q, 1);
		e.esprimiPreferenza(sch, p);
		assertFalse(e.getSchedeElettoraliSenzaPreferenza().contains(sch));
		q.elimina();
		sch.elimina();
		s.elimina();
		e.elimina();
	}
	
	/*impostare come public la visibilità del metodo autorizzato
	
	@Test
	void autorizzatoSì() {
		Elettore e = new Elettore("test@test.it", "TTTTTT00A01TTTTT", "Palermo");
		e.crea("");
		assertTrue(e.autorizzato(18));
		e.elimina();
	}
	
	@Test
	void autorizzatoNo() {
		Elettore e = new Elettore("test@test.it", "TTTTTT21A01TTTTT", "Palermo");
		e.crea("");
		assertFalse(e.autorizzato(18));
		e.elimina();
	}*/
}
