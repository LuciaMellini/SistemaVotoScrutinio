package model;

import java.util.Objects;

import database.CandidatoDAO;
import database.CandidatoDAOImpl;

public class Candidato implements Voce{
	private String codiceFiscale;
    private String nome;
    private String cognome;
    
	public Candidato(String codiceFiscale, String nome, String cognome) {
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
	}
	
	public Candidato(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
		CandidatoDAO cDAO = new CandidatoDAOImpl();
		Candidato candidato = cDAO.getCandidato(codiceFiscale);
		this.nome = candidato.nome;
		this.cognome = candidato.cognome;
	}
	
	public String getCodiceFiscale() {
		return new String(codiceFiscale);
	}
	
	public String getNome() {
		return new String(nome);
	}
	
	public String getCognome() {
		return new String(cognome);
	}
	
	@Override
	public void crea() {
		CandidatoDAO cDAO = new CandidatoDAOImpl();
		if(!cDAO.esiste(this)) cDAO.crea(this);

		SistemaVotoScrutinio.getIstanza().log("Creazione candidato " + this.codiceFiscale);
	}
	
	@Override
	public void elimina() {
		CandidatoDAO cDAO = new CandidatoDAOImpl();
		if(cDAO.esiste(this)) {
			cDAO.elimina(this);
			SistemaVotoScrutinio.getIstanza().log("Eliminazione candidato " + this.codiceFiscale);
		}

	}
	
	@Override
	public boolean esiste() {
		CandidatoDAO cDAO = new CandidatoDAOImpl();
		return cDAO.esiste(this);
	}
	
	@Override
	public int inserisciInSchedaElettorale(SchedaElettorale s) {
		CandidatoDAO cDAO = new CandidatoDAOImpl();
		return cDAO.inserisciInSchedaElettorale(s, this);
	}

	@Override
	public String toString() {
		return nome + " " + cognome;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Candidato)) return false;
		Candidato o = (Candidato) obj;
		return o.nome.equals(nome) && o.cognome.equals(cognome) && o.codiceFiscale.equals(codiceFiscale);
	}
	
	@Override
	public int hashCode() {
		int hash = codiceFiscale.hashCode();
		hash = hash*31 + nome.hashCode();
		hash = hash*31 + cognome.hashCode();
		return hash;
	}

	
}
