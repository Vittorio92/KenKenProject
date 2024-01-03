package grafica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import grafica.componenti.PannelloGrigliaUtente;

public class Finestra {
	private final static Dimension SCHERMO = Toolkit.getDefaultToolkit().getScreenSize();

	private JFrame finestra;
	private Gestore gestore;
	
	private JSpinner numSoluzioni;

	private PannelloGrigliaUtente pannelloGriglia;
	
	private static final int DIM=3; //dimensione griglia di default

	public Finestra(Gestore gestore) {
		this.gestore = gestore;
		inizializzaFinestra();
		inizializzaComponenti();
		inizializzaMenu();
		finestra.pack();
		finestra.setVisible(true);
	}

	private void inizializzaFinestra() {
		finestra = new JFrame("Kenken Games");
		finestra.setMinimumSize(new Dimension(400, 400));
		Dimension dimensioni = new Dimension(500, 500);
		finestra.setSize(dimensioni);
		finestra.setPreferredSize(dimensioni);
		finestra.setResizable(true);
		finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		centra();
	}

	private void inizializzaComponenti() {

		pannelloGriglia = new PannelloGrigliaUtente(DIM);
		gestore.actionPerformed(new ActionEvent(pannelloGriglia, ActionEvent.ACTION_PERFORMED, "pannelloGriglia"));
		finestra.add(pannelloGriglia.getPannello(), BorderLayout.CENTER);

		JPanel pannelloSotto = new JPanel();

		numSoluzioni = new JSpinner();
		numSoluzioni.setPreferredSize(new Dimension(40, 30));
		numSoluzioni.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		gestore.actionPerformed(new ActionEvent(numSoluzioni, ActionEvent.ACTION_PERFORMED, "numSoluzioni"));
		pannelloSotto.add(numSoluzioni);

		JButton visualSoluzione = new JButton("visualizzaSoluzione");
		gestore.actionPerformed(new ActionEvent(visualSoluzione, ActionEvent.ACTION_PERFORMED, "visualizzaSoluzione"));
		visualSoluzione.setPreferredSize(new Dimension(150,26));
		pannelloSotto.add(visualSoluzione);
		
		JButton verificaSoluzione=new JButton("verificaSoluzione");
		gestore.actionPerformed(new ActionEvent(verificaSoluzione, ActionEvent.ACTION_PERFORMED, "verificaSoluzione"));
		verificaSoluzione.setPreferredSize(new Dimension(150,26));
		pannelloSotto.add(verificaSoluzione);
		
		JButton cancella = new JButton("Cancella tutto");
		gestore.actionPerformed(new ActionEvent(cancella, ActionEvent.ACTION_PERFORMED, "cancellaTutto"));
		pannelloSotto.add(cancella);

		finestra.add(pannelloSotto, BorderLayout.SOUTH);

	}
	
	private void inizializzaMenu() {
		JMenuBar menu = new JMenuBar();
		finestra.setJMenuBar(menu);
		
		JMenu nuovaPartita = new JMenu("Nuova partita");
		menu.add(nuovaPartita);
		
		JMenu opzioni=new JMenu("Opzioni partita");
		menu.add(opzioni);
		
		JMenu dimensione = new JMenu("Dimensione griglia");
		nuovaPartita.add(dimensione);

		JMenuItem treXtre = new JMenuItem("3x3");
		gestore.actionPerformed(new ActionEvent(treXtre, ActionEvent.ACTION_PERFORMED, "menuGriglia3x3"));
		dimensione.add(treXtre);
		
		JMenuItem quattroXquattro= new JMenuItem("4x4");
		gestore.actionPerformed(new ActionEvent(quattroXquattro, ActionEvent.ACTION_PERFORMED, "menuGriglia4x4"));
		dimensione.add(quattroXquattro);
		
		JMenuItem cinqueXcinque = new JMenuItem("5x5");
		gestore.actionPerformed(new ActionEvent(cinqueXcinque, ActionEvent.ACTION_PERFORMED, "menuGriglia5x5"));
		dimensione.add(cinqueXcinque);
		
		JMenuItem salva=new JMenuItem("Salva partita");
		gestore.actionPerformed(new ActionEvent(salva, ActionEvent.ACTION_PERFORMED, "salvaPartita"));
		opzioni.add(salva);
		
		JMenuItem ripristina=new JMenuItem("Ripristina salvataggio");
		gestore.actionPerformed(new ActionEvent(ripristina, ActionEvent.ACTION_PERFORMED, "ripristina"));
		opzioni.add(ripristina);
	}

	private void centra() {
		Dimension dimensioni = finestra.getSize();
		int x = (SCHERMO.width - dimensioni.width)/2;
		int y = (SCHERMO.height - dimensioni.height)/2;
		finestra.setLocation(x, y);
	}


}
