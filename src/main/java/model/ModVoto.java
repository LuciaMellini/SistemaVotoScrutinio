package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Map.Entry;

import controllers.AreaVotoController;
import controllers.CreaSchedaElettoraleController;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public enum ModVoto {
    ORDINALE{
    	@Override
    	public List<ModCalcoloVincitore> possibiliModCalcoloVincitore() {
    		List<ModCalcoloVincitore> listModCalcoloVincitore = new ArrayList<ModCalcoloVincitore>();
    		listModCalcoloVincitore.add(ModCalcoloVincitore.MAGGIORANZA);
    		listModCalcoloVincitore.add(ModCalcoloVincitore.MAGGIORANZAASS);
    		return listModCalcoloVincitore;
    	}
    	
    	@Override
    	public String toString() {
    		return "Ordinale";
    	}
    	
    	@Override
    	public String literalString() {
    		return "ORDINALE";
    	}

		@Override
		public String toStringRisultato(Risultato r) {
			StringBuilder sb = new StringBuilder("Numero votanti: " + r.getNumeroVotanti() + "\n");
			List<Entry<Voce, Integer>> list = new ArrayList<Entry<Voce, Integer>>(r.getRisultato().entrySet());
			list.sort(Entry.comparingByValue());

			Collections.reverse(list);
			for(Entry<Voce, Integer> e : list) {
				sb.append(e.getKey().toString());
			}
			Voce vincitore = r.getVincitore();
			if(!Objects.isNull(vincitore)) {
				sb.append("\nVincitore: " + vincitore.toString());
			}else {
				sb.append("\nNon è stato possibile calcolare il vincitore; consultare il risulato complessivo");
			}
			return sb.toString();
		}

		@Override
		public void handleViewVoto(Label lblTitle, GridPane paneOpzioni, SchedaElettorale schedaElettorale,
				Preferenza preferenza) {
    		AreaVotoController.handleVotoOrdinale(lblTitle, paneOpzioni, schedaElettorale, preferenza);
		}

		@Override
		public String toStringPreferenza(Preferenza preferenza) {
			StringBuilder sb = new StringBuilder();
			List<Entry<Voce, Integer>> list = new ArrayList<Entry<Voce, Integer>>(preferenza.getPreferenze());
			int n = list.size();
			list.sort(Entry.comparingByValue());
			Collections.reverse(list);
			for(Entry e : list) sb.append((n - ((Integer) e.getValue())) + " " + e.getKey().toString());
			return sb.toString();
		}

		@Override
		public void handleViewCreazione(Label lblInfo, GridPane paneOpzioni, InformazioneScheda informazioneScheda, Cae cae) {
			CreaSchedaElettoraleController.handleSceltaCandidatiPartiti(lblInfo, paneOpzioni, informazioneScheda, cae);
			
		}
		
		
    },
    CATEGORICO{
    	@Override
    	public List<ModCalcoloVincitore> possibiliModCalcoloVincitore() {
    		List<ModCalcoloVincitore> listModCalcoloVincitore = new ArrayList<ModCalcoloVincitore>();
    		listModCalcoloVincitore.add(ModCalcoloVincitore.MAGGIORANZA);
    		listModCalcoloVincitore.add(ModCalcoloVincitore.MAGGIORANZAASS);
    		return listModCalcoloVincitore;
    	}
    	
    	@Override
    	public String toString() {
    		return "Categorico";
    	}
    	
    	@Override
    	public String literalString() {
    		return "CATEGORICO";
    	}

		@Override
		public String toStringRisultato(Risultato r) {
			StringBuilder sb = new StringBuilder("Numero votanti: " + r.getNumeroVotanti() + "\n");
			List<Entry<Voce, Integer>> list = new ArrayList<Entry<Voce, Integer>>(r.getRisultato().entrySet());
			list.sort(Entry.comparingByValue());
			
			for(Entry<Voce, Integer> e : list) {
				sb.append(e.getKey().toString() + ": " + e.getValue() + "\n");
			}
			Voce vincitore = r.getVincitore();
			if(!Objects.isNull(vincitore)) {
				sb.append("\nVincitore: " + vincitore.toString());
			}else {
				sb.append("\nNon è stato possibile calcolare il vincitore; consultare il risulato complessivo");
			}
			return sb.toString();
		}
		
		@Override
		public void handleViewVoto(Label lblTitle, GridPane paneOpzioni, SchedaElettorale schedaElettorale,
				Preferenza preferenza) {

    		AreaVotoController.handleVotoCategorico(lblTitle, paneOpzioni, schedaElettorale, preferenza);
		}

		@Override
		public String toStringPreferenza(Preferenza preferenza) {
			StringBuilder sb = new StringBuilder();
			for(Entry e : preferenza)sb.append(e.getKey().toString());
			return sb.toString();
		}

		@Override
		public void handleViewCreazione(Label lblInfo, GridPane paneOpzioni, InformazioneScheda informazioneScheda, Cae cae) {
			CreaSchedaElettoraleController.handleSceltaCandidatiPartiti(lblInfo, paneOpzioni, informazioneScheda, cae);
		}
    },
    CATEGORICOCONPREF{
    	@Override
    	public List<ModCalcoloVincitore> possibiliModCalcoloVincitore() {
    		List<ModCalcoloVincitore> listModCalcoloVincitore = new ArrayList<ModCalcoloVincitore>();
    		listModCalcoloVincitore.add(ModCalcoloVincitore.MAGGIORANZA);
    		listModCalcoloVincitore.add(ModCalcoloVincitore.MAGGIORANZAASS);
    		return listModCalcoloVincitore;
    	}
    	
    	@Override
    	public String toString() {
    		return "Categorico con preferenze";
    	}
    	
    	@Override
    	public String literalString() {
    		return "CATEGORICOCONPREF";
    	}

		@Override
		public String toStringRisultato(Risultato r) {
			StringBuilder sb = new StringBuilder("Numero votanti: " + r.getNumeroVotanti() + "\n");
			List<Entry<Voce, Integer>> partiti = new ArrayList<>();
			for(Entry<Voce, Integer> e: r) {
				if(e.getKey() instanceof Partito) {
					partiti.add(e);
				}
			}
			partiti.sort(Entry.comparingByValue());
			
			for(Entry<Voce, Integer> e : partiti) {
				sb.append(e.getKey().toString() + ": " + e.getValue() + "\n");
				for(Candidato c : (Partito) e.getKey()) {
					sb.append("\t" + c.toString() + ": " + r.getRisultato().get(c) + "\n");
				}
			}			
			Voce vincitore = r.getVincitore();
			if(!Objects.isNull(vincitore)) {
				sb.append("\nVincitore: " + vincitore.toString());
			}else {
				sb.append("\nNon è stato possibile calcolare il vincitore; consultare il risulato complessivo");
			}
			return sb.toString();
		}
		
		@Override
		public void handleViewVoto(Label lblTitle, GridPane paneOpzioni, SchedaElettorale schedaElettorale,
				Preferenza preferenza) {

    		new AreaVotoController().handleVotoCategoricoConPref(lblTitle, paneOpzioni, schedaElettorale, preferenza);
		}

		@Override
		public String toStringPreferenza(Preferenza preferenza) {
			StringBuilder sb = new StringBuilder();
			for(Entry e : preferenza) {
				if(e.getKey() instanceof Partito) sb.append(e.getKey().toString() + "\n");
			}
			for(Entry e : preferenza) {
				if(e.getKey() instanceof Candidato) sb.append("Candidato: " + e.getKey().toString() + "\n");
			}
			return sb.toString();
		}

		@Override
		public void handleViewCreazione(Label lblInfo, GridPane paneOpzioni, InformazioneScheda informazioneScheda, Cae cae) {
			CreaSchedaElettoraleController.handleInformazioneSchedaPartiti(lblInfo, paneOpzioni, informazioneScheda, cae, true);		
		}
    },
    REFERENDUM{
    	@Override
    	public List<ModCalcoloVincitore> possibiliModCalcoloVincitore() {
    		List<ModCalcoloVincitore> listModCalcoloVincitore = new ArrayList<ModCalcoloVincitore>();
    		listModCalcoloVincitore.add(ModCalcoloVincitore.MAGGIORANZA);
    		return listModCalcoloVincitore;
    	}
    	
    	@Override
    	public String toString() {
    		return "Referendum";
    	}
    	
    	@Override
    	public String literalString() {
    		return "REFERENDUM";
    	}

		@Override
		public String toStringRisultato(Risultato r) {
			StringBuilder sb = new StringBuilder("Numero votanti: " + r.getNumeroVotanti() + "\n");
			Entry<Voce, Integer> e = (Entry<Voce, Integer>) r.iterator().next();
			sb.append(e.getKey() + "\n");
			sb.append("\tSì: " + e.getValue() + "\n");
			sb.append("\tNo" + (r.getNumeroVotanti()-e.getValue()));
			
			return sb.toString();
		}
		
		@Override
		public void handleViewVoto(Label lblTitle, GridPane paneOpzioni, SchedaElettorale schedaElettorale,
				Preferenza preferenza) {

    		AreaVotoController.handleReferendum(lblTitle, paneOpzioni, schedaElettorale, preferenza);
		}

		@Override
		public String toStringPreferenza(Preferenza preferenza) {
			StringBuilder sb = new StringBuilder();
			for(Entry e : preferenza) {
				sb.append(e.getKey().toString() + "\n\n");
				switch ((Integer) e.getValue()) {
				case 1:
					sb.append("Sì");
					break;
				case 0:
					sb.append("No");
					break;
				}
			}
			return sb.toString();
		}

		@Override
		public void handleViewCreazione(Label lblInfo, GridPane paneOpzioni, InformazioneScheda informazioneScheda, Cae cae) {
			CreaSchedaElettoraleController.handleInformazioneSchedaReferendum(lblInfo, paneOpzioni, informazioneScheda, cae);
			
		}
    };
	
	abstract public List<ModCalcoloVincitore> possibiliModCalcoloVincitore();
	
	@Override
	abstract public String toString();
	
	abstract public String literalString();
	
	abstract public String toStringRisultato(Risultato r);
	
	abstract public String toStringPreferenza(Preferenza p);
	
	abstract public void handleViewVoto(Label lblTitle, GridPane paneOpzioni, SchedaElettorale schedaElettorale, Preferenza preferenza);
	
	abstract public void handleViewCreazione(Label lblInfo, GridPane paneOpzioni, InformazioneScheda informazioneScheda, Cae cae);
}
