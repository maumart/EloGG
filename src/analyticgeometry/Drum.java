package analyticgeometry;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Drum {
	List<DrumSingle> _myDrums = new ArrayList<>();

	private float _myNumbrOfDrums; // Anzahl Saiten
	private float _myDrumSpace; // Abstand Saiten
	private float _myFredDistance; // Abstand COM - Tail

	public PApplet p;
	public boolean debug = true;
	private Midi midi;

	private boolean fredValue = false;

	public Drum(float _myNumbrOfDrums, float _myDrumSpace, float _myFredDistance, PApplet p, Midi midi) {
		super();
		this._myNumbrOfDrums = _myNumbrOfDrums;
		this._myDrumSpace = _myDrumSpace;
		this._myFredDistance = _myFredDistance;

		this.p = p;
		this.midi = midi;

		generateDrums(_myNumbrOfDrums);
	}

	private void generateDrums(float _myNumbrOfDrums) {
		_myDrums.clear();
		if (_myNumbrOfDrums < 1)
			return;
		float padding = -(_myNumbrOfDrums - 1) / 2;
		for (int i = 0; i < _myNumbrOfDrums; i++) {
			_myDrums.add(new DrumSingle(padding, i));
			padding += 1;

			// System.out.println(padding);
		}
	}

	public void draw(Player player) {
		p.stroke(255, 0, 255);
		p.strokeWeight(2);

		for (DrumSingle myString : _myDrums) {
			p.stroke(0, 255, 255);
			p.line(myString.start().x, myString.start().y, myString.end().x, myString.end().y);
		}

		// Draw Player
		// p.ellipse(player.getHandLeftAbsolute().x,player.getHandLeftAbsolute().y,
		// 10, 10);
		// p.ellipse(player.getHandRightAbsolute().x,
		// player.getHandRightAbsolute().y, 10, 10);
	}

}
