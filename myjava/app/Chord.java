package myjava.app;

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
	private int indexInGrille;

	private String chaine = "";
	private List<Note> notes;

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
	 * @return
	 */
	public Chord update() {
		if (this.getNotes()==null || this.getNotes().size()==0) {
			this.setNotes(Harmonie.chordToComponents(this));
		}
		return this;
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

	/**
	 * @param chaine est la chaine témoin de la construction de l'accord
	 */
	public void setChaine(String chaine) {
		this.chaine = chaine;
	}

	/**
	 * @return chaine est la chaine témoin de la construction de l'accord
	 */
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
		else
			return this.getFondamentale().transpose(4);
	}

	/**
	 * @return
	 */
	public Note getSeptieme() {
		if (this.isMinor())
			return this.getFondamentale().transpose(10);
		else if (this.isSeptieme())
			return this.getFondamentale().transpose(10);
		else if (this.isMajor())
			return this.getFondamentale().transpose(11);
		else
			return this.getFondamentale().transpose(10);
	}

	/**
	 * @return Note seconde of Chord
	 */
	public Note getSecondFromFondamental() {
		
		return (this.getFondamentale()).transpose(2);
	}

	/**
	 * @return true if this chord is minor, false if this chord is not minor
	 */
	public boolean isMinor() {

		return this.getQuality().getQualityName().contains("m");
	}

	/**
	 * @return true if this chord is Major
	 */
	public boolean isMajor() {
		return this.getQuality().getQualityName().contains("M");
	}

	/**
	 * @return true if this chord is 7th
	 */
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

	/**
	 * @return
	 */
	public int getIndexInGrille() {
		return this.indexInGrille;
	}

	/**
	 * @param indexInGrille
	 */
	public void setIndexInGrille(int indexInGrille) {
		this.indexInGrille = indexInGrille;
	}

	/**
	 * @return
	 */
	public List<Note> getNotes() {
		return this.notes;
	}

	/**
	 * @return
	 */
	public int getIndex() {
		return this.indexInGrille;
	}

	/**
	 * @param index
	 */
	public void setIndex(int index) {
		this.indexInGrille = index;
	}

	/**
	 * @param grille
	 */
	public void addToGrille(Grille grille) {
		grille.addChord(this);
	}

	/**
	 * @param grille
	 */
	public void removeFromGrille(Grille grille) {
		grille.removeChord(this);
	}

	@Override
	public Chord clone() {
		return new Chord();
	}

	@Override
	public String toString() {
		return this.time + ":" + this.fondamentale.getName() + this.quality.getQualityName() + "/" + this.basse.getName();
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

	/**
	 * @param notes
	 */
	public void setNotes(List<Note> notes) {
		this.notes=notes;
		
		
	}

}
