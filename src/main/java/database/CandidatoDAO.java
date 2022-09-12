package database;

import model.Candidato;
import model.SchedaElettorale;

public interface CandidatoDAO{
	public void elimina(Candidato c);
	public boolean esiste(Candidato c);
	public void crea(Candidato c);
	public Candidato getCandidato(String codiceFiscale);
	public int inserisciInSchedaElettorale(SchedaElettorale s, Candidato c);
}
