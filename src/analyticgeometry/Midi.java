package analyticgeometry;

import processing.core.PApplet;

public class Midi {
	// PApplet p;
	midi.Midi midi;

	public Midi(PApplet p) {
		midi = new midi.Midi(p);
	}

	public void playMidi(int myString, int myHead, boolean upAndDown) {
		midi.strumChord(myHead + 1, 3, 1, upAndDown);
		// System.out.println("String " + myString + " Head" + myHead);
	}
}
