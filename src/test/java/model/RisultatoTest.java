package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

class RisultatoTest {
	
	@Test
	void calcolaVincitoreNoRisultatoVuoto() {
		InformazioneScheda i = new InformazioneScheda();
		Quesito q = new Quesito("test");
		q.crea();
		i.add(q);
		SchedaElettorale sch = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<Voce> voci = new HashSet<>();
		for(Voce v:sch.getInformazione()) voci.add(v);
		Risultato r = new Risultato(ModCalcoloVincitore.MAGGIORANZA, 0, voci);
		r.add(q, 1);
		assertEquals(r.getVincitore(), sch.getModCalcoloVincitore().calcoloVincitore(r));
	}
	
	@Test
	void calcolaVincitoreRisultatoVuoto() {
		InformazioneScheda i = new InformazioneScheda();
		Quesito q = new Quesito("test");
		q.crea();
		i.add(q);
		SchedaElettorale sch = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<Voce> voci = new HashSet<>();
		Risultato r = new Risultato(ModCalcoloVincitore.MAGGIORANZA, 0, voci);
		assertNull(r.getVincitore());
	}
	
	@Test
	void addNoRisultatoVuoto() {
		InformazioneScheda i = new InformazioneScheda();
		Quesito q = new Quesito("test");
		q.crea();
		i.add(q);
		SchedaElettorale sch = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<Voce> voci = new HashSet<>();
		for(Voce v:sch.getInformazione()) voci.add(v);
		Risultato r = new Risultato(ModCalcoloVincitore.MAGGIORANZA, 0, voci);
		r.add(q, 1);
		for(Entry<Voce, Integer> e : r) {
			assertEquals(e.getKey(), q);
			assertEquals(e.getValue(), 1);
		}
	}
	
	@Test
	void addRisultatoVuoto() {
		InformazioneScheda i = new InformazioneScheda();
		Quesito q = new Quesito("test");
		q.crea();
		i.add(q);
		SchedaElettorale sch = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<Voce> voci = new HashSet<>();
		Risultato r = new Risultato(ModCalcoloVincitore.MAGGIORANZA, 0, voci);
		r.add(q, 1);
		assertTrue(r.getRisultato().isEmpty());
	}

}
