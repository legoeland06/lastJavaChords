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
	 * @return
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
