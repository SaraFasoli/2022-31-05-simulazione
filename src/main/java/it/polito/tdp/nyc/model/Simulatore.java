package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.nyc.model.Event.EventType;

public class Simulatore {
	private Map<String,HotspotQuartiere> hotspotQuartiere;
	private Graph<String,DefaultWeightedEdge> grafo;
	private List<String> daVisitare; //quartieri ancora da visitare 
	
	//coda eventi
	private PriorityQueue<Event> queue;
	
	//parametri
	int tecniciTotali;
	int tecniciDisp;
	int vMedia=50;
	String quartierePartenza;
	
	//output 
	private int durataTotale;
	private Map<Integer,Integer> hotspotPerTecnico; 
	
	
	public Simulatore(Graph<String, DefaultWeightedEdge> grafo) {
		hotspotQuartiere=new HashMap<>();
		this.grafo=grafo;
		hotspotPerTecnico=new HashMap<>();
		daVisitare=new ArrayList<>();
		
	}	
	
	public void init(int ntecnici, List<HotspotQuartiere> hotspotQ, String qPartenza) {
		this.queue=new PriorityQueue<>();
		for(HotspotQuartiere hq: hotspotQ) {
			this.hotspotQuartiere.put(hq.getQuartiere(), hq);
		}
		tecniciTotali=ntecnici;
		tecniciDisp=ntecnici;
		quartierePartenza=qPartenza;
		for(int i=0;i<tecniciTotali;i++) {
			hotspotPerTecnico.put(i,0);
		}
	
		for(HotspotQuartiere hq: hotspotQ) {
			daVisitare.add(hq.getQuartiere());
		}
		daVisitare.remove(qPartenza);
	}
	
	public void caricaEventi(String qPartenza,int time) {
		for(int i=0; i<tecniciTotali;i++) {
			//bohif(hotspot.size()) {
				Event e=new Event(Event.EventType.INIZIO_REV,time,qPartenza,i);
				tecniciDisp--;
				hotspotQuartiere.get(e.getQuartiere()).riduciHotspot();
				this.queue.add(e);
				
			}
	}
		
	
	public void run() {
  		while(!queue.isEmpty()) {
  			Event e=queue.poll();//prendo e rimuovo evento in coda
  			System.out.println(e.getTime()+" "+e.getQuartiere()+" "+e.getType()+" "+e.getTecnico());
  			durataTotale=e.getTime();
  			processEvent(e);
  			
  		}
  		
  	}
	private void processEvent(Event e) {
		int timev=e.getTime();
		EventType type=e.getType();
		int tecnico=e.getTecnico();
		String quartiere=e.getQuartiere();
		
		// TODO Auto-generated method stub
		switch(type) {
  		case INIZIO_REV:
  			
			int time=10;
		    double random=Math.random();
			if(random<=0.1) {
					time+=15;
			}
			
  			hotspotPerTecnico.put(tecnico, hotspotPerTecnico.get(tecnico)+1);
			
  			Event e1=new Event(EventType.FINE_REV,timev+time,e.getQuartiere(),tecnico);
  			this.queue.add(e1);
  			
  			break;
  			
  		case FINE_REV:
  			tecniciDisp++;
  			if(hotspotQuartiere.get(quartiere).getNumHotspot()>0) {
  				int time1=10;
  			    double random1=Math.random();
  			    time1+=((int)random1*11);
  			  Event e2=new Event(EventType.INIZIO_REV,timev+time1,quartiere,tecnico);
				tecniciDisp--;
				hotspotQuartiere.get(quartiere).riduciHotspot();
				
				this.queue.add(e2);
				//System.out.println("hotspotrim:"+hotspotQuartiere.get(quartiere).getNumHotspot());
			    //System.out.println("tecnici disp:"+tecniciDisp);
			}else if(tecniciDisp==tecniciTotali && daVisitare.size()>0) {//devo controllare non solo che num_hotspo_darev pari a0
				                                                         //ma anche che tecnicidisp==tutti
				                                                         //trovo edge min e passo al quartiere vicino
				
				String quartiereVicino=trovaQuartierePiuVicino(quartiere,daVisitare);
				this.quartierePartenza=quartiereVicino;
				
				DefaultWeightedEdge ee =this.grafo.getEdge(quartiere, quartiereVicino);
				int tempo=(int)(this.grafo.getEdgeWeight(ee)/vMedia*60);
				daVisitare.remove(quartierePartenza);
				
				
				caricaEventi(quartierePartenza,timev+tempo);
			}
			break;
	
		}
	}

	private String trovaQuartierePiuVicino(String quartiere,List<String> daVisit) {
		// TODO Auto-generated method stub
	    double min=10000.0;
	    String quartiereVicino=null;
	    
		double peso=0;
		for(DefaultWeightedEdge e: this.grafo.edgesOf(quartiere)) {
			peso=this.grafo.getEdgeWeight(e);
			String q=Graphs.getOppositeVertex(this.grafo, e, quartiere);
			
			if(peso<min && daVisit.contains(q) ) {
				min=peso;
				quartiereVicino=q;
			}
		}
		return quartiereVicino;
	}

	public int getDurataTotale() {
		return durataTotale;
	}

	public Map<Integer, Integer> getHotspotPerTecnico() {
		return hotspotPerTecnico;
	}
	

}
