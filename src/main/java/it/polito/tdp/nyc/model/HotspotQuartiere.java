package it.polito.tdp.nyc.model;

public class HotspotQuartiere {
	String quartiere;
	int numHotspot;
	public HotspotQuartiere(String quartiere, int numHotspot) {
		super();
		this.quartiere = quartiere;
		this.numHotspot = numHotspot;
	}
	public String getQuartiere() {
		return quartiere;
	}
	public void setQuartiere(String quartiere) {
		this.quartiere = quartiere;
	}
	public int getNumHotspot() {
		return numHotspot;
	}
	public void riduciHotspot() {
		this.numHotspot --;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numHotspot;
		result = prime * result + ((quartiere == null) ? 0 : quartiere.hashCode());
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
		HotspotQuartiere other = (HotspotQuartiere) obj;
		if (numHotspot != other.numHotspot)
			return false;
		if (quartiere == null) {
			if (other.quartiere != null)
				return false;
		} else if (!quartiere.equals(other.quartiere))
			return false;
		return true;
	}

}
