package myjava.app;

import java.util.ArrayList;
import java.util.List;

public class Grille {
	private List<Chord> contenu = new ArrayList<>();
	private int tempo;

	public int getTempo() {
		return this.tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public List<Chord> getContenu() {
		return this.contenu;
	}

	public void setContenu(List<Chord> contenu) {
		this.contenu = contenu;
	}

	public Grille() {
		super();
	}
	
	public boolean add(Chord c) {
		return this.contenu.add(c);
	}
	
}
