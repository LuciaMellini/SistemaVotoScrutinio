package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import database.CandidatoDAO;
import database.CandidatoDAOImpl;
import database.SchedaElettoraleDAO;
import database.SchedaElettoraleDAOImpl;

class CandidatoTest {

	@Test
	void creaCandidatoNonEsiste() {
		Candidato c = new Candidato("TTTTTTTTTTTTTTTT", "test", "test");
		c.crea();
		assertTrue(c.esiste());
		c.elimina();
	}

	
	@Test
	void creaCandidatoEsiste() {
		Candidato c = new Candidato("TTTTTTTTTTTTTTTT", "test", "test");
		c.crea();
		c.crea();
		assertTrue(c.esiste());
		c.elimina();
	}

	
	@Test
	void eliminaCandidatoNonEsiste() {
		Candidato c = new Candidato("BBBBBBBBBBBBBBBB", "test", "test");
		c.elimina();
		assertFalse(c.esiste());
	}

	
	@Test
	void eliminaCandidatoEsiste() {
		Candidato c = new Candidato("TTTTTTTTTTTTTTTT", "test", "test");
		c.crea();
		c.elimina();
		assertFalse(c.esiste());
	}
	
	@Test
	void inserisciInSchedaElettorale() {
		Candidato c = new Candidato("TTTTTTTTTTTTTTTT", "test", "test");
		c.crea();
		InformazioneScheda i = new InformazioneScheda();
		i.add(c);
		SchedaElettorale s = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		s.crea(); //invoca inserisciInSchedaElettorale
		for(Voce v:s.getInformazione()) assertTrue(((Candidato) v).equals(c));
		c.elimina();
		s.elimina();
	}
}
