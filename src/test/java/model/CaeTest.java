package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import database.SchedaElettoraleDAO;
import database.SchedaElettoraleDAOImpl;

class CaeTest {


	@Test
	void scrutinioNonScrutinatore() {
		Cae cae = new Cae("prova@prova.com", false, false);
		SchedaElettorale s = new SchedaElettorale("", new InformazioneScheda(), 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		assertFalse(cae.scrutinio(s));
	}
	
	@Test
	void scrutinioScrutinatoreNoRisultato() {
		Cae cae = new Cae("prova@prova.com", true, false);
		SchedaElettorale s = new SchedaElettorale("", new InformazioneScheda(), 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		s.crea();
		assertFalse(cae.scrutinio(s));
		s.elimina();
	}
	
	@Test
	void scrutinioScrutinatoreSìRisultato() {
		Cae cae = new Cae("prova@prova.com", true, false);
		InformazioneScheda i = new InformazioneScheda();
		i.add(new Quesito("test"));
		SchedaElettorale s = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		s.crea();
		Elettore e = new Elettore("test@test.com", "TTTTTTTTTTTTTTT", "Palermo");
		Preferenza p = new Preferenza();
		for(Voce v: s.getInformazione().getVoci()) p.add(v, 1);
		e.esprimiPreferenza(s, p);
		assertEquals(cae.scrutinio(s), s.scrutinio());
		s.elimina();
	}


	@Test
	void creaSchedaElettoraleNoConfiguratore() {
		Cae cae = new Cae("prova@prova.com", false, false);
		SchedaElettorale s = new SchedaElettorale("", new InformazioneScheda(), 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		cae.creaSchedaElettorale(s);
		assertTrue(s.esiste());
		s.elimina();
	}
	
	@Test
	void creaSchedaElettoraleSìConfiguratore() {
		Cae cae = new Cae("prova@prova.com", true, false);
		SchedaElettorale s = new SchedaElettorale("", new InformazioneScheda(), 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		cae.creaSchedaElettorale(s);
		assertTrue(s.esiste());
		s.elimina();
	}
	
	/* impostare come public la visibilità del metodo configuratorePerScheda
	@Test
	void configuratorePerSchedaNoConfiguratore() {
		Cae cae = new Cae("prova@prova.com", false, false);
		SchedaElettorale s = new SchedaElettorale("", new InformazioneScheda(), 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		assertFalse(cae.configuratorePerScheda(s));
	}
	
	
	@Test
	void configuratorePerSchedaConfiguratorePerScheda() {
		Cae cae = new Cae("prova@prova.com", false, true);
		SchedaElettorale s = new SchedaElettorale("", new InformazioneScheda(), 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		cae.creaSchedaElettorale(s);
		assertTrue(cae.configuratorePerScheda(s));
	}
	
	
	@Test
	void configuratorePerSchedaConfiguratoreNonPerScheda() {
		Cae cae = new Cae("prova@prova.com", false, true);
		SchedaElettorale s = new SchedaElettorale("", new InformazioneScheda(), 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		assertFalse(cae.configuratorePerScheda(s));
	}*/
	
	@Test
	void getSchedeElettoraliConElettoriSchedeElettorali() {
		Cae cae = new Cae("prova@prova.com", true, false);
		SchedaElettorale s = new SchedaElettorale("", new InformazioneScheda(), 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		cae.creaSchedaElettorale(s);
		cae.getSchedeElettoraliConElettori();
		s.elimina();
	}
	
	@Test
	void getSchedeElettoraliConElettoriNoSchedeElettorali() {
		Cae cae = new Cae("prova@prova.it", true, false);
		cae.getSchedeElettoraliConElettori();
	}
}
