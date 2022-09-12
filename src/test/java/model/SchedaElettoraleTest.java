package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SchedaElettoraleTest {
	
	@Test
	void creaSchedaElettoraleNonEsiste() {
		SchedaElettorale s = new SchedaElettorale("", new InformazioneScheda(), 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		s.crea();
		assertTrue(s.esiste());
		s.elimina();
	}
	
	@Test
	void creaSchedaElettoraleEsiste() {
		SchedaElettorale s = new SchedaElettorale("", new InformazioneScheda(), 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		s.crea();
		s.crea();
		assertTrue(s.esiste());
		s.elimina();
	}
	
	@Test
	void eliminaSchedaElettoraleNonEsiste() {
		SchedaElettorale s = new SchedaElettorale("", new InformazioneScheda(), 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		s.elimina();
		assertFalse(s.esiste());
	}
	
	@Test
	void eliminaSchedaElettoraleEsiste() {
		SchedaElettorale s = new SchedaElettorale("", new InformazioneScheda(), 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		s.crea();
		s.elimina();
		assertFalse(s.esiste());
	}
	
	@Test
	void esprimiPreferenza() {
		InformazioneScheda i = new InformazioneScheda();
		Quesito q = new Quesito("test");
		q.crea();
		i.add(q);
		SchedaElettorale s = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		s.crea();
		Preferenza p = new Preferenza();
		for(Voce v: s.getInformazione().getVoci()) p.add(v, 1);
		s.esprimiPreferenza(p);
		s.elimina();
		q.elimina();
	}

	@Test
	void getPreferenzeVuoto() {
		SchedaElettorale s = new SchedaElettorale("", new InformazioneScheda(), 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		assertTrue(s.getPreferenze().isEmpty());
	}
	
	
	/*test vari tipi scheda eletrtorale scrutinio
	
	@Test
	void scrutinioScrutinatoreNoPreferenze() {
		Cae cae = new Cae("prova@prova.com", true, false);
		SchedaElettorale s = new SchedaElettorale("", new InformazioneScheda(), 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 0);
		s.crea();
		assertEquals(cae.scrutinio(s), s.scrutinio());
		s.elimina();
	}
	
	@Test
	void scrutinioScrutinatoreRisultatiRaggiuntoLivelloQuorum() {
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
	void scrutinioScrutinatoreRisultatiNoRaggiuntoLivelloQuorum() {
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
	*/
}
