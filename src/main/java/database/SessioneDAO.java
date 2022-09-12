package database;

import java.util.Set;

import model.Sessione;
import model.Elettore;
import model.SchedaElettorale;

public interface SessioneDAO {
	public void elimina(Sessione s);
	public boolean esiste(Sessione s);
	public int crea(Sessione c);
	public void inserisciSchedaElettorale(Sessione c, SchedaElettorale s);
	public Sessione getSessione(int id);
	public void inserisciElettore(Sessione s, Elettore e);
}
