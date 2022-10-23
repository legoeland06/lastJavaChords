package myjava.app;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ericbruneau
 *
 */
public enum ChordFactory implements ChordInterface {
	/**
	 * Singleton
	 */
	INSTANCE;

	private ChordFactory() {

	}

	private List<Note> notes = new ArrayList<>();
	private Chord chord = new Chord();

	
	private Note createNote(String noteName,int nature) {
		Note note= Harmonie.stringToNote(noteName);
		note.setNature(nature);
		return note;
	}
	
	/**
	 * @return notes
	 */
	public List<Note> getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	/**
	 * @return chord
	 */
	public Chord getChord() {
		return chord;
	}

	/**
	 * @param chord
	 */
	public void setChord(Chord chord) {
		this.chord = chord;
	}
	
	
	@Override
	public Note createFondamentale(String c) {
		Note note = this.createNote(c,Note.listNature.FONDAMENTALE.ordinal());
		this.chord.getNotes().add(note);
		return note;
	}
	
	@Override
	public Note setToFondamentale(Note noteLambda) {
		noteLambda.setNature(Note.listNature.FONDAMENTALE.ordinal());
		return noteLambda;
	}

	@Override
	public Note createTierceMinor(Note noteFondamentale) {
		noteFondamentale.setNature(Note.listNature.TIERCE.ordinal());
		Note resultat=noteFondamentale.transpose(3);
		this.notes.add(resultat);
		return resultat;
	}

	@Override
	public Note createTierceMajor(Note noteFondamentale) {
		noteFondamentale.setNature(Note.listNature.TIERCE.ordinal());
		this.notes.add(noteFondamentale.transpose(4));
		return noteFondamentale.transpose(4);
	}

	@Override
	public Note createQuinte(Note noteFondamentale) {
		noteFondamentale.setNature(Note.listNature.QUINTE.ordinal());
		this.notes.add(noteFondamentale.transpose(7));
		return noteFondamentale.transpose(7);
	}

	@Override
	public Note createQuinteDim(Note noteFondamentale) {
		noteFondamentale.setNature(Note.listNature.QUINTE.ordinal());
		this.notes.add(noteFondamentale.transpose(6));
		return noteFondamentale.transpose(6);
	}

	@Override
	public Note createQuinteAug(Note noteFondamentale) {
		noteFondamentale.setNature(Note.listNature.QUINTE.ordinal());
		this.notes.add(noteFondamentale.transpose(8));
		return noteFondamentale.transpose(8);
	}

	@Override
	public Note createSeptieme(Note noteFondamentale) {
		noteFondamentale.setNature(Note.listNature.SEPTIEME.ordinal());
		this.notes.add(noteFondamentale.transpose(10));
		return noteFondamentale.transpose(10);
	}

	@Override
	public Note createSeptiemeMineur(Note noteFondamentale) {
		return this.createSeptieme(noteFondamentale);
	}

	@Override
	public Note createSeptiemeMajeur(Note noteFondamentale) {
		noteFondamentale.setNature(Note.listNature.SEPTIEME.ordinal());
		this.notes.add(noteFondamentale.transpose(11));
		return noteFondamentale.transpose(11);
	}

	@Override
	public Note createNeuvieme(Note noteFondamentale) {
		noteFondamentale.setNature(Note.listNature.NEUVIEME.ordinal());
		this.notes.add(noteFondamentale.transpose(14));
		return noteFondamentale.transpose(14);
	}

	
	@Override
	public Note setToTierce(Note note) {
		note.setNature(Note.listNature.FONDAMENTALE.ordinal());
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Note setToFondamental(String noteLambda) {
		Note resultat=new Note(noteLambda);
		resultat.setNature(Note.listNature.FONDAMENTALE.ordinal());
		return resultat;
	}

	@Override
	public Note setToQuinte(Note noteLambda) {
		noteLambda.setNature(Note.listNature.QUINTE.ordinal());
		return noteLambda;
	}

	@Override
	public Note setToSeptieme(Note noteLambda) {
		noteLambda.setNature(Note.listNature.SEPTIEME.ordinal());
		return noteLambda;
	}
	
	@Override
	public Note setToNeuvieme(Note noteLambda) {
		noteLambda.setNature(Note.listNature.NEUVIEME.ordinal());
		return noteLambda;
	}

}
