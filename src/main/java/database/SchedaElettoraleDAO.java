package database;

import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import model.Sessione;
import model.InformazioneScheda;
import model.Preferenza;
import model.SchedaElettorale;
import model.Voce;

public interface SchedaElettoraleDAO {
	public void elimina(SchedaElettorale s);
	public boolean esiste(SchedaElettorale s);
	public int crea(SchedaElettorale s);
	public void esprimiPreferenza(int idVoceInSchedaElettorale, int preferenza);
	public SchedaElettorale getSchedaElettorale(int id);
	public Set<Sessione> getSessioni(SchedaElettorale s);
	public int getNumeroElettoriTotali(SchedaElettorale s);
	public int getNumeroElettoriEffettivi(SchedaElettorale s);
	public List<Entry<Voce, Integer>> getPreferenze(SchedaElettorale s);
}
