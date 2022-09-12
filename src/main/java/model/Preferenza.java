package model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Preferenza implements Iterable<Entry<Voce, Integer>>{
	
	private Map<Voce,Integer> preferenze;
		
	public Preferenza() {
		preferenze = new HashMap<>();
	}
	
	public Preferenza(Preferenza p) {
		preferenze = new HashMap<>(p.preferenze);
	}
	
	public void add(Voce v, Integer n) {
		preferenze.put(v,n);
	}
	
	public void remove(Voce v) {
		preferenze.remove(v);
	}
	

	public boolean bianca() {
		return preferenze.isEmpty();
	}
	
	Set<Entry<Voce, Integer>> getPreferenze(){
		return preferenze.entrySet();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Entry<Voce, Integer> e : preferenze.entrySet()) {
			sb.append(e.getKey().toString() + ": " + e.getValue());
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Preferenza)) return false;
		Preferenza o = (Preferenza) obj;
		return preferenze.equals(o.preferenze);
	}
	
	@Override
	public int hashCode() {		
		return preferenze.hashCode();
	}

	@Override
	public Iterator<Entry<Voce, Integer>> iterator() {
		return preferenze.entrySet().iterator();	
	}
}
