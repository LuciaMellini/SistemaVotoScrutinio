package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

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
		for(Entry<Voce, Integer> e : p) assertTrue(s.getPreferenze().contains(e));

		s.elimina();
		q.elimina();
	}
	

	@Test
	void scrutinioNoTerminataSessione() {
		InformazioneScheda i = new InformazioneScheda();
		Quesito q = new Quesito("test");
		q.crea();
		i.add(q);
		SchedaElettorale sch = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 100);
		sch.crea();
		Set<SchedaElettorale> schede = new HashSet<>();
		schede.add(sch);
				
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.DATE, -1);
		Date inizio = c.getTime();
		
		c = Calendar.getInstance(); 
		c.add(Calendar.DATE, 2);
		Date fine = c.getTime();
				
		Sessione s = new Sessione("test sessione", inizio, fine, schede, "Palermo");
		s.crea();
		Preferenza p = new Preferenza();
		for(Voce v: sch.getInformazione().getVoci()) p.add(v, 1);
		sch.esprimiPreferenza(p);
		assertFalse(sch.scrutinio());
		Set<Voce> voci = new HashSet<>();
		for(Voce v:sch.getInformazione()) voci.add(v);
		Risultato r = new Risultato(ModCalcoloVincitore.MAGGIORANZA, 0, voci);
		r.add(q, 1);
		r.calcolaVincitore();
		q.elimina();
		s.elimina();
		sch.elimina();
	}
	
	@Test
	void scrutinioTerminataSessioneRisultatoNullRaggiuntoQuorum() {
		Elettore e1 = new Elettore("test@test.it", "TTTTTTTTTTTTTTTT", "Palermo");
		Elettore e2 = new Elettore("test@test.fr", "TTTTTTTTTTTTTTTR", "Palermo");
		e1.crea("");
		e2.crea("");
		InformazioneScheda i = new InformazioneScheda();
		Quesito q = new Quesito("test");
		q.crea();
		i.add(q);
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
		Preferenza p = new Preferenza();
		for(Voce v: sch.getInformazione().getVoci()) p.add(v, 1);
		e1.esprimiPreferenza(sch, p);
		assertTrue(sch.scrutinio());
		Set<Voce> voci = new HashSet<>();
		for(Voce v:sch.getInformazione()) voci.add(v);
		Risultato r = new Risultato(ModCalcoloVincitore.MAGGIORANZA, 1, voci);
		r.add(q, 1);
		r.calcolaVincitore();
		assertEquals(sch.getRisultato(), r);
		e1.elimina();
		e2.elimina();
		q.elimina();
		s.elimina();
		sch.elimina();
	}
	
	@Test
	void scrutinioTerminataSessioneRisultatoNullNoRaggiuntoQuorum() {
		Elettore e1 = new Elettore("test@test.it", "TTTTTTTTTTTTTTTT", "Palermo");
		Elettore e2 = new Elettore("test@test.fr", "TTTTTTTTTTTTTTTR", "Palermo");
		e1.crea("");
		e2.crea("");
		InformazioneScheda i = new InformazioneScheda();
		Quesito q = new Quesito("test");
		q.crea();
		i.add(q);
		SchedaElettorale sch = new SchedaElettorale("", i, 0, ModVoto.CATEGORICO, ModCalcoloVincitore.MAGGIORANZA, 100);
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
		Preferenza p = new Preferenza();
		for(Voce v: sch.getInformazione().getVoci()) p.add(v, 1);
		e1.esprimiPreferenza(sch, p);
		assertFalse(sch.scrutinio());
		Set<Voce> voci = new HashSet<>();
		for(Voce v:sch.getInformazione()) voci.add(v);
		Risultato r = new Risultato(ModCalcoloVincitore.MAGGIORANZA, 1, voci);
		r.add(q, 1);
		r.calcolaVincitore();
		assertEquals(sch.getRisultato(), r);
		e1.elimina();
		e2.elimina();
		q.elimina();
		s.elimina();
		sch.elimina();
	}

	
	@Test
	void scrutinioTerminataSessioneRisultatoNoNullRaggiuntoQuorum() {
		InformazioneScheda i = new InformazioneScheda();
		Quesito q = new Quesito("test");
		q.crea();
		i.add(q);
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
		Preferenza p = new Preferenza();
		for(Voce v: sch.getInformazione().getVoci()) p.add(v, 1);
		sch.esprimiPreferenza(p);
		sch.scrutinio();
		assertTrue(sch.scrutinio());
		Set<Voce> voci = new HashSet<>();
		q.elimina();
		s.elimina();
		sch.elimina();
		q.elimina();
	}


}
