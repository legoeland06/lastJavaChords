package myjava.app;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ericbruneau
 *
 */
public enum ChordFactory implements ChordInterface {
	/**
	 * 
	 */
	INSTANCE;
	
	private ChordFactory() {
		
	}
	
	private List<Note> notes = new ArrayList<>();
	private Chord chord = new Chord();

	@Override
	public Note createFondamental(String c) {
		this.notes.add(Harmonie.stringToNote(c));
		return Harmonie.stringToNote(c);
	}
	@Override
	public Note createTierceMinor(Note note) {
		this.notes.add(note);
		return note.transpose(3);
	}
	
	@Override
	public Note createTierceMajor(Note note) {
		this.notes.add(note);
		return note.transpose(4);
	}
	@Override
	public Note createQuinte(Note note) {
		this.notes.add(note);
		return note.transpose(7);
	}
	@Override
	public Note createQuinteDim(Note note) {
		this.notes.add(note);
		return note.transpose(6);
	}
	@Override
	public Note createQuinteAug(Note note) {
		this.notes.add(note);
		return note.transpose(8);
	}
	@Override
	public Note createSeptieme(Note note) {
		this.notes.add(note);
		return note.transpose(10);
	}
	@Override
	public Note createSeptiemeMajeur(Note note) {
		this.notes.add(note);
		return note.transpose(11);
	}
	@Override
	public Note createNeuvieme(Note note) {
		this.notes.add(note);
		return note.transpose(14);
	}
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	public Chord getChord() {
		return chord;
	}
	public void setChord(Chord chord) {
		this.chord = chord;
	}
	

	
}
