package myjava.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ericbruneau
 *
 */
public enum Grille implements Transposable<Grille>{
	
	/**
	 * 
	 */
	INSTANCE;

	private List<Chord> contenu;;
	private Map<Integer,Chord> contenuMap ;
	private long tempo;
	private String title;
	
	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title=title;
	}
	
	/**
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * constructeur
	 */
	private Grille() {
	}

	public void initMap(Map<Integer,Chord> newMap) {
		this.contenuMap = new HashMap<Integer,Chord>();
		this.contenuMap.putAll(newMap);
		
		this.contenu = new ArrayList<Chord>();
		this.contenu.addAll(newMap.values().stream().toList());
	}
	/**
	 * @return tempo
	 */
	public long getTempo() {
		return this.tempo;
	}

	/**
	 * @param tempo
	 */
	public void setTempo(long tempo) {
		this.tempo = tempo;
	}

	/**
	 * @param c
	 * ajoute un accord à la grille
	 */
	public void addChord(Chord c) {
		c.setIndex(this.contenuMap.size());
		this.contenu.add(c);
		this.contenuMap.put(this.contenuMap.size(), c);
	}

	/**
	 * @param c
	 */
	public void removeChord(Chord c) {
		this.contenu.remove(c.getIndex());
	}
	
	/**
	 * @param c
	 * @param index
	 */
	public void insertChord(Chord c, int index) {
		c.setIndex(index);
		this.contenu.add(index, c);
	}
	/**
	 * @return liste du contenu de la grille
	 */
	public List<Chord> getContenu() {
		return this.contenu;
	}
	
	/**
	 * @return
	 */
	public Map<Integer,Chord> getContenuMap(){
		return this.contenuMap;
	}

	/**
	 * @param contenu
	 * assigne une liste à la grille en une fois
	 * valable pour importer un fichier
	 * par exemple
	 * 
	 */
	public void setContenu(List<Chord> contenu) {
		this.contenu=contenu;
	}
	
	/**
	 * @param c
	 */
	public void setContenuMap(Map<Integer,Chord> c) {
		this.contenuMap=c;
	}
	
	@Override
	public String toString() {
		
		//Arrays.toString(this.contenu.toArray());
		this.contenu.forEach((c) -> System.out.println(c));
		this.contenuMap.forEach((c,d)->System.out.println(c+" "+d));
		return "";
	}

	/**
	 * @param index
	 * @return l'accord qui à l'index dans la grille
	 */
	public Chord get(Integer index) {
		if (index < this.contenu.size())
			return this.contenu.get(index);
		else
			return this.contenu.get(index % this.contenu.size());
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Chord getChordFromGrille(Integer index) {
		if (index<this.contenuMap.size()) {
			return this.contenuMap.get(index);
		}
		else return null;
	}
	

	@Override
	public Grille transpose(Integer t) {
		List<Chord> newContenu = this.contenu.stream().map(c->c.transpose(t)).toList();
		this.setContenu(newContenu);
		
		this.contenuMap.forEach((c,v)->v.transpose(t));
		return this;
	}
	

}