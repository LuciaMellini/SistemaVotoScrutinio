package database;

import java.util.Set;

import model.Candidato;
import model.Elettore;
import model.Sessione;
import model.Partito;
import model.Quesito;
import model.SchedaElettorale;

public interface SistemaVotoScrutinioDAO {
	public Set<Elettore> getElettori();
	public Set<Sessione> getSessioni();
	public Set<SchedaElettorale> getSchedeElettorali();
	public Set<Partito> getPartiti();
	public Set<Candidato> getCandidati();
	public Set<Candidato> getCandidatiSenzaPartito();
	public Set<Quesito> getQuesiti();
	public int getNumeroElettori();
}
