package myjava.app;

public class Note {

	private int victoire=0;
	private String name;
	private int value;
	private static String[][] NOTE_VAL_DICT = { { "A", "9" }, { "A#", "10" }, { "Bb", "10" }, { "Cb", "11" },
			{ "B", "11" }, { "C", "0" }, { "Db", "1" }, { "C#", "1" }, { "D", "2" }, { "D#", "3" }, { "Eb", "3" },
			{ "E", "4" }, { "F", "5" }, { "Gb", "6" }, { "F#", "6" }, { "G", "7" }, { "G#", "8" }, { "Ab", "8" }, };

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

	public Note(String name) {
		super();
		this.name = name;
		this.setValue(0/* TODO: midi value of this note */);
	}

	public Note(int value) {
		super();
		this.value = value;
		// TODO : this.name = .value..;
	}

	public static Note stringToNote(String s) {
		return new Note(s);
	}

	public static String noteToString(Note n) {
		return n.name;
	}

	public Integer noteToVal() {

		for (String[] laval : NOTE_VAL_DICT) {
			if (laval[0].contentEquals(this.getName())) {
				victoire = Integer.parseInt(laval[1]);
			}
		}

		return victoire;
	}
	public static Note valToNote(int v) {
		Integer moduloNote = (v + 1) % 12;
		String lanote = Integer.toString(moduloNote);
		String victor = "";
		for (String[] strings : NOTE_VAL_DICT) {
			if (strings[1].indexOf(lanote) != -1) {
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

}
