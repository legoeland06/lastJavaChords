package myjava.app;

import java.util.*;
import java.util.List;

/**
 * @author EricBruneau
 */
public final class Chord implements Transposable<Chord>{

	private int time = 2;
	private Note fondamentale;
	private Quality quality;
	private Note basse;
	private int multi;
	private boolean played = true;

	private static char SEPARATE_INT = ':';
	private static char SEPARATE_BASSE = '/';
	private static char SEPARATE_MULTI = 'X';
	private static char DIEZE = '#';
	private static char BEMOL = 'b';

	/**
	 * @param time
	 * @param fondamentale
	 * @param quality
	 * @param basse
	 */
	public Chord(int time, Note fondamentale, Quality quality, Note basse) {

		super();
		this.time = time;
		this.fondamentale = fondamentale;
		this.quality = quality;
		this.basse = basse;
	}

	/**
	 * @param strChord
	 */
	public Chord(String strChord) {
		super();
	}

	/**
	 * Constructeur vide
	 */
	public Chord() {
		super();
	}

	/**
	 * @return this.time
	 */
	public int getTime() {
		return this.time;
	}

	/**
	 * @param time
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return Note
	 */
	public Note getFondamentale() {
		return this.fondamentale;
	}

	/**
	 * @param fondamentale
	 */
	public void setFondamentale(Note fondamentale) {
		this.fondamentale = fondamentale;
	}

	/**
	 * @return Quality
	 */
	public Quality getQuality() {
		return this.quality;
	}

	/**
	 * @param quality
	 */
	public void setQuality(Quality quality) {
		this.quality = quality;
	}

	/**
	 * @return Note
	 */
	public Note getBasse() {
		return this.basse;
	}

	/**
	 * @param basse
	 */
	public void setBasse(Note basse) {
		this.basse = basse;
	}

	/**
	 * @return int
	 */
	public int getMulti() {
		return this.multi;
	}

	/**
	 * @param multi
	 */
	public void setMulti(int multi) {
		this.multi = multi;
	}

	/**
	 * @return boolean
	 */
	public boolean isPlayed() {
		return this.played;
	}

	/**
	 * @param played
	 */
	public void setPlayed(boolean played) {
		this.played = played;
	}

	/**
	 * @return List<Note> componentsOfChord
	 * 
	 *         methode qui renvoi un Objet List<Note> il transforme un accord vers
	 *         une liste d'objets Note
	 */
	public List<Note> chordToComponents() {

		List<Integer> values = chordToValues();

		/*
		 * Attention : map stream et map index pas sur le 0 tous les deux
		 */
		List<Note> componentsOfChord = values != null ? values.stream().map(c -> Note.valToNote(c - 1)).toList() : null;

		return componentsOfChord;
	}

