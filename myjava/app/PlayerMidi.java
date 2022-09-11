package myjava.app;

import javax.sound.midi.*;
import java.util.*;

public class PlayerMidi {

	private static Sequencer sequencer;
	private Sequence sequence;
	private long tempo;
	private Track track, trackBasse;
	public static long newPos = 0;

	public Sequencer getSequencer() {
		return sequencer;
	}

	public void setSequencer(Sequencer s) {
		sequencer = s;
	}

	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	public long getTempo() {
		return tempo;
	}

	public void setTempo(long tempo) {
		this.tempo = tempo;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public Track getTrackBasse() {
		return trackBasse;
	}

	public void setTrackBasse(Track trackBasse) {
		this.trackBasse = trackBasse;
	}

	public PlayerMidi() {
		super();
	}

	public PlayerMidi(long leTempo) {
		super();
		this.tempo = leTempo;
	}

	public void putANote(Track track, int channel, boolean played, int valeurNote, Long _time, long newPos,
			Integer timming) {
		Integer velocite = played ? 100 : 0;
		try {

			MidiEvent msgOn = this.makeEvent(144, channel, valeurNote + 36, velocite,
					Math.round(newPos * (60 * 4 / tempo)));
			MidiEvent msgOff = this.makeEvent(128, channel, valeurNote + 36, velocite,
					Math.round((newPos + 8 / timming) * (60 * 4 / tempo)));
			track.add(msgOn);
			track.add(msgOff);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopPlay(Sequencer sequencer) {
		sequencer.stop();
		sequencer.close();
	}

	public void injectSeq(Grille grilleAccord) {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			this.sequence = new Sequence(Sequence.PPQ, 4);
			this.tempo = grilleAccord.getTempo();
			newPos = 0l;

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
						v += 12;
						if (v < 4)
							v += 12;
						if (v < 16)
							v += 12;
						if (v > 70)
							v -= -12;
						if (v > 58)
							v -= 12;
						putANote(this.track, 4, accEnCours.isPlayed(), v, 4l / accEnCours.getTime(), newPos,
								accEnCours.getTime());

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

	public static void moduloNombre() {
		String sortie = "";
		for (int i = 0; i <= 16; i++)
			for (int j = 16; j > 0; j--) {
				sortie = ("i % j = " + i % j);
				Application.prtln(sortie);
			}
	}

	public Integer encadreValeurBasse(Integer v) {
		if (v < 0)
			v += 12;
		if (v > 18)
			v -= 12;
		return v;
	}

	public Integer iA(int compte, Chord c, Chord accPrecedant, Chord accSuivant) {

		Integer alea = (int) Math.random() * (16) + 1;
		List<Integer> valeur = c.chordToValues();
		compte %= 36;
		if (c.getTime() == 4 && !c.isPlayed()) {
			if ((compte % (4 * alea)) == 1) {
				return this.encadreValeurBasse(valeur.get(0) + 2);
			} else if ((compte % (4 * alea)) == 3) {
				return this.encadreValeurBasse(valeur.get(1));
			} else if ((compte % (4 * alea)) == 4) {
				return this.encadreValeurBasse(valeur.get(2));
			} else if ((compte % 16 + 1) == 4) {
				return this.encadreValeurBasse(valeur.get(0) - 1);
			}
			return this.encadreValeurBasse(valeur.get(compte > alea ? (compte - alea) % 4 : (alea - compte) % 4));
		}
		return Note.noteToVal(c.getFondamentale());
	}

	public void injectBasse(Grille grilleAccord) {
		try {

			// // IMPORTANT ICI CREATION DE LA TRACK DE LA BASSE
			this.trackBasse = this.sequence.createTrack();

			sequencer.setSequence(this.sequence);

			sequencer.setTempoInBPM(this.tempo);
			List<Chord> maListe = grilleAccord.getContenu();
			long newPos = 0;

			for (int increment = 0; increment < maListe.size(); increment++) {
				Chord accEnCours = maListe.get(increment);
				Chord accSuivant = increment == maListe.size() - 1 ? maListe.get(0) : maListe.get(increment + 1);
				Chord accPrecedent = increment > 0 ? maListe.get(increment - 1) : maListe.get(maListe.size() - 1);
				Integer valeur = iA(increment, accEnCours, accPrecedent, accSuivant);
				putANote(this.trackBasse, 7, true, valeur, 4l / accEnCours.getTime(), newPos, accEnCours.getTime());
				Application.prt(valeur+":");
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

	public void stop() {
		try {
			sequencer.stop();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public MidiEvent makeEvent(int command, int channel, int note, int velocity, int tick) {

		MidiEvent event = null;

		try {

			// ShortMessage stores a note as command type, channel,
			// instrument it has to be played on and its speed.
			ShortMessage a = new ShortMessage();
			a.setMessage(command, channel, note, velocity);

			// A midi event is comprised of a short message(representing
			// a note) and the tick at which that note has to be played
			event = new MidiEvent(a, tick);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return event;
	}
}
// 11:2:7:10:3:5:7:7:9:11:2:6:7:10:3:6:11:13: 11 :15:5:5:10:14:3:3:7:7:9:11:2:6:7:9: 7  :11:1:3:6:10:11:13:11:15:5:7:10:14:3:5:7:7:1:1:1:1:11:2:7:10:3:5:7: 7 :9:11:2:6:7:10:3:6:11:13:15: 11 :5:7:10:14:3:5:7:7:9:11:2:6:7:9:11:11:1: 3 :6:10:11:13:15:15:5:7:10:14:3:5: 7 :7:1:1:1:1:11:2:7:10:3:5:7: 7 :9:11:2:6:7:10:3:6:11:11: 11 :15:5:5:10:10:3:5:7:7:9: 11 :2:6:7:9:11:11:1:3:6:10:11:11: 15 :11:5:7:10:14:3:3:7:7:1:1:1:1:11:2:7:10:3:5: 7: 3:9:11:2:6:7:10:3:6:11:13:15:15:5:7:10:14:3:5:7:3:9:11:2:6:7:9:11:7:1:3:6:10:11:13:11:15:5:5:10:14:3:5:7:7:1:1:1:1:11:2:7:10:3:5:7:3:9:11:2:6:7:10:3:6:11:13:11:11:5:5:10:14:3:3:3:7:9:9:2:6:7:9:11:11:1:3:6:10:11:13:15:11:5:7:10:14:3:3:7:7:1:1:1:1:11:2:7:10:3:3:3:3:9:11:2:6:7:10:3:6:11:13:15:15:5:7:10:14:3:5:7:7:9:9:2:6:7:9:7:7:1:3:6:10:11:11:11:15:5:7:10:14:3:5:3:7:1:1:1:1:11:2:7:10:3:5:3:3:9:9:2:6:7:10:3:6:11:13:15:15:5:5:10:14:3:5:7:3:9:11:2:6:7:7:11:11:1:3:6:10:11:13:15:15:5:7:10:14:3:3:3:7:1:1:1:1:11:2:7:10:3:5:7:7:9:11:2:6:7:10:3:6:11:13:11:15:5:5:10:14:3:5:7:7:9:11:2:6:7:9:7:7:1:3:6:10:11:13:11:11:5:7:10:14:3:5:7:7:1:1:1:1:
// 11:2:7:10:3:5:7:7:9:11:2:6:7:10:3:6:11:13: 15 :15:5:5:10:14:3:3:7:7:9:11:2:6:7:9: 11 :11:1:3:6:10:11:13:15:11:5:7:10:14:3:3:7:7:1:1:1:1:11:2:7:10:3:5:7: 3 :9:11:2:6:7:10:3:6:11:13:15: 15 :5:5:10:14:3:5:7:7:9:11:2:6:7:9:11:11:1: 1 :6:10:11:13:15:15:5:7:10:14:3:5: 3 :7:1:1:1:1:11:2:7:10:3:3:7: 3 :9:11:2:2:7:10:3:6:11:13: 15 :15:5:7:10:10:3:3:3:7:9: 9  :2:6:7:9:11:11:1:3:6:10:11:11: 11 :15:5:7:10:14:3:5:7:7:1:1:1:1:11:2:7:10:3:5: 3: 3:9:11:2:6:7:10:3:6:11:13:11:15:5:5:10:14:3:5:7:3:9:9:2:6:7:9:11:11:1:3:6:10:11:13:11:11:5:5:10:10:3:5:3:3:1:1:1:1:11:2:7:10:3:5:7:3:9:11:2:6:7:10:3:6:11:13:15:15:5:7:10:14:3:5:7:7:9:11:2:6:7:9:7:11:1:1:6:6:11:13:15:15:5:7:10:14:3:3:7:3:1:1:1:1:11:2:7:10:3:5:7:7:9:11:2:6:7:10:3:6:11:13:15:15:5:7:10:14:3:5:7:7:9:11:2:6:7:9:7:11:1:3:6:10:11:13:11:15:5:7:10:14:3:5:3:7:1:1:1:1:11:2:7:10:3:5:7:7:9:11:2:6:7:10:3:6:11:13:11:15:5:7:10:14:3:5:7:7:9:9:2:6:7:7:7:11:1:3:6:6:11:13:15:15:5:7:10:14:3:5:7:7:1:1:1:1:11:2:7:10:3:5:7:7:9:11:2:6:7:10:3:6:11:13:15:15:5:7:10:10:3:3:3:7:9:11:2:6:7:7:11:7:1:3:6:6:11:13:15:15:5:5:10:14:3:3:7:7:1:1:1:1:
