package myjava.app;

import java.util.ArrayList;
import java.util.List;

public class Note {

	private String name;
	private int value;
	private static final String[][] NOTE_VAL_DICT = { { "A", "9" }, { "A#", "10" }, { "Bb", "10" }, { "Cb", "11" },
			{ "B", "11" }, { "C", "0" }, { "Db", "1" }, { "C#", "1" }, { "D", "2" }, { "D#", "3" }, { "Eb", "3" },
			{ "E", "4" }, { "F", "5" }, { "Gb", "6" }, { "F#", "6" }, { "G", "7" }, { "G#", "8" }, { "Ab", "8" }, };
	private static final String[][] NOTE_BEMOL_DIEZE = { { "A#", "10" }, { "Bb", "10" }, { "Cb", "11" }, { "Db", "1" },
			{ "C#", "1" }, { "D#", "3" }, { "Eb", "3" }, { "Gb", "6" }, { "F#", "6" }, { "G#", "8" }, { "Ab", "8" }, };

	public Note() {
		super();
	}

	public Note(String name) {
		super();
		this.name = name;
		this.setValue(0);
	}

	public Note(int value) {
		super();
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static Note stringToNote(String s) {
		if (!Note.existeOrNo(s)) {
			System.out.println("Probleme de notes: " + s);
			System.exit(0);
		}
		return new Note(s);
	}

	public static Note charToNote(char s) {
		if (!Note.existeOrNo(String.valueOf(s))) {
			System.out.println("Probleme de notes: " + s);
			System.exit(0);
		}
		return new Note(String.valueOf(s));
	}

	public static String noteToString(Note n) {
		return n.name;
	}

	public static Integer noteToVal(Note n) {

		Integer sortie = 0;
		for (String[] laval : NOTE_VAL_DICT) {
			if (laval[0].contentEquals(n.getName())) {
				sortie = Integer.parseInt(laval[1]);
				break;
			}
		}
		return sortie;
	}

	public static Note valToNote(int v) {
		Integer moduloNote = (v + 1) % 12;
		String lanote = String.valueOf(moduloNote);
		String victor = "";
		for (String[] strings : NOTE_VAL_DICT) {
			if (strings[1].equals(lanote)) {
				victor = strings[0];
				continue;
			}
		}
		return Note.stringToNote(victor);
	}

	public static Boolean existeOrNo(String note) {
		boolean existe = false;
		for (String[] strings : NOTE_VAL_DICT) {
			if (strings[0].indexOf(note) != -1) {
				existe = true;
			}
		}
		return existe;
	}

	public static List<Note> getNoteValDictByBemolDieze() {
		List<Note> receptacle = new ArrayList<>();
		for (String[] note : NOTE_BEMOL_DIEZE) {
			receptacle.add(Note.stringToNote(note[0]));

		}
		for (String[] note : NOTE_VAL_DICT) {
			receptacle.add(Note.stringToNote(note[0]));
		}
		return receptacle;
	}

	public static Note getNext(Chord c, Note n) {
		return valToNote(noteToVal(n) + 1);
	}

	public static Note getPrev(Chord c, Note n) {
		return valToNote(noteToVal(n) - 1);
	}

	public static Note getSecond(Note n) {
		return valToNote(noteToVal(n) + 2);
	}

	public static Note getQuinte(Chord c, Note n) {

		String qualy = c.getQuality().getQualityName();
		if (qualy.contains("m") || qualy.contains("M"))
			return valToNote(noteToVal(n) + 7);
		else if (qualy.contains("aug") || qualy.contains("#5"))
			return valToNote(noteToVal(n) + 8);
		else if (qualy.contains("dim") || qualy.contains("b5"))
			return valToNote(noteToVal(n) + 6);
		return valToNote(noteToVal(n) + 7);
	}

	@Override
	public String toString() {
		return name;
	}

}