	/**
	 * @return List<Integer> valuesChordIntList
	 *
	 *         méthode qui renvoi un Objet List<Integer>
	 * 
	 *         Transforme un <b>accord</b> précis (ex Cm7b5/D) en <b>StringListe de
	 *         ecartNoteInteger</b> sans prendre en compte la basse
	 * 
	 * @param this : de la forme Chord
	 * 
	 */
	public List<Integer> chordToValues() {

		/*
		 * valuesChordIntList est la liste qui récupère les int écarts entre les notes
		 * de l'accords grace au tableau Quality.listeQualiesName() et le tableau
		 * Quality.listeQualitiesEcart() tous les dexu issues du tableau 2D
		 * _qualitiesStrings
		 */

		List<Integer> valuesChordIntList = new ArrayList<>();

		String qualityName = this.getQuality().getQualityName();

		/*
		 * on récupère la fondamentale de l'accord
		 */
		Note rootNote = this.getFondamentale();

		/*
		 * on recupère la valeur de la fondamentale dans rootNoteToVal
		 */
		Integer rootNoteToVal = Note.noteToVal(rootNote);

		/*
		 * on recupère l'index (qualityNameIndex) de la quality de l'accord s'il existe,
		 * sinon qualityNameIndex=0
		 */
		Integer qualityNameIndex = Quality.listeQualitiesName().contains(qualityName)
				? Quality.listeQualitiesName().indexOf(qualityName)
				: 0;

		/*
		 * on en déduit la qualityEcartsString qui en résulte dans le tableaux de
		 * correspondances le tableau listeQualitiesEcart.
		 */
		String qualityEcartsString = Quality.listeQualitiesEcart().get(qualityNameIndex);

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
	public String parseTimerFromChord(String chaine) {
		/*
		 * initialisation de la chaineRetour
		 */
		String chaineRetour = "";

		/*
		 * position du séparateur de timer
		 */
		Integer posSepInt = chaine.indexOf(SEPARATE_INT);
		/*
		 * si il y a un timer, alors on récupère le int tu timer et on efface le bloc
		 * timer
		 */
		if (posSepInt >= 0) {
			this.setTime(Integer.parseInt(chaine.substring(0, posSepInt)));
			chaineRetour = chaine.substring(posSepInt + 1);
		} else {
			/*
			 * sinon on met un timer de 4 par defaut et on ne touche à rien et on retourne
			 * la chaine inchangée
			 */
			this.setTime(4);
			return chaine;
		}
		/*
		 * dans tous les cas une chaine est retournée, changée ou pas et le timer est
		 * setté par défaut ou par parametre
		 */
		return chaineRetour;
	}

	/**
	 * @param chaine
	 * @return String chaine
	 */
	public String parseMultiFromChord(String chaine) {
		/*
		 * init de chaineRetour
		 */
		String chaineRetour = "";

		/*
		 * il y a t-il un multi ???
		 */
		int posSepMulti = chaine.indexOf(SEPARATE_MULTI);
		if (posSepMulti != -1) {
			/*
			 * si oui alors on récupère le paramètre multi proposé et on efface le bloc
			 * multi de la chaine
			 */
			this.setMulti(Integer.parseInt(chaine.substring(posSepMulti + 1)));
			chaineRetour = chaine.substring(0, posSepMulti);
		} else {
			/*
			 * sinon, on set le param multi à 1 et on ne change rien à la chaine et on
			 * renvoi la chaine
			 */
			this.setMulti(1);
			return chaine;
		}
		/*
		 * dans tous les cas la chaine est renvoyée modifiée ou non un multi est setté
		 * par défaut ou par demande de paramètre dans la chaine
		 */
		return chaineRetour;
	}

	/**
	 * @param chaine
	 * @return Chord
	 * 
	 * {@summary C'est la méthode centrale de l'application
	 * elle va servir pour parser n'importe quel accord passé en paramètre
	 * sous la forme C, C# Cm Am7b5 FX4 2:AbM7#9/F }
	 */
	public Chord parseToChord(String chaine) {

		/*
		 * cas particuliers de 1 seule lettre
		 */
		if (chaine.length() == 1) { // cas A B C D E F G
			setFondamentale(Note.stringToNote(chaine));
			setTime(4);
			setMulti(1);
			setBasse(getFondamentale());
			setQuality(new Quality(" "));
			return this;
		} else if (chaine.length() == 2) {
			/*
			 * sinon cas particulier de 2 lettres
			 */
			setTime(4);
			setMulti(1);
			/*
			 * il y a t-il des bemols ou des dieze en deuxième position ?
			 */
			if (chaine.indexOf(BEMOL) == 1 || chaine.indexOf(DIEZE) == 1) {
				/*
				 * si oui on prend le tout pour fixer la fondamentale et on fixe la basse et on
				 * fixe la qualité et on retourne le l'accord formaté
				 */
				setFondamentale(Note.stringToNote(chaine));
				setBasse(getFondamentale());
				setQuality(new Quality(" "));
				return this;
			} else {

				/*
				 * si il n'y avait pas de dièze ni de bémols alors la deuxième lettre est la
				 * qualité de l'accord..., on la récupère ainsi que la fondamentale, on en
				 * deduit la basse ensuite on nettoie chaine des ces blocs qui viennent d'être
				 * traités enfin on retourne l'accord ainsi formaté
				 */

				setFondamentale(Note.stringToNote(String.valueOf(chaine.charAt(0))));
				setQuality(new Quality(String.valueOf(chaine.charAt(1))));
				setBasse(getFondamentale());
				chaine = String.valueOf(chaine.charAt(0));
				return this;
			}
		}

		/*
		 * Tout les autres cas
		 */

		/*
		 * parsing du paramètre timer
		 */
		chaine = parseTimerFromChord(chaine);

		/*
		 * parsing du paramètre multi
		 */
		chaine = parseMultiFromChord(chaine);

		/*
		 * il y a t il des dieze ou des bemols ? A la fin de cette condition, la
		 * fondamentale est récupérée et la chaine en est nettoyée
		 */
		if (chaine.indexOf(DIEZE) == 1 || chaine.indexOf(BEMOL) == 1) {
			/*
			 * si oui on en déduit la fondamentale
			 */
			setFondamentale(Note.stringToNote(chaine.substring(0, 2)));
			/*
			 * et on efface le bloc fondamentale de la chaine
			 */
			chaine = chaine.substring(2);
		} else {
			/*
			 * sinon on en déduit la fondamentale ne fait qu'une lettre, on la récupère et
			 * on l'efface de la chaine
			 */
			setFondamentale(Note.stringToNote(String.valueOf(chaine.charAt(0))));
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
			setBasse(Note.stringToNote(chaine.substring(chaine.indexOf(SEPARATE_BASSE) + 1)));
			chaine = chaine.substring(0, chaine.indexOf(SEPARATE_BASSE));
		} else {
			/*
			 * sinon on récupère la fondamentale
			 */
			setBasse(getFondamentale());
		}

		/*
		 * la chaine restante est soit " ", soit une qualityStringName on la récupère et
		 * on renvois le Chord formaté par
		 */
		if (chaine.length() > 0) {
			setQuality(new Quality(chaine));
		} else
			setQuality(new Quality(" "));

		return this;
	}
	
	/**
	 * @return Note
	 * revois la quinte de l'accord en traitant la fondamentale
	 * et en testant si l'accord (la quinte) est simple, diminuée ou augmentée
	 */
	public Note getQuinte() {

		if (this.isAugmented()) return this.getFondamentale().transpose(8);
		if (this.isDimined()) return this.getFondamentale().transpose(6);
		return this.getFondamentale().transpose(7);
		
	}
	
	/**
	 * @return Note seconde of Chord
	 */
	public Note getSecondInChord() {
		return this.getFondamentale().transpose(2);
	}
	
	/**
	 * @return true if this chord is minor, false if this chord is not minor
	 */
	public boolean isMinor() {
		
		return this.getQuality().getQualityName().contains("m");
	}
	
	/**
	 * @return true if this chord is aug, +5, or #5 (augmented chord)
	 * return false if not.
	 */
	public boolean isAugmented() {
		return
			this.getQuality().getQualityName().contains("aug") || 
			this.getQuality().getQualityName().contains("+5") ||
			this.getQuality().getQualityName().contains("#5");
	}
	
	/**
	 * @return true if this chors is a alteration alt,dim, -5, b5
	 */
	public boolean isDimined() {
		return
				this.getQuality().getQualityName().contains("alt") || 
				this.getQuality().getQualityName().contains("dim") || 
				this.getQuality().getQualityName().contains("-5") ||
				this.getQuality().getQualityName().contains("b5");
	}
	
	@Override
	public String toString() {
		return getTime() + ":" + getFondamentale().getName() + getQuality().getQualityName() + "/" + getBasse();
	}

	@Override
	public Chord transpose(Integer t) {
		Note newFondamentale = this.getFondamentale().transpose(t);
		this.setFondamentale(newFondamentale);
		// TODO Auto-generated method stub
		return this;
	}

}
