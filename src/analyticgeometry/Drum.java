package analyticgeometry;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Drum implements KinectInstrument {
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

	public void update(Player player) {	
		PVector v3 = player.neck.get();

		// Richtungsvektor zu neck
		PVector rv = new PVector(v3.x - player.centerOfMass.x, v3.y - player.centerOfMass.y);
		rv.normalize();
		
		// Ortsvektor -> Orthogonal zu RV
		PVector ov = new PVector(rv.y, -rv.x);
		ov.normalize();

		// Position des Necks
		PVector neckPos = ov.get();
		neckPos.mult(_myFredDistance/2);

		// Position des Freds
		PVector fredPos = ov.get();
		fredPos.mult(-_myFredDistance/2);

		for (DrumSingle myString : _myDrums) {			
			// Start und Ende verschieben
			myString.start().set(neckPos);			
			myString.end().set(fredPos);
			
		}
	}

	@Override
	public void checkFredMatch(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public int checkNeckMatch(Player player) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void checkHeadFred() {
		// TODO Auto-generated method stub

	}
	
	public void draw(Player player) {
		p.stroke(255, 0, 255);
		p.strokeWeight(2);

		for (DrumSingle myString : _myDrums) {
			p.stroke(0, 255, 255);
			//p.line(myString.start().x, myString.start().y, myString.end().x, myString.end().y);
			p.line(myString.start().x, myString.start().y, myString.end().x, myString.end().y);
			p.rect(myString.start().x, myString.start().y,_myFredDistance,_myFredDistance);
		}

		// Draw Player
		// p.ellipse(player.getHandLeftAbsolute().x,player.getHandLeftAbsolute().y,
		// 10, 10);
		// p.ellipse(player.getHandRightAbsolute().x,
		// player.getHandRightAbsolute().y, 10, 10);
	}

}
