package database;

import model.Quesito;
import model.SchedaElettorale;

public interface QuesitoDAO{
	public void elimina(Quesito q);
	public boolean esiste(Quesito q);
	public int crea(Quesito q);
	public int inserisciInSchedaElettorale(SchedaElettorale s, Quesito q);
	public Quesito getQuesito(int id);
}
