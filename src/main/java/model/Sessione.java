package model;

import java.util.Set;

import database.SessioneDAO;
import database.SessioneDAOImpl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Sessione implements Iterable<SchedaElettorale>{
	
	private int id;
	private String descrizione;
	private Date inizio;
	private Date fine;
	private Set<SchedaElettorale> schedeElettorali;
	private String luogo;

	
	public Sessione(int id) {
		SessioneDAO cDAO = new SessioneDAOImpl();
		Sessione sessione = cDAO.getSessione(id);
		this.id = id;
		this.descrizione = sessione.descrizione;
		this.inizio = sessione.inizio;
		this.fine = sessione.fine;
		this.schedeElettorali = sessione.schedeElettorali;
		this.luogo = sessione.luogo;
	}
	
	public Sessione(int id, String descrizione, Date inizio, Date fine, Set<SchedaElettorale> schedeElettorali, String luogo) {
		this.id = id;
		this.descrizione = new String(descrizione);
		this.inizio = inizio;
		this.fine = fine;
		this.schedeElettorali = schedeElettorali;
		this.luogo = luogo;
	}
	
	public Sessione(String descrizione, Date inizio, Date fine, Set<SchedaElettorale> schedeElettorali, String luogo) {
		this.id = -1;
		this.descrizione = new String(descrizione);
		Calendar c = Calendar.getInstance(); 
		c.setTime(inizio); 
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		inizio = c.getTime();
		
		c.setTime(fine);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.DATE, 1);
		c.add(Calendar.MILLISECOND, -1);
		fine = c.getTime();
		this.inizio = inizio;
		this.fine = fine;
		this.schedeElettorali = schedeElettorali;
		this.luogo = luogo;
	}
	
	public String getDescrizione() {
		return new String(descrizione);
	}
	
	public Date getDataInizio() {
		return inizio;
	}
	
	public Date getDataFine() {
		return fine;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLuogo() {
		return luogo;
	}
	
	public Set<SchedaElettorale> getSchedeElettorali(){
		return schedeElettorali;
	}
	
	public void inserisciElettore(Elettore e) {
		SessioneDAO sDAO = new SessioneDAOImpl();
		sDAO.inserisciElettore(this, e);
	}
	
	public void crea() {
		SessioneDAO sDAO = new SessioneDAOImpl();
		if(!sDAO.esiste(this)) this.id = sDAO.crea(this);	
		for(SchedaElettorale s : this.schedeElettorali) {
			sDAO.inserisciSchedaElettorale(this, s);
		}
		
		for(Elettore e : SistemaVotoScrutinio.getIstanza().getElettori()) {
			if(e.getLuogoResidenza().equals(luogo)) this.inserisciElettore(e);
		}
		SistemaVotoScrutinio.getIstanza().log("Creazione sessione " + this.getId());
	}
	
	
	public void elimina() {
		SessioneDAO sDAO = new SessioneDAOImpl();
		if(sDAO.esiste(this)) sDAO.elimina(this);
		
		SistemaVotoScrutinio.getIstanza().log("Eliminazione sessione " + this.getId());
	}
	
	@Override
	public Iterator<SchedaElettorale> iterator(){
		return schedeElettorali.iterator();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(descrizione);
		sb.append("\nData inizio: " + inizio.toString());
		sb.append("\tData fine: " + fine.toString());
	    return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Sessione)) return false;
		Sessione o = (Sessione) obj;		
		return id == o.id && descrizione.equals(o.descrizione) && inizio.equals(o.inizio) && fine.equals(o.fine) && schedeElettorali.equals(o.schedeElettorali) && o.luogo.equals(luogo);
	}
	
	@Override
	public int hashCode() {
		int hash = id;
		hash = hash*31 + descrizione.hashCode();
		hash = hash*31 + inizio.hashCode();
		hash = hash*31 + fine.hashCode();
		hash = hash*31 + schedeElettorali.hashCode();
		hash = hash*31 + luogo.hashCode();
		return hash;
	}

}
