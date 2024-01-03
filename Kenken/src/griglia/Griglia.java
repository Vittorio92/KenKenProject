package griglia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class Griglia {

	private int dimensione;
	private Cella[][] griglia;
	private int numCaselle;
	private int caselleSegnate;
	private Blocco bloccoEVincoli;
	private Random r=new Random();
	private int scelta=0;
	
	public Griglia(Cella[][] griglia, int dimensione) {
		caselleSegnate=0;	
		if(griglia.length != dimensione) {
			throw new IllegalArgumentException("Cella non valida");	
		}
		this.griglia=new Cella[this.dimensione][this.dimensione];
		
		
		for(int i=0;i<griglia.length;++i) {
			for(int j=0; j<griglia[i].length ; ++j) {
				if(griglia[i][j].getValore()<0 || griglia[i][j].getValore()> dimensione)
					throw new IllegalArgumentException();
				this.griglia[i][j]=new Cella(i, j, griglia[i][j].getValore());
				if(this.griglia[i][j].getValore()!=0)
					caselleSegnate++;
			}
		}
		
		this.dimensione=dimensione;
		numCaselle=this.dimensione*this.dimensione;
		this.bloccoEVincoli=new Blocco(dimensione);
		try {
		aggiungiBlocchiEVincoli();
		}catch (Exception e) {
			System.out.println("Nessuna aggiunta");
		}
	}
	
	public Griglia(int[][] g,int dimensione, Blocco bEv ) {
		if(g.length!=dimensione)
			throw new IllegalArgumentException("Parametri non validi");
		this.dimensione=dimensione;
		this.caselleSegnate=0;
		this.numCaselle=dimensione*dimensione;
		
		griglia=new Cella[dimensione][dimensione];
		
		for(int i=0;i<griglia.length;++i) {
			for(int j=0; j<griglia[i].length ; ++j) {
				if(g[i][j]<0 || g[i][j]> dimensione)
					throw new IllegalArgumentException();
				this.griglia[i][j]=new Cella(i, j, g[i][j]);
				if(this.griglia[i][j].getValore()!=0)
					caselleSegnate++;
			}
		}
		this.bloccoEVincoli=bEv;
	}
	
	public Griglia(int dimensione, boolean nuovaCasuale) {
		this.dimensione=dimensione;
		this.numCaselle=dimensione*dimensione;
		this.caselleSegnate=0;
		this.bloccoEVincoli=new Blocco(dimensione);
		
		this.griglia=new Cella[dimensione][dimensione];
		
		for(int i=0;i< dimensione;++i)
			for(int j=0;j<dimensione;++j)
				this.griglia[i][j]=new Cella(i, j, 0);
		
		if(nuovaCasuale) {
			try {
				aggiungiBlocchiEVincoli();
			}catch (Exception e) {
				System.out.println("Nessuna aggiunta");
			}
		}
	}
	
	
	private void aggiungiBlocchiEVincoli() throws IOException {
		ArrayList<Blocco> blocchiGriglia=new ArrayList<Blocco>();
		StringBuilder sb=new StringBuilder();
		File f;
		switch (this.dimensione){
		case 4: f=new File("C:\\ingSoftware\\ProvaKenken\\inputGriglie\\kenken4.txt"); break;
		case 5: f=new File("C:\\ingSoftware\\ProvaKenken\\inputGriglie\\kenken5.txt"); break;
		default: f=new File("C:\\ingSoftware\\ProvaKenken\\inputGriglie\\kenken3.txt"); break;
		}
		
		BufferedReader br=new BufferedReader(new FileReader(f));
		try {
			for(;;) {
				String linea=br.readLine();
				if(linea.equals("-")) {
					Blocco b=new Blocco(this.dimensione);
					b.aggiungiBlocchi(sb.toString());
					blocchiGriglia.add(b);
					sb=new StringBuilder();
					linea=br.readLine();
				}
				if( linea==null )
					break;
				sb.append(linea+" ");
			}
		}catch( Exception e ) {
			System.out.println("Errore. Nessuna lettura1.");
		}finally {
			br.close();
		}

		switch (this.dimensione){
		case 4: f=new File("C:\\ingSoftware\\ProvaKenken\\inputGriglie\\kenken4V.txt"); break;
		case 5: f=new File("C:\\ingSoftware\\ProvaKenken\\inputGriglie\\kenken5V.txt"); break;
		default: f=new File("C:\\ingSoftware\\ProvaKenken\\inputGriglie\\kenken3V.txt"); break;
		}
		
		br=new BufferedReader(new FileReader(f));
		int k=0;
		try {
			for(;;) {
				String linea=br.readLine();
				if(linea.equals("-")) {
					k++;
					linea=br.readLine();
				}
				if( linea==null )
					break;
				blocchiGriglia.get(k).aggiungiVincolo(linea);
			}
		}catch( Exception e ) {
			System.out.println("Errore. Nessuna lettura.");
		}finally {
			br.close();
		}
		
		scelta=r.nextInt(blocchiGriglia.size());
		bloccoEVincoli.setBlocchi(blocchiGriglia.get(scelta).getBlocchi());
		bloccoEVincoli.setVincoli(blocchiGriglia.get(scelta).getVincoliGriglia());
	}
	
	public void setCasella(int r, int c, int val) {
		if(r < 0 || r > dimensione || c < 0 || c > dimensione)
			throw new IllegalArgumentException("Cella non valida");
		if(val < 0 || val > dimensione)
			throw new IllegalArgumentException("Valore non valido");
		
		if(this.griglia[r][c].getValore()==0 && val !=0)
			caselleSegnate++;
		else if (this.griglia[r][c].getValore()!=0 && val==0)
				caselleSegnate--;
		this.griglia[r][c].setValore(val);
	}
	
	
	
	public int getCaselleSegnate() {
		return caselleSegnate;
	}
	
	public int getDimensione() {
		return dimensione;
	}
	
	public int getValore(int r, int c) {
		return griglia[r][c].getValore();
	}
	
	public int getNumCaselle() {
		return numCaselle;
	}
	
	public Blocco getBlocchi() {
		return bloccoEVincoli;
	}
	
	
	public Cella[][] getGriglia(){
		Cella[][] ris=new Cella[dimensione][dimensione];
		for(int i=0;i<dimensione;++i)
			for(int j=0;j<dimensione;++j)
				ris[i][j]=new Cella(griglia[i][j]);
		return ris;
	}
	
	
	public boolean isValida() {
		for(int i=0;i<dimensione;++i)
			for(int j=0;j<dimensione;++j)
				if(!confrontaRigheEColonne(i, j, griglia[i][j].getValore()))
					return false;
		return true;
	}
	
	public ArrayList<Cella> notValide() {
		ArrayList<Cella> nonValide=new ArrayList<>();
		for(int i=0;i<dimensione;++i)
			for(int j=0;j<dimensione;++j)
				if(!confrontaRigheEColonne(j, i, griglia[i][j].getValore()))
					nonValide.add(griglia[i][j]);
		return nonValide;
	}
	
	public ArrayList<Cella> nonRispettanoIVincoli() {
		ArrayList<Cella> nonRispettano=new ArrayList<>();
		
		ArrayList<VincoliOperazioni> vincoli=this.bloccoEVincoli.getVincoliGriglia();
		for(VincoliOperazioni v: vincoli) {
			char gruppo=v.getGruppo();
			String operatoreVincolo=v.getOperatore();
			int ris=v.getValore();
			ArrayList<Cella> nuoveCelleVerificate=rispettoVincolo(gruppo, operatoreVincolo, ris);
			if(!(nuoveCelleVerificate==null))
				nonRispettano.addAll(nuoveCelleVerificate);
		}
		return nonRispettano;
		
	}
		
		
	private ArrayList<Cella> rispettoVincolo(char gruppo, String operatore, int ris){
		boolean rispettato=false;
		ArrayList<Cella> celleDiUnGruppo=new ArrayList<>();
		char[][] blocchi=this.bloccoEVincoli.getBlocchi();
		for(int i=0;i<dimensione;++i)
			for(int j=0;j<dimensione;++j)
				if(blocchi[i][j]==gruppo)
					celleDiUnGruppo.add(griglia[i][j]);
		switch (operatore) {
		case "addizione": if(add(gruppo)==ris) rispettato=true; break;
		case "sottrazione": if(sott(gruppo, ris)==ris) rispettato=true; break;
		case "moltiplicazione": if(mul(gruppo)==ris) rispettato=true; break;
		case "divisione": if(div(gruppo, ris)==ris) rispettato=true; break;
		default: if(vuoto(gruppo)==ris) rispettato=true; break;
		}
		
		if(rispettato)
			return null;
		else
			return celleDiUnGruppo;
		
				
	}
	
	private boolean confrontaRigheEColonne(int r, int c, int val) {
		if(val==0)
			return true;
		for(int i=0;i<dimensione;++i)
			if(i!=c && griglia[r][i].getValore()==val)
				return false;
		
		for(int i=0;i<dimensione;++i)
			if(r!=i && griglia[i][c].getValore()==val)
				return false;
		
		return true;
	}
	
	public boolean eCorretta() {
		if(!(this.isValida()))
			return false;
		for(int i=0;i<dimensione;++i)
			for(int j=0;j<dimensione;++j)
				if(griglia[i][j].getValore()==0)
					return false;
		ArrayList<VincoliOperazioni> vincoli=bloccoEVincoli.getVincoliGriglia();
		
		for(VincoliOperazioni v: vincoli) {
			char gruppo=v.getGruppo();
			String operatoreVincolo=v.getOperatore();
			int ris=v.getValore();
			switch (operatoreVincolo) {
			case "addizione": if(!(add(gruppo)==ris)) return false; break;
			case "sottrazione": if(!(sott(gruppo, ris)==ris)) return false; break;
			case "moltiplicazione": if(!(mul(gruppo)==ris)) return false; break;
			case "divisione": if(!(div(gruppo, ris)==ris)) return false; break;
			default: if(!(vuoto(gruppo)==ris)) return false; break;
			}
		}
		return true;
	}
	
	
	private int add(char gruppo) {
		char[][] blocchi=bloccoEVincoli.getBlocchi();
		int val=0;
		for(int i=0;i<dimensione;++i)
			for(int j=0;j<dimensione;++j)
				if(blocchi[i][j]==gruppo)
					val=val+griglia[i][j].getValore();
		return val;
	}
	
	private int mul(char gruppo) {
		char[][] blocchi=bloccoEVincoli.getBlocchi();
		int val=1;
		for(int i=0;i<dimensione;++i)
			for(int j=0;j<dimensione;++j)
				if(blocchi[i][j]==gruppo)
					val=val*griglia[i][j].getValore();
		return val;
	}
	
	private int div(char gruppo, int ris) {
		char[][] blocchi=bloccoEVincoli.getBlocchi();
		int val=0, k=0;
		
		int[] operandi=new int[2];
		for(int i=0;i<dimensione;++i) {
			for(int j=0;j<dimensione;++j)
				if(blocchi[i][j]==gruppo) {
					operandi[k]=griglia[i][j].getValore();
					k++;
					if(operandi[1]!=0)
						break;
				}
			if(operandi[1]!=0)
				break;
		}
		
		int add1=operandi[0];
		int add2=operandi[1];
		if((add1/add2)==ris)
			val=add1/add2;
		else if((add2/add1)==ris)
			val=add2/add1;
		else
			return 0;
		return val;
		
	}

	private int sott(char gruppo, int ris) {
		char[][] blocchi=bloccoEVincoli.getBlocchi();
		int val=0, k=0;
		
		int[] operandi=new int[2];
		for(int i=0;i<dimensione;++i) {
			for(int j=0;j<dimensione;++j)
				if(blocchi[i][j]==gruppo) {
					operandi[k]=griglia[i][j].getValore();
					k++;
					if(operandi[1]!=0)
						break;
				}
			if(operandi[1]!=0)
				break;
		}
		
		int add1=operandi[0];
		int add2=operandi[1];
		if((add1-add2)==ris)
			val=add1-add2;
		else if((add2-add1)==ris)
			val=add2-add1;
		else
			return 0;
		return val;
	}
	
	private int vuoto(char gruppo) {
		char[][] blocchi=bloccoEVincoli.getBlocchi();
		for(int i=0;i<dimensione;++i)
			for(int j=0;j<dimensione;++j)
				if(blocchi[i][j]==gruppo)
					return griglia[i][j].getValore();
		return 0;
	}
	
	
	public void salva() {
		char[][] b=bloccoEVincoli.getBlocchi();
		ArrayList<VincoliOperazioni> v=bloccoEVincoli.getVincoliGriglia();
		
		try {
			PrintWriter pw=new PrintWriter(new FileWriter("C:\\ingSoftware\\ProvaKenken\\salvataggio\\salva.txt"));
			pw.println(dimensione);
			pw.println("-");
			for(int i=0;i<dimensione;++i) {
				for(int j=0;j<dimensione;++j)
					pw.print(griglia[i][j].getValore()+" ");
			}
			pw.print("\n");
			pw.println("-");
			for(int i=0;i<dimensione;++i) {
				for(int j=0;j<dimensione;++j)
					pw.print(b[i][j]+" ");
			}
			pw.print("\n");
			pw.println("-");
			for(VincoliOperazioni vincol: v)
				pw.print(vincol.getGruppo()+" "+vincol.getValore()+" "+vincol.getOperatore()+";");
			pw.print("\n");
			pw.println("-");
			pw.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void ripristina() {
		this.caselleSegnate=0;
		this.numCaselle=0;
		int count=0;
		int k=0;
		
		ArrayList<VincoliOperazioni> v;
		boolean avanti=true;
		String linea="";
		try {
			BufferedReader br=new BufferedReader(new FileReader("C:\\ingSoftware\\ProvaKenken\\salvataggio\\salva.txt"));
			while(avanti) {
				linea=br.readLine();
				if(linea.equals("-")) {
					count++;
					linea=br.readLine();
				}
				if(linea==null) {
					avanti=false;
					break;
				}
				switch (count) {
				case 1: {
					k=0;
					griglia=new Cella[dimensione][dimensione];
					String [] val=linea.split("\\s");
					for(int i=0;i<dimensione;++i)
						for(int j=0;j<dimensione;++j) {
							griglia[i][j]=new Cella(i, j, Integer.parseInt(val[k++]));
							if(griglia[i][j].getValore()!=0)
								this.caselleSegnate++;
							}
				} break;
				case 2: {
					k=0;
					char [][] b=new char [dimensione][dimensione];
					String [] val=linea.split("\\s");
					for(int i=0;i<dimensione;++i)
						for(int j=0;j<dimensione;++j)
							b[i][j]=val[k++].charAt(0);
					this.bloccoEVincoli.setBlocchi(b);
				} break;
				case 3: {
					k=0;
					String tk="";
					v=new ArrayList<>();
					StringTokenizer st=new StringTokenizer(linea,";");
					while(st.hasMoreTokens()) {
						tk=st.nextToken();
						VincoliOperazioni vi=new VincoliOperazioni(tk);
						v.add(vi);
					}
					this.bloccoEVincoli.setVincoli(v);
				} break;
				default: this.dimensione=Integer.parseInt(linea.trim()); break;
				}
			}
			br.close();
			this.numCaselle=dimensione*dimensione;
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getDimRipristino() {
		int dim=0;
		String linea="";
		try {
			BufferedReader br=new BufferedReader(new FileReader("C:\\ingSoftware\\ProvaKenken\\salvataggio\\salva.txt"));
			linea=br.readLine();
			dim=Integer.parseInt(linea.trim());
			br.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return dim;
	}
	
	
	public boolean equals(Object obj) {
		if(obj==null)
			return false;
		if(!(obj instanceof Griglia))
			return false;
		if(obj==this)
			return true;
		Griglia grid=(Griglia) obj;
		if(this.dimensione != grid.dimensione || this.caselleSegnate != grid.caselleSegnate)
			return false;
		
		for(int i = 0; i< dimensione; ++i)
			for(int j=0; j< dimensione; ++j)
				if(!griglia[i][j].equals(grid.griglia[i][j]))
					return false;
		return true;
	}


	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder(300);
		sb.append("Grigla \n");
		for(int i=0;i< dimensione ;++i) {
			for(int j=0; j<dimensione; ++j)
				sb.append(" "+griglia[i][j].getValore()+" ");
			sb.append("\n");
		}
		
		sb.append("\n");
		sb.append(bloccoEVincoli.toString());
		
		return sb.toString();
	}
	
	
}
