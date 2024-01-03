package risolutore;

import java.util.ArrayList;
import griglia.Griglia;

public class ConcreteStrategySoluzioni implements StrategySoluzioni {

	private ArrayList<Griglia> soluzioni;
	
	public ConcreteStrategySoluzioni() {
		soluzioni=new ArrayList<>();
	}
	
	@Override
	public void add(Griglia sol) {
		soluzioni.add(sol);
	}
	
	public void clear() {
		soluzioni.clear();
	}
	
	public ArrayList<Griglia> getSoluzioni(){
		return soluzioni;
	}

}
