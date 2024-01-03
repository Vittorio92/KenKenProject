package grafica.componenti;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import griglia.Griglia;
import griglia.VincoliOperazioni;

public class PannelloGrigliaSoluzioni {
	
	private JPanel pannello;
	private GridBagLayout layout;
	private GridBagConstraints limit;
	
	private JTextField[][] caselle;
	private ArrayList<Character> inseriti=new ArrayList<>();
	
	public PannelloGrigliaSoluzioni(Griglia g) {
		layout=new GridBagLayout();
		limit=new GridBagConstraints();
		pannello=new JPanel();
		pannello.setLayout(layout);
		
		final int dim=g.getDimensione();
		caselle= new JTextField[dim][dim];
		int xOffset=0;
		int yOffset=0;
		
		for(int i=0;i<dim;i++) {
			if(i>0 && i%dim==0) {
				xOffset++;
			}
			yOffset=0;
			for(int j=0;j<dim;++j) {
				if(j>0 && j%dim==0) {
					yOffset++;
				}
				caselle[i][j]=new JTextField();
				caselle[i][j].setEditable(false);
				caselle[i][j].setBackground(Color.WHITE);
				caselle[i][j].setHorizontalAlignment(JTextField.CENTER);
				setBordiPerGruppi(caselle[i][j], i, j, g);
				setLim(caselle[i][j], j+yOffset, i+xOffset, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1, 1, 1, 1);
			}
		}
	}
	
	
private void setBordiPerGruppi(JTextField c, int r, int col, Griglia griglia) {
		
		final int spessore=4;
		char[][] b=griglia.getBlocchi().getBlocchi();
		int dim=griglia.getDimensione();
		ArrayList<VincoliOperazioni> vincoli=griglia.getBlocchi().getVincoliGriglia();
		String vincolo="";
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
	
	protected void setLim(JComponent componente, int x, int y, int tipo, int fill, int gridwidth, int gridheight, double weightx, double weighty) {
		pannello.add(componente);
		limit.gridx=x;
		limit.gridy=y;
		limit.anchor=tipo;
		limit.fill=fill;
		limit.gridwidth=gridwidth;
		limit.gridheight=gridheight;
		limit.weightx=weightx;
		limit.weighty=weighty;
		layout.setConstraints(componente, limit);	
	}
	
	public JPanel getPannello() {
		return pannello;
	}
	
	
	public void setGriglia(Griglia g) {
		if(g.getDimensione()!=caselle.length)
			throw new IllegalArgumentException("Parametron non valido");
		int valore=0;
		for(int i=0;i<caselle.length;++i) {
			for(int j=0;j<caselle.length;++j) {		
				valore = g.getValore(i, j);
				caselle[i][j].setText(String.valueOf(valore));
				}
			}
		}
}
