package myjava.app;

import java.util.*;
import java.util.List;

public final class Chord {

	private Integer posFonda;

	private int time = 2;
	private Note fondamentale;
	private Quality quality;
	private Note basse;
	private int multi;
	private boolean played = true;

	private static char SEPARATE_INT = ':';
	private static char SEPARATE_BASSE = '/';
	private static char SEPARATE_MULTI = 'X';
	private static char DIEZE = '#';
	private static char BEMOL = 'b';

	public Chord(int time, Note fondamentale, Quality quality, Note basse) {
		super();
		this.time = time;
		this.fondamentale = fondamentale;
		this.quality = quality;
		this.basse = basse;
	}

	public Chord(String strChord) {
		super();
		// parseToChord(strChord);
	}

	public Chord() {
		super();
	}

	public int getTime() {
		return this.time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Note getFondamentale() {
		return this.fondamentale;
	}

	public void setFondamentale(Note fondamentale) {
		this.fondamentale = fondamentale;
	}

	public Quality getQuality() {
		return this.quality;
	}

	public void setQuality(Quality quality) {
		this.quality = quality;
	}

	public Note getBasse() {
		return this.basse;
	}

	public void setBasse(Note basse) {
		this.basse = basse;
	}

	public int getMulti() {
		return this.multi;
	}

	public void setMulti(int multi) {
		this.multi = multi;
	}

	public boolean isPlayed() {
		return this.played;
	}

	public void setPlayed(boolean played) {
		this.played = played;
	}

	public List<Note> chordToComponents() {

		// on récupère les chordToValues
		List<Integer> values = chordToValues();
		List<Note> componentsOfChord = values.stream().map(c -> Note.valToNote(c)).toList();

		return componentsOfChord;
		// ***************************************************
	}

	public List<Integer> chordToValues() {
		List<Integer> values = new ArrayList<>();
		String myQualiString = this.getQuality().getQualityName();
		Note root = this.getFondamentale();
		Integer myQualiIndex = Quality.listeQualitiesS().indexOf(myQualiString);
		Integer valRoot = Note.noteToVal(root);
		String stringValues = Quality.listeQualitiesT().get(myQualiIndex);
		for (String lulu : stringValues.split(":")) {
			values.add(Integer.parseInt(lulu) + valRoot);
		}
		return values;
	}

	private String parseTimerFromChord(String chaine) {
		String chaineRetour = "";
		Integer posSepInt = chaine.indexOf(SEPARATE_INT);
		if (posSepInt >= 0) {
			this.setTime(Integer.parseInt(chaine.substring(0, posSepInt)));
			chaineRetour = chaine.substring(posSepInt + 1);
		} else {
			this.setTime(4);
			return chaine;
		}
		return chaineRetour;
	}

	private String parseMultiFromChord(String chaine) {
		String chaineRetour = "";
		int posSepMulti = chaine.indexOf(SEPARATE_MULTI);
		if (posSepMulti != -1) {
			this.setMulti(Integer.parseInt(chaine.substring(posSepMulti + 1)));
			chaineRetour = chaine.substring(0, posSepMulti);
		} else {
			this.setMulti(1);
			return chaine;
		}
		return chaineRetour;
	}

	public Chord parseToChord(String chaine) {

		// CAS : ., ..
		if (chaine.length() == 1) { // cas A B C D E F G
			setFondamentale(Note.stringToNote(chaine));
			setTime(4);
			setMulti(1);
			setBasse(getFondamentale());
			setQuality(new Quality(" "));
			return this;
		} else if (chaine.length() == 2) { // CAS : Ab, C#
			setTime(4);
			setMulti(1);
			if (chaine.indexOf(BEMOL) == 1 || chaine.indexOf(DIEZE) == 1) {
				setFondamentale(Note.stringToNote(chaine));
				setBasse(getFondamentale());
				setQuality(new Quality(" "));
				return this;
			} else { // CAS Am ou C9
				setFondamentale(Note.stringToNote(String.valueOf(chaine.charAt(0))));
				setQuality(new Quality(String.valueOf(chaine.charAt(1))));
				chaine = String.valueOf(chaine.charAt(0));
				setBasse(getFondamentale());
				return this;
			}
		}

		// *********************************************************$

		chaine = parseTimerFromChord(chaine);
		chaine = parseMultiFromChord(chaine);

		if (chaine.indexOf(DIEZE) == 1 || chaine.indexOf(BEMOL) == 1) {
			setFondamentale(Note.stringToNote(chaine.substring(0, 2)));
			chaine = chaine.substring(2);
		} else {
			setFondamentale(Note.stringToNote(String.valueOf(chaine.charAt(0))));
			chaine = chaine.substring(1);
		}

		if (chaine.indexOf(SEPARATE_BASSE) >= 0) {
			setBasse(Note.stringToNote(chaine.substring(chaine.indexOf(SEPARATE_BASSE) + 1)));
			chaine = chaine.substring(0, chaine.indexOf(SEPARATE_BASSE));
		} else {
			setBasse(getFondamentale());
		}

		if (chaine.length() > 0) {
			setQuality(new Quality(chaine));
		} else
			setQuality(new Quality(" "));

		return this;
	}

	@Override
	public String toString() {
		return getTime() + ":" + getFondamentale().getName() + getQuality().getQualityName() + "/" + getBasse();
	}

}
