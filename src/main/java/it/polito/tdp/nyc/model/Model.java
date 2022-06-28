package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private Graph<String,DefaultWeightedEdge> grafo;
	private List<String> quartieri;
	private Map<String,String> quartieriMap;
	private NYCDao dao;
	private Simulatore sim;
	
	public Model() {
		quartieri=new ArrayList<>();
		quartieriMap=new HashMap<>();
		dao=new NYCDao();
		
	}
	
	
	public String creaGrafo(String p) {
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		
		
		List<String> quartieri=dao.getVertici(p);
		for(String s: quartieri) {
			quartieriMap.put(s, s);
		}
		Graphs.addAllVertices(this.grafo, quartieri);
		
		List<Arco> archi= dao.getArchi(p);
		for(Arco a: archi) {
			 LatLng q1 = new LatLng(a.getLat1(), a.getLong1());
			 LatLng q2 = new LatLng(a.getLat2(), a.getLong2());
			 double peso=LatLngTool.distance(q1, q2, LengthUnit.KILOMETER);
			 
			 Graphs.addEdge(this.grafo, a.getCity1(), a.getCity2(), peso);
		}
		
		String s="#vertici: "+this.grafo.vertexSet().size()+"\n#archi: "+this.grafo.edgeSet().size()+"\n";
		return s;
}
	
	public List<String> getProvider(){
		return dao.getAllProvider();
	}
	
	
	
	public List<String> getQuartieri(String p) {
		return dao.getVertici(p);
	}


	public List<QuartiereAdiacente> getVicini(String quartiere){
		List<QuartiereAdiacente> vicini=new ArrayList<>();
		
		for(DefaultWeightedEdge e: this.grafo.edgesOf(quartiere)) {
			String vicino=Graphs.getOppositeVertex(this.grafo, e, quartiere);
			double peso=this.grafo.getEdgeWeight(e);
			QuartiereAdiacente q=new QuartiereAdiacente(vicino,peso);
			vicini.add(q);
		}
		Collections.sort(vicini);
		return vicini;
	}
	
	public void simula(int ntecnici,String provider,String qPartenza) {
		sim=new Simulatore(this.grafo);
		List<HotspotQuartiere> hq=dao.getHotspot(provider);
		sim.init(ntecnici, hq, qPartenza);
		sim.caricaEventi(qPartenza, 0);
		sim.run();
	
	}
	
	public int getDurataSimulazione() {
		return sim.getDurataTotale();
	}
	
	public Map<Integer,Integer> getHotspotPerTecnico(){
		return sim.getHotspotPerTecnico();
	}


}
