package analyticgeometry;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Drum implements KinectInstrument {
	List<DrumSingle> _myDrums = new ArrayList<>();

	private float _myNumberOfDrums; // Anzahl Drums
	private float _myDrumSpace; // Abstand Drums
	private float _myDrumWidth; // Abstand COM - Tail
	private float _myDrumHeight; // Abstand COM - Tail

	public PApplet p;
	public boolean debug = true;
	private Midi midi;

	private boolean fredValue = false;

	public Drum(float _myNumberOfDrums, float _myDrumSpace, float _myDrumWidth, float myDrumHeight, PApplet p, Midi midi) {
		super();
		this._myNumberOfDrums = _myNumberOfDrums;
		this._myDrumSpace = _myDrumSpace + _myDrumWidth;
		this._myDrumWidth = _myDrumWidth;
		this._myDrumHeight = myDrumHeight;

		this.p = p;
		this.midi = midi;

		generateDrums(_myNumberOfDrums);
	}

	private void generateDrums(float _myNumbrOfDrums) {
		_myDrums.clear();
		if (_myNumbrOfDrums < 1)
			return;
		float padding = -(_myNumbrOfDrums - 1) / 2;
		for (int i = 0; i < _myNumbrOfDrums; i++) {
			_myDrums.add(new DrumSingle(padding, i));
			padding += 1;
		}
	}

	public void update(Player player) {
		PVector v3 = player.neck.get();

		// Richtungsvektor zu Neck
		PVector rv = new PVector(v3.x - player.centerOfMass.x, v3.y - player.centerOfMass.y);
		rv.normalize();

		// Ortsvektor -> Orthogonal zu RV
		PVector ov = new PVector(rv.y, -rv.x);
		ov.normalize();

		// Position des Necks
		PVector neckPos = ov.get();
		neckPos.mult(_myDrumWidth / 2);

		// Position des Freds
		PVector fredPos = ov.get();
		fredPos.mult(-_myDrumWidth / 2);

		for (DrumSingle myDrum : _myDrums) {

			// Verschiebungsvektor vom COM
			PVector translation = new PVector(ov.x, ov.y);
			translation.mult(myDrum.padding * _myDrumSpace);

			PVector translation2 = new PVector(ov.x, ov.y);
			translation2.mult((myDrum.padding * _myDrumSpace) - _myDrumWidth / 2);

			// Start und Ende verschieben
			myDrum.start().set(neckPos);
			myDrum.start().add(translation);

			myDrum.end().set(fredPos);
			myDrum.end().add(translation);

			myDrum.center().set(neckPos);
			myDrum.center().add(translation2);
		}
	}

	public void checkFredMatch(Player player) {
		PVector v2 = player.getHandRightAbsolute().get();
		v2.normalize();

		for (DrumSingle myDrum : _myDrums) {
			// Vektor von Center of Vector zu Ende/Start
			PVector rv2 = new PVector(myDrum.start().x - myDrum.center().x, myDrum.start().y - myDrum.center().y);
			rv2.normalize();

			// Orthogonaler Vektor zum RV2
			PVector ov2 = new PVector(rv2.y, -rv2.x);
			ov2.normalize();
			// ov2.mult(60f);

			float dotProduct = v2.dot(ov2);
			int neckValue = 0;
			// Crap
			if (myDrum.dotProduct < 0 && dotProduct > 0) {
				// System.out.println("pass");
				fredValue = true;
				System.out.println("hit up - Neck" + neckValue);
				midi.playMidi(myDrum.id, neckValue, true);

			} else if (myDrum.dotProduct > 0 && dotProduct < 0) {
				fredValue = true;
				midi.playMidi(myDrum.id, neckValue, false);
				System.out.println("hit down- Neck" + neckValue);
			}

			// Neues Dot Product Speichern
			myDrum.dotProduct = dotProduct;

			// System.out.println(dotProduct);
		}

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

		for (DrumSingle myDrum : _myDrums) {
			p.stroke(0, 255, 255);
			// p.line(myString.start().x, myString.start().y, myString.end().x,
			// myString.end().y);
			p.line(myDrum.start().x, myDrum.start().y, myDrum.end().x, myDrum.end().y);
			p.rect(myDrum.start().x, myDrum.start().y, _myDrumWidth, _myDrumHeight);
			p.ellipse(myDrum.center().x, myDrum.center().y, 20, 20);

			// End and Start Vectors
			// p.ellipse(myDrum.start().x, myDrum.start().y, 20, 20);
			// p.ellipse(myDrum.end().x, myDrum.end().y, 20, 20);
		}

		// Draw Player
		// p.ellipse(player.getHandLeftAbsolute().x,player.getHandLeftAbsolute().y,10,
		// 10);
		p.ellipse(player.getHandRightAbsolute().x, player.getHandRightAbsolute().y, 10, 10);
	}

}
