package risolutore;

import java.util.ArrayList;

import griglia.Blocco;
import griglia.Cella;
import griglia.Griglia;
import griglia.VincoliOperazioni;

public class ConcreteRisolutore2 implements TemplateMethod2<Integer, Integer>, Risolutore {
	private int  dim;
	private int numCaselle;
	private int[][] gridAusiliare;
	private Cella[] caselleVuote;
	private int numCaselleVuote;
	private ArrayList<VincoliOperazioni> vincoli;
	private char[][]blocchi;
	
	StrategySoluzioni scriviSol;
	
	public ConcreteRisolutore2(Griglia g) {	
		this.dim=g.getDimensione();
		this.numCaselle=g.getNumCaselle();
		this.numCaselleVuote=numCaselle;
		this.vincoli=g.getBlocchi().getVincoliGriglia();
		this.blocchi=g.getBlocchi().getBlocchi();
		
		gridAusiliare=new int[dim][dim];
		caselleVuote=new Cella[numCaselleVuote];
		int k=0;
		for(int i=0;i<dim;++i) {
			for(int j=0;j<dim;++j) {
				gridAusiliare[i][j]=0;
				caselleVuote[k++]=new Cella(i, j, 0);
				}
			}
	}
	
	
	public void setScriviSoluzione(StrategySoluzioni strategy) {
		this.scriviSol=strategy;
	}
	
	
	@Override
	public Integer primoPuntoDiScelta() {
		return 0;
	}

	@Override
	public Integer prossimoPuntoDiScelta(Integer ps) {
		return ps+1;
	}

	@Override
	public Integer ultimoPuntoDiScelta() {
		return caselleVuote.length-1;
	}

	@Override
	public Integer primaScelta(Integer ps) {
		return 1;
	}

	@Override
	public Integer prossimaScelta(Integer s) {
		return s+1;
	}

	@Override
	public Integer ultimaScelta(Integer ps) {
		return dim;
	}

	@Override
	public boolean assegnabile(Integer scelta, Integer puntoDiScelta) {
		final int riga = caselleVuote[puntoDiScelta].getRiga();
		final int colonna = caselleVuote[puntoDiScelta].getColonna();

		//controlla che nella stessa riga e nella stessa colonna se ci siano numeri uguali
		for(int i=0; i < dim; ++i) {
			if(i != colonna && gridAusiliare[riga][i] == scelta) {
				return false;
			}
			if(i != riga && gridAusiliare[i][colonna] == scelta) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void assegna(Integer scelta, Integer puntoDiScelta) {
		int riga=caselleVuote[puntoDiScelta].getRiga();
		int colonna=caselleVuote[puntoDiScelta].getColonna();
		
		gridAusiliare[riga][colonna]=scelta;
		caselleVuote[puntoDiScelta].setValore(scelta);
		
	}

	@Override
	public void deassegna(Integer scelta, Integer puntoDiScelta) {
		int riga=caselleVuote[puntoDiScelta].getRiga();
		int colonna=caselleVuote[puntoDiScelta].getColonna();
		
		gridAusiliare[riga][colonna]=0;
		caselleVuote[puntoDiScelta].setValore(0);
		
	}

	@Override
	public Integer precedentePuntoDiScelta(Integer puntoDiScelta) {
		return puntoDiScelta-1;
	}

	@Override
	public Integer ultimaSceltaAssegnataA(Integer puntoDiScelta) {
		return caselleVuote[puntoDiScelta].getValore();
	}

	@Override
	public void scriviSoluzione(int nr_sol) {
		
		Blocco bEv=new Blocco(dim);
		bEv.setBlocchi(blocchi);
		bEv.setVincoli(vincoli);
		
		Griglia sol=new Griglia(gridAusiliare, dim, bEv);
		scriviSol.add(sol); 
	}
	
	public ArrayList<Griglia> getSoluzioni(){
		return scriviSol.getSoluzioni();
	}

	@Override
	public void risolvi(int maxSoluzioni) {
		TemplateMethod2.super.risolvi(maxSoluzioni);
		
	}

	@Override
	public boolean esisteSoluzione() {
		//for(int i=0;i<dim;++i) 
			//System.out.print(Arrays.toString(gridAusiliare[i]));
		//System.out.print("\n");
		for(VincoliOperazioni v: vincoli) {
			char gruppo=v.getGruppo();
			String operatoreVincolo=v.getOperatore();
			int ris=v.getValore();
			switch (operatoreVincolo) {
			case "addizione": if(!(add(gruppo)==ris)) return false; break;
			case "sottrazione": if(!(sott(gruppo, ris)==ris)) return false; break;
			case "moltiplicazione": if(!(mul(gruppo)==ris)) return false; break;
			case "divisione": if(!(div(gruppo, ris)==ris)) return false; break;
			default: if(!(vuoto(gruppo)==ris)) return false; break;
			}
		}
		return true;
	}
	
	
	private int add(char gruppo) {
		int val=0;
		for(int i=0;i<dim;++i)
			for(int j=0;j<dim;++j)
				if(blocchi[i][j]==gruppo)
					val=val+gridAusiliare[i][j];
		return val;
	}
	
	private int mul(char gruppo) {
		int val=1;
		for(int i=0;i<dim;++i)
			for(int j=0;j<dim;++j)
				if(blocchi[i][j]==gruppo)
					val=val*gridAusiliare[i][j];
		return val;
	}
	
	private int div(char gruppo, int ris) {
		int val=0, k=0;
		
		int[] operandi=new int[2];
		for(int i=0;i<dim;++i) {
			for(int j=0;j<dim;++j)
				if(blocchi[i][j]==gruppo) {
					operandi[k]=gridAusiliare[i][j];
					k++;
					if(operandi[1]!=0)
						break;
				}
			if(operandi[1]!=0)
				break;
		}
		
		int add1=operandi[0];
		int add2=operandi[1];
		if((add1/add2)==ris)
			val=add1/add2;
		else if((add2/add1)==ris)
			val=add2/add1;
		else
			return 0;
		return val;
		
	}

	private int sott(char gruppo, int ris) {
		int val=0, k=0;
		
		int[] operandi=new int[2];
		for(int i=0;i<dim;++i) {
			for(int j=0;j<dim;++j)
				if(blocchi[i][j]==gruppo) {
					operandi[k]=gridAusiliare[i][j];
					k++;
					if(operandi[1]!=0)
						break;
				}
			if(operandi[1]!=0)
				break;
		}
		
		int add1=operandi[0];
		int add2=operandi[1];
		if((add1-add2)==ris)
			val=add1-add2;
		else if((add2-add1)==ris)
			val=add2-add1;
		else
			return 0;
		return val;
	}
	
	private int vuoto(char gruppo) {
		for(int i=0;i<dim;++i)
			for(int j=0;j<dim;++j)
				if(blocchi[i][j]==gruppo)
					return gridAusiliare[i][j];
		return 0;
	}
}
