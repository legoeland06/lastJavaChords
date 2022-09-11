package myjava.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCreation;

public class Grille {

	private List<Chord> contenu = new ArrayList<>();
	private long tempo;
	private String title;
	private String author;
	private DateTimeAtCreation dateCreation;
	
	public void setTitle(String title) {
		this.title=title;
	}
	
	public String getTitle() {
		return this.title;
	}

	public Grille() {
		super();
	}

	public long getTempo() {
		return this.tempo;
	}

	public void setTempo(long tempo) {
		this.tempo = tempo;
	}

	public void addChord(Chord c) {
		this.contenu.add(c);
	}

	public List<Chord> getContenu() {
		return this.contenu;
	}

	public void setContenu(List<Chord> contenu) {
		this.contenu=contenu;
	}
	@Override
	public String toString() {

		Arrays.toString(contenu.toArray());
		contenu.forEach((c) -> System.out.println(c));
		return null;
	}

	public Chord get(Integer index) {
		if (index < contenu.size())
			return contenu.get(index);
		else
			return contenu.get(index % contenu.size());
	}

}