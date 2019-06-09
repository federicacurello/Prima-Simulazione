package it.polito.tdp.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.javadocmd.simplelatlng.LatLng;

public class Evento implements Comparable<Evento> {
	private double geo_lon;
	private double geo_lat;
	private Integer district_id;
	private LocalDateTime reported_date;
	private String offense_category_id;
	private int durata;
	private LatLng posizione;
	private TipoEvento tipo;
	
	public enum TipoEvento{
		MANDA_AGENTE,
		//ARRIVA_AGENTE,
		LIBERA_AGENTE
		
	}
	public Evento(TipoEvento ti, LocalDateTime ora) {
		tipo=ti;
		reported_date= ora;
	}

	public Evento(double geo_lon, double geo_lat, Integer district_id, LocalDateTime localDateTime,
			String offense_category_id, TipoEvento t) {
		this.geo_lon = geo_lon;
		this.geo_lat = geo_lat;
		this.district_id = district_id;
		this.reported_date = localDateTime;
		this.offense_category_id = offense_category_id;
		this.setDurata(offense_category_id);
		this.posizione= new LatLng(geo_lat, geo_lon);
		setTipo(t);
	}

	public double getGeo_lon() {
		return geo_lon;
	}

	public void setGeo_lon(double geo_lon) {
		this.geo_lon = geo_lon;
	}

	public double getGeo_lat() {
		return geo_lat;
	}

	public void setGeo_lat(double geo_lat) {
		this.geo_lat = geo_lat;
	}

	public Integer getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(Integer district_id) {
		this.district_id = district_id;
	}

	public LocalDateTime getReported_date() {
		return reported_date;
	}

	public void setReported_date(LocalDateTime reported_date) {
		this.reported_date = reported_date;
	}

	public String getOffense_category_id() {
		return offense_category_id;
	}

	public void setOffense_category_id(String offense_category_id) {
		this.offense_category_id = offense_category_id;
	}

	public int getDurata() {
		return durata;
	}

	public LatLng getPosizione() {
		return posizione;
	}

	public void setPosizione(LatLng posizione) {
		this.posizione = posizione;
	}

	public void setDurata(String offense_category_id) {
		if(offense_category_id.equals("all_other_crimes")) {
			durata= (Math.random() <= 0.5) ? 1 : 2;
		}
		else
			durata=2; //h
	}

	@Override
	public int compareTo(Evento altro) {
		
		return this.reported_date.compareTo(this.reported_date);
	}

	public TipoEvento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}
	
}
