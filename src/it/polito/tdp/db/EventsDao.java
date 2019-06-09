package it.polito.tdp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.model.District;
import it.polito.tdp.model.Event;
import it.polito.tdp.model.Evento;
import it.polito.tdp.model.Evento.TipoEvento;


public class EventsDao {
	Map<Integer, Integer> map= new HashMap<Integer, Integer>();
	int min;
	int idMin;
	List<Evento> eventi= new ArrayList<Evento>();
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> anniEventi(){
		String sql = "SELECT DISTINCT reported_date " + 
				"FROM EVENTS "+
				"ORDER BY reported_date ASC";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					if(!list.contains(res.getTimestamp("reported_date").toLocalDateTime().getYear())) {
					list.add((
							res.getTimestamp("reported_date").toLocalDateTime().getYear()));
					}
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		
	}
	
	public void calcolaCentro(District d, int anno) {
		String sql = "SELECT AVG(geo_lat) AS mlat, AVG(geo_lon) AS mlon, reported_date " + 
				"FROM EVENTS " + 
				"WHERE YEAR(reported_date)=? AND district_id=? "; 
				
		try {
			Connection conn = DBConnect.getConnection() ;
			
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setInt(2, d.getId());
			
			//List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
						
						d.setCentro(new LatLng(res.getDouble("mlat"), res.getDouble("mlon")));
					
					
					
				} catch (Throwable t) {
					t.printStackTrace();
					//System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	public int trovaDistretto(int anno) {
		min= Integer.MAX_VALUE;
		idMin=0;
		String sql = "SELECT district_id AS id, COUNT(*) AS cnt " + 
				"FROM EVENTS " + 
				"WHERE YEAR(reported_date) = ? " + 
				"GROUP BY district_id "; 
				
		try {
			Connection conn = DBConnect.getConnection() ;
			
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			
			
			//List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
						int id= res.getInt("id");
						int cnt= res.getInt("cnt");
						map.put(id, cnt);
					
					
					
				} catch (Throwable t) {
					t.printStackTrace();
					//System.out.println(res.getInt("id"));
				}
			}conn.close();
			for(Integer i: map.keySet()) {
				if( map.get(i)< min) {
					min= map.get(i);
					idMin= i;
			}}
			return idMin;
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
				return 0;
		}
	
	}
	public void avvieneEvento(int anno, int mese, int giorno) {
		String sql = "SELECT district_id, reported_date AS t, geo_lat, geo_lon, offense_category_id " + 
				"FROM EVENTS " + 
				"WHERE YEAR(reported_date) = ? AND MONTH(reported_date)=? AND DAY(reported_date)=? " + 
				"ORDER BY reported_date"; 
				
		try {
			Connection conn = DBConnect.getConnection() ;
			
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setInt(2, mese);
			st.setInt(3, giorno);
			
			
			
			//List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Evento e= new Evento(res.getDouble("geo_lon"), res.getDouble("geo_lat"), res.getInt("district_id"), res.getTimestamp("t").toLocalDateTime(),
								res.getString("offense_category_id"), TipoEvento.MANDA_AGENTE);
					eventi.add(e);
					
				} catch (Throwable t) {
					t.printStackTrace();
					//System.out.println(res.getInt("id"));
				}
			}conn.close();
			
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
				
		}
	}

	public List<Evento> getEventi() {
		return eventi;
	}
	

}
