package model;

import database.ElettoreDAO;
import database.ElettoreDAOImpl;
import database.UtenteDAO;
import database.UtenteDAOImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Elettore extends Utente{

	private String codiceFiscale;
	private String luogoResidenza;
	
	public Elettore(String email) {
		super(email);
		ElettoreDAO eDAO = new ElettoreDAOImpl();
		Elettore elettore = eDAO.getElettore(email);
		this.codiceFiscale = elettore.codiceFiscale;
		this.luogoResidenza = elettore.luogoResidenza;
	}
	
	public Elettore(String email, String codiceFiscale, String luogoResidenza) {
		super(email);
		this.codiceFiscale = codiceFiscale;
		this.luogoResidenza = luogoResidenza;
	}
	
	public Elettore(Utente u) {
		super(u.getEmail());
		ElettoreDAO eDAO = new ElettoreDAOImpl();
		Elettore elettore = eDAO.getElettore(email);
		this.codiceFiscale = elettore.codiceFiscale;
		this.luogoResidenza = elettore.luogoResidenza;
	}
	
	public Elettore(Utente u, String codiceFiscale, String luogoResidenza) {
		super(u.getEmail());
		this.codiceFiscale = codiceFiscale;
		this.luogoResidenza = luogoResidenza;
	}
	
	@Override
	public void crea(String password) {
		super.crea(password);
		ElettoreDAO eDAO = new ElettoreDAOImpl();
		if(!eDAO.esiste(this)) eDAO.crea(this);
	}
	
	public boolean registrato() {
		ElettoreDAO eDAO = new ElettoreDAOImpl();
    	return eDAO.esiste(this);
	}
	
	public static boolean registrato(String email) {
		ElettoreDAO eDAO = new ElettoreDAOImpl();
    	return !Objects.isNull(eDAO.getElettore(email));
	}
	
	public void esprimiPreferenza(SchedaElettorale s, Preferenza p) {
		s.esprimiPreferenza(p);
		ElettoreDAO eDAO = new ElettoreDAOImpl();
		eDAO.segnaVoceComeNonEsprimibile(this, s);
		SistemaVotoScrutinio.getIstanza().log("Elettore " + this.codiceFiscale + "esprime la preferenza per la scheda elettorale " + s.getId());
		comunicaPreferenzaEspressa();
	}
	
	private void comunicaPreferenzaEspressa() {
		
	}
	
	public boolean preferenzaEspressa(SchedaElettorale s) {
		ElettoreDAO eDAO = new ElettoreDAOImpl();
		return eDAO.preferenzaEspressa(this, s);
	}
	
	public Set<Sessione> getSessioniInCorso(){
		ElettoreDAO eDAO = new ElettoreDAOImpl();
		Date date = new Date();
		Set<Sessione> sessioni = eDAO.getSessioni(this);
		sessioni.removeIf(c -> (c.getDataInizio().after(date) || c.getDataFine().before(date)));
		return sessioni;
	}
	
	public Set<SchedaElettorale> getSchedeElettorali(){
		Set<SchedaElettorale> schedeElettorali = new HashSet<SchedaElettorale>();
    	for(Sessione c:this.getSessioniInCorso()) {
    		for(SchedaElettorale s : c.getSchedeElettorali()) {
    			if(this.autorizzato(s.getLimiteEta())) {
    				schedeElettorali.add(s);
    			}
    		}
    	} 
    	return schedeElettorali;
	}
	
	public Set<SchedaElettorale> getSchedeElettoraliSenzaPreferenza(){
		Set<SchedaElettorale> schedeElettorali = new HashSet<SchedaElettorale>();
		ElettoreDAO eDAO = new ElettoreDAOImpl();
    	for(Sessione c:this.getSessioniInCorso()) {
    		for(SchedaElettorale s : c.getSchedeElettorali()) {
    			if(this.autorizzato(s.getLimiteEta()) && !eDAO.preferenzaEspressa(this, s)) {
    				schedeElettorali.add(s);
    			}
    		}
    	} 
    	return schedeElettorali;
	}	
	
	@SuppressWarnings("deprecation")
	public boolean autorizzato(int etaMin) {
		Date oggi=new Date();
		String cf = this.getCodiceFiscale();
		Date nascita = dataNascitaDaCodiceFiscale(cf);
		return (new Date(nascita.getYear()+etaMin, nascita.getMonth(), nascita.getDay())).before(oggi);
	}
	
	@SuppressWarnings("deprecation")
	private static Date dataNascitaDaCodiceFiscale(String cf){
		char[] cfarray = cf.toCharArray();
		
		int y=(cfarray[6]-'0')*10+(cfarray[7]-'0');
		
		char[] codiciMesi= {'A','B','C','D','E','H','L','M','P','R','S','T'};
		
		int m = Arrays.asList(codiciMesi).indexOf(cfarray[8])+1;
		int d = (cfarray[9]-'0')*10+(cfarray[10]-'0');
		if (d>40) {
			d=d-40;
		}

		Date oggi=new Date();
		if (y+100 < oggi.getYear()) {
			y+=100;
		}
		return new Date(y,m,d);
	}
		
	public String getCodiceFiscale() {
		return new String(codiceFiscale);
	}
	
	public String getLuogoResidenza() {
		return new String(luogoResidenza);
	}
	
	@Override
	public boolean isCae() {
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Codice fiscale:" + codiceFiscale;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Elettore)) return false;
		Elettore o = (Elettore) obj;
		return super.equals(o) && o.codiceFiscale.equals(codiceFiscale) && o.luogoResidenza.equals(luogoResidenza);
	}
	
	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = hash*31 + codiceFiscale.hashCode();
		hash = hash*31 + luogoResidenza.hashCode();
		return hash;
	}
}
