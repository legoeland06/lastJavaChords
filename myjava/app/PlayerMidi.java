package myjava.app;

import javax.sound.midi.*;
import java.util.*;

/**
 * @author Eric Bruneau
 *
 */
public class PlayerMidi {

	private static Sequencer sequencer;
	private Sequence sequence;
	private long tempo;
	private Track track, trackBasse;
	public static float newPos = 0;
	public static int volumeNote = 100;

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
	 * 
	 */
	public PlayerMidi() {
		super();
	}

	/**
	 * @param leTempo
	 */
	public PlayerMidi(long leTempo) {
		super();
		this.tempo = leTempo;
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
	 * @see #injectBasse(Grille)
	 * @see #injectSeq(Grille)
	 */
	public void putANote(Track track, int channel, int veloce, boolean played, int valeurNote, Long _time, float newPos,
			Integer timming) {
		Integer velocite = played ? veloce : 0;
		try {

			MidiEvent msgOn = this.makeEvent(144, channel, valeurNote + 36, velocite,
					Math.round(newPos * (3200 / tempo)));
			MidiEvent msgOff = this.makeEvent(128, channel, valeurNote + 36, velocite,
					Math.round((newPos + 8 / timming) * (3200 / tempo)));
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
	 * @param grilleAccord
	 * Injecteur de sequence piano accord
	 */
	public void injectSeq(Grille grilleAccord) {

		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			this.sequence = new Sequence(Sequence.SMPTE_24, 4);
			this.tempo = grilleAccord.getTempo();
			newPos = 0l;
			volumeNote = 100;

			// IMPORTANT ICI CREATION DE LA TRACK DES ACCORDS
			this.track = this.sequence.createTrack();

			sequencer.setSequence(this.sequence);

			sequencer.setTempoInBPM(this.tempo);
			// Long deplacementCurseur = 0f;
			List<Chord> maListe = grilleAccord.getContenu();
			for (int f = 0; f < maListe.size(); f++) {
				Chord accEnCours = maListe.get(f);
				Chord accSuivant = f == maListe.size() - 1 ? maListe.get(0) : maListe.get(f + 1);
				Chord accPrecedent = f > 0 ? maListe.get(f - 1) : maListe.get(maListe.size() - 1);
				Boolean commeLePrecedent = (accEnCours.getFondamentale().getName()
						+ accEnCours.getQuality().getQualityName() + accEnCours.getBasse().getName())
						.contentEquals(accPrecedent.getFondamentale().getName()
								+ accPrecedent.getQuality().getQualityName() + accPrecedent.getBasse().getName());
				accEnCours.setPlayed((!commeLePrecedent) || f % Math.round(Math.random() * 8 + 1) == 0);
				if (accEnCours.isPlayed()) {

					List<Integer> valNoteToPlay = Quality.explodeChord(accEnCours.chordToValues());
					valNoteToPlay.forEach((v) -> {
						if (v <= 12)
							v += 24;

						newPos += (float) valNoteToPlay.indexOf(v) / (8 * valNoteToPlay.size()); // BIZZARE DE 8 à 4

						putANote(this.track, 4, volumeNote, accEnCours.isPlayed(), v, 4l / accEnCours.getTime(), newPos,
								accEnCours.getTime());
						newPos -= (float) valNoteToPlay.indexOf(v) / (8 * valNoteToPlay.size()); // BIZZARE DE 8 à 4

					});
				}

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
			e.printStackTrace();
			System.exit(-1);
		}
		sequencer.stop();
	}

	/**
	 * calcul de plusieurs modulos
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
		if (v < -8)
			v += 12;
		if (v > 16)
			v -= 12;
		return v;
	}

	/**
	 * @param compte
	 * @param c
	 * @param accPrecedant
	 * @param accSuivant
	 * @return Integer : la note envoyée est faite après passage par
	 * cette méthode
	 * <pre>
	 * {@code
		 * Integer alea = (int) Math.random() * (16) + 1;
		 * Integer alea2 = (int) Math.random() * (4) + 1;
		 * List<Integer> valeur = c.chordToValues();
		 * compte %= 16;
		 * Application.prt(" || compte = " + String.valueOf(compte) + " || ");
		 * if (c.getTime() == 4 && !c.isPlayed()) {
		 * 	  if ((compte % (4 * alea)) == 1) {
		 * 		 return this.encadreValeurBasse(valeur.get(0) + 2);
		 * 	  } else if ((compte % (4 * alea)) == 2) {
		 * 			return this.encadreValeurBasse(valeur.get(1));
		 * 	  }
		 * 	  if (compte%(8*alea2)==3) {
		 * 		 return this.encadreValeurBasse(Note.noteToVal(c.getFondamentale())-1);
		 * 	  } else if ((compte % (4 * alea)) == 3) {
		 * 			return this.encadreValeurBasse(valeur.get(2));
		 * 	  }
		 * int valGet = compte > alea2 ? (compte - alea2) : (alea2 - compte);
		 * }
		 * return Note.noteToVal(c.getFondamentale());
		}</pre>
	 */
	public Integer iA(int compte, Chord c, Chord accPrecedant, Chord accSuivant) {

		Integer alea = (int) Math.random() * (16) + 1;
		Integer alea2 = (int) Math.random() * (4) + 1;
		List<Integer> valeur = c.chordToValues();
		compte %= 16;
		Application.prt(" || compte = " + String.valueOf(compte) + " || ");
		if (c.getTime() == 4 && !c.isPlayed()) {
			if ((compte % (4 * alea)) == 1) {
				return this.encadreValeurBasse(valeur.get(0) + 2);
			} else if ((compte % (4 * alea)) == 2) {
				return this.encadreValeurBasse(valeur.get(1));
			}
			if (compte%(8*alea2)==3) {
				return this.encadreValeurBasse(Note.noteToVal(c.getFondamentale())-1);
			} else if ((compte % (4 * alea)) == 3) {
				return this.encadreValeurBasse(valeur.get(2));
			}
			int valGet = compte > alea2 ? (compte - alea2) : (alea2 - compte);
			return this.encadreValeurBasse(valeur.get(valGet != 0 ? (valeur.size() - 1) % valGet : valGet - 1));
		}
		return Note.noteToVal(c.getFondamentale());
	}

	/**
	 * @param grilleAccord
	 * Injection de la track de basse
	 * @see #iA(int, Chord, Chord, Chord)
	 */
	public void injectBasse(Grille grilleAccord) {
		try {

			// // IMPORTANT ICI CREATION DE LA TRACK DE LA BASSE
			this.trackBasse = this.sequence.createTrack();

			sequencer.setSequence(this.sequence);

			sequencer.setTempoInBPM(this.tempo);
			List<Chord> maListe = grilleAccord.getContenu();
			float newPos = 0;

			for (int increment = 0; increment < maListe.size(); increment++) {
				Chord accEnCours = maListe.get(increment);
				Chord accSuivant = increment == maListe.size() - 1 ? maListe.get(0) : maListe.get(increment + 1);
				Chord accPrecedent = increment > 0 ? maListe.get(increment - 1) : maListe.get(maListe.size() - 1);
				Integer valeur = iA(increment, accEnCours, accPrecedent, accSuivant);
				putANote(this.trackBasse, 7, 100, true, valeur, 4l / accEnCours.getTime(), newPos,
						accEnCours.getTime());
				Application.prt("valeur après iA() = " + valeur + " incrément = " + increment + "\n");
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