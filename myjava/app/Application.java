package myjava.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * @author Eric Bruneau
 *
 */
public class Application {

	public static int count = 0;
	private static char LINE = '-';

	/**
	 * méthode qui écrit sur la ligne et incrémente un compteur. Utilisation :
	 * pouvoir aligner un certain nombre d'objets sur une ligne et prévoir un retour
	 * à la ligne au bout de n fois On utilisera le modulo n
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
	 * @param a
	 * @param b
	 * @param c
	 * @return tab
	 */
	public static int[] getRange(int a,int b,int c){
	        return IntTableau.INSTANCE.getRangeInt(a,b,c);
	    }

	/**
	 * @param grille
	 */
	public static void printGrille(Grille grille) {
		if (grille != null)
			grille.getContenu().forEach(c -> prt(c + " "));
		else
			prtln("Pas de grille à afficher");
	}

	/**
	 * @param grille
	 */
	public static void printGrilleDetaillee(Grille grille) {
		if (grille != null)
			grille.getContenu().forEach(element -> {
				prtln("Accord :: " + element.toString());
				prt(" transposition(1) :: " + (element.transpose(1)).toString() + " ");
				prtln("getSeconde() :: " + element.getSecondFromFondamental().getName() + " getQuinte() :: "
						+ element.getQuinte().toString() + " " + "getTierce() :: " + element.getTierce().getName()
						+ " ");

				prtln(LINE);

			});
		else
			prtln("Pas de grille à afficher");
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
		if ((objectToStringOut.toString().contentEquals(String.valueOf(LINE)))) {
			System.out.println("--------------------------------------------------------");
			return;
		}
		System.out.println(objectToStringOut.toString());
	}

	/**
	 * 
	 */
	public static void creationSwing() {
		// create a basic JFrame
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("ZChords");
		frame.setTitle("ZChords version 3.0");

		// add the JComponent to main frame
		JPanel panel = new JPanel();

		JMenu menu = new JMenu();
		menu.setSize(40, 600);

		JMenuBar myMenuBar = new JMenuBar();
		myMenuBar.setBounds(0, 0, 500, 30);

		String[] tableauMenu = new String[] { "_Fichier...[fileMenu]", "-Sauver[saveMenu]", "_Charger[loadMenu]",
				"_Nouveau[newMenu]", "+Accord[accordMenu]", "-Nouvel[nouvAccord]", "+Grille[grilleMenu]",
				"-Nouvelle[nouvGrille]", "+Aide[aideMenu]", "-A PROPOS DE NOUS...[aboutMenu]", "+Outils[aideMen1]",
				"-Préférences[aideMenu2]", "_noyau[aideMenu3]", "-supprimer la racine[aideMenu4]",
				"_modifier le noyau[aideMenu5]", "_supprimer le noyau[aideMenu6]", "+Agenda[aideMenu7]",
				"-créer[aideMenu8]", "_modifier[aideMenu9]", "_supprimer[aideMenu10]", "_exporter[aideMenu11]" };
		Integer niveau = 0;
		JMenuItem menuItem = null;
		for (String elementMenuStr : tableauMenu) {

			char premierChar = elementMenuStr.charAt(0);
			elementMenuStr = elementMenuStr.substring(1, elementMenuStr.indexOf('['));

			switch (premierChar) {
			case '-':
				niveau -= 1;
				break;
			case '+':
				niveau += 1;
				break;

			default:
				System.out.println("appuyer");
			}
			switch (niveau) {
			case 0:
				// creation des menus
				menu = new JMenu(elementMenuStr);
				menu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						System.out.println("action : " + this.toString());

					}
				});

