package database;

import java.util.List;
import model.Utente;

public interface UtenteDAO {
	public void crea(Utente u, String password);
	public List<Utente> getUtenti();
	public void aggiornaPassword(Utente u, String password);
	public boolean esiste(Utente u);
	public void elimina(Utente u);
	public boolean passwordCorretta(Utente u, String password);
	public boolean isCAE(Utente u);
}
