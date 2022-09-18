package myjava.app;

import java.util.Scanner;

/**
 * @author Eric Bruneau
 *
 */
public class Application {

	public static int count = 0;
	private static char LINE = '-';
		
	

	/**
	 * méthode qui écrit sur la ligne et incrémente un compteur utilité : pouvoir
	 * aligner un certain nombre d'objets sur une ligne et prévoir un retour à la
	 * ligne au bout de n fois
	 * 
	 * @param objectToStringOut
	 * @return count incremented
	 */
	public static Integer prt(Object objectToStringOut) {
		System.out.print((String) String.valueOf(objectToStringOut));
		count++;
		return count;
	}

	/**
	 * Affiche une ligne en sortie de console
	 */
	public static void printLigne() {
		prtln(LINE);
	}
	/**
	 * @param objectToStringOut
	 */
	public static void prtln(Object objectToStringOut) {
		if (
				( (String) String.valueOf(objectToStringOut) ).contentEquals(String.valueOf(LINE))) {
			System.out.println("\n--------------------------------------------------------");
			return;
		}
		System.out.println((String) String.valueOf(objectToStringOut));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		prtln("Entrez un accords ou une suite d'accords...");

		prtln(LINE);
		Scanner quest = new Scanner(System.in);
		Grille grille = new Grille();
		Chord receptacle;

		String[] rep = ((String[]) quest.nextLine().split(" "));

		for (String elem : rep) {
			Chord accord = new Chord();
			accord = Harmonie.parseToChord(elem);
			for (int j = 0; j < accord.getMulti(); j++) {
				receptacle = Harmonie.parseToChord(elem);
				grille.addChord(receptacle);
			}
		}

		prtln("------------------------------------------------------");
		prtln("1. Jouer les accords proposés en paramètre");
		prtln("2. Quitter le programme");

		switch (quest.nextInt()) {
		case 1:
			quest.close();

			PlayerMidi pl = new PlayerMidi();

			grille.setTempo(180);

			grille.getContenu().forEach(acc -> {
				if (prt(acc.toString()) % 4 == 0) {
					prtln(LINE);
				} else {
					prt("  \t");
					count--;
				}
			});

			pl.injectSeq(grille);
			pl.injectBasse(grille);

			printLigne();
			prt(" Affiche contenu de la grille ");
			printLigne();
			grille.getContenu().forEach(element -> {
				prtln("Accord :: " + element.toString());
					prt(" transposition(1) :: " + (element.transpose(1)).toString() + " ");
					prtln("getSeconde() :: " + element.getSecondInChord().getName() + " getQuinte() :: "
							+ element.getQuinte().toString()+" "
							+ "getTierce() :: "+element.getTierce().getName()+" "
							);
				
				prtln(LINE);

			});

			pl.play();

			prtln("merci au revoir");
			System.exit(0);
			break;
		case 2:
			prtln("Merci et à bientôt!!!");
			System.exit(0);
			quest.close();
			break;

		default:
			quest.close();
			throw new IllegalArgumentException("Unexpected value: " + quest.nextInt());
		}
		quest.close();
	}

	/**
	 * @param c
	 */
	public static void affiche(Chord c) {
		prtln("****************************************************************");
		prtln("*************************   " + c + "   **************************");
		prtln("****************************************************************");
		prtln(c.toString());
		prtln("timer: " + c.getTime());
		prtln("Fondamentale: " + c.getFondamentale().getName());
		prtln("Quality: " + c.getQuality().getQualityName());
		prtln("Basse: " + c.getBasse().getName());
		prtln("Multiplicateur: " + c.getMulti());
		prtln("");

	}

}
