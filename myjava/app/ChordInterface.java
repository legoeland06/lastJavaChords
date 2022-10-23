package myjava.app;

public interface ChordInterface {

	Note createFondamentale(String noteLambda);
	Note setToFondamentale(Note noteLambda);
	Note setToFondamental(String noteLambda);
	
	Note createTierceMinor(Note noteFondamentale);
	Note setToTierce(Note noteLambda);
	
	Note createTierceMajor(Note noteFondamentale);
	
	Note createQuinte(Note noteFondamentale);
	Note setToQuinte(Note noteLambda);
	
	Note createQuinteDim(Note noteFondamentale);
	Note createQuinteAug(Note noteFondamentale);
	
	Note createSeptieme(Note noteFondamentale);
	Note setToSeptieme(Note noteLambda);
	
	Note createSeptiemeMajeur(Note noteFondamentale);
	Note createSeptiemeMineur(Note noteFondamentale);
	
	Note createNeuvieme(Note noteFondamentale);
	Note setToNeuvieme(Note noteLambda);
	
}