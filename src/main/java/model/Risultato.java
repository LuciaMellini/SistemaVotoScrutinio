package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

public class Risultato implements Iterable<Entry<Voce, Integer>>{
	private Map<Voce, Integer> risultato;
	private Voce vincitore;
	private ModCalcoloVincitore modCalcoloVincitore;
	private int numeroVotanti;
	
	public Risultato(ModCalcoloVincitore modCalcoloVincitore, int numeroVotanti, Set<Voce> voci) {
		risultato = new HashMap<>();
		for(Voce v: voci) {
			risultato.put(v,0);
		}
		this.numeroVotanti = numeroVotanti;
		this.modCalcoloVincitore = modCalcoloVincitore;
		this.vincitore = null;
	}
	
	public Risultato(Risultato risultato) {
		this.risultato = new HashMap<>(risultato.risultato);
		this.numeroVotanti = risultato.numeroVotanti;
		this.modCalcoloVincitore = risultato.modCalcoloVincitore;
		this.vincitore = risultato.vincitore;
	}
	
	public int getNumeroVotanti() {
		return numeroVotanti;
	}
	
	public Voce getVincitore() {
		calcolaVincitore();
		return vincitore;
	}
	
	public void calcolaVincitore() {
		if(!risultato.isEmpty()) {
			vincitore = modCalcoloVincitore.calcoloVincitore(this);
		}
	}
	
	Map<Voce, Integer> getRisultato() {
		return new HashMap<>(risultato);
	}
	
	public void add(Voce v, int ris) {
		if(!risultato.isEmpty()) risultato.put(v,risultato.get(v) + ris);
	}
		
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Numero votanti: " + numeroVotanti + "\n");
		List<Entry<Voce, Integer>> list = new ArrayList<Entry<Voce, Integer>>(risultato.entrySet());
		list.sort(Entry.comparingByValue());
		
		for(Entry<Voce, Integer> e : list) {
			sb.append(e.getKey().toString() + ": " + e.getValue() + "\n");
		}
		sb.append("\nVincitore: " + vincitore.toString());
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Risultato)) return false;
		Risultato o = (Risultato) obj;
		return o.risultato.equals(risultato) && o.numeroVotanti == numeroVotanti && o.modCalcoloVincitore.equals(modCalcoloVincitore);
	}
	
	@Override
	public int hashCode() {
		int hash = risultato.hashCode();
		hash = hash*31 + numeroVotanti;
		hash = hash*31 + modCalcoloVincitore.hashCode();
		return hash;
	}

	@Override
	public Iterator<Entry<Voce, Integer>> iterator() {
		return risultato.entrySet().iterator();
	}
}
