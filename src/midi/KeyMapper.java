package midi;

import processing.core.PApplet;
import processing.core.PVector;

public class KeyMapper {

	private Midi midi;

	private int[][] pitches = { { 30, 40, 50, 60 }, { 70, 80, 90, 100 },
			{ 30, 40, 50, 60 }, { 70, 80, 90, 100 } };

	private int[] lastLeft;
	private int[] lastRight;
	private PApplet p;
	public KeyMapper(PApplet p) {
		this.p = p;
		lastLeft = new int[4];
		lastRight = new int[4];
	}

	public void map(int[] v) {

		int hand = v[0];

		if (hand == 0) {
			processTrigger(lastLeft, v);

		} else if (hand == 1) {
			processTrigger(lastRight, v);
		}
	}

	private void processTrigger(int[] side, int[] v) {

		int key = v[1];
		int note = v[2];
		int depth = v[3];

		if (key == -1) {
			// note off
			midi.noteOff(pitches[side[1]][side[2]], -1);
			side = null;
			return;
		} else if (key == side[1] && note == side[2]) {
			return;
		} else {
			if(side != null) midi.noteOff(pitches[side[1]][side[2]], -1);
			midi.noteOn(pitches[key][note], -1);
			side = v;
		}

	}

}
