package grafica;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import griglia.Griglia;
import risolutore.RisolutoreProxy;
import risolutore.ConcreteStrategySoluzioni;

public class CaricamentoVisualizzaSoluzioni{
	private JFrame finestra;
	private int maxSoluzioni;

	public CaricamentoVisualizzaSoluzioni(Griglia griglia, int maxSoluzioni){
		finestra = new JFrame("Visualizza Soluzioni");		
		this.maxSoluzioni = maxSoluzioni;

		inizializzaFinestra();
		inizializzaComponenti();
		finestra.pack();

		Risolvi calcola = new Risolvi(griglia);
		finestra.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		calcola.calcolaSoluzioni();
	}

	private void inizializzaFinestra() {
		finestra.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		finestra.setPreferredSize(new Dimension(300, 90));
		finestra.setLocation(480, 200);
		finestra.setAutoRequestFocus(false);
	}

	private void inizializzaComponenti() {
		JPanel pannello = new JPanel();
		finestra.setContentPane(pannello);

		JLabel label = new JLabel("Soluzioni:");
		pannello.add(label);
	}
	
	private class Risolvi  {

		private Griglia griglia;
		private RisolutoreProxy tab;
		private ConcreteStrategySoluzioni scriviSoluzioni;
		
		public Risolvi(Griglia griglia){
			this.griglia = griglia;
			scriviSoluzioni = new ConcreteStrategySoluzioni();
			tab = new RisolutoreProxy(griglia);
			tab.setSoluzioni(scriviSoluzioni);
		}

		protected void calcolaSoluzioni(){
			tab.risolvi(maxSoluzioni);
			ArrayList<Griglia> array = scriviSoluzioni.getSoluzioni();
			if(array.size() > 0) {
				GestoreVisualizzaSoluzioni gestore = new GestoreVisualizzaSoluzioni(array, maxSoluzioni);
				new FinestraSoluzioni(gestore, griglia);
			} else {
				JOptionPane.showMessageDialog(finestra, "Questa griglia di kenken non ha soluzioni. Inizia un'altra partita", "ERRORE", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
