package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ModCalcoloVincitoreTest {

	@Test
	void calcoloVincitoreMaggioranzaDueTipiVoceNumeroPreferenzeUguale() {
		InformazioneScheda i = new InformazioneScheda();
		Quesito q1 = new Quesito("test1");
		Quesito q2 = new Quesito("test2");
		q1.crea();
		q2.crea();
		i.add(q1);
		i.add(q2);
		SchedaElettorale sch = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<Voce> voci = new HashSet<>();
		for(Voce v:sch.getInformazione()) voci.add(v);
		Risultato r = new Risultato(ModCalcoloVincitore.MAGGIORANZA, 0, voci);
		r.add(q1, 1);
		r.add(q2, 1);
		assertNull(ModCalcoloVincitore.MAGGIORANZA.calcoloVincitore(r));
		q1.elimina();
		q2.elimina();
		sch.elimina();
	}
	
	@Test
	void calcoloVincitoreMaggioranzaDueTipiVoceNumeroPreferenzeDifferenti() {
		InformazioneScheda i = new InformazioneScheda();
		Quesito q1 = new Quesito("test1");
		Quesito q2 = new Quesito("test2");
		q1.crea();
		q2.crea();
		i.add(q1);
		i.add(q2);
		SchedaElettorale sch = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<Voce> voci = new HashSet<>();
		for(Voce v:sch.getInformazione()) voci.add(v);
		Risultato r = new Risultato(ModCalcoloVincitore.MAGGIORANZA, 0, voci);
		r.add(q1, 1);
		r.add(q1, 1);
		r.add(q2, 1);
		assertEquals(ModCalcoloVincitore.MAGGIORANZA.calcoloVincitore(r), q1);
		q1.elimina();
		q2.elimina();
		sch.elimina();
	}
	
	@Test
	void calcoloVincitoreMaggioranzaUnTipoVoce() {
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
		assertEquals(ModCalcoloVincitore.MAGGIORANZA.calcoloVincitore(r), q);
		q.elimina();
		sch.elimina();
	}
	
	
	@Test
	void calcoloVincitoreMaggioranzaAssolutaUnaVoceRaggiunto() {
		InformazioneScheda i = new InformazioneScheda();
		Quesito q1 = new Quesito("test1");
		Quesito q2 = new Quesito("test2");
		q1.crea();
		q2.crea();
		i.add(q1);
		i.add(q2);
		SchedaElettorale sch = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<Voce> voci = new HashSet<>();
		for(Voce v:sch.getInformazione()) voci.add(v);
		Risultato r = new Risultato(ModCalcoloVincitore.MAGGIORANZA, 1, voci);
		r.add(q1, 1);
		r.add(q2, 1);
		assertEquals(ModCalcoloVincitore.MAGGIORANZA.calcoloVincitore(r), q1);
		q1.elimina();
		q2.elimina();
		sch.elimina();
	}
	

	@Test
	void calcoloVincitoreMaggioranzaAssolutaUnaVoceNonRaggiunto() {
		InformazioneScheda i = new InformazioneScheda();
		Quesito q = new Quesito("test");
		q.crea();
		i.add(q);
		SchedaElettorale sch = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		sch.crea();
		Set<Voce> voci = new HashSet<>();
		for(Voce v:sch.getInformazione()) voci.add(v);
		Risultato r = new Risultato(ModCalcoloVincitore.MAGGIORANZAASS, 50, voci);
		r.add(q, 1);
		r.add(q, 1);
		assertNull(ModCalcoloVincitore.MAGGIORANZAASS.calcoloVincitore(r));
		q.elimina();
		sch.elimina();
	}

}
