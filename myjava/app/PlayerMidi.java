package myjava.app;

import javax.sound.midi.*;
import java.util.*;

public class PlayerMidi {

	private Sequencer sequencer;
	private Sequence sequence;
	private Integer tempo;
	private Integer compte = 0;
	private Track track, trackBasse;

	// TODO : private boolean walking = false;
	
	public Sequencer getSequencer() {
		return sequencer;
	}

	public void setSequencer(Sequencer sequencer) {
		this.sequencer = sequencer;
	}

	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public Integer getCompte() {
		return compte;
	}

	public void setCompte(Integer compte) {
		this.compte = compte;
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
		this.tempo = 60;
	}

	public PlayerMidi(Integer leTempo) {
		super();
		this.tempo = leTempo;
	}

	public void putANote(Track track, boolean played,int valeurNote, Integer tempo, Long _time, long newPos, Integer timming) {
		Integer velocite = played ? 100 : 0;
		try {
			
			MidiEvent msgOn = this.makeEvent(144, 1, valeurNote + 48, velocite,
					Math.round(newPos * (60 * 4 / tempo) + 60 * 4 / tempo));
			MidiEvent msgOff = this.makeEvent(128, 1, valeurNote + 48, velocite,
					Math.round((newPos + 8 / timming) * (60 * 4 / tempo) + 60 * 4 / tempo));
			track.add(msgOn);
			track.add(msgOff);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopPlay(Sequencer sequencer) {
		this.sequencer.stop();
		this.sequencer.close();
	}

	public void injectSeq(Grille grilleAccord) {
		try {
			this.sequencer = MidiSystem.getSequencer();
			this.sequencer.open();
			this.sequence = new Sequence(Sequence.PPQ, 4);
			this.tempo = grilleAccord.getTempo();

			// IMPORTANT ICI CREATION DE LA TRACK DES ACCORDS
			this.track = this.sequence.createTrack();

			this.sequencer.setSequence(this.sequence);
			
			this.sequencer.setTempoInBPM(this.tempo);
			// Long deplacementCurseur = 0f;
			long newPos = 0;
			for (Chord c : grilleAccord.getContenu()) {
				List<Integer> valNoteToPlay = c.chordToValues();

				for (int valeur : valNoteToPlay) {
					// TODO: série de if pour avoir des renversements ou des accord ouverts
					if (valeur < 4)
						valeur += 24;
					if (valeur < 9)
						valeur += 12;
					if (valeur > 46)
						valeur -= 12;

					putANote(this.track, c.getPlayed(),valeur, this.tempo, 4l / c.getTime(), newPos, c.getTime());
					
				}
				newPos += 8 / c.getTime(); // nouvellePosition;
			}

			while (true) {
				// Exit the program when sequencer has stopped playing.
				if (!this.sequencer.isRunning()) {
					this.sequencer.stop();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("OOPPS: " + e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
		this.sequencer.stop();
	}

	public Note iA(Chord c, boolean commeLePrecedent, Chord accSuivant) {

		Random monRandom = new Random();
		monRandom.ints(0, 4);
		Integer valFondAccSuivant = accSuivant.getFondamentale().noteToVal();
		//Integer valFondAccActuel = Note.noteToVal(c.getFondamental().toString());
		Boolean commeLeSuivant = c.toString().contentEquals(accSuivant.toString());

		if (c.getTime() == 4 && commeLePrecedent && commeLeSuivant) {

			if (compte > 3) {
				int susu = compte;
				compte = 0;
				return accSuivant.chordToComponents().get(susu - 1);
			}
			if (compte == 7 % 3) {
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				compte = 0;
				System.out.println(Note.valToNote(valFondAccSuivant));
				return Note.valToNote(valFondAccSuivant); // on renvoi une note de passage un demiton en dessous
			}
			compte += 1;
			System.out.println(compte);
			return c.chordToComponents().get((c.chordToComponents().size() - 1) % (compte + monRandom.nextInt(4)));

		}
		return ((c.getBasse() == null || c.getBasse().toString() == "") ? c.getFondamentale() : c.getBasse());
	}

	public void injectBasse(Grille grilleAccord) {
		try {

			// // IMPORTANT ICI CREATION DE LA TRACK DE LA BASSE
			this.trackBasse = this.sequence.createTrack();

			this.sequencer.setSequence(this.sequence);

			this.sequencer.setTempoInBPM(this.tempo);
			List<Chord> maListe = grilleAccord.getContenu();
			long newPos = 0;
			String sosisse = "";
			
			List<Chord> list = Collections.synchronizedList(maListe);
			synchronized (list) {
				Iterator<Chord> iterator = list.iterator();
				while (iterator.hasNext()) {
					System.out.println(iterator.toString());
					iterator.next();
				}
			}
			
			Integer cpter = 0;
			for (Chord c : maListe) {

				Chord accSuivant = maListe.get(cpter);
				Boolean commeLePrecedent = (c.toString()).contentEquals(sosisse);
				

				Integer valeur = iA(c, commeLePrecedent, accSuivant).noteToVal();

				putANote(this.trackBasse,true, valeur - 13, this.tempo, 4l / c.getTime(), newPos, c.getTime());

				newPos += 8 / c.getTime(); // nouvellePosition;
				sosisse = c.toString();
				cpter += 1; // Chord accSuivant = c.next();
			}

			while (true) {
				// Exit the program when sequencer has stopped playing.
				if (!this.sequencer.isRunning()) {
					this.sequencer.stop();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("OOPPS: " + e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
		// this.sequencer.stop();
	}

	public void play() {
		try {
			this.sequencer.setSequence(this.sequence);
			//this.sequencer.setLoopCount(1);
			this.sequencer.start();
			while (true) {
				// Exit the program when sequencer has stopped playing.
				if (!this.sequencer.isRunning()) {
					this.sequencer.stop();
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
			this.sequencer.stop();

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
