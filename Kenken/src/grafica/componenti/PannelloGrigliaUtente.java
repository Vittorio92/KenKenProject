package grafica.componenti;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import griglia.Blocco;
import griglia.Cella;
import griglia.Griglia;
import griglia.VincoliOperazioni;

public class PannelloGrigliaUtente {
	private JPanel pannello;
	private GridBagLayout layout;
	private GridBagConstraints lim;

	private ArrayList<Character> inseriti=new ArrayList<>();
	private JTextField[][] caselle;

	private Griglia griglia;

	public PannelloGrigliaUtente(int dimensione) {
		griglia = new Griglia(dimensione,true);

		layout = new GridBagLayout();
		lim = new GridBagConstraints();
		pannello = new JPanel();
		pannello.setLayout(layout);
		impostaGriglia(dimensione);
	}
	
	private void impostaGriglia(int dim) {
		final int dimensione=dim;
		caselle = new JTextField[dim][dim];
		int xOffset = 0;
		int yOffset = 0;
		for(int i=0; i < dimensione; ++i) {
			if(i > 0 && i % dimensione == 0) {
				xOffset++;
			}
			yOffset = 0;
			for(int j=0; j < dimensione; ++j) {
				if(j > 0 && j % dimensione == 0) {
					yOffset++;
				}
				caselle[i][j] = new DigitField();
				caselle[i][j].setEditable(true);
				caselle[i][j].setHorizontalAlignment(JTextField.CENTER);
				caselle[i][j].setBackground(Color.WHITE);
				caselle[i][j].setText("0");
				setBordiPerGruppi(caselle[i][j], i, j);
				setLim(caselle[i][j], j+yOffset, i+xOffset, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1, 1, 1, 1);
			}
		}
	}
	
	private void setBordiPerGruppi(JTextField c, int r, int col) {
		
		final int spessore=3;
		char[][] b=griglia.getBlocchi().getBlocchi();
		ArrayList<VincoliOperazioni> vincoli=griglia.getBlocchi().getVincoliGriglia();
		String vincolo="";
		int dim=griglia.getDimensione();
		int sopra=1;
		int sotto=1;
		int destra=1;
		int sinistra=1;
		
		if(r==0 && col==0) {
			if(b[r][col]!=b[r][col+1])
				destra=spessore;
			if(b[r][col]!=b[r+1][col])
				sotto=spessore;
		}
		else if(r==0 && col==dim-1) {
			if(b[r][col]!=b[r][col-1])
				sinistra=spessore;
			if(b[r][col]!=b[r+1][col])
				sotto=spessore;
		}
		else if(r==0) {
			if(b[r][col]!=b[r][col+1])
				destra=spessore;
			if(b[r][col]!=b[r+1][col])
				sotto=spessore;
			if(b[r][col]!=b[r][col-1])
				sinistra=spessore;
		}
		else if(r==dim-1 && col==0) {
			if(b[r][col]!=b[r-1][col])
				sopra=spessore;
			if(b[r][col]!=b[r][col+1])
				destra=spessore;
		}
		else if(r==dim-1 && col==dim-1) {
			if(b[r][col]!=b[r-1][col])
				sopra=spessore;
			if(b[r][col]!=b[r][col-1])
				sinistra=spessore;
		}
		else if(r==dim-1) {
			if(b[r][col]!=b[r-1][col])
				sopra=spessore;
			if(b[r][col]!=b[r][col-1])
				sinistra=spessore;
			if(b[r][col]!=b[r][col+1])
				destra=spessore;
		}
		else if(col==0) {
			if(b[r][col]!=b[r][col+1])
				destra=spessore;
			if(b[r][col]!=b[r+1][col])
				sotto=spessore;
			if(b[r][col]!=b[r-1][col])
				sopra=spessore;
		}
		else if(col==dim-1) {
			if(b[r][col]!=b[r-1][col])
				sopra=spessore;
			if(b[r][col]!=b[r][col-1])
				sinistra=spessore;
			if(b[r][col]!=b[r+1][col])
				sotto=spessore;
		}else {
			if(b[r][col]!=b[r-1][col])
				sopra=spessore;
			if(b[r][col]!=b[r][col-1])
				sinistra=spessore;
			if(b[r][col]!=b[r+1][col])
				sotto=spessore;
			if(b[r][col]!=b[r][col+1])
				destra=spessore;
		}
		Border bordoEst=BorderFactory.createMatteBorder(sopra, sinistra, sotto, destra, Color.BLACK);
		Character gruppo=b[r][col];
		if(!inseriti.contains(gruppo)) {
			for(VincoliOperazioni v:vincoli)
				if(v.getGruppo()==gruppo) {
					vincolo=v.getValore()+" "+v.getSimboloOperatore();
					break;
				}
			inseriti.add(gruppo);
			Border bordoInt=BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), vincolo);
			c.setBorder(BorderFactory.createCompoundBorder(bordoEst, bordoInt));
		}else {
			c.setBorder(bordoEst);
		}	
	}
	
	public void nuovaGriglia(int dim) {
		griglia = new Griglia(dim,true);
		pannello.removeAll();
		inseriti.clear();
		impostaGriglia(dim);
		pannello.revalidate();
		pannello.repaint();
	}

	public JPanel getPannello() {
		return pannello;
	}

	private void setLim(JComponent component, int x, int y, int zonaPos, int fill, int gridwidth, int gridheight, double weightx, double weighty){
		pannello.add(component);
		lim.gridx = x;
		lim.gridy = y;
		lim.anchor = zonaPos;
		lim.fill = fill;
		lim.gridwidth = gridwidth;
		lim.gridheight = gridheight;
		lim.weightx = weightx;
		lim.weighty = weighty;
		lim.insets=new Insets(0,0,0,0);
		layout.setConstraints(component, lim);
	}

	public void cancellaTutto() {
		final String VUOTA = "";
		for(int i=0; i < caselle.length; ++i) {
			for(int j=0; j < caselle[0].length; ++j) {
				caselle[i][j].setText(VUOTA);
				caselle[i][j].setBackground(Color.WHITE);
			}
		}
	}
	
	public Griglia getGriglia() {
		final int dim = griglia.getDimensione();
		int [][] valori=new int[dim][dim];
		String text="";
		int  valore = 0;
		for(int i=0; i < dim; ++i) {
			for(int j=0; j < dim; ++j) {
				valore=0;
				text = caselle[i][j].getText();
				if(!text.equals("")) 
					valore = Integer.parseInt(text);
				valori[i][j]=valore;
			}
		}		
		Griglia ris=new Griglia(valori, griglia.getDimensione(), griglia.getBlocchi());
		return ris;
	}

	
	public void verificaSoluzione() {	
		int dimensione=griglia.getDimensione();
		
		for(int i=0;i<dimensione;++i)
			for(int j=0;j<dimensione;++j)
				if(!verifica(caselle[i][j], dimensione)) {
					JOptionPane.showMessageDialog(null,"Valore inserito nella cella: ["+ (i+1)+ ", "+ (j+1)+"] non valido");
					return;
				}
		
		int [][] aus=new int [dimensione][dimensione];
		
		
		for(int i=0;i<dimensione;++i)
			for(int j=0;j<dimensione;++j) 
				aus[i][j]=Integer.parseInt(caselle[i][j].getText());
		
		Griglia prova=new Griglia(aus,dimensione,griglia.getBlocchi());
		
		if(!prova.isValida())
			setCelleNonValide(prova,false);
		else if(prova.eCorretta())
			vittoria();
		else
			setCelleNonValide(prova,true);
	}
	
	private void vittoria() {
		for(int i=0;i<caselle.length;++i)
			for(int j=0;j<caselle.length;++j)
				caselle[i][j].setBackground(Color.GREEN);
		JOptionPane.showMessageDialog(null,"Complimenti, HAI VINTO !!");
		int nuova= JOptionPane.showConfirmDialog(null, "Nuova parita?");
		if(nuova==JOptionPane.YES_OPTION) {
			int dim=griglia.getDimensione();
			nuovaGriglia(dim);
		}
		for(int i=0;i<caselle.length;++i)
			for(int j=0;j<caselle.length;++j)
				caselle[i][j].setBackground(Color.WHITE);
	}
	
	//fare questo
	private void setCelleNonValide(Griglia g2,boolean controllaVincoli) {  //controllaVincoli=true allora si controllano i vincoli; =false si controlla l'unicità dei valori in righe e colonne
		if(!controllaVincoli) {
			ArrayList<Cella> nonValide=g2.notValide();
			for(Cella c:nonValide) {
				int r=c.getRiga();
				int col=c.getColonna();
				caselle[r][col].setBackground(Color.RED);
				new Timer(c);
			}
		}else {
			ArrayList<Cella> nonRispettano=g2.nonRispettanoIVincoli();
			for(Cella c:nonRispettano) {
				int r=c.getRiga();
				int col=c.getColonna();
				caselle[r][col].setBackground(Color.RED);
				new Timer(c);
				}
			
		}
	}
	
	private boolean verifica(JTextField c, int dimensione) {
		String testo=c.getText();
		if(testo.equals(""))
			return false;
		int val=Integer.parseInt(testo);
		if(val<0 || val > dimensione)
			return false;
		return true;
	}
	
	
	public void salva() {
		final int dim=griglia.getDimensione();
		int val=0;
		int[][] valori=new int [dim][dim];
		String txt="";
		for(int i=0;i<dim;++i) {
			for(int j=0;j<dim;++j) {
				txt=caselle[i][j].getText();
				if(txt.equals(""))
					val=0;
				else
					val=Integer.parseInt(txt);
				valori[i][j]=val;
			}
		}
		Blocco bEv=griglia.getBlocchi();
		griglia=new Griglia(valori, dim, bEv);
		griglia.salva();
		JOptionPane.showMessageDialog(null, "Parita salvata");
	}
	
	public void ripristina(int dimensione) {
		this.nuovaGriglia(griglia.getDimRipristino());
		griglia.ripristina();
		final int dim=griglia.getDimensione();
		for(int i=0;i<dim;++i) {
			for(int j=0;j<dim;++j) {
				caselle[i][j].setText(""+griglia.getValore(i, j));
			}
		}
		
	}
	

	private class Timer implements Runnable{

		private Cella[] celle;

		public Timer(Cella... celle) {
			this.celle = celle;
			Thread t = new Thread(this);
			t.setDaemon(true);
			t.start();
		}

		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			animazione();
		}
		
		private void animazione() {
			Color color;
			int riga, colonna;
			for(int i = 0; i <= 255; i += 5) {
				color = new Color(255, i, i);
				for(int j=0; j < celle.length; ++j) {
					riga = celle[j].getRiga();
					colonna = celle[j].getColonna();
					caselle[riga][colonna].setBackground(color);
				}				
				try {
					TimeUnit.MILLISECONDS.sleep(70);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}

	}

	private class DigitField extends JTextField {

		private static final long serialVersionUID = 1L;

		public DigitField() {
			super();
		}

		protected Document createDefaultModel() {
			return new DigitDocument();
		}

		private class DigitDocument extends PlainDocument {

			private static final long serialVersionUID = 1L;

			public void insertString(int offs, String str, AttributeSet a)
					throws BadLocationException {
				if (str == null) {
					return;
				}
				char[] caratteri = str.toCharArray();
				char[] ris = new char[caratteri.length];
				int length = 0;
				for (int i = 0; i < caratteri.length; ++i) {
					if(Character.isDigit(caratteri[i])) {
						ris[length++] = caratteri[i];
					}
				}
				super.insertString(offs, new String(ris, 0, length), a);
			}
		}
	}


}
