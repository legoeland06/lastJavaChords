package myjava.app;

import javax.sound.midi.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Eric Bruneau
 *
 */
public enum PlayerMidi {

	INSTANCE;

	private Sequencer sequencer;
	private Sequence sequence;
	private long tempo;
	private Track track, trackBasse;
	private Grille grille;
	/**
	 * 
	 */
	public float newPos = 0;
	public float newPosBasse = 0;
	/**
	 * 
	 */
	public int volumeNote = 100;

	/**
	 * @param grille
	 * @param tempo
	 */
	public void init(Grille grille, int tempo) {
		grille.setTempo(tempo);
		this.setGrille(grille);
		inject();
//		injectB();
	}

	public float getNewPosBasse() {
		return newPosBasse;
	}

	public void setNewPosBasse(float newPosBasse) {
		this.newPosBasse = newPosBasse;
	}

	private PlayerMidi() {

	}

	/**
	 * @return seuencer
	 */
	public Sequencer getSequencer() {
		return sequencer;
	}

	/**
	 * @param s
	 */
	public void setSequencer(Sequencer s) {
		sequencer = s;
	}

	/**
	 * @return sequence
	 */
	public Sequence getSequence() {
		return sequence;
	}

	/**
	 * @param sequence
	 */
	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return tempo
	 */
	public long getTempo() {
		return tempo;
	}

	/**
	 * @param tempo
	 */
	public void setTempo(long tempo) {
		this.tempo = tempo;
	}

	/**
	 * @return track
	 */
	public Track getTrack() {
		return track;
	}

	/**
	 * @param track
	 */
	public void setTrack(Track track) {
		this.track = track;
	}

	/**
	 * @return trackBasse
	 */
	public Track getTrackBasse() {
		return trackBasse;
	}

	/**
	 * @param trackBasse
	 */
	public void setTrackBasse(Track trackBasse) {
		this.trackBasse = trackBasse;
	}

	/**
	 * @return Grille grille
	 */
	public Grille getGrille() {
		return this.grille;
	}

	/**
	 * @param grille
	 */
	public void setGrille(Grille grille) {
		this.grille = grille;
	}

	/**
	 * @return
	 */
	public float getNewPos() {
		return newPos;
	}

	/**
	 * @param newPos
	 */
	public void setNewPos(float newPos) {
		this.newPos = newPos;
	}

	/**
	 * @return integer volumeNote
	 */
	public int getVolumeNote() {
		return volumeNote;
	}

	/**
	 * @param volumeNote
	 */
	public void setVolumeNote(int volumeNote) {
		this.volumeNote = volumeNote;
	}

