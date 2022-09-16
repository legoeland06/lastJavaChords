package myjava.app;

import java.util.Scanner;
import java.util.function.Consumer;

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

	/**
	 * méthode qui écrit sur la ligne et incrémente un compteur utilité : pouvoir
	 * aligner un certain nombre d'objets sur une ligne et prévoir un retour à la
	 * ligne au bout de n fois
	 * 
	 * @param string
	 * @return count incremented
	 */
	public static Integer prt(String string) {
		System.out.print(string);
		count++;
		return count;
	}

	/**
	 * @param stringOut
	 * @return count but not incremented
	 */
	public static Integer prtln(String stringOut) {
		if (stringOut == "-") {
			System.out.println("\n--------------------------------------------------------");
			return count;
		}
		System.out.println(stringOut);
		return count;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		prtln("Entrez les accords sous la forme 2:Cm7b5/C 2:Cm7b5/C ... :");
		prtln("Séparés par des espaces");

		Chord myAccord = new Chord();
		myAccord.parseToChord("Cm7b5");
		myAccord.chordToComponents().forEach(c -> prt(c.toString()));

		prtln("-");
		Scanner quest = new Scanner(System.in);
		Grille grille = new Grille();

		//String repString = "2:BM7 2:D7 2:GM7 2:Bb7 4:EbM7X4 4:Am7X2 4:D7X2 2:GM7 2:Bb7 2:EbM7 2:F#7 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 4:Am7X2 4:D7X2 4:GM7X4 4:C#m7X2 4:F#7X2 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 8:C#m7 4:C#ot 8:C#m7 2:C#ot 2:BM7 2:D7 2:GM7 2:Bb7 4:EbM7X4 4:Am7X2 4:D7X2 2:GM7 2:Bb7 2:EbM7 2:F#7 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 4:Am7X2 4:D7X2 4:GM7X4 4:C#m7X2 4:F#7X2 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 8:C#m7 4:C#ot 8:C#m7 2:C#ot 2:BM7 2:D7 2:GM7 2:Bb7 4:EbM7X4 4:Am7X2 4:D7X2 2:GM7 2:Bb7 2:EbM7 2:F#7 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 4:Am7X2 4:D7X2 4:GM7X4 4:C#m7X2 4:F#7X2 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 8:C#m7 4:C#ot 8:C#m7 2:C#ot 2:BM7 2:D7 2:GM7 2:Bb7 4:EbM7X4 4:Am7X2 4:D7X2 2:GM7 2:Bb7 2:EbM7 2:F#7 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 4:Am7X2 4:D7X2 4:GM7X4 4:C#m7X2 4:F#7X2 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 8:C#m7 4:C#ot 8:C#m7 2:C#ot 2:BM7 2:D7 2:GM7 2:Bb7 4:EbM7X4 4:Am7X2 4:D7X2 2:GM7 2:Bb7 2:EbM7 2:F#7 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 4:Am7X2 4:D7X2 4:GM7X4 4:C#m7X2 4:F#7X2 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 8:C#m7 4:C#ot 8:C#m7 2:C#ot 2:BM7 2:D7 2:GM7 2:Bb7 4:EbM7X4 4:Am7X2 4:D7X2 2:GM7 2:Bb7 2:EbM7 2:F#7 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 4:Am7X2 4:D7X2 4:GM7X4 4:C#m7X2 4:F#7X2 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 8:C#m7 4:C#ot 8:C#m7 2:C#ot 2:BM7 2:D7 2:GM7 2:Bb7 4:EbM7X4 4:Am7X2 4:D7X2 2:GM7 2:Bb7 2:EbM7 2:F#7 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 4:Am7X2 4:D7X2 4:GM7X4 4:C#m7X2 4:F#7X2 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 8:C#m7 4:C#ot 8:C#m7 2:C#ot 2:BM7 2:D7 2:GM7 2:Bb7 4:EbM7X4 4:Am7X2 4:D7X2 2:GM7 2:Bb7 2:EbM7 2:F#7 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 4:Am7X2 4:D7X2 4:GM7X4 4:C#m7X2 4:F#7X2 4:BM7X4 4:Fm7X2 4:Bb7X2 4:EbM7X4 8:C#m7 4:C#ot 8:C#m7 2:C#ot";

//		MyInterface myInterface = (int a, String b) -> String.valueOf(a + Integer.parseInt(b));
//		prt(myInterface.affiche(2, "7"));

		String[] rep = quest.nextLine().split(" ");

		for (String elem : rep) {
			Chord accord = new Chord().parseToChord(elem);

			for (int j = 0; j < accord.getMulti(); j++) {
				Chord receptacle = new Chord().parseToChord(elem);
				receptacle.setMulti(1);
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

			grille.getContenu().forEach(c -> {
				if (prt(c.toString()) % 4 == 0) {
					prtln(" ");
				} else {
					prt("  \t");
					count--;
				}
			});

			grille.getContenu().forEach(g -> prt(g.toString() + " "));

			pl.injectSeq(grille);
			pl.injectBasse(grille);

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
