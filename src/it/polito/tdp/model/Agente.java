package it.polito.tdp.model;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.db.EventsDao;

public class Agente {
	private int velocità=60;
	private boolean disponibile;
	private LatLng posizione;
	//private EventsDao dao= new EventsDao();
	private Model m= new Model();
	
	public int getVelocità() {
		return velocità;
	}
	public void setVelocità(int velocità) {
		this.velocità = velocità;
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
