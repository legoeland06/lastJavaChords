package myjava.app;

import java.util.Scanner;

/**
 * @author Eric Bruneau
 *
 */
public class Application {

	// Consumer<T> pln = new Consumer(u)->System.out.print(u.toString());

	/**
	 * 
	 */
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

//		prtln("Entrez les accords sous la forme 2:Cm7b5/CX4 ou AmX2 ou juste F ... :");
//		prtln("Séparés par des espaces");
		prtln("Entrez un accords ou une suite d'accords...");

//		Chord myAccord = new Chord();
//		myAccord.parseToChord("Cm7b5");
//		myAccord.chordToComponents().forEach(c -> prt(c.toString()));

		prtln(LINE);
		Scanner quest = new Scanner(System.in);
		Grille grille = new Grille();
		Chord receptacle;
		// String repString = "2:BM7 2:D7 2:GM7 2:Bb7 4:EbM7X4 4:Am7X2 4:D7X2 2:GM7
		// 2:Bb7 2:EbM7 2:F#7 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 4:Am7X2 4:D7X2 4:GM7X4
		// 4:C#m7X2 4:F#7X2 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 8:C#m7 4:C#ot 8:C#m7 2:C#ot
		// 2:BM7 2:D7 2:GM7 2:Bb7 4:EbM7X4 4:Am7X2 4:D7X2 2:GM7 2:Bb7 2:EbM7 2:F#7
		// 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 4:Am7X2 4:D7X2 4:GM7X4 4:C#m7X2 4:F#7X2
		// 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 8:C#m7 4:C#ot 8:C#m7 2:C#ot 2:BM7 2:D7 2:GM7
		// 2:Bb7 4:EbM7X4 4:Am7X2 4:D7X2 2:GM7 2:Bb7 2:EbM7 2:F#7 4:BM7X4 4:Fm7X2
		// 4:Bb7X2 4:EbM7X4 4:Am7X2 4:D7X2 4:GM7X4 4:C#m7X2 4:F#7X2 4:BM7X4 4:Fm7X2
		// 4:Bb7X2 4:EbM7X4 8:C#m7 4:C#ot 8:C#m7 2:C#ot 2:BM7 2:D7 2:GM7 2:Bb7 4:EbM7X4
		// 4:Am7X2 4:D7X2 2:GM7 2:Bb7 2:EbM7 2:F#7 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4
		// 4:Am7X2 4:D7X2 4:GM7X4 4:C#m7X2 4:F#7X2 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4
		// 8:C#m7 4:C#ot 8:C#m7 2:C#ot 2:BM7 2:D7 2:GM7 2:Bb7 4:EbM7X4 4:Am7X2 4:D7X2
		// 2:GM7 2:Bb7 2:EbM7 2:F#7 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 4:Am7X2 4:D7X2
		// 4:GM7X4 4:C#m7X2 4:F#7X2 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 8:C#m7 4:C#ot
		// 8:C#m7 2:C#ot 2:BM7 2:D7 2:GM7 2:Bb7 4:EbM7X4 4:Am7X2 4:D7X2 2:GM7 2:Bb7
		// 2:EbM7 2:F#7 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 4:Am7X2 4:D7X2 4:GM7X4 4:C#m7X2
		// 4:F#7X2 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 8:C#m7 4:C#ot 8:C#m7 2:C#ot 2:BM7
		// 2:D7 2:GM7 2:Bb7 4:EbM7X4 4:Am7X2 4:D7X2 2:GM7 2:Bb7 2:EbM7 2:F#7 4:BM7X4
		// 4:Fm7X2 4:Bb7X2 4:EbM7X4 4:Am7X2 4:D7X2 4:GM7X4 4:C#m7X2 4:F#7X2 4:BM7X4
		// 4:Fm7X2 4:Bb7X2 4:EbM7X4 8:C#m7 4:C#ot 8:C#m7 2:C#ot 2:BM7 2:D7 2:GM7 2:Bb7
		// 4:EbM7X4 4:Am7X2 4:D7X2 2:GM7 2:Bb7 2:EbM7 2:F#7 4:BM7X4 4:Fm7X2 4:Bb7X2
		// 4:EbM7X4 4:Am7X2 4:D7X2 4:GM7X4 4:C#m7X2 4:F#7X2 4:BM7X4 4:Fm7X2 4:Bb7X2
		// 4:EbM7X4 8:C#m7 4:C#ot 8:C#m7 2:C#ot";

//		MyInterface myInterface = (int a, String b) -> String.valueOf(a + Integer.parseInt(b));
//		prt(myInterface.affiche(2, "7"));

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
				for (int h = 1; h < 3; h++) {
					prt(" transposition(" + h + ") :: " + (element.transpose(h)).toString() + " ");
					prtln("getSeconde() :: " + element.getSecondInChord().getName() + " getQuinte() :: "
							+ element.getQuinte().toString()+" "
							+ "getTierce() :: "+element.getTierce().getName()+" "
							);
				}
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
