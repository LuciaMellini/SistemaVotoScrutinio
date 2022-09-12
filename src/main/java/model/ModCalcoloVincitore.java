package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;

public enum ModCalcoloVincitore {

	MAGGIORANZA{
		@Override
		public String toString() {
			return "Maggioranza";
		}
		
		@Override
		public String literalString() {
    		return "MAGGIORANZA";
    	}

		@Override
		public Voce calcoloVincitore(Risultato r) {
			List<Entry<Voce, Integer>> list = new ArrayList<Entry<Voce, Integer>>(r.getRisultato().entrySet());
			list.sort(Entry.comparingByValue());
			int listsize = list.size();
			if(listsize>1 && list.get(listsize-2).getValue() == list.get(listsize-1).getValue()) return null; 
			return list.get(list.size()-1).getKey();
		}

	},
	MAGGIORANZAASS{
		@Override
		public String toString() {
			return "Maggioranza assoluta";
		}
		
		@Override
		public String literalString() {
    		return "MAGGIORANZAASS";
    	}

		@Override
		public Voce calcoloVincitore(Risultato r) {
			Voce vincitore = MAGGIORANZA.calcoloVincitore(r);
			if(!Objects.isNull(vincitore) && r.getRisultato().get(vincitore) >= r.getNumeroVotanti()/2 + 1)
				return vincitore;
			
			return null;
		}
	};
	
	@Override
	abstract public String toString();
	
	abstract public String literalString();
	
	abstract public Voce calcoloVincitore(Risultato r);
}
