package model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import database.CandidatoDAO;
import database.CandidatoDAOImpl;
import database.PartitoDAO;
import database.PartitoDAOImpl;
import database.SistemaVotoScrutinioDAO;
import database.SistemaVotoScrutinioDAOImpl;

public class Partito implements Voce, Iterable<Candidato>{
	private String nome;
	private Set<Candidato> candidati;
	private Candidato capoPartito;
	
	public Partito(String nome) {
		this.nome = nome;
		PartitoDAO pDAO = new PartitoDAOImpl();
		Partito partito = pDAO.getPartito(nome);
		this.capoPartito = partito.capoPartito;
		this.candidati = partito.candidati;
	}
	
	public Partito(String nome, Candidato capoPartito, Set<Candidato> candidati) {
		this.nome = nome;
		this.capoPartito = capoPartito;
		this.candidati = candidati;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Candidato getCapoPartito() {		
		return capoPartito;
	}
	
	public Set<Candidato> getCandidati(){
		return new HashSet(candidati);
	}
	
	@Override
	public void crea() {
		PartitoDAO pDAO = new PartitoDAOImpl();
		if(!pDAO.esiste(this)) {
			pDAO.crea(this);
		}
		SistemaVotoScrutinio.getIstanza().log("Creazione partito " + this.nome);
	}
	
	@Override
	public boolean esiste() {
		PartitoDAO pDAO = new PartitoDAOImpl();
		return pDAO.esiste(this);
	}
	
	@Override
	public void elimina() {
		PartitoDAO pDAO = new PartitoDAOImpl();
		if(pDAO.esiste(this)) pDAO.elimina(this);
		SistemaVotoScrutinio.getIstanza().log("Eliminazione partito " + this.nome);
	}
	
	
	@Override
	public int inserisciInSchedaElettorale(SchedaElettorale s) {
		PartitoDAO pDAO = new PartitoDAOImpl();
		return pDAO.inserisciInSchedaElettorale(s, this);
	}
	
	@Override
	public Iterator<Candidato> iterator() {
		return getCandidati().iterator();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(nome);
		if(!Objects.isNull(capoPartito)) {
			sb.append(" - capo partito: ");
			sb.append(capoPartito.toString());
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Partito)) return false;
		Partito o = (Partito) obj;
		return o.nome.equals(nome) && o.candidati.equals(candidati) && o.capoPartito.equals(capoPartito);
	}
	
	@Override
	public int hashCode() {
		return nome.hashCode();
	}
}
