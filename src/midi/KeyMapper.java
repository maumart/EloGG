package midi;

import java.util.Arrays;

import controlP5.Println;
import processing.core.PApplet;
import processing.core.PVector;

public class KeyMapper {

	private Midi midi;

	private int[][] pitches = { { 30, 40, 50, 60 }, { 70, 80, 90, 100 },
			{ 30, 40, 50, 60 }, { 70, 80, 90, 100 } };

	private int[] lastLeft = { -1, -1, -1, -1};
	private int[] lastRight = { -1, -1, -1, -1};
	private PApplet p;

	public KeyMapper(PApplet p) {
		midi = new Midi(p);
		this.p = p;
	}

	public void map(int[] v) {
		
		p.println("hand: "+v[0]+" keybar: "+v[1]+" note: "+v[2]);
		
		int hand = v[0];

		if (hand == 0) {
			processTrigger(lastLeft, v);

		} else if (hand == 1) {
			processTrigger(lastRight, v);
		}
	}

	private void processTrigger(int[] side, int[] v) {
		
		p.println("side "+side);
		int key = v[1];
		int note = v[2];
		int depth = v[3];
		if (key == -1) {
			// note off
			p.println("stop sound 0");
			if (side[0] != -1) {
				p.println("stop sound 1");
				midi.noteOff(pitches[side[1]][side[2]], -1);
				side[0] = -1;
			}
			return;
		} else if (side[0] != -1 && key == side[1] && note == side[2]) {
			p.println("continue sound 1");
			return;
		} else {
			if (side[0] != -1){
				p.println("stop sound befor start new ");
				midi.noteOff(pitches[side[1]][side[2]], -1);
				side[0] = -1;
			} else {
				
			}
			p.println("start new sound 1");
			midi.noteOn(pitches[key][note], -1);
			side[0] = v[0];
			side[1] = v[1];
			side[2] = v[2];
			side[3] = v[3];
		}

	}

}
