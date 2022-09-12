package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtenteTest {
	/* impostare come public la visibilità del metodo registrato*/
	@Test
	void creaUtenteNonRegistrato() {
		Utente u = new Utente("test@test.it");
		u.crea("");
		assertTrue(u.registrato());
		u.elimina();
	}
	
	@Test
	void creaUtenteRegistrato() {
		Utente u = new Utente("test@test.it");
		u.crea("");
		u.crea("");
		assertTrue(u.registrato());
		u.elimina();
	}
	
	@Test
	void eliminaUtenteNonEsiste() {
		Utente u = new Utente("test@test.it");
		u.elimina();
		assertFalse(u.registrato());
	}

	
	@Test
	void eliminaUtenteEsiste() {
		Utente u = new Utente("test@test.it");
		u.crea("");
		u.elimina();
		assertFalse(u.registrato());
	}

}
