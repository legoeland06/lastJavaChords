package myjava.app;

import java.util.*;
import java.util.List;

/**
 * @author EricBruneau
 */
public final class Chord implements Transposable<Chord> {

	private int time = 2;
	private Note fondamentale;
	private Quality quality;
	private Note basse;
	private int multi;
	private boolean played = true;

	private String chaine="";
	/**
	 * @param time
	 * @param fondamentale
	 * @param quality
	 * @param basse
	 */
	public Chord(int time, Note fondamentale, Quality quality, Note basse) {

		super();
		this.time = time;
		this.fondamentale = fondamentale;
		this.quality = quality;
		this.basse = basse;
	}

	/**
	 * @param strChord
	 */
	public Chord(String strChord) {
		super();
	}

	/**
	 * Constructeur vide
	 */
	public Chord() {
		super();
	}
	
	public void setChaine(String chaine) {
		this.chaine=chaine;
	}
	
	public String getChaine() {
		return this.chaine;
	}

	/**
	 * @return this.time
	 */
	public int getTime() {
		return this.time;
	}

	/**
	 * @param time
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return Note
	 */
	public Note getFondamentale() {
		return this.fondamentale;
	}

	/**
	 * @param fondamentale
	 */
	public void setFondamentale(Note fondamentale) {
		this.fondamentale = fondamentale;
	}

	/**
	 * @return Quality
	 */
	public Quality getQuality() {
		return this.quality;
	}

	/**
	 * @param quality
	 */
	public void setQuality(Quality quality) {
		this.quality = quality;
	}

	/**
	 * @return Note
	 */
	public Note getBasse() {
		return this.basse;
	}

	/**
	 * @param basse
	 */
	public void setBasse(Note basse) {
		this.basse = basse;
	}

	/**
	 * @return this.multi;
	 */
	public int getMulti() {
		return this.multi;
	}

	/**
	 * @param multi
	 */
	public void setMulti(int multi) {
		this.multi = multi;
	}

	/**
	 * @return boolean
	 */
	public boolean isPlayed() {
		return this.played;
	}

	/**
	 * @param played
	 */
	public void setPlayed(boolean played) {
		this.played = played;
	}

	/**
	 * @return List<Note> componentsOfChord
	 * 
	 *         methode qui renvoi un Objet List<Note> il transforme un accord vers
	 *         une liste d'objets Note
	 */
	

	/**
	 * @return List<Integer> valuesChordIntList
	 *
	 *         méthode qui renvoi un Objet List<Integer>
	 * 
	 *         Transforme un <b>accord</b> précis (ex Cm7b5/D) en <b>StringListe de
	 *         ecartNoteInteger</b> sans prendre en compte la basse
	 * 
	 * @param this : de la forme Chord
	 * 
	 */

	/**
	 * @return Note revois la quinte de l'accord en traitant la fondamentale et en
	 *         testant si l'accord (la quinte) est simple, diminuée ou augmentée
	 */
	public Note getQuinte() {

		if (this.isAugmented())
			return this.getFondamentale().transpose(8);
		if (this.isDimined())
			return this.getFondamentale().transpose(6);
		return this.getFondamentale().transpose(7);

	}
	
	/**
	 * @return Note la tierce mineure ou majeure de l'accord 
	 */
	public Note getTierce() {
		if (this.isMinor()) 
			return this.getFondamentale().transpose(3);
		else return this.getFondamentale().transpose(4);
	}
	
	public Note getSeptieme() {
		if (this.isMinor()) 
			return this.getFondamentale().transpose(10);
		else if (this.isSeptieme())
			return this.getFondamentale().transpose(10);
		else if (this.isMajor()) 
			return this.getFondamentale().transpose(11);
		else return this.getFondamentale().transpose(10); 
	}

	/**
	 * @return Note seconde of Chord
	 */
	public Note getSecondInChord() {
		return (this.getFondamentale()).transpose(2);
	}

	/**
	 * @return true if this chord is minor, false if this chord is not minor
	 */
	public boolean isMinor() {

		return this.getQuality().getQualityName().contains("m");
	}
	
	/**
	 * @return
	 */
	public boolean isMajor() {
		return this.getQuality().getQualityName().contains("M");
	}
	
	public boolean isSeptieme() {
		return this.getQuality().getQualityName().contains("7") && !isMinor();
	}

	/**
	 * @return true if this chord is aug, +5, or #5 (augmented chord) return false
	 *         if not.
	 */
	public boolean isAugmented() {
		return this.getQuality().getQualityName().contains("aug") || this.getQuality().getQualityName().contains("+5")
				|| this.getQuality().getQualityName().contains("#5");
	}

	/**
	 * @return true if this chors is a alteration alt,dim, -5, b5
	 */
	public boolean isDimined() {
		return this.getQuality().getQualityName().contains("alt") || this.getQuality().getQualityName().contains("dim")
				|| this.getQuality().getQualityName().contains("-5")
				|| this.getQuality().getQualityName().contains("b5");
	}

	@Override
	public Chord clone() {
		return new Chord();
	}

	@Override
	public String toString() {
		return getTime() + ":" + getFondamentale().getName() + getQuality().getQualityName() + "/" + getBasse();
	}

	@Override
	public Chord transpose(Integer t) {
		Chord newChord = new Chord();
		newChord.setTime(this.getTime());
		newChord.setMulti(this.getMulti());
		newChord.setQuality(this.getQuality());
		newChord.setBasse(this.getBasse().transpose(t));
		newChord.setFondamentale(this.getFondamentale().transpose(t));
		return newChord;
	}

}
