package griglia;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Blocco {
	private char[][] blocchiGrid;
	private ArrayList<VincoliOperazioni> vincoliGriglia;
	
	public Blocco(int dimensione) {
		blocchiGrid=new char[dimensione][dimensione];
		vincoliGriglia=new ArrayList<>(dimensione);
	}
	
	
	public char[][] getBlocchi(){
		return blocchiGrid;
	}
	
	public ArrayList<VincoliOperazioni> getVincoliGriglia(){
		return vincoliGriglia;
	}
	
	public void setBlocchi(char[][]b) {
		if(blocchiGrid.length!=b.length)
			throw new IllegalArgumentException("Parametro non valido");
		for(int i=0;i<blocchiGrid.length;++i)
			for(int j=0;j<blocchiGrid.length;++j)
				blocchiGrid[i][j]=b[i][j];
	}
	
	public void setVincoli(ArrayList<VincoliOperazioni>v) {
		vincoliGriglia.clear();
		for(VincoliOperazioni vincolo: v)
			vincoliGriglia.add(vincolo);
	}
	
	public void aggiungiVincolo(String op, int valore, char b) {
		VincoliOperazioni nuovo=new VincoliOperazioni(op, valore, b);
		vincoliGriglia.add(nuovo);
	}
	
	public void aggiungiVincolo(String vincolo) {
		char op;
		VincoliOperazioni nuovo;
		StringTokenizer tk=new StringTokenizer(vincolo, " ");
		char b=tk.nextToken().charAt(0);
		int val=Integer.parseInt(tk.nextToken());
		if(tk.hasMoreTokens()) {
			op=tk.nextToken().charAt(0);
			nuovo=new VincoliOperazioni(op, val, b);
		}else {
			op=' ';
			nuovo=new VincoliOperazioni(op, val, b);
		}
		vincoliGriglia.add(nuovo);
	}
	
	public void aggiungiBlocchi(char[][]b) {
		if(blocchiGrid.length!=b.length)
			throw new IllegalArgumentException("Parametro non valido");
		if(blocchiGrid[0].length!=b[0].length)
			throw new IllegalArgumentException("Parametro non valido");
		for(int i=0;i<blocchiGrid.length;++i)
			for(int j=0;j<blocchiGrid.length;++j)
				blocchiGrid[i][j]=b[i][j];
	}
	
	public void aggiungiBlocchi(StringTokenizer st) {
		int numCaselle=blocchiGrid.length*blocchiGrid.length;
		if(!(numCaselle==st.countTokens()))
			throw new IllegalArgumentException("Parametro non valido");
		int i=0; int j=0;
		while(st.hasMoreTokens()) {
			blocchiGrid[i][j]=st.nextToken().charAt(0);
			j++;
			if(j==3) {
				i++;
				j=0;
			}
		}
	}
	
	public void aggiungiBlocchi(String b) {
		int k=0;
		String[] blocchi=b.split("\\s");
		for(int i=0;i<blocchiGrid.length;++i)
			for(int j=0;j<blocchiGrid.length;++j) {
				blocchiGrid[i][j]=blocchi[k++].charAt(0);
			}
	}
	
	
	public String toString() {
		StringBuilder sb=new StringBuilder(300);
		sb.append("Blocchi \n");
		for(int i=0;i< blocchiGrid.length ;++i) {
			for(int j=0; j<blocchiGrid.length; ++j)
				sb.append(" "+blocchiGrid[i][j]+" ");
			sb.append("\n");
		}
		sb.append("\n");
		for(VincoliOperazioni v: vincoliGriglia)
			sb.append(v.toString()+"\n");
		return sb.toString();
	}
}
