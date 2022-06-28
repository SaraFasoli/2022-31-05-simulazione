package it.polito.tdp.nyc.model;

public class Arco {
	private String city1;
	private String city2;
	
	private double lat1;
	private double long1;
	private double lat2;
	private double long2;
	public Arco(String city1, String city2, double lat1, double long1, double lat2, double long2) {
		super();
		this.city1 = city1;
		this.city2 = city2;
		this.lat1 = lat1;
		this.long1 = long1;
		this.lat2 = lat2;
		this.long2 = long2;
	}
	public String getCity1() {
		return city1;
	}
	public void setCity1(String city1) {
		this.city1 = city1;
	}
	public String getCity2() {
		return city2;
	}
	public void setCity2(String city2) {
		this.city2 = city2;
	}
	public double getLat1() {
		return lat1;
	}
	public void setLat1(double lat1) {
		this.lat1 = lat1;
	}
	public double getLong1() {
		return long1;
	}
	public void setLong1(double long1) {
		this.long1 = long1;
	}
	public double getLat2() {
		return lat2;
	}
	public void setLat2(double lat2) {
		this.lat2 = lat2;
	}
	public double getLong2() {
		return long2;
	}
	public void setLong2(double long2) {
		this.long2 = long2;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city1 == null) ? 0 : city1.hashCode());
		result = prime * result + ((city2 == null) ? 0 : city2.hashCode());
		long temp;
		temp = Double.doubleToLongBits(lat1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lat2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(long1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(long2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Arco other = (Arco) obj;
		if (city1 == null) {
			if (other.city1 != null)
				return false;
		} else if (!city1.equals(other.city1))
			return false;
		if (city2 == null) {
			if (other.city2 != null)
				return false;
		} else if (!city2.equals(other.city2))
			return false;
		if (Double.doubleToLongBits(lat1) != Double.doubleToLongBits(other.lat1))
			return false;
		if (Double.doubleToLongBits(lat2) != Double.doubleToLongBits(other.lat2))
			return false;
		if (Double.doubleToLongBits(long1) != Double.doubleToLongBits(other.long1))
			return false;
		if (Double.doubleToLongBits(long2) != Double.doubleToLongBits(other.long2))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Arco [city1=" + city1 + ", city2=" + city2 + ", lat1=" + lat1 + ", long1=" + long1 + ", lat2=" + lat2
				+ ", long2=" + long2 + "]";
	}
	
	





}
