package griglia;

import java.util.StringTokenizer;

public class VincoliOperazioni {
	private Operatore op;
	private int valore;
	private char gruppo;
	
	public VincoliOperazioni(Operatore op, int valore, char gruppo) {
		this.op=op;
		this.valore=valore;
		this.gruppo=gruppo;
	}
	
	public VincoliOperazioni(String operatore, int valore, char gruppo) {
		switch (operatore) {
		case "addizione": op=Operatore.addizione; break;
		case "sottrazione": op=Operatore.sottrazione; break;
		case "moltiplicazione": op=Operatore.moltiplicazione; break;
		case "divisione": op=Operatore.divisione;
		case "vuoto": op=Operatore.vuoto; break;
		default:
			throw new IllegalArgumentException("Operatore errato");
		}
		this.valore=valore;
		this.gruppo=gruppo;
	}
	
	public VincoliOperazioni(char operatore, int valore, char gruppo) {
		switch(operatore) {
		case '+': op=Operatore.addizione; break;
		case '-': op=Operatore.sottrazione; break;
		case '/': op=Operatore.divisione; break;
		case '*': op=Operatore.moltiplicazione; break;
		case ' ': op=Operatore.vuoto; break;
		default:
			throw new IllegalArgumentException("Operatore errato");
		}
		this.valore=valore;
		this.gruppo=gruppo;
	}
	
	public VincoliOperazioni(String linea) {
		StringTokenizer st=new StringTokenizer(linea," ");
		char gruppo=st.nextToken().charAt(0);
		int valore=Integer.parseInt(st.nextToken());
		String operatore=st.nextToken();
		
		switch (operatore) {
		case "addizione": op=Operatore.addizione; break;
		case "sottrazione": op=Operatore.sottrazione; break;
		case "moltiplicazione": op=Operatore.moltiplicazione; break;
		case "divisione": op=Operatore.divisione;
		case "vuoto": op=Operatore.vuoto; break;
		default:
			throw new IllegalArgumentException("Operatore errato");
		}
		this.valore=valore;
		this.gruppo=gruppo;
	}
	
	
	public int getValore() {
		return valore;
	}
	
	
	public char getGruppo() {
		return gruppo;
	}
	
	public String getOperatore() {
		String operatore;
		switch (op) {
		case addizione: operatore="addizione"; break;
		case sottrazione: operatore="sottrazione"; break;
		case moltiplicazione: operatore="moltiplicazione"; break;
		case divisione: operatore="divisione"; break;
		default: operatore="vuoto";break;
	}
		return operatore;
	}
	
	public String getSimboloOperatore() {
		String operatore;
		switch (op) {
		case addizione: operatore="+"; break;
		case sottrazione: operatore="-"; break;
		case moltiplicazione: operatore="*"; break;
		case divisione: operatore="/"; break;
		default: operatore="";break;
	}
		return operatore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gruppo;
		result = prime * result + ((op == null) ? 0 : op.hashCode());
		result = prime * result + valore;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof VincoliOperazioni)) {
			return false;
		}
		VincoliOperazioni other = (VincoliOperazioni) obj;
		if (gruppo != other.gruppo) {
			return false;
		}
		if (op != other.op) {
			return false;
		}
		if (valore != other.valore) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Vincolo: [" +gruppo+" "+valore+" "+op + "]";
	}
	
	
	
}
