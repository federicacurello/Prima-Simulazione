package it.polito.tdp.model;


import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.db.EventsDao;
import it.polito.tdp.model.Evento.TipoEvento;

public class Simulatore {
	
private PriorityQueue<Evento> queue = new PriorityQueue<Evento>() ;
private EventsDao dao;
private List<Agente> agentiLista = new ArrayList<Agente>() ;
private int distanzaMin= Integer.MAX_VALUE;
private Map<Evento, Agente> associa= new HashMap<Evento, Agente>();
private Model m= new Model();
	
	// Stato del mondo
	private int agentiCentrale ;
	
	// Parametri di simulazione
	
		private Agente agenteScelto;
		
		// Statistiche raccolte
		private int eventiMalGestiti ;
		
		public Simulatore() {
			
		}
		
		public void init(int anno, int mese, int giorno, int agenti) {
			this.agentiCentrale = agenti ;
			this.eventiMalGestiti = 0 ;
			dao=new EventsDao();
			//dao.trovaDistretto(anno);
			dao.avvieneEvento(anno, mese, giorno);
			m.trovaCentrale(anno);
			
			this.queue.clear();
			
			// carico gli eventi iniziali:
			// arriva un cliente ogni intervalloArrivoCliente
			// a partire dalle oraInizio
			
			  for(Evento e : dao.getEventi()) {
				 queue.add(e) ;
			}
			 while(agentiCentrale>0){
				  Agente a= new Agente();
				  agentiLista.add(a);
				  agentiCentrale--;
			  }
		}
		public void run() {
			
			while(!queue.isEmpty()) {
				
				Evento ev = queue.poll() ;
				System.out.println(ev) ;
				
				;
				
				switch(ev.getTipo()) {
				
				case MANDA_AGENTE:
					for(Agente a :agentiLista) {
					if(distanzaAgente(a,ev) <distanzaMin && a.isDisponibile()==true) {
						agenteScelto= a;
						
					}
				}
				agenteScelto.setDisponibile(false);
				agenteScelto.setPosizione(ev.getPosizione());
				double tempoArrivo= (distanzaAgente(agenteScelto, ev))/3600; //min
				if(tempoArrivo<=15 ) {
					LocalDateTime ora= ev.getReported_date().plusMinutes((int)tempoArrivo);
					queue.add(new Evento(TipoEvento.LIBERA_AGENTE, ora.plusMinutes(ev.getDurata()) ));
						
					}
				else{
					eventiMalGestiti++;
				}
					break;
					
				case LIBERA_AGENTE:
					Agente ag= associa.get(ev);
					ag.setDisponibile(true);
					break;
				
				}
				
			}
			
		}
		public double distanzaAgente(Agente a, Evento ev) {
			double distanza = LatLngTool.distance(a.getPosizione(), ev.getPosizione(), LengthUnit.KILOMETER);
			//System.out.println("distanza tra "+ a.getPosizione()+" e "+ev.getPosizione()+" : "+distanza);
			return distanza;
		}

		public int getEventiMalGestiti() {
			return eventiMalGestiti;
		}

		public void setEventiMalGestiti(int eventiMalGestiti) {
			this.eventiMalGestiti = eventiMalGestiti;
		}


}
