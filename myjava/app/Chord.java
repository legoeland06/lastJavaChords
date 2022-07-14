package myjava.app;

import java.util.ArrayList;
import java.util.List;

public class Chord {

	private int time = 2;
	private Note fondamentale = new Note();
	private Quality quality = new Quality();
	private Note basse = new Note();
	private int multi=1;
	private boolean played=true;
	
	public void setPlayed(boolean p) {
		this.played = p;
	}

	public boolean getPlayed() {
		return this.played;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public int getMulti() {
		return multi;
	}

	public void setMulti(int multi) {
		this.multi = multi;
	}

	public Note getFondamentale() {
		return fondamentale;
	}

	public void setFondamentale(Note fondamentale) {
		this.fondamentale = fondamentale;
	}

	public Quality getQuality() {
		return quality;
	}

	public void setQuality(Quality quality) {
		this.quality = quality;
	}

	public Note getBasse() {
		return basse;
	}

	public void setBasse(Note basse) {
		this.basse = basse;
	}

	public Chord(int time, Note fondamentale, Quality quality, Note basse) {
		super();
		this.time = time;
		this.fondamentale = fondamentale;
		this.quality = quality;
		this.basse = basse;
	}

	public Chord(String strChord) {
		super();
		this.parseToChord(strChord);
	}

	public Chord() {
		super();
	}
	
	public List<Note> chordToComponents() {

		List<Note> componentsOfChord = new ArrayList<>();
		String bonneListe = "";
		Note root = this.fondamentale;
		Integer valRoot = root.noteToVal();

		for (String[] maliste : Quality.listeQualities()) {
			if (maliste[0].contentEquals(this.quality.toString())) {
				bonneListe = maliste[1];
			}
		}

		// ***************************************************
		String[] listeTraitement = bonneListe.split(":");
		Integer i = 0;
		for (String valeurNoteString : listeTraitement) {
			Integer ValeurNote = Integer.parseInt(valeurNoteString) + valRoot;
			componentsOfChord.add(Note.valToNote(ValeurNote - 1));
			i++;
		}

		return componentsOfChord;
		// ***************************************************
	}
	public List<Integer> chordToValues() {

		List<Integer> componentsOfValues = new ArrayList<>();
		String bonneListe = "";
		Note root = this.fondamentale;
		Integer valRoot = root.noteToVal();

		for (String[] maliste : Quality.listeQualities()) {
			if (maliste[0].contentEquals(this.quality.getQualityName())) {
				bonneListe = maliste[1];
				System.out.println("maliste: "+maliste[0]+" "+maliste[1]);
			}
		}
		String[] listeTraitement = bonneListe.split(":");
		Integer i = 0;
		for (String valeurNoteString : listeTraitement) {
			Integer ValeurNote = Integer.parseInt(valeurNoteString) + valRoot;
			if (ValeurNote < 5) {
				ValeurNote += 12;
			}
			componentsOfValues.add(ValeurNote - 1);
			i++;
		}
		System.out.println("componentsOfValues: "+componentsOfValues);
		return componentsOfValues;
	}

	public Chord parseToChord(String c) {
		String AccordSansTime = c.substring(2); // Ebm7b5/G
		String _quality;
		int positionDuSlash;
		int longBasse;

		if (c.indexOf(":") == -1) {
			System.out.println("Accord de la forme  4:Cm7b5/D ");
			System.exit(0);
		}

		if (AccordSansTime.indexOf("/") != -1) {
			positionDuSlash = AccordSansTime.indexOf("/"); // positionDuSlash = 6
		} else
			positionDuSlash = 0;

		Note basse=new Note();
		String noteFondamentale;

		int longAccordSansTime = AccordSansTime.length(); // longAccordSansTime = 6

		int _time = Integer.parseInt(c.substring(0, 1)); // _time = 4
		this.setTime(_time);

		if (AccordSansTime.length() > 1) {
			if (((AccordSansTime.substring(0, 2)).indexOf("#") != -1)
					|| ((AccordSansTime.substring(0, 2)).indexOf("b") != -1)) {
				noteFondamentale = AccordSansTime.substring(0, 2); // noteFondamentale = Eb ou F#
			} else {
				noteFondamentale = AccordSansTime.substring(0, 1); // noteFondamentale = E
			}

			if (!Note.existeOrNo(noteFondamentale)) {
				System.out.println("Probleme de notes: " + noteFondamentale);
				System.exit(0);
			}

			int longnoteFondamentale = noteFondamentale.length();

			if (positionDuSlash != 0) {
				basse = Note.stringToNote(AccordSansTime.substring(positionDuSlash + 1, longAccordSansTime));
				longBasse = basse.getName().length();
				_quality = AccordSansTime.substring(longnoteFondamentale, longAccordSansTime - longBasse - 1);
			} else { // pas de basse
				basse = fondamentale;
				longBasse = 0;
				if (longAccordSansTime >= 1) {
					_quality = AccordSansTime.substring(longnoteFondamentale);
				} else
					_quality = " ";
			}

		} else { // accord simple 4:D
			noteFondamentale = AccordSansTime;
			basse = Note.stringToNote(noteFondamentale);
			longBasse = 0;
			_quality = " ";
		}

		//Chord accParse = new Chord();
		setTime(_time);

		setFondamentale(Note.stringToNote(noteFondamentale));

		setQuality((_quality == "") ? new Quality("") : new Quality(_quality));

		setBasse((basse == null ) ? Note.stringToNote(noteFondamentale) : basse );

		return this;
	}

	public String toString() {
		return this.getTime()+":"+this.getFondamentale().getName()+this.getQuality().getQualityName()+"/"+this.getBasse().getName();
	}
	
}
