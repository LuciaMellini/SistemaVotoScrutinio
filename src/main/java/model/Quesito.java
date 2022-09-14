package model;

import com.mysql.cj.exceptions.StatementIsClosedException;

import database.QuesitoDAO;
import database.QuesitoDAOImpl;

public class Quesito implements Voce{
	int id;
	private String quesito;

	public Quesito(String quesito) {
		int id = -1;
		this.quesito = quesito;
	}
	
	public Quesito(int id, String quesito) {
		this.id = id;
		this.quesito = quesito;
	}
	
	public Quesito(int id) {
		QuesitoDAO qDAO = new QuesitoDAOImpl();
		Quesito q = qDAO.getQuesito(id);
		this.id = id;
		this.quesito = q.quesito;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public void crea() {
		QuesitoDAO qDAO = new QuesitoDAOImpl();
		if(!qDAO.esiste(this)) this.id = qDAO.crea(this);
		
		SistemaVotoScrutinio.getIstanza().log("Creazione quesito " + this.getId());
	}
	
	@Override
	public void elimina() {
		QuesitoDAO qDAO = new QuesitoDAOImpl();
		if(qDAO.esiste(this)) qDAO.elimina(this);
		
		SistemaVotoScrutinio.getIstanza().log("Eliminazione quesito " + this.getId());
	}
	
	@Override
	public boolean esiste() {
		QuesitoDAO qDAO = new QuesitoDAOImpl();
		return qDAO.esiste(this);
	}
	
	public int inserisciInSchedaElettorale(SchedaElettorale s) {
		QuesitoDAO qDAO = new QuesitoDAOImpl();
		return qDAO.inserisciInSchedaElettorale(s, this);
	}
	
	
	public String getQuesito() {
		return this.quesito;
	}

	@Override
	public String toString() {
		return quesito;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Quesito)) return false;
		Quesito o = (Quesito) obj;
		return o.quesito.equals(quesito);
	}
	
	@Override
	public int hashCode() {
		return quesito.hashCode();
	}
}
