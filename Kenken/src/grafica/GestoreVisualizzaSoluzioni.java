package grafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import grafica.componenti.PannelloGrigliaSoluzioni;
import griglia.Griglia;
import statiFinestra.GestoreStato;
import statiFinestra.Stato;

public class GestoreVisualizzaSoluzioni extends GestoreStato implements Gestore {
	
	private JLabel label;

	private PannelloGrigliaSoluzioni pannello;

	private int contatore = 0;
	private int maxSol=0;

	private ArrayList<Griglia> array;

	private final Stato INIT = new Init();
	private final Stato PRINCIPALE = new Principale();

	public GestoreVisualizzaSoluzioni(ArrayList<Griglia> array, int max) {
		this.array = array;
		if(array.size() == 0) {
			throw new IllegalArgumentException("nessuna soluzione");
		}
		this.maxSol=max;
		JOptionPane.showMessageDialog(null, "Numero soluzioni trovate: "+ maxSol);
		transition(INIT);
	}

	private interface StatoGestore extends Stato, ActionListener{}

	private class Init implements StatoGestore{

		private static final int NUM_COMPONENTI = 4;
		private int contatore = NUM_COMPONENTI;

		@Override
		public void actionPerformed(ActionEvent e) {
			String comando = e.getActionCommand();
			switch(comando) {
			case "previous":
				JButton previous = (JButton)e.getSource();
				previous.setActionCommand("previous");
				previous.addActionListener(GestoreVisualizzaSoluzioni.this);
				break;
			case "next":
				JButton next = (JButton)e.getSource();
				next.setActionCommand("next");
				next.addActionListener(GestoreVisualizzaSoluzioni.this);
				break;
			case "label":
				label = (JLabel)e.getSource();
				break;
			case "pannello":
				pannello = (PannelloGrigliaSoluzioni)e.getSource();
				break;
			default:
				System.err.println("comando sconosciuto: " + comando);
				++contatore;
			}
			--contatore;
			if(contatore == 0) {
				transition(PRINCIPALE);
			}
		}

	}

	private class Principale implements StatoGestore{
		
		@Override
		public void entryAction() {
			visualizza();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String comando = e.getActionCommand();
			switch(comando) {
			case "next":
				++contatore;
				visualizza();
				break;
			case "previous":
				--contatore;
				visualizza();
				break;
			default:
				System.err.println("comando sconosciuto: " + comando);
			}

		}
		
		private void visualizza() {
			
			if(contatore < 0) {
				contatore = 0;
			}
			if(contatore >= array.size()) {
				contatore = array.size() - 1;
			}
			
			label.setText("soluzione: " + (contatore+1) + " di " + array.size());
			pannello.setGriglia(array.get(contatore));
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		((StatoGestore)corrente).actionPerformed(e);
	}

}
