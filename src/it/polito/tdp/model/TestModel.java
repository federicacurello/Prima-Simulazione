package it.polito.tdp.model;

import it.polito.tdp.db.EventsDao;

public class TestModel {

	public static void main(String[] args) {
		Model model=new Model();
		model.creaGrafo(2015);
		Simulatore sim= new Simulatore();
		//System.out.println(model.trovaTuttiVicini());
		//EventsDao dao= new EventsDao();
		//System.out.println(dao.trovaDistretto(2015));
		sim.init(2015,1, 30, 4);
		sim.run();
		System.out.print(sim.getEventiMalGestiti());
	}

}
