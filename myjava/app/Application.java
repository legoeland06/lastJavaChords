package myjava.app;

import java.util.Scanner;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Début de l'applicateion");
		Scanner quest = new Scanner(System.in);
		Grille grille = new Grille();

		for (String elem : quest.nextLine().split(" ")) {
			Chord newChord = new Chord().parseToChord(elem);
			grille.add(newChord);
			affiche(newChord);
		}
		
		System.out.println("Voulez-vous jouer ?");
		System.out.println("1. Jouer les accords proposés en paramètre");
		System.out.println("2. Quitter le programme");
		switch (quest.nextInt()) {
			case 1:
				quest.close();
				System.out.println("fonction play()");

				PlayerMidi pl = new PlayerMidi();
				grille.setTempo(100);
				
				pl.injectSeq(grille);
				//pl.injectBasse(grille);
				pl.play();
				System.exit(0);
				break;
			case 2:
				System.out.println("Merci et à bientôt!!!");
				System.exit(0);
				quest.close();
				break;
			
		default:
			quest.close();
			throw new IllegalArgumentException("Unexpected value: " + quest.nextInt());
		}
		quest.close();
	}

	public static void affiche(Chord c) {
		System.out.println("****************************************************************");
		System.out.println("*************************   "+c+"   **************************");
		System.out.println("****************************************************************");
		System.out.println(c.toString());
		System.out.println("timer: " + c.getTime());
		System.out.println("Fondamentale: " + c.getFondamentale().getName());
		System.out.println("Quality: " + c.getQuality().getQualityName());
		System.out.println("Basse: " + c.getBasse().getName());
		System.out.println("Multiplicateur: " + c.getMulti());
		System.out.println();

	}

}
