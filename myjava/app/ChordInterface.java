package myjava.app;

public interface ChordInterface {

	Note createFondamental(String c);

	Note createTierceMinor(Note note);

	Note createTierceMajor(Note note);

	Note createQuinte(Note note);

	Note createQuinteDim(Note note);

	Note createQuinteAug(Note note);

	Note createSeptieme(Note note);

	Note createSeptiemeMajeur(Note note);

	Note createNeuvieme(Note note);

}