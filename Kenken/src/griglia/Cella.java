package griglia;

public class Cella {
		private int riga, colonna, valore;

		public Cella(int riga, int colonna, int valore) {
			this.riga = riga;
			this.colonna = colonna;
			this.valore=valore;
		}
		
		public Cella(Cella c) {
			this.colonna=c.colonna;
			this.riga=c.riga;
			this.valore=c.valore;
		}

		public int getValore() {
			return this.valore;
		}
		
		public int getRiga() {
			return this.riga;
		}

		public int getColonna() {
			return this.colonna;
		}
		
		public void setValore(int val) {
			this.valore=val;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + colonna;
			result = prime * result + riga;
			result = prime * result + valore;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Cella)) {
				return false;
			}
			Cella other = (Cella) obj;
			if (colonna != other.colonna) {
				return false;
			}
			if (riga != other.riga) {
				return false;
			}
			if (valore != other.valore) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return " "+valore;
		}
		
		
		
		
}
