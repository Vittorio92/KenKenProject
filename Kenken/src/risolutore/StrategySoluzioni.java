package risolutore;

import java.util.ArrayList;

import griglia.Griglia;

public interface StrategySoluzioni {

	public void add(Griglia sol);
	
	public void clear();
	
	public ArrayList<Griglia> getSoluzioni();
}
