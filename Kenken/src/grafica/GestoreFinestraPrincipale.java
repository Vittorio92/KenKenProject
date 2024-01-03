package grafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;

import grafica.componenti.PannelloGrigliaUtente;
import statiFinestra.GestoreStato;
import statiFinestra.Stato;

public class GestoreFinestraPrincipale extends GestoreStato implements Gestore {
	private JButton cancellaTutto;
	private JButton visualizzaSoluzione;
	private JButton verificaSoluzione;
	private JSpinner numSoluzioni;
	
	private PannelloGrigliaUtente pannelloG;
	private int dim=3;
	private final Stato INIT=new Init();
	private final Stato PRINCIPALE=new Principale();
	
	public GestoreFinestraPrincipale() {
		transition(INIT);
	}
	
	private interface StatoGestore extends Stato, ActionListener{}
	
	private class Init implements StatoGestore{
		
		private static final int NUMERO_COMPONENTI =11;

		private int contatore = NUMERO_COMPONENTI;

		@Override
		public void actionPerformed(ActionEvent e) {

			String comando = e.getActionCommand();
			switch(comando) {
			case "pannelloGriglia":
				pannelloG = (PannelloGrigliaUtente) e.getSource();
				break;
			case "menuGriglia3x3":
				JMenuItem menu3x3 = (JMenuItem) e.getSource();
				menu3x3.setActionCommand("menuGriglia3x3");
				menu3x3.addActionListener(GestoreFinestraPrincipale.this);
				break;
			case "menuGriglia4x4":
				JMenuItem menu4x4 = (JMenuItem) e.getSource();
				menu4x4.setActionCommand("menuGriglia4x4");
				menu4x4.addActionListener(GestoreFinestraPrincipale.this);
				break;
			case "menuGriglia5x5":
				JMenuItem menu5x5 = (JMenuItem) e.getSource();
				menu5x5.setActionCommand("menuGriglia5x5");
				menu5x5.addActionListener(GestoreFinestraPrincipale.this);
				break;
			case "cancellaTutto":
				cancellaTutto = (JButton) e.getSource();
				cancellaTutto.setActionCommand("cancellaTutto");
				cancellaTutto.addActionListener(GestoreFinestraPrincipale.this);
				break;
			case "visualizzaSoluzione":
				visualizzaSoluzione = (JButton) e.getSource();
				visualizzaSoluzione.setActionCommand("visualizzaSoluzione");
				visualizzaSoluzione.addActionListener(GestoreFinestraPrincipale.this);
				break;
				
			case "verificaSoluzione":
				verificaSoluzione=(JButton)e.getSource();
				verificaSoluzione.setActionCommand("verificaSoluzione");
				verificaSoluzione.addActionListener(GestoreFinestraPrincipale.this);
				break;
			case "numSoluzioni":
				numSoluzioni = (JSpinner) e.getSource();
				break;
			case "salvaPartita":
				JMenuItem salva=(JMenuItem)e.getSource();
				salva.setActionCommand("salvaPartita");
				salva.addActionListener(GestoreFinestraPrincipale.this);
				break;
			case "ripristina":
				JMenuItem ripristina=(JMenuItem)e.getSource();
				ripristina.setActionCommand("ripristina");
				ripristina.addActionListener(GestoreFinestraPrincipale.this);
				break;
			default:
				System.err.println("errore Comando sconosciuto: \"" + comando + "\"");
				contatore++;
			}
			if(--contatore == 0) {
				transition(PRINCIPALE);
			}
		}

	}

	private class Principale implements StatoGestore{

		@Override
		public void actionPerformed(ActionEvent e) {
			String comando = e.getActionCommand();
			switch(comando) {
			case "menuGriglia3x3":
				setGriglia(3);
				dim=3;
				break;
			case "menuGriglia4x4":
				setGriglia(4);
				dim=4;
				break;
			case "menuGriglia5x5":
				setGriglia(5);
				dim=5;
				break;
			case "cancellaTutto":
				pannelloG.cancellaTutto();
				break;
			case "verificaSoluzione":
				pannelloG.verificaSoluzione();
				break;
			case "visualizzaSoluzione":
				int soluzioni = (Integer)numSoluzioni.getValue();
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						new CaricamentoVisualizzaSoluzioni(pannelloG.getGriglia(), soluzioni);
					}
				});
				break;
			case "salvaPartita":
				pannelloG.salva();
				break;
			case "ripristina":
				pannelloG.ripristina(dim);
				break;
			default:
				System.err.println("errore COMANDO sconosciuto: \"" + comando + "\"");
			}

		}

		private void setGriglia(int dim) {
			pannelloG.nuovaGriglia(dim);
		}

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		((StatoGestore)corrente).actionPerformed(e);
	}
}
