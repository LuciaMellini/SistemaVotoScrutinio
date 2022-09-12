package database;

import java.util.List;
import java.util.Set;

import model.Candidato;
import model.Partito;
import model.SchedaElettorale;

public interface PartitoDAO {
	public void elimina(Partito p);
	public boolean esiste(Partito p);
	public void crea(Partito p);
	public Set<Partito> getPartiti();
	public int inserisciInSchedaElettorale(SchedaElettorale s,Partito p);
	public Partito getPartito(String nome);
	public Candidato getCapoPartito(Partito p);
}
