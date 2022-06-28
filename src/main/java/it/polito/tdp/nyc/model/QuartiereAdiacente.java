package it.polito.tdp.nyc.model;


public class QuartiereAdiacente implements Comparable<QuartiereAdiacente> {

		private String vicino;
		private double distanza;
		public QuartiereAdiacente(String vicino, double distanza) {
			super();
			this.vicino = vicino;
			this.distanza = distanza;
		}
		public String getVicino() {
			return vicino;
		}
		public void setVicino(String vicino) {
			this.vicino = vicino;
		}
		public double getDistanza() {
			return distanza;
		}
		public void setDistanza(double distanza) {
			this.distanza = distanza;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(distanza);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + ((vicino == null) ? 0 : vicino.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			QuartiereAdiacente other = (QuartiereAdiacente) obj;
			if (Double.doubleToLongBits(distanza) != Double.doubleToLongBits(other.distanza))
				return false;
			if (vicino == null) {
				if (other.vicino != null)
					return false;
			} else if (!vicino.equals(other.vicino))
				return false;
			return true;
		}
		@Override
		public int compareTo(QuartiereAdiacente o) {
			// TODO Auto-generated method stub
			return (int)(this.getDistanza()-o.getDistanza());
		}
		


}
