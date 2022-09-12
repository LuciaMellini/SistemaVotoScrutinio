package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class PartitoTest {

	@Test
	void creaPartitoNonEsiste() {
		Candidato capoPartito = new Candidato("TTTTTTTTTTTTTTTT", "test", "test");
		capoPartito.crea();
		Set<Candidato> candidati = new HashSet<>();
		candidati.add(capoPartito);
		Partito p = new Partito("test", capoPartito, candidati);
		p.crea();
		assertTrue(p.esiste());
		capoPartito.elimina();
		p.elimina();
	}

	
	@Test
	void creaPartitoEsiste() {
		Candidato capoPartito = new Candidato("TTTTTTTTTTTTTTTT", "test", "test");
		capoPartito.crea();
		Set<Candidato> candidati = new HashSet<>();
		candidati.add(capoPartito);
		Partito p = new Partito("test", capoPartito, candidati);
		p.crea();
		p.crea();
		assertTrue(p.esiste());
		capoPartito.elimina();
		p.elimina();	
	}
	
	@Test
	void eliminaPartitoNonEsiste() {
		Candidato capoPartito = new Candidato("TTTTTTTTTTTTTTTT", "test", "test");
		capoPartito.crea();
		Set<Candidato> candidati = new HashSet<>();
		candidati.add(capoPartito);
		Partito p = new Partito("test", capoPartito, candidati);
		
		p.elimina();
		assertFalse(p.esiste());
		capoPartito.elimina();
	}

	
	@Test
	void eliminaPartitoEsiste() {
		Candidato capoPartito = new Candidato("TTTTTTTTTTTTTTTT", "test", "test");
		capoPartito.crea();
		Set<Candidato> candidati = new HashSet<>();
		candidati.add(capoPartito);
		Partito p = new Partito("test", capoPartito, candidati);
		
		p.crea();
		p.elimina();
		assertFalse(p.esiste());
		capoPartito.elimina();
	}
	
	@Test
	void inserisciInSchedaElettorale() {
		Candidato capoPartito = new Candidato("TTTTTTTTTTTTTTTT", "test", "test");
		capoPartito.crea();
		Set<Candidato> candidati = new HashSet<>();
		candidati.add(capoPartito);
		Partito p = new Partito("test", capoPartito, candidati);
		
		p.crea();
		InformazioneScheda i = new InformazioneScheda();
		i.add(p);
		SchedaElettorale s = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		s.crea(); //invoca inserisciInSchedaElettorale
		for(Voce v:s.getInformazione()) assertTrue(((Partito) v).equals(p));
		capoPartito.elimina();
		p.elimina();
		s.elimina();
	}

}
