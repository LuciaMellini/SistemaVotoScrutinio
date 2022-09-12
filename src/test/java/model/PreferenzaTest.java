package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

class PreferenzaTest {

	@Test
	void add() {
		Preferenza p = new Preferenza();
		Quesito q = new Quesito("test");
		p.add(q, 1);
		for(Entry<Voce, Integer> e : p) {
			assertEquals(e.getKey(), q);
			assertEquals(e.getValue(), 1);
		}
	}
	
	@Test
	void remove() {
		Preferenza p = new Preferenza();
		Quesito q = new Quesito("test");
		p.add(q, 1);
		p.remove(q);
		assertFalse(p.getPreferenze().contains(q));
	}

}
