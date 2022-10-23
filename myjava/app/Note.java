package myjava.app;


/**
 * @author ericbruneau
 *
 */
public class Note implements Transposable<Note> {

	private String name;
	private int nature;
	public static enum listNature{
		FONDAMENTALE,SECOND,TIERCE,QUARTE,QUINTE,SIXTE,SEPTIEME,OCTAVE,NEUVIEME
	}
	private int value;

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

	public int getNature() {
		return nature;
	}

	public void setNature(int nature) {
		this.nature = nature;
	}

	/**
	 * @param value
	 */
	public Note(int value) {
		super();
		this.value = value;
	}

	/**
	 * @return name String of this note
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

	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public Note transpose(Integer t) {
		Note myNote = Harmonie.valToNote(Harmonie.noteToVal(this)+t);
		return myNote;
	}

}
