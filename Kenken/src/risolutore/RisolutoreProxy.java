package risolutore;

import java.util.ArrayList;

import griglia.Cella;
import griglia.Griglia;

public class RisolutoreProxy implements Risolutore {
	private ConcreteRisolutore2 risol;
	private int maxSol;
	
	public RisolutoreProxy(Griglia g) {
		risol=new ConcreteRisolutore2(g);
	}
	
	public void setSoluzioni(StrategySoluzioni s) {
		risol.setScriviSoluzione(s);
	}
	
	@Override
	public void risolvi(int maxSoluzioni) {
		this.maxSol=maxSoluzioni;
		risol.risolvi(maxSoluzioni);			
	}
	
	public void visualizzaSoluzioni() {	
		ArrayList<Griglia> sol=risol.getSoluzioni();
		int k=0;
		System.out.println("Numero soluzioni richieste: "+ maxSol);
		System.out.println("Numero soluzioni trovate: "+ sol.size());
		for(Griglia g: sol) {
			Cella[][] soluzione=g.getGriglia();
			System.out.println("Soluzione: "+(++k));
			for(int i=0;i<soluzione.length;++i) {
				for(int j=0;j<soluzione.length;++j)
					System.out.print(soluzione[i][j].getValore()+" ");
				System.out.print("\n");
			}
		}
	}
}
