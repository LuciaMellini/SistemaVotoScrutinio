package database;

import java.util.List;
import java.util.Set;

import model.Cae;
import model.SchedaElettorale;

public interface CaeDAO {
	
	public void crea(Cae u, String password);
	public List<Cae> getCae();
	public Cae getCae(String email);
	public void aggiornaPassword(Cae c, String password);
	public boolean esiste(Cae c);
	public void elimina(Cae c);
	public boolean passwordCorretta(Cae c, String password);
	public boolean isScrutinatore(Cae c);
	public boolean isConfiguratore(Cae c);
	public Set<SchedaElettorale> getSchedeElettorali(Cae c);
	public void inserisciSchedaElettorale(Cae c, SchedaElettorale s);
}
