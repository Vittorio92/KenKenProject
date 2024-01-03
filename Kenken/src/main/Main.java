package main;

import grafica.Finestra;
import grafica.Gestore;
import grafica.GestoreFinestraPrincipale;

public class Main {
	
	public static void main(String [] args) {
		Gestore gestore=new GestoreFinestraPrincipale();
		new Finestra(gestore);
	}

}
