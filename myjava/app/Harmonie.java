package myjava.app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Harmonie {

	private static char SEPARATE_INT = ':';
	private static char SEPARATE_BASSE = '/';
	private static char SEPARATE_MULTI = 'X';
	private static char DIEZE = '#';
	private static char BEMOL = 'b';

	private static final String[][] NOTE_VAL_DICT = { { "A", "9" }, { "A#", "10" }, { "Bb", "10" }, { "Cb", "11" },
			{ "B", "11" }, { "C", "0" }, { "Db", "1" }, { "C#", "1" }, { "D", "2" }, { "D#", "3" }, { "Eb", "3" },
			{ "E", "4" }, { "F", "5" }, { "Gb", "6" }, { "F#", "6" }, { "G", "7" }, { "G#", "8" }, { "Ab", "8" }, };
	private static final String[][] NOTE_BEMOL_DIEZE = { { "A#", "10" }, { "Bb", "10" }, { "Cb", "11" }, { "Db", "1" },
			{ "C#", "1" }, { "D#", "3" }, { "Eb", "3" }, { "Gb", "6" }, { "F#", "6" }, { "G#", "8" }, { "Ab", "8" }, };

	private static final String[][] _qualitiesStrings = { { "ot", ":" }, { "5", "0:7:12:19:" }, { "no5", "0:4:12:16:" },
			{ "omit5", "0:4:12:16:" }, { "m(no5)", "0:3:12:15:" }, { "m(omit5)", "0:3:12:15:" }, { " ", "0:4:7:12:" },
			{ "maj", "0:4:7:12:" }, { "m", "0:3:7:12:" }, { "min", "0:3:7:12:" }, { "-", "0:3:7:12:" },
			{ "dim", "0:3:6:12:" }, { "(b5)", "0:4:6:12:" }, { "aug", "0:4:8:12:" }, { "sus2", "0:2:7:12:" },
			{ "sus4", "0:5:7:12:" }, { "sus", "0:5:7:12:" }, { "6", "0:4:7:9:" }, { "7", "0:4:7:10:" },
			{ "7-5", "0:4:6:10:" }, { "7b5", "0:4:6:10:" }, { "7+5", "0:4:8:10:" }, { "7#5", "0:4:8:10:" },
			{ "7sus4", "0:5:7:10:" }, { "m6", "0:3:7:9:" }, { "m7", "0:3:7:10:" }, { "m7-5", "0:3:6:10:" },
			{ "m7b5", "0:3:6:10:" }, { "m7+5", "0:3:8:10:" }, { "m7#5", "0:3:8:10:" }, { "dim6", "0:3:6:8:" },
			{ "dim7", "0:3:6:9:" }, { "7alt", "0:3:6:9:" }, { "M7", "0:4:7:11:" }, { "maj7", "0:4:7:11:" },
			{ "M7+5", "0:4:8:11:" }, { "mM7", "0:3:7:11:" }, { "add4", "0:4:5:7:" }, { "Madd4", "0:4:5:7:" },
			{ "madd4", "0:3:5:7:" }, { "add9", "0:4:7:14:" }, { "Madd9", "0:4:7:14:" }, { "madd9", "0:3:7:14:" },
			{ "sus4add9", "0:5:7:14:" }, { "sus4add2", "0:2:5:7:" }, { "2", "0:4:7:14:" }, { "add11", "0:4:7:17:" },
			{ "m11", "0:3:7:17:" }, { "4", "0:4:7:17:" }, { "m69", "0:3:7:9:14:" }, { "69", "0:4:7:9:14:" },
			{ "9", "0:4:7:10:14:" }, { "m9", "0:3:7:10:14:" }, { "M9", "0:4:7:11:14:" }, { "maj9", "0:4:7:11:14:" },
			{ "9sus4", "0:5:7:10:14:" }, { "7-9", "0:4:7:10:13:" }, { "7b9", "0:4:7:10:13:" },
			{ "7+9", "0:4:7:10:15:" }, { "7#9", "0:4:7:10:15:" }, { "9-5", "0:4:6:10:14:" }, { "9b5", "0:4:6:10:14:" },
			{ "9+5", "0:4:8:10:14:" }, { "9#5", "0:4:8:10:14:" }, { "7#9b5", "0:4:6:10:15:" },
			{ "7#9#5", "0:4:8:10:15:" }, { "m7b9b5", "0:3:6:10:13:" }, { "7b9b5", "0:4:6:10:13:" },
			{ "7b9#5", "0:4:8:10:13:" }, { "11", "0:7:10:14:17:" }, { "7+11", "0:4:7:10:18:" },
			{ "7#11", "0:4:7:10:18:" }, { "M7+11", "0:4:7:11:18:" }, { "M7#11", "0:4:7:11:18:" },
			{ "7b9#9", "0:4:7:10:13:15:" }, { "7b9#11", "0:4:7:10:13:18:" }, { "7#9#11", "0:4:7:10:15:18:" },
			{ "7-13", "0:4:7:10:20:" }, { "7b13", "0:4:7:10:20:" }, { "m7add11", "0:3:7:10:17:" },
			{ "M7add11", "0:4:7:11:17:" }, { "mM7add11", "0:3:7:11:17:" }, { "7b9b13", "0:4:7:10:13:17:20" },
			{ "9+11", "0:4:7:10:14:18:" }, { "9#11", "0:4:7:10:14:18:" }, { "13", "0:4:7:10:14:21:" },
			{ "13-9", "0:4:7:10:13:21:" }, { "13b9", "0:4:7:10:13:21:" }, { "13+9", "0:4:7:10:15:21:" },
			{ "13#9", "0:4:7:10:15:21:" }, { "13+11", "0:4:7:10:18:21:" }, { "13#11", "0:4:7:10:18:21:" },
			{ "M7add13", "0:4:7:9:11:14:" } };

	/**
	 * @return List<Note>
	 */
	public static List<Note> getNoteValDict() {
		List<Note> receptacle = new ArrayList<>();
		for (String[] note : NOTE_VAL_DICT) {
			receptacle.add(stringToNote(note[0]));
		}
		return receptacle;
	}

	/**
	 * @return List<Note>
	 */
	public static List<Note> getNoteValDictByBemolDieze() {

		List<Note> receptacle = new ArrayList<>();

		getNoteValDict().stream().filter(t -> t.getName().length() == 2).forEach(note -> receptacle.add(note));
		getNoteValDict().stream().filter(t -> t.getName().length() == 1).forEach(note -> receptacle.add(note));

		/*
		 * receptacle va devenir une liste de Note en commençant par les bémols et les
		 * dièzes et en finissant par les notes simples
		 */
		return receptacle;
	}

	/**
	 * @param note
	 * @return Boolean
	 */
	public static Boolean existeOrNo(String note) {
		boolean existe = false;
		for (String[] strings : NOTE_VAL_DICT) {
			if (strings[0].indexOf(note) != -1) {
				existe = true;
			}
		}
		return existe;
	}

	/**
	 * @param n
	 * @return Integer
	 */
	public static Integer noteToVal(Note n) {

		Integer sortie = 0;
		for (String[] laval : NOTE_VAL_DICT) {
			if (laval[0].contentEquals(n.getName())) {
				sortie = Integer.parseInt(laval[1]);
				break;
			}
		}
		return sortie;
	}

	/**
	 * @param v
	 * @return Note
	 */
	public static Note valToNote(int v) {
		Integer moduloNote = (v) % 12;
		String victor = "";
		for (String[] strings : NOTE_VAL_DICT) {
			if (Integer.parseInt(strings[1]) == moduloNote) {
				victor = strings[0];
				continue;
			}
		}
		return stringToNote(victor);
	}

	/**
	 * @param s
	 * @return Note
	 */
	public static Note stringToNote(String s) {
		if (!existeOrNo(s)) {
			System.out.println("Probleme de notes: " + s);
			System.exit(0);
		}
		return new Note(s);
	}

	/**
	 * @param s
	 * @return Note from char
	 */
	public static Note charToNote(char s) {
		if (!existeOrNo(String.valueOf(s))) {
			System.out.println("Probleme de notes: " + s);
			System.exit(0);
		}
		return new Note(String.valueOf(s));
	}

	/**
	 * @param n
	 * @return StringName of note
	 */
	public static String noteToString(Note n) {
		return n.getName();
	}

	/**
	 * @param c
	 * @param note
	 * @return Note
	 */
	public static Note getNextNoteInChord(Chord c, Note note) {
		List<Note> noteList = chordToComponents(c);
		int noteIndexInList = noteList.indexOf(note);
		Note prevNote = noteList.get(noteList.size() % (noteIndexInList + 1));
		return prevNote;
	}

	/**
	 * @param c
	 * @param note
	 * @return Note
	 */
	public static Note getPrevNoteInChord(Chord c, Note note) {
		List<Note> noteList = chordToComponents(c);
		int noteIndexInList = noteList.indexOf(note);
		Note prevNote = noteList.get(noteList.size() % (noteIndexInList - 1));
		return prevNote;
	}

	public static List<Note> chordToComponents(Chord c) {

		List<Integer> values = chordToValues(c);

		/*
		 * Attention : map stream et map index pas sur le 0 tous les deux
		 */
		List<Note> componentsOfChord = values != null ? values.stream().map(r -> valToNote(r)).toList() : null;

		return componentsOfChord;
	}

	public static List<Integer> chordToValues(Chord c) {

		/*
		 * valuesChordIntList est la liste qui récupère les int écarts entre les notes
		 * de l'accords grace au tableau Quality.listeQualiesName() et le tableau
		 * Quality.listeQualitiesEcart() tous les dexu issues du tableau 2D
		 * _qualitiesStrings
		 */

		List<Integer> valuesChordIntList = new ArrayList<>();

		String qualityName = c.getQuality().getQualityName();

		/*
		 * on récupère la fondamentale de l'accord
		 */
		Note rootNote = c.getFondamentale();

		/*
		 * on recupère la valeur de la fondamentale dans rootNoteToVal
		 */
		Integer rootNoteToVal = noteToVal(rootNote);

		/*
		 * on recupère l'index (qualityNameIndex) de la quality de l'accord s'il existe,
		 * sinon qualityNameIndex=0
		 */
		Integer qualityNameIndex = listeQualitiesName().contains(qualityName)
				? listeQualitiesName().indexOf(qualityName)
				: 6;

		/*
		 * on en déduit la qualityEcartsString qui en résulte dans le tableaux de
		 * correspondances le tableau listeQualitiesEcart.
		 */
		String qualityEcartsString = listeQualitiesEcart().get(qualityNameIndex);

		/*
		 * on lit dans le tableau des ecarts
		 */
		for (String ecartNotes : (String[]) qualityEcartsString.split(":")) {
			valuesChordIntList.add(Integer.parseInt(ecartNotes) + rootNoteToVal);
		}

		return valuesChordIntList;
	}

	/**
	 * @param chaine
	 * @return String chaine
	 */
	public static Chord parseTimerFromString(String chaine,Chord c) {
		/*
		 * initialisation de la chaineRetour
		 */
		// String chaineRetour = "";
		Chord sauveChord = new Chord();

		/*
		 * position du séparateur de timer
		 */
		Integer posSepInt = chaine.indexOf(SEPARATE_INT);
		/*
		 * si il y a un timer, alors on récupère le int tu timer et on efface le bloc
		 * timer
		 */
		if (posSepInt >= 0) {
			sauveChord.setTime(Integer.parseInt(chaine.substring(0, posSepInt)));
			chaine = chaine.substring(posSepInt + 1);
			sauveChord.setChaine(chaine);
			return sauveChord;
		} else {
			/*
			 * sinon on met un timer de 4 par defaut et on ne touche à rien et on retourne
			 * la chaine inchangée
			 */
			sauveChord.setTime(4);
			sauveChord.setChaine(chaine);
			return sauveChord;
		}
		/*
		 * dans tous les cas une chaine est retournée, changée ou pas et le timer est
		 * setté par défaut ou par parametre
		 */
	}

	/**
	 * @param chaine
	 * @return String chaine
	 */
	public static Chord parseMultiFromString(String chaine,Chord c) {
		/*
		 * init de chaineRetour
		 */
		String chaineRetour = "";
		Chord sauveChord = new Chord();

		/*
		 * il y a t-il un multi ???
		 */
		int posSepMulti = chaine.indexOf(SEPARATE_MULTI);
		if (posSepMulti != -1) {
			/*
			 * si oui alors on récupère le paramètre multi proposé et on efface le bloc
			 * multi de la chaine
			 */
			sauveChord.setMulti(Integer.parseInt(chaine.substring(posSepMulti + 1)));
			chaineRetour = chaine.substring(0, posSepMulti);
		} else {
			/*
			 * sinon, on set le param multi à 1 et on ne change rien à la chaine et on
			 * renvoi la chaine
			 */
			sauveChord.setMulti(1);
			sauveChord.setTime(c.getTime());
			sauveChord.setChaine(chaine);
			return sauveChord;
		}
		/*
		 * dans tous les cas la chaine est renvoyée modifiée ou non un multi est setté
		 * par défaut ou par demande de paramètre dans la chaine
		 */
		sauveChord.setChaine(chaineRetour);
		sauveChord.setTime(c.getTime());
		return sauveChord;
	}

	/**
	 * @param chaine
	 * @return Chord
	 * 
	 *         {@summary C'est la méthode centrale de l'application elle va servir
	 *         pour parser n'importe quel accord passé en paramètre sous la forme C,
	 *         C# Cm Am7b5 FX4 2:AbM7#9/F }
	 */
	public static Chord parseToChord(String chaine) {

		Chord parsedChord = new Chord();
		/*
		 * cas particuliers de 1 seule lettre
		 */
		if (chaine.length() == 1) { // cas A B C D E F G
			parsedChord.setFondamentale(stringToNote(chaine));
			parsedChord.setTime(4);
			parsedChord.setMulti(1);
			parsedChord.setBasse(stringToNote(chaine));
			parsedChord.setQuality(new Quality(" "));
			parsedChord.setChaine(parsedChord.toString());
			return parsedChord;
		} else if (chaine.length() == 2) {
			/*
			 * sinon cas particulier de 2 lettres
			 */
			parsedChord.setTime(4);
			parsedChord.setMulti(1);
			/*
			 * il y a t-il des bemols ou des dieze en deuxième position ?
			 */
			if (chaine.indexOf(BEMOL) == 1 || chaine.indexOf(DIEZE) == 1) {
				/*
				 * si oui on prend le tout pour fixer la fondamentale et on fixe la basse et on
				 * fixe la qualité et on retourne le l'accord formaté
				 */
				parsedChord.setFondamentale(stringToNote(chaine));
				parsedChord.setBasse(stringToNote(chaine));
				parsedChord.setQuality(new Quality(" "));
				parsedChord.setChaine(parsedChord.toString());
				return parsedChord;
			} else {

				/*
				 * si il n'y avait pas de dièze ni de bémols alors la deuxième lettre est la
				 * qualité de l'accord..., on la récupère ainsi que la fondamentale, on en
				 * deduit la basse ensuite on nettoie chaine des ces blocs qui viennent d'être
				 * traités enfin on retourne l'accord ainsi formaté
				 */

				parsedChord.setFondamentale(stringToNote(String.valueOf(chaine.charAt(0))));
				parsedChord.setQuality(new Quality(String.valueOf(chaine.charAt(1))));
				parsedChord.setBasse(stringToNote(String.valueOf(chaine.charAt(0))));
				chaine = String.valueOf(chaine.charAt(0));
				parsedChord.setChaine(parsedChord.toString());
				return parsedChord;
			}
		}

		/*
		 * Tout les autres cas
		 */

		/*
		 * parsing du paramètre timer
		 */
		parsedChord = parseTimerFromString(chaine,parsedChord);
		chaine = parsedChord.getChaine();

		/*
		 * parsing du paramètre multi
		 */
		parsedChord = parseMultiFromString(chaine,parsedChord);
		chaine = parsedChord.getChaine();

		/*
		 * il y a t il des dieze ou des bemols ? A la fin de cette condition, la
		 * fondamentale est récupérée et la chaine en est nettoyée
		 */
		if (chaine.indexOf(DIEZE) == 1 || chaine.indexOf(BEMOL) == 1) {
			/*
			 * si oui on en déduit la fondamentale
			 */
			parsedChord.setFondamentale(stringToNote(chaine.substring(0, 2)));
			/*
			 * et on efface le bloc fondamentale de la chaine
			 */
			chaine = chaine.substring(2);
		} else {
			/*
			 * sinon on en déduit la fondamentale ne fait qu'une lettre, on la récupère et
			 * on l'efface de la chaine
			 */
			parsedChord.setFondamentale(stringToNote(String.valueOf(chaine.charAt(0))));
			chaine = chaine.substring(1);
		}

		/*
		 * il y a t il une basse ? A la fin de cette condition, la basse est récupérée
		 * ou settée par la fondamentale, et la chaine en est nettoyée
		 */
		if (chaine.indexOf(SEPARATE_BASSE) >= 0) {
			/*
			 * si oui on la récupère et on nettoie
			 */
			parsedChord.setBasse(stringToNote(chaine.substring(chaine.indexOf(SEPARATE_BASSE) + 1)));
			chaine = chaine.substring(0, chaine.indexOf(SEPARATE_BASSE));
		} else {
			/*
			 * sinon on récupère la fondamentale
			 */
			parsedChord.setBasse(parsedChord.getFondamentale());
		}

		/*
		 * la chaine restante est soit " ", soit une qualityStringName on la récupère et
		 * on renvois le Chord formaté par
		 */
		if (chaine.length() > 0) {
			parsedChord.setQuality(new Quality(chaine));
		} else
			parsedChord.setQuality(new Quality(" "));

		parsedChord.setChaine(parsedChord.toString() + parsedChord.getMulti());
		return parsedChord;
	}

	/**
	 * @return Optional<List<String>>
	 */
	public static Optional<List<String>> listeQualitiesNameSorted() {
		List<String> listeSorted = new ArrayList<>();
		listeSorted = listeQualitiesName().stream().sorted(Comparator.naturalOrder()).toList();
		return Optional.of(listeSorted);
	}

	/**
	 * @return ListeQualitiesName (nom des qualités) correspond au champs de gauche
	 *         de la liste principale.
	 */
	public static List<String> listeQualitiesName() {
		List<String> receptacleQuality = new ArrayList<>();
		for (String[] quali : _qualitiesStrings) {
			receptacleQuality.add(quali[0]);
		}
		return receptacleQuality;
	}

	/**
	 * @return listeQualitiesEcart (ecart entre les notes par rapport à la qualités
	 *         de l'accord donné en parametre) correspond au champs de droite de la
	 *         liste principale.
	 */
	public static List<String> listeQualitiesEcart() {
		List<String> receptacle = new ArrayList<>();
		for (String[] quali : _qualitiesStrings) {
			receptacle.add(quali[1]);
		}
		return receptacle;
	}

	/**
	 * @param lis
	 * @return une liste plus explosée des ecarts dans les notes de l'accord
	 */
	public static List<Integer> explodeChord(List<Integer> lis) {
		List<Integer> receptacle = new ArrayList<>();

		if (lis.size() < 5) {
			lis.stream().map(c -> c - 12).toList().forEach(item -> {
				receptacle.add(item);
				receptacle.add(item + 12);
			});

			receptacle.sort(Comparator.naturalOrder());
			Application.prtln(receptacle.toString());
			return receptacle;

		} else
			Application.prtln(lis.toString());
		return lis;
	}

}
