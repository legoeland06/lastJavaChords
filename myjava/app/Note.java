package myjava.app;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ericbruneau
 *
 */
public class Note implements Transposable<Note> {

	private String name;
	private int value;
	private static final String[][] NOTE_VAL_DICT = { { "A", "9" }, { "A#", "10" }, { "Bb", "10" }, { "Cb", "11" },
			{ "B", "11" }, { "C", "0" }, { "Db", "1" }, { "C#", "1" }, { "D", "2" }, { "D#", "3" }, { "Eb", "3" },
			{ "E", "4" }, { "F", "5" }, { "Gb", "6" }, { "F#", "6" }, { "G", "7" }, { "G#", "8" }, { "Ab", "8" }, };
	private static final String[][] NOTE_BEMOL_DIEZE = { { "A#", "10" }, { "Bb", "10" }, { "Cb", "11" }, { "Db", "1" },
			{ "C#", "1" }, { "D#", "3" }, { "Eb", "3" }, { "Gb", "6" }, { "F#", "6" }, { "G#", "8" }, { "Ab", "8" }, };

	/**
	 * Constructeur vide
	 */
	public Note() {
		super();
	}

	/**
	 * @param name
	 */
	public Note(String name) {
		super();
		this.name = name;
		this.setValue(0);
	}

	/**
	 * @param value
	 */
	public Note(int value) {
		super();
		this.value = value;
	}

	/**
	 * @return StringName of Note Attention getName est propre à this alors que
	 *         noteToString est static et prend une note en parametre ainsi que
	 *         stringToNote qui prend une String en parametre
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return int value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @param s
	 * @return Note from String
	 */
	public static Note stringToNote(String s) {
		if (!Note.existeOrNo(s)) {
			System.out.println("Probleme de notes: " + s);
			System.exit(0);
		}
		return new Note(s);
	}

	/**
	 * @param s
	 * @return Note from char
	 */
	public static Note charToNote(char s) {
		if (!Note.existeOrNo(String.valueOf(s))) {
			System.out.println("Probleme de notes: " + s);
			System.exit(0);
		}
		return new Note(String.valueOf(s));
	}

	/**
	 * @param n
	 * @return StringName of note
	 */
	public static String noteToString(Note n) {
		return n.name;
	}

	/**
	 * @param n
	 * @return Integer
	 */
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

	/**
	 * @param v
	 * @return Note
	 */
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

	/**
	 * @param note
	 * @return Boolean
	 */
	public static Boolean existeOrNo(String note) {
		boolean existe = false;
		for (String[] strings : NOTE_VAL_DICT) {
			if (strings[0].indexOf(note) != -1) {
				existe = true;
			}
		}
		return existe;
	}
	
	/**
	 * @return List<Note> receptacle
	 */
	public static List<Note> getNoteValDict(){
		List<Note> receptacle = new ArrayList<>();
		for (String[] note : NOTE_VAL_DICT) {
			receptacle.add(Note.stringToNote(note[0]));
		}
		return receptacle;
	}

	/**
	 * @return List<Note>
	 */
	public static List<Note> getNoteValDictByBemolDieze() {
		
		List<Note> receptacle = new ArrayList<>();
		
		getNoteValDict().stream().filter(t -> t.getName().length()==2 ).forEach(note->receptacle.add(note));
		getNoteValDict().stream().filter(t -> t.getName().length()==1 ).forEach(note->receptacle.add(note));
		
		/*
		 * receptacle va devenir une liste de Note en commençant par les bémols et les dièzes
		 * et en finissant par les notes simples
		 */
		return receptacle;
	}

	/**
	 * @param c
	 * @param n
	 * @return Note
	 */
	public static Note getNextNoteInChord(Chord c, Note note) {
		List<Note> noteList = c.chordToComponents();
		int noteIndexInList = noteList.indexOf(note);
		Note prevNote = noteList.get(noteList.size()%(noteIndexInList+1));
		return prevNote;
	}

	/**
	 * @param c
	 * @param note
	 * @return prevNote
	 */
	public static Note getPrevNoteInChord(Chord c, Note note) {
		List<Note> noteList = c.chordToComponents();
		int noteIndexInList = noteList.indexOf(note);
		Note prevNote = noteList.get(noteList.size()%(noteIndexInList-1));
		return prevNote;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Note transpose(Integer t) {
		return valToNote(noteToVal(this) + t);
	}

}
