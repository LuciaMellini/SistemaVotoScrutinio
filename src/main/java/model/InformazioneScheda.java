package model;


import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class InformazioneScheda implements Iterable<Voce>{ 
	private Map<Voce, Integer> informazioneScheda;
	
	public InformazioneScheda() {
		this.informazioneScheda = new HashMap<>();
	}
	
	public InformazioneScheda(InformazioneScheda i) {
		informazioneScheda = new HashMap<>(i.informazioneScheda);
	}
	
	public void inserisciInSchedaElettorale(SchedaElettorale s) {
		for(Voce v : this) {
			informazioneScheda.replace(v,v.inserisciInSchedaElettorale(s));
		}
	}
	
	public void add(Voce v) {
		informazioneScheda.put(v, -1);
	}
	
	public void remove(Voce v) {
		informazioneScheda.remove(v);
	}
	
	public void add(Voce v, int id) {
		informazioneScheda.put(v, id);
	}
	
	public boolean isEmpty() {
		return informazioneScheda.size() == 0;
	}
	
	public Set<Voce> getVoci(){
		return new HashSet<>(informazioneScheda.keySet());
	}
	
	public Integer getId(Voce v) {
		return informazioneScheda.get(v);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Voce v : informazioneScheda.keySet()) {
			sb.append(v.toString() +  "\n");
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof InformazioneScheda)) return false;
		InformazioneScheda o = (InformazioneScheda) obj;
		return o.informazioneScheda.equals(this.informazioneScheda);
	}
	
	@Override
	public int hashCode() {
		return informazioneScheda.hashCode();
	}

	@Override
	public Iterator iterator() {
		return informazioneScheda.keySet().iterator();
	}
}