				break;
			case -1:
				// creation des sous menus (items)
				menuItem = new JMenuItem(elementMenuStr);
				menu.add(menuItem);
				menu.addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub

					}
				});

				break;
			case -2:
				// creation des sous sous menus (autresItems)
				menuItem = new JMenuItem(elementMenuStr);
				menu.add(menuItem);
				break;
			default:
			}

			myMenuBar.add(menu);
			panel.add(myMenuBar);

		}

		frame.setSize(400, 600);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		frame.setSize(500, 400);
		frame.setContentPane(panel);
		frame.setVisible(true);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/*
		 * creationSwing();
		 */
		/*
		 * MyJmenu.main(args);
		 */

		/*
		 * Harmonie.mapNoteInteger().forEach((c, d) -> prt(" :: " + c + " :: " + d
		 * +"\n"));
		 */

		printLigne();
		prtln("1: Créateur d'accord ");
		prtln("2: Parseur d'accord");
		printLigne();
		prtln("Faites votre choix : 1 :: 2  :: q pour quitter");

		boolean sortir;
		try (Scanner menu = new Scanner(System.in)) {
			String choix = menu.next();
			sortir = false;

			 while (!sortir) {
				PlayerMidi playerInstance = PlayerMidi.INSTANCE;
				Grille grilleInstance = Grille.INSTANCE;
				switch (choix) {

				case "1":
					System.out.flush();
					prtln("Menu de Création d'accord");
					printLigne();
					prtln("Quelle est la fondamentale ? ou q pour quitter le menu");
					Note fonda = null;
					Note laTierce = null;
					Note laQuinte = null;
					Note laSeptieme = null;
					String fondaString = null;
					String tierceString = null;
					String quinteString = null;
					String septiemeString = null;
					String jouerQuestion = null;

					try (Scanner question = new Scanner(System.in)) {
						fondaString = question.next();
						ChordFactory chordFactoryInstance = ChordFactory.INSTANCE;
						sortir = false;
						while (!sortir) {
							switch (fondaString) {
							case "q":
								sortir = true;
								break;

							default:
								if (Harmonie.existeOrNo(fondaString)) {
									fonda = chordFactoryInstance.createFondamentale(fondaString);
									prtln("La nouvelle fondamentale " + fondaString + " est enregistrée.");
									prtln("voulez pouvez la changer ou quitter ce menu en tapant q");
									break;

								} else {
									prtln("Vous avez du vous tromper de note !");
								}
							}
							fondaString = sortir ? fondaString : question.next();

						}

						printLigne();
						prtln("Quelle est la nature de la tierce ? m, M ou q pour quitter le menu");
						tierceString = question.next();

						sortir = false;
						while (!sortir) {
							switch (tierceString) {
							case "m":
							case "min": {
								laTierce = chordFactoryInstance.createTierceMinor(fonda);
								prtln("La nouvelle tierce " + tierceString + " est enregistrée.");
								prtln("il s'agit d'un : " + laTierce);
								prtln("voulez pouvez la changer ou quitter ce menu en tapant q");
								break;
							}
							case "M":
							case "maj": {
								laTierce = chordFactoryInstance.createTierceMajor(fonda);
								prtln("La nouvelle tierce " + tierceString + " est enregistrée.");
								prtln("il s'agit d'un : " + laTierce);
								prtln("voulez pouvez la changer ou quitter ce menu en tapant q");
								break;
							}
							case "q":
								sortir = true;
								break;
							default:
								prtln("Vous avez du vous tromper: " + tierceString);
							}
							tierceString = sortir ? tierceString : question.next();
						}

						printLigne();
						prtln("Quelle est la nature de la quinte ? dim(-), Aug(+), normale( ) ou q pour quitter le menu");
						quinteString = question.next();

						sortir = false;
						while (!sortir) {
							switch (quinteString) {
							case "-": {
								laQuinte = chordFactoryInstance.createQuinteDim(fonda);
								prtln("La nouvelle quinte " + quinteString + " est enregistrée.");
								prtln("il s'agit d'un : " + laQuinte);
								prtln("voulez pouvez la changer ou quitter ce menu en tapant q");
								break;
							}
							case "+": {
								laQuinte = chordFactoryInstance.createQuinteAug(fonda);
								prtln("La nouvelle quinte " + quinteString + " est enregistrée.");
								prtln("il s'agit d'un : " + laQuinte);
								prtln("voulez pouvez la changer ou quitter ce menu en tapant q");
								break;
							}
							case " ":
							case "norm":
							case "":
								laQuinte = chordFactoryInstance.createQuinte(fonda);
								prtln("La nouvelle quinte " + quinteString + " est enregistrée.");
								prtln("il s'agit d'un : " + laQuinte);
								prtln("voulez pouvez la changer ou quitter ce menu en tapant q");
								break;
							case "q":
								sortir = true;
								break;
							default:
								prtln("Vous avez du vous tromper: " + quinteString);
							}
							quinteString = sortir ? quinteString : question.next();

						}

						printLigne();
						prtln("Quelle est la nature de la septième ? maj(M), mineur(m) ou q pour quitter le menu");
						septiemeString = question.next();

						sortir = false;
						while (!sortir) {
							switch (septiemeString) {
							case "m":
							case "norm":
							case "7":
							case "min": {
								laSeptieme = chordFactoryInstance.createSeptieme(fonda);
								prtln("La nouvelle septieme " + septiemeString + " est enregistrée.");
								prtln("il s'agit d'un : " + laSeptieme);
								prtln("voulez pouvez la changer ou quitter ce menu en tapant q");
								break;
							}
							case "M":
							case "Maj": {
								laSeptieme = chordFactoryInstance.createSeptiemeMajeur(fonda);
								prtln("La nouvelle septieme " + septiemeString + " est enregistrée.");
								prtln("il s'agit d'un : " + laSeptieme);
								prtln("voulez pouvez la changer ou quitter ce menu en tapant q");
								break;
							}

							case "q":
								sortir = true;
								break;
							default:
								prtln("Vous avez du vous tromper: " + septiemeString);
							}

							septiemeString = sortir ? septiemeString : question.next();

						}

						Chord chordCreated = new Chord();

						List<Note> goodListe = new ArrayList<>();
						goodListe.add(fonda);
						goodListe.add(laTierce);
						goodListe.add(laQuinte);
						goodListe.add(laSeptieme);
						chordCreated.setNotes(goodListe);
						Map myMap= new HashMap();
						myMap.put(0,chordCreated);
						grilleInstance.initMap(myMap);
						//grilleInstance.addChord(chordCreated);
						prtln("L'accord a été créé : ");
						prtln("Voulez-vous le jouer ? ");
						jouerQuestion = question.next();
						sortir = false;
						while (!sortir) {

							switch (jouerQuestion) {
							case "oui":
							case "o": {
								grilleInstance.getContenuMap().forEach((c, d) -> prtln(c + " " + d.getNotes()));
								grilleInstance.getContenu().forEach(e -> prtln(e.getNotes()));

								playerInstance.init(grilleInstance, 80);
								playerInstance.play();

								prtln("voulez pouvez rejouer ou quitter ce menu en tapant q");
								break;
							}
							case "non":
							case "n":
								break;

							case "q":
								sortir = true;
								break;
							default:
								prtln("Vous avez du vous tromper: " + jouerQuestion);
							}
							jouerQuestion = sortir ? jouerQuestion : question.next();
						}
					}
					break;

					
				case "2":
					System.out.flush();
					prtln("Menu du Parseur d'accords");
					printLigne();

					prtln("Entrez un accords ou une suite d'accords...");

					prtln(LINE);
					Scanner quest = new Scanner(System.in);

					String[] rep = ((String[]) quest.nextLine().split(" "));

					grilleInstance.initMap(new HashMap());
					for (String elem : rep) {
						Chord accord = new Chord();
						accord = Harmonie.parseToChord(elem);
						accord.update();
						for (int j = 0; j < accord.getMulti(); j++) {
							Chord receptacle = Harmonie.parseToChord(elem);
							receptacle.update();
							grilleInstance.addChord(receptacle);
						}
					}

					prtln("------------------------------------------------------");
					prtln("1. Jouer les accords proposés en paramètre");
					prtln("2. Quitter le programme");
					while (quest.hasNext())
						switch (quest.nextInt()) {
						case 1:
							playerInstance.init(grilleInstance, 80);

							playerInstance.play();

							break;
						case 2:
							prtln("Merci et à bientôt!!!");
							quest.close();
							System.exit(0);
							break;

						default:
							quest.close();
							throw new IllegalArgumentException("Unexpected value: " + quest.nextInt());
						}
					quest.close();
					break;
					
				case "q":
					sortir = true;
					break;
					
				default:
					prtln("kiki");
					choix = menu.next();
				}
			}
			prtln("Merci et au revoir");
			menu.close();
			System.exit(0);
		}

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
