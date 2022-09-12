package model;
import java.util.Set;

import database.ElettoreDAO;
import database.ElettoreDAOImpl;
import database.UtenteDAO;
import database.UtenteDAOImpl;

public class Utente {
	protected String email;
	
	public Utente(String email){
		this.email=email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void crea(String password) {
		UtenteDAO uDAO = new UtenteDAOImpl();
		if(!uDAO.esiste(this)) uDAO.crea(this, password);
	}
	
	public boolean registrato() {
		UtenteDAO uDAO = new UtenteDAOImpl();
    	return uDAO.esiste(this);
	}
	
	public void elimina() {
		UtenteDAO uDAO = new UtenteDAOImpl();
    	uDAO.elimina(this);
	}
	
	private boolean passwordCorretta(String password) {
		UtenteDAO uDAO = new UtenteDAOImpl();
    	return uDAO.passwordCorretta(this, password);
	}
	
	public boolean accesso(String password) {
		SistemaVotoScrutinio.getIstanza().log("Accesso utente " + this.getEmail());
		return this.registrato() && this.passwordCorretta(password);
	}
	
	public void uscita() {
		SistemaVotoScrutinio.getIstanza().log("Uscita utente " + this.getEmail());
	}
	
	public boolean isCae() {
		UtenteDAO uDAO = new UtenteDAOImpl();
		return uDAO.isCAE(this);
	}
	
	@Override
	public String toString() {
		return "Email: " + email;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Utente)) return false;
		Utente u=(Utente) o;
		return this.email.equals(u.email);
	}
	
	@Override
	public int hashCode() {
		return email.hashCode();
	}
	
}
