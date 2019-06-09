package it.polito.tdp.model;

import com.javadocmd.simplelatlng.LatLng;

public class Agente {
	private int velocitÓ=60;
	private boolean disponibile;
	private LatLng posizione;
	//private EventsDao dao= new EventsDao();
	private Model m= new Model();
	
	public int getVelocitÓ() {
		return velocitÓ;
	}
	public void setVelocitÓ(int vel) {
		this.velocitÓ = vel;
	}
	public boolean isDisponibile() {
		return disponibile;
	}
	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}
	public LatLng getPosizione() {
		return posizione;
	}
	public void setPosizione(LatLng posizione) {
		this.posizione = posizione;
	}
	public Agente() {
		
		this.disponibile = true;
		this.posizione = m.getCentrale();
	}
	
	

}
