package model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import controllers.AreaScrutinioController;
import database.CaeDAO;
import database.CaeDAOImpl;

public class Cae extends Utente{
    private Boolean scrutinatore;
    private Boolean configuratore;
	
	public Cae(String email, boolean scrutinatore, boolean configuratore) {
		super(email);
		this.scrutinatore = scrutinatore;
		this.configuratore = configuratore;
	}
	
	public Cae(String email) {
		super(email);
		CaeDAO cDAO = new CaeDAOImpl();
		Cae cae = cDAO.getCae(email);
		this.scrutinatore = cae.scrutinatore;
		this.configuratore = cae.configuratore;
	}
	
	public Cae(Utente u) {
		super(u.getEmail());
		CaeDAO cDAO = new CaeDAOImpl();
		Cae cae = cDAO.getCae(email);
		this.scrutinatore = cae.scrutinatore;
		this.configuratore = cae.configuratore;
	}
	
	public boolean esiste() {
		CaeDAO cDAO = new CaeDAOImpl();
		return cDAO.esiste(this);
	}
	
	public boolean isConfiguratore() {
		return configuratore;
	}
	
	public boolean isScrutinatore() {
		return scrutinatore;
	}
	
	private boolean passwordCorretta(String password) {
		CaeDAO cDAO = new CaeDAOImpl();
    	return cDAO.passwordCorretta(this, password);
	}
	
	public boolean accesso(String password) {
		SistemaVotoScrutinio.getIstanza().log("Accesso CAE " + this.email);
		return passwordCorretta(password);
	}
	
	public void uscita() {
		SistemaVotoScrutinio.getIstanza().log("Uscita CAE " + this.email);
	}
	
	public boolean scrutinio(SchedaElettorale s) {
		if(this.scrutinatore) {
			boolean result = s.scrutinio();
			if(result) comunicaRisultato(s);
			return result;
		}
		
		return false;
	}
	
	public String comunicaRisultato(SchedaElettorale s) {
		return s.toStringRisultato();
	}
	
	public void creaSchedaElettorale(SchedaElettorale s) {
		if(this.configuratore) {
			s.crea();
			CaeDAO cDAO = new CaeDAOImpl();
			cDAO.inserisciSchedaElettorale(this, s);	
		}
				
	}
	
	@Override
	public boolean isCae() {
		return true;
	}
	
	public Set<SchedaElettorale> getSchedeElettorali(){
		CaeDAO cDAO = new CaeDAOImpl();
		return cDAO.getSchedeElettorali(this);
	}
	
	private boolean configuratorePerScheda(SchedaElettorale s) {
		if(!configuratore) return false;
		if(!getSchedeElettorali().contains(s)) return false;
		return true;
	}
	
	public Set<SchedaElettorale> getSchedeElettoraliConElettori(){
		Set<SchedaElettorale> schedeConElettori = new HashSet<>();
		CaeDAO cDAO = new CaeDAOImpl();
		for(SchedaElettorale s : cDAO.getSchedeElettorali(this)) {
			if(s.numeroElettoriTotali()>0) {
				schedeConElettori.add(s);
			}
		}
		return schedeConElettori;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		if(scrutinatore) {
			sb.append(" scrutinatore ");
		}
		if(configuratore) {
			sb.append(" configuratore");
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Cae)) return false;
		Cae o = (Cae) obj;
		return super.equals(obj) && o.scrutinatore == this.scrutinatore && o.configuratore == this.configuratore;
	}
	
	@Override
	public int hashCode() {
		int hash;
		hash = super.hashCode();
		hash = hash*31 + scrutinatore.hashCode();
		hash = hash*3 + configuratore.hashCode();
		return  hash;
	}
}
