package it.polito.tdp.model;

public class Vicini implements Comparable<Vicini> {
	District d1;
	District d2;
	double distanza;
	Model m;
	public Vicini(District d1, District d2, double distanza) {
		this.d1 = d1;
		this.d2 = d2;
		m= new Model();
		this.distanza= distanza;
		
	}
	@Override
	public int compareTo(Vicini altro) {
		
		return (int)( this.distanza-altro.distanza );
	}
	@Override
	public String toString() {
		return String.format(" [%s, distanza=%s]", d2, distanza);
	}
	public District getD1() {
		return d1;
	}
	public void setD1(District d1) {
		this.d1 = d1;
	}
	public District getD2() {
		return d2;
	}
	public void setD2(District d2) {
		this.d2 = d2;
	}
	public double getDistanza() {
		return distanza;
	}
	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}
	
	
	
}
