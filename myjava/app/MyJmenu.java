package myjava.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * @author ericbruneau
 *
 */
public class MyJmenu {

	/**
	 * @param file
	 */
	public MyJmenu(String file) {
		super();

	}

//	public MyJmenu(String file) {
//		super();
//
//	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// tout remettre dans le main
		JFrame fen = new JFrame("zChords - Description des accords");
		JPanel panel = new JPanel();
		JMenuBar myMenuBar = new JMenuBar();

		panel.setLayout(null);
		fen.setSize(500, 400);
		fen.setContentPane(panel);
		panel.addInputMethodListener(null);

		// création de la barre des menus

		// Ajout de la barre de menus au JPanel
		panel.add(myMenuBar);
		myMenuBar.setBounds(0, 0, 500, 30);

		// Automatisation de la création du menu

		String[] sophiaAutomatiqueMenu = new String[] { "_Fichier...[fileMenu]", "-Sauver[saveMenu]",
				"_Charger[loadMenu]", "_Nouveau[newMenu]",

				"+Accord[accordMenu]", "-Nouvel[nouvAccord]",

				"+Grille[grilleMenu]", "-Nouvelle[nouvGrille]",

				"+Aide[aideMenu]", "-A PROPOS DE NOUS...[aboutMenu]" };
		Integer niveau = 0;
		JMenu menu = null;
		JMenuItem menuItem = null;
		String aktion = null;

		for (String sting : sophiaAutomatiqueMenu) {

			aktion = sting.substring(sting.indexOf('['), sting.indexOf(']'));

			char premierChar = sting.charAt(0);
			sting = sting.substring(1, sting.indexOf('['));

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
				menu = new JMenu(sting);
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
				menuItem = new JMenuItem(sting);
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
				break;
			default:
				break;
			}

			myMenuBar.add(menu);

		}
		fen.setVisible(true);

//		JMenu fileMenu = new JMenu("Fichier");
//		JMenuItem saveMenu = new JMenuItem("Sauver");
//		JMenuItem loadMenu = new JMenuItem("Charger");
//
//		JMenu menuAccord = new JMenu("Accord");
//		JMenuItem item1 = new JMenuItem("Nouveau");
//
//		JMenu menuGrille = new JMenu("Grille");
//		JMenuItem item2 = new JMenuItem("Nouvelle");
//
//		JMenu menuFichier = new JMenu("Aide");
//
//		fileMenu.add(saveMenu);
//		fileMenu.add(loadMenu);
//
//		menuAccord.add(item1);
//
//		menuGrille.add(item2);
//
//		// Ajout du menu à la barre des menus
//		myMenuBar.add(fileMenu);
//		myMenuBar.add(menuAccord);
//		myMenuBar.add(menuGrille);
//		myMenuBar.add(menuFichier);

	}
}