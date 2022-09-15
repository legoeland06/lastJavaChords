package myjava.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCreation;

/**
 * @author ericbruneau
 *
 */
public class Grille implements Transposable<Grille>{

	private List<Chord> contenu = new ArrayList<>();
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
	public Grille() {
		super();
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
		this.contenu.add(c);
	}

	/**
	 * @return liste du contenu de la grille
	 */
	public List<Chord> getContenu() {
		return this.contenu;
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
	
	@Override
	public String toString() {

		Arrays.toString(contenu.toArray());
		contenu.forEach((c) -> System.out.println(c));
		return null;
	}

	/**
	 * @param index
	 * @return l'accord qui à l'index dans la grille
	 */
	public Chord get(Integer index) {
		if (index < contenu.size())
			return contenu.get(index);
		else
			return contenu.get(index % contenu.size());
	}

	@Override
	public Grille transpose(Integer t) {
		List<Chord> newContenu = this.contenu.stream().map(c->c.transpose(t)).toList();
		this.setContenu(newContenu);
		return this;
	}

}