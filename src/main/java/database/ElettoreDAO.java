package database;

import java.util.List;
import java.util.Set;

import model.Sessione;
import model.Elettore;
import model.SchedaElettorale;
import model.Voce;

public interface ElettoreDAO {
	public void crea(Elettore e);
	public void elimina(Elettore e);
	public List<Elettore> getElettori();
	public boolean esiste(Elettore e);
	public String codiceFiscale(Elettore e);
	public Set<Sessione> getSessioni(Elettore e);
	public void segnaVoceComeNonEsprimibile(Elettore e, SchedaElettorale s);
	public boolean preferenzaEspressa(Elettore e, SchedaElettorale s);
	public Elettore getElettore(String email);
}