	/**
	 * @param track
	 * @param channel
	 * @param veloce
	 * @param played
	 * @param valeurNote
	 * @param _time
	 * @param newPos
	 * @param timming
	 * 
	 * @see #injectBasse()
	 * @see #injectSeq()
	 */
	public void putANote(Track track, int channel, int veloce, boolean played, int valeurNote, Long _time, float newPos,
			Integer timming) {
		Integer velocite = played ? veloce : 0;
		try {

			MidiEvent msgOn = this.makeEvent(144, channel, valeurNote + 36, velocite,
					Math.round(newPos * (3200 / this.tempo)));
			MidiEvent msgOff = this.makeEvent(128, channel, valeurNote + 36, velocite,
					Math.round((newPos + 8 / timming) * (3200 / this.tempo)));
			track.add(msgOn);
			track.add(msgOff);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param sequencer
	 */
	public void stopPlay(Sequencer sequencer) {
		sequencer.stop();
		sequencer.close();
	}

	/**
	 * 
	 */
	public void inject() {
		try {
			
			
			this.setSequencer(MidiSystem.getSequencer());
			this.sequencer.open();
			this.setSequence(new Sequence(Sequence.SMPTE_24, 4));
			this.setTempo(this.getGrille().getTempo());

			this.setNewPos(0);
			this.setNewPosBasse(0);
			this.setVolumeNote(100);
			
			// IMPORTANT ICI CREATION DE LA TRACK DES ACCORDS
			this.setTrack(this.sequence.createTrack());
			this.setTrackBasse(this.sequence.createTrack());
			
			this.sequencer.setSequence(this.getSequence());
			this.sequencer.setTempoInBPM(this.getTempo());
			Map<Integer, Chord> myMap = new HashMap<Integer, Chord>();

			myMap = this.getGrille().getContenuMap();

//			myMap.forEach((index, chord) -> {
//				Application.prtln(index + " " + chord);
//			});

			myMap.forEach((index, chord) -> {

				Chord accPrecedent = index > 0 ? this.getPrevChord(chord) : this.getGrille().getLastChord();
				Chord accSuivant = index < getGrille().getContenuMap().size() ? this.getNextChord(chord)
						: this.getGrille().getFirstChord();
				Boolean commeLePrecedent = (chord.getFondamentale().getName() + chord.getQuality().getQualityName()
						+ chord.getBasse().getName())
						.contentEquals(accPrecedent.getFondamentale().getName()
								+ accPrecedent.getQuality().getQualityName() + accPrecedent.getBasse().getName());
				chord.setPlayed((!commeLePrecedent) 
						|| index % Math.round(Math.random() * 21 + 1) == 0
				|| index % Math.round(Math.random() * 13 + 1) == 0);
				Integer valeur = chord.getFondamentale() != null ? iA(index, chord, accPrecedent, accSuivant)
						: chord.getNotes().get(0).getValue();
				
				putANote(this.getTrackBasse(), 7, 100, true, valeur, 4l / chord.getTime(), this.getNewPosBasse(),
						chord.getTime());
//				Application.prt("valeur après iA() = " + valeur + " incrément = " + index + "\n");
				
				this.setNewPosBasse(this.getNewPosBasse() + (8l / chord.getTime()));

				if (chord.isPlayed()) {
					List<Integer> valNoteToPlay = Harmonie.explodeChord(Harmonie.chordToValues(chord));
					valNoteToPlay.forEach((v) -> {
						if (v <= 12)
							v += 24;
						this.setNewPos(
								this.getNewPos() + (float) valNoteToPlay.indexOf(v) / (8 * valNoteToPlay.size()));

						this.putANote(this.getTrack(), 4, this.getVolumeNote(), chord.isPlayed(), v,
								4l / chord.getTime(), this.getNewPos(), chord.getTime());
						this.setNewPos(
								this.getNewPos() - (float) valNoteToPlay.indexOf(v) / (8 * valNoteToPlay.size()));

					});

				}
				this.setNewPos(this.getNewPos() + (8l / chord.getTime()));

			});
			while (true) {
				// Exit the program when sequencer has stopped playing.
				if (!this.getSequencer().isRunning()) {
					this.getSequencer().stop();
					break;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * calcul de plusieurs modulos fonction de test qui ne rentre pas dans le code
	 * principal
	 */
	public static void moduloNombre() {
		String sortie = "";
		for (int i = 0; i <= 16; i++)
			for (int j = 16; j > 0; j--) {
				sortie = ("i % j = " + i % j);
				Application.prtln(sortie);
			}
	}

	/**
	 * @param v valeur initiale
	 * @return integer : la valeur de la basse encadrée Integer
	 */
	public Integer encadreValeurBasse(Integer v) {
		if (v < -5)
			v += 12;
		if (v > 7)
			v -= 12;
		return v;
	}

	/**
	 * @param chord
	 * @return int howLong
	 */
	public float howLongIsThisChord(Chord chord) {

		/*
		 * initialisation de howlong à la valeur du timer du premier accord la valeur
		 * est l'inverse du timer multiplié par 4 exemple : timer 4 => 1, timer 1 => 4,
		 * et timer 2 => 2
		 * 
		 * howlong est incrémenté à chaque fois que la racine de l'accord du type
		 * rootChord : String < FondamentaleQuality/Basse > suivant est le même.
		 */
		float howLong = chord.getTime() != 0 ? 4 / chord.getTime() : 0;
		Chord nextChord = getNextChord(chord);
		String chordRoot = chord.getFondamentale().getName() + chord.getQuality().getQualityName() + "/"
				+ chord.getBasse().getName();
		String nextChordRoot = nextChord.getFondamentale().getName() + nextChord.getQuality().getQualityName() + "/"
				+ nextChord.getBasse().getName();
		while (chordRoot.contentEquals(nextChordRoot)) {
			howLong += nextChord.getTime() != 0 ? 4 / nextChord.getTime() : 0;
		}
		return howLong;
	}

	/**
	 * @param Chord c
	 * @return the next chord in the grid
	 */
	public Chord getNextChord(Chord c) {
		return this.getGrille().getContenuMap().get(c.getIndex() + 1);
	}

	/**
	 * @param Chord c
	 * @return the prev chrod in the grid
	 */
	public Chord getPrevChord(Chord c) {
		return this.getGrille().getContenuMap().get(c.getIndex() - 1);
	}

	/**
	 * @param compte
	 * @param chord
	 * @param accPrecedant
	 * @param accSuivant
	 * @return
	 */
	public Integer iA(int compte, Chord chord, Chord accPrecedant, Chord accSuivant) {

		Integer alea = (int) Math.random() * (5) + 1;
		Integer alea2 = (int) Math.round(Math.random() * 7 + 1);
		List<Integer> valeur = Harmonie.chordToValues(chord);
		compte = compte % 64;
//		Application.prt(" || compte = " + String.valueOf(compte) + " || ");
		if (chord.getTime() == 4 && !chord.isPlayed() && compte % 4 != 0) {
			if (compte % 13 == 1) {
				return this.encadreValeurBasse(Harmonie.noteToVal(chord.getSecondFromFondamental()));
			} else if ((compte % (alea2)) == 2) {
				return this.encadreValeurBasse(Harmonie.noteToVal(chord.getTierce()));

			} else if (compte % 13 == 3) {
				return this.encadreValeurBasse(Harmonie.noteToVal(chord.getSecondFromFondamental()));
			} else if (compte % 13 == 4) {
				return this.encadreValeurBasse(Harmonie.noteToVal(chord.getQuinte()));
			}

			else if ((compte % (alea)) == 4) {
				return this.encadreValeurBasse(Harmonie.noteToVal(chord.getTierce()));
			}
			int valGet = compte > alea2 ? (compte - alea2) : (alea2 - compte);
			return this.encadreValeurBasse(valeur.get(valGet != 0 ? valGet % valeur.size() : valGet));
		} else if (chord.getTime() == 2 && compte % 2 == 0) {
			return this.encadreValeurBasse(Harmonie.noteToVal(chord.getSecondFromFondamental()));
		} else if (chord.getTime() == 2 && compte % 2 == 1) {
			return this.encadreValeurBasse(Harmonie.noteToVal(chord.getSecondFromFondamental()));
		}
//		Application.printLigne();
//		Application.prt(
//				Harmonie.noteToVal(Harmonie.chordToComponents(chord).get(compte % Harmonie.chordToComponents(chord).size())));

		return Harmonie.noteToVal(chord.getFondamentale());
	}

	/**
	 * @param grilleAccord Injection de la track de basse
	 * @see #iA(int, Chord, Chord, Chord)
	 */
	public void injectBasse() {
		try {

			// // IMPORTANT ICI CREATION DE LA TRACK DE LA BASSE
			this.trackBasse = this.sequence.createTrack();

			sequencer.setSequence(this.sequence);

			sequencer.setTempoInBPM(this.tempo);

			/*
			 * ICI on récupère le contenu de la grille
			 */
			List<Chord> maListe = getGrille().getContenu();

			this.setNewPos(0);

			for (int i = 0; i < maListe.size(); i++) {
				Chord accEnCours = maListe.get(i);
				Chord accSuivant = i == maListe.size() - 1 ? maListe.get(0) : maListe.get(i + 1);
				Chord accPrecedent = i > 0 ? maListe.get(i - 1) : maListe.get(maListe.size() - 1);
				Integer valeur = accEnCours.getFondamentale() != null ? iA(i, accEnCours, accPrecedent, accSuivant)
						: accEnCours.getNotes().indexOf(0);
				putANote(this.trackBasse, 7, 100, true, valeur, 4l / accEnCours.getTime(), newPos,
						accEnCours.getTime());
				Application.prt("valeur après iA() = " + valeur + " incrément = " + i + "\n");
				newPos += 8l / accEnCours.getTime();
			}

			while (true) {
				// Exit the program when sequencer has stopped playing.
				if (!sequencer.isRunning()) {
					sequencer.stop();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("OOPPS: " + e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * 
	 */
	public void play() {
		try {
			sequencer.setSequence(this.sequence);
			sequencer.start();
			while (true) {
				// Exit the program when sequencer has stopped playing.
				if (!sequencer.isRunning()) {
					sequencer.stop();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void stop() {
		try {
			sequencer.stop();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @param command
	 * @param channel
	 * @param note
	 * @param velocity
	 * @param tick
	 * @return MidiEvent
	 * @see #putANote(Track, int, int, boolean, int, Long, float, Integer)
	 */
	public MidiEvent makeEvent(int command, int channel, int note, int velocity, int tick) {

		MidiEvent event = null;

		try {
			ShortMessage a = new ShortMessage();

			a.setMessage(command, channel, note, velocity);

			event = new MidiEvent(a, tick);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return event;
	}
}