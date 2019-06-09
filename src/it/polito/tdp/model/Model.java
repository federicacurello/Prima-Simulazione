package it.polito.tdp.model;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.db.EventsDao;

public class Model {
	private EventsDao dao;
	private SimpleWeightedGraph<District, DefaultWeightedEdge> grafo;
	Map<Integer, LatLng> centri= new HashMap<Integer, LatLng>();
	LatLng centrale;
	
	public Model() {
		
		this.dao = new EventsDao();
	}

	public List<Integer> getAnni(){
		
		return dao.anniEventi();
	}
	
	public void creaGrafo(int anno) {
		 grafo = new SimpleWeightedGraph<District, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		 for(int i=1; i<8; i++) {
			 District d = new District(i);
			 dao.calcolaCentro(d, anno);
			 centri.put(i, d.getCentro());
			 grafo.addVertex(d);
			// System.out.println(d.getCentro());
			 
		 }
		 
		 for( District d: grafo.vertexSet()) {
			 for(District d2: grafo.vertexSet()) {
				if(!d.equals(d2) && !grafo.containsEdge(d, d2)) {
				 Graphs.addEdgeWithVertices(grafo, d, d2, calcolaDistanza(d, d2));
				
				 
				}
			 }
		 }
		 //System.out.println(grafo);
		 
	}
	
	public List<District> trovaVicini(District d){
		return Graphs.neighborListOf(grafo, d);
	}
	
	public String listaVicini(District d) {
		
			List<Vicini> vicini = new LinkedList<Vicini>();
			for(District d2: trovaVicini(d)) {
				Vicini v= new Vicini(d, d2, calcolaDistanza(d,d2));
				vicini.add(v);
			}
			Collections.sort(vicini);
			return vicini.toString();
		
		}
	public String trovaTuttiVicini() {
		String s="";
		for(District d:grafo.vertexSet()) {
			s+="\nLista dei vicini per il distretto "+d.toString()+" (ordinata con distanza crescente)\n"+listaVicini(d).toString();
			
		}
		return s;
		
	}
	
	
	public double calcolaDistanza(District d1, District d2) {
		double distanza = LatLngTool.distance(d1.getCentro(), d2.getCentro(), LengthUnit.KILOMETER);
		//System.out.println("distanza tra "+ d1.getId()+" e "+d2.getId()+" : "+distanza);
		return distanza;
		
	}
	public double getWeight(DefaultWeightedEdge e) {
		
		return grafo.getEdgeWeight(e);
	}

	public SimpleWeightedGraph<District, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	public List<Integer> getMesi() {
		List<Integer> mesi= new LinkedList<Integer>();
		for(int m=1; m<13; m++) {
			mesi.add(m);
		}
		return mesi;
	}
	public List<Integer> getGiorni() {
		List<Integer> giorni= new LinkedList<Integer>();
		for(int m=1; m<=31; m++) {
			giorni.add(m);
		}
		return giorni;
	}
	public void trovaCentrale(int anno) {
		centrale= centri.get(dao.trovaDistretto(anno));
	}

	public LatLng getCentrale() {
		return centrale;
	}

	public void setCentrale(LatLng centrale) {
		this.centrale = centrale;
	}
		
	
	
}
