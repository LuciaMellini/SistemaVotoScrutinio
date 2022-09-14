package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import database.SchedaElettoraleDAO;
import database.SchedaElettoraleDAOImpl;
import database.SistemaVotoScrutinioDAOImpl;

public class SchedaElettorale{
	private int id;
	private String descrizione;
	private int limiteEta;
	private InformazioneScheda informazione;
	private ModVoto modVoto;
	private ModCalcoloVincitore modCalcoloVincitore;
	private int quorum;
	private Risultato risultato;
	
	public SchedaElettorale(String descrizione, InformazioneScheda informazione, int limiteEta, ModVoto modVoto, ModCalcoloVincitore modCalcoloVincitore, int quorum) {
		this.id = -1;
		this.descrizione = descrizione;
		this.informazione = informazione;
		this.limiteEta = limiteEta;
		this.modVoto = modVoto;
		this.modCalcoloVincitore = modCalcoloVincitore;
		this.quorum = quorum;
	}
	
	public SchedaElettorale(int id, String descrizione, InformazioneScheda informazione, int limiteEta, ModVoto modVoto, ModCalcoloVincitore modCalcoloVincitore, int quorum) {
		this.id = id;
		this.descrizione = descrizione;
		this.informazione = informazione;
		this.limiteEta = limiteEta;
		this.modVoto = modVoto;
		this.modCalcoloVincitore = modCalcoloVincitore;
		this.quorum = quorum;
	}
	
	public SchedaElettorale(int id) {
		SchedaElettoraleDAO sDAO = new SchedaElettoraleDAOImpl();
		SchedaElettorale schedaElettorale = sDAO.getSchedaElettorale(id);
		this.id = id;
		this.descrizione = schedaElettorale.descrizione;
		this.limiteEta = schedaElettorale.limiteEta;
		this.informazione = schedaElettorale.informazione;
		this.modVoto = schedaElettorale.modVoto;
		this.modCalcoloVincitore = schedaElettorale.modCalcoloVincitore;
		this.quorum = schedaElettorale.quorum;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDescrizione() {
		return new String(descrizione);
	}
	
	public int getLimiteEta() {
		return limiteEta;
	}
	
	public ModVoto getModVoto() {
		return modVoto;
	}
	
	public InformazioneScheda getInformazione() {
		return new InformazioneScheda(informazione);
	}
	
	public ModCalcoloVincitore getModCalcoloVincitore() {
		return modCalcoloVincitore;
	}
	
	public Risultato getRisultato() {
		if(!Objects.isNull(risultato)) return new Risultato(risultato);
		else return null;
	}
	
	public int getQuorum(){
		return quorum*this.numeroElettoriTotali()/100;
	}
	
	public boolean esiste() {
		SchedaElettoraleDAO sDAO = new SchedaElettoraleDAOImpl();
		return sDAO.esiste(this);
	}
	
	public void crea() {
		SchedaElettoraleDAO sDAO = new SchedaElettoraleDAOImpl();
		if(!sDAO.esiste(this)) this.id = sDAO.crea(this);
		informazione.inserisciInSchedaElettorale(this);
		SistemaVotoScrutinio.getIstanza().log("Creazione scheda elettorale " + this.getId());
	}
	
	public void elimina() {
		SchedaElettoraleDAO sDAO = new SchedaElettoraleDAOImpl();
		if(sDAO.esiste(this)) sDAO.elimina(this);		
		SistemaVotoScrutinio.getIstanza().log("Eliminazione scheda elettorale " + this.getId());
	}
	
	public void esprimiPreferenza(Preferenza p) {
		SchedaElettoraleDAO sDAO = new SchedaElettoraleDAOImpl();
		for(Entry<Voce,Integer> e: p) {
			sDAO.esprimiPreferenza(informazione.getId(e.getKey()), e.getValue());
		}
	}
	
	public List<Entry<Voce, Integer>> getPreferenze() {
		SchedaElettoraleDAO sDAO = new SchedaElettoraleDAOImpl();
		List<Entry<Voce, Integer>> list  = sDAO.getPreferenze(this);	
		Collections.shuffle(list);
	return list;
	}
	
	public Set<Sessione> getSessioni(){
		SchedaElettoraleDAO sDAO = new SchedaElettoraleDAOImpl();
		return sDAO.getSessioni(this);
	}
	
	public int numeroElettoriTotali() {
		SchedaElettoraleDAO sDAO = new SchedaElettoraleDAOImpl();
		return sDAO.getNumeroElettoriTotali(this);
	}
	
	public int numeroElettoriEffettivi() {
		SchedaElettoraleDAO sDAO = new SchedaElettoraleDAOImpl();
		return sDAO.getNumeroElettoriEffettivi(this);
	}
	
	public boolean scrutinio() {
		risultato = null;
		Date date = new Date();
		for (Sessione c : this.getSessioni()) {
			if(c.getDataFine().after(date)) return false;
		}
		if(Objects.isNull(risultato)) {
			risultato = new Risultato(modCalcoloVincitore, this.numeroElettoriEffettivi(), this.getInformazione().getVoci());
		    SistemaVotoScrutinio.getIstanza().log("Scrutinio scheda " + this.getId());
		
		    for(Entry<Voce, Integer> e: this.getPreferenze()) {
		        risultato.add(e.getKey(), e.getValue());
		    }
		
		    if(risultato.getNumeroVotanti() < this.getQuorum()) {	
			    return false;
		    }
		    risultato.calcolaVincitore();
		}
		
		return true;
	}
	
	public String toStringRisultato() {
		return modVoto.toStringRisultato(this.risultato);
	}
	
	@Override
	public String toString() {
		return descrizione.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SchedaElettorale)) return false;
		SchedaElettorale o = (SchedaElettorale) obj;
		return o.id == id && o.limiteEta == limiteEta && o.informazione.equals(informazione) && o.modVoto.equals(modVoto) && o.modCalcoloVincitore.equals(modCalcoloVincitore) && o.descrizione.equals(descrizione) && o.quorum == quorum;
	}
	
	@Override
	public int hashCode() {
        int hash=id;
        hash = hash*31 + limiteEta;
        hash = hash*31 + descrizione.hashCode();
        hash = hash*31 + modVoto.hashCode();
        hash = hash*31 + modCalcoloVincitore.hashCode();
        hash = hash*31 + descrizione.hashCode();
        hash = hash*31 + quorum;
        return hash;
	}
}
