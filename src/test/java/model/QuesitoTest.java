package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QuesitoTest {

	@Test
	void creaQuesitoNonEsiste() {
		Quesito q = new Quesito("test");		
		q.crea();
		assertTrue(q.esiste());
		q.elimina();
	}

	
	@Test
	void creaQuesitoEsiste() {
		Quesito q = new Quesito("test");		
		q.crea();
		q.crea();
		assertTrue(q.esiste());
		q.elimina();
	}
	
	@Test
	void eliminaQuesitoNonEsiste() {
		Quesito q = new Quesito("test");	
		q.elimina();	
		assertFalse(q.esiste());
	}

	
	@Test
	void eliminaQuesitoEsiste() {
		Quesito q = new Quesito("test");		
		q.crea();
		q.elimina();
		assertFalse(q.esiste());
		
	}
	
	@Test
	void inserisciInSchedaElettorale() {
		Quesito q = new Quesito("test");		
		q.crea();
		InformazioneScheda i = new InformazioneScheda();
		i.add(q);
		SchedaElettorale s = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		s.crea(); //invoca inserisciInSchedaElettorale
		for(Voce v:s.getInformazione()) assertTrue(((Quesito) v).equals(q));
		q.elimina();
		s.elimina();
	}
}
