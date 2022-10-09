package myjava.app;

import java.awt.Dimension;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
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
				prtln("getSeconde() :: " + element.getSecondInChord().getName() + " getQuinte() :: "
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
		if (((String) String.valueOf(objectToStringOut)).contentEquals(String.valueOf(LINE))) {
			System.out.println("\n--------------------------------------------------------");
			return;
		}
		System.out.println((String) String.valueOf(objectToStringOut));
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
		menu.setSize(40,600);

		JMenuBar myMenuBar = new JMenuBar();
		myMenuBar.setBounds(0, 0, 500, 30);

		String[] sophiaAutomatiqueMenu = new String[] { "_Fichier...[fileMenu]", "-Sauver[saveMenu]",
				"_Charger[loadMenu]", "_Nouveau[newMenu]",

				"+Accord[accordMenu]", "-Nouvel[nouvAccord]",

				"+Grille[grilleMenu]", "-Nouvelle[nouvGrille]",

				"+Aide[aideMenu]", "-A PROPOS DE NOUS...[aboutMenu]" ,
				"+Outils[aideMen1]","-Préférences[aideMenu2]","_noyau[aideMenu3]","-supprimer la racine[aideMenu4]","_modifier le noyau[aideMenu5]","_supprimer le noyau[aideMenu6]",
				"+Agenda[aideMenu7]","-créer[aideMenu8]","_modifier[aideMenu9]","_supprimer[aideMenu10]","_exporter[aideMenu11]"
				};
		Integer niveau = 0;
		JMenuItem menuItem = null;
		for (String elementMenuStr : sophiaAutomatiqueMenu) {

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
				break;
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
				break;
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

		creationSwing();
		//MyJmenu.main(args);
		
		Harmonie.mapNoteInteger().forEach((c, d) -> prt(" :: " + c + " :: " + d + "\n"));

//		for (Map.Entry<Note, Integer> map : Harmonie.mapNoteInteger().entrySet()) {
//			prt(" || " + map.getKey() + " || " + map.getValue() + "\n");
//		}
		
		//forEach((g)->prt(" :: " + g + " :: " + "\n"));
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

			grille.setTempo(210);

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
			printGrilleDetaillee(grille);

			printGrille(grille);
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
