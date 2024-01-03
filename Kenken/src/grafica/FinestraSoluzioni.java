package grafica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import grafica.componenti.PannelloGrigliaSoluzioni;
import griglia.Griglia;

public class FinestraSoluzioni {
	
private final static Dimension SCHERMO = Toolkit.getDefaultToolkit().getScreenSize();
	
	private JFrame finestra;
	
	private PannelloGrigliaSoluzioni pannello;
	
	private JLabel label;
	
	private Gestore gestore;
	
	public FinestraSoluzioni(GestoreVisualizzaSoluzioni gestore, Griglia griglia) {
		this.gestore = gestore;
		inizializzaFinestra();
		inizializzaComponenti(griglia);
		finestra.pack();
		centra();
		finestra.setVisible(true);
	}
	
	private void inizializzaFinestra() {
		finestra = new JFrame("Soluzioni");
		Dimension dimensioni = new Dimension(400, 400);
		finestra.setPreferredSize(dimensioni);
		finestra.setResizable(true);
		finestra.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void inizializzaComponenti(Griglia griglia) {
		
		JPanel label_pnl = new JPanel();
		label = new JLabel();
		label_pnl.add(label);
		gestore.actionPerformed(new ActionEvent(label, ActionEvent.ACTION_PERFORMED, "label"));
		finestra.add(label_pnl, BorderLayout.NORTH);
		
		pannello = new PannelloGrigliaSoluzioni(griglia);
		gestore.actionPerformed(new ActionEvent(pannello, ActionEvent.ACTION_PERFORMED, "pannello"));
		//aggiorna();
		finestra.add(pannello.getPannello(), BorderLayout.CENTER);
		
		JPanel pnl = new JPanel();
		
		Dimension dimensioniPulsanti = new Dimension(90, 25);
		
		JButton previous = new JButton("previous");
		gestore.actionPerformed(new ActionEvent(previous, ActionEvent.ACTION_PERFORMED, "previous"));
		previous.setPreferredSize(dimensioniPulsanti);
		pnl.add(previous);
		
		JButton next = new JButton("next");
		gestore.actionPerformed(new ActionEvent(next, ActionEvent.ACTION_PERFORMED, "next"));
		next.setPreferredSize(dimensioniPulsanti);
		pnl.add(next);
		
		finestra.add(pnl, BorderLayout.SOUTH);
	}
	
	private void centra() {
		Dimension dimensioni = finestra.getSize();
		int x = (SCHERMO.width - dimensioni.width)/2;
		int y = (SCHERMO.height - dimensioni.height)/2;
		finestra.setLocation(x, y);
	}
}
