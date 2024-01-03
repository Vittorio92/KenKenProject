package statiFinestra;

public abstract class GestoreStato {

	protected Stato corrente;
	
	protected GestoreStato() {	}
	
	public void transition(Stato state) {
		if(corrente != null) {
			corrente.exitAction();
		}
		corrente = state;
		state.entryAction();
	}
	
}
