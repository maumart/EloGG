package analyticgeometry;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Guitar implements KinectInstrument {
	List<GuitarString> _myStrings = new ArrayList<>();

	private float _myNumbrOfStrings; // Anzahl Saiten
	private float _myStringSpace; // Abstand Saiten
	private float _myNeckDistance; // Abstand COM - Head
	private float _myFredDistance; // Abstand COM - Tail

	public PApplet p;
	public boolean debug = false;
	private Midi midi;

	public Guitar(float _myNumbrOfStrings, float _myStringSpace, float _myNeckDistance, float _myFredDistance,
			PApplet p, Midi midi) {
		super();
		this._myNumbrOfStrings = _myNumbrOfStrings;
		this._myStringSpace = _myStringSpace;
		this._myNeckDistance = _myNeckDistance;
		this._myFredDistance = _myFredDistance;

		this.p = p;
		this.midi = midi;

		generateStrings(_myNumbrOfStrings);
	}

	private void generateStrings(float numberOfStrings) {
		_myStrings.clear();
		if (numberOfStrings < 1)
			return;
		float padding = -(numberOfStrings - 1) / 2;
		for (int i = 0; i < numberOfStrings; i++) {
			_myStrings.add(new GuitarString(padding, i));
			padding += 1;

			// System.out.println(padding);
		}
	}

	public void update(Player player) {
		// Ausgangsvektoren
		PVector v1 = player.handLeft.get();

		// Richtungsvektor zu punkt 1 aka Linke Hand
		PVector rv = new PVector(v1.x - player.centerOfMass.x, v1.y - player.centerOfMass.y);
		rv.normalize();

		// Ortsvektor -> Orthogonal zu RV
		PVector ov = new PVector(rv.y, -rv.x);
		ov.normalize();

		// Position des Necks
		PVector neckPos = new PVector(rv.x, rv.y);
		neckPos.mult(_myNeckDistance);

		// Position des Freds
		PVector fredPos = new PVector(rv.x, rv.y);
		fredPos.mult(-_myFredDistance);

		for (GuitarString myString : _myStrings) {

			// Verschiebungsvektor vom COM
			PVector translation = new PVector(ov.x, ov.y);
			translation.mult(myString.padding * _myStringSpace);

			// Neuer COM des Vektors
			myString.centerOfVector = translation.get();

			// Start und Ende verschieben
			myString.start().set(neckPos);
			myString.start().add(translation);
			myString.end().set(fredPos);
			myString.end().add(translation);
		}

	}

	public void checkFredMatch(Player player) {
		PVector v2 = player.handRight.get();
		v2.normalize();
		
		p.ellipse(v2.x, v2.y, 10, 10);

		for (GuitarString myString : _myStrings) {
			// Vektor von Center of Vector zu Ende/Start
			PVector rv2 = new PVector(myString.start().x - myString.centerOfVector.x, myString.start().y
					- myString.centerOfVector.y);
			rv2.normalize();

			// Orthogonaler Vektor zum RV2
			PVector ov2 = new PVector(rv2.y, -rv2.x);
			ov2.normalize();
			// ov2.mult(60f);

			// frickity frack
			int testArea = 30;
			PVector testVectorTop = myString.centerOfVector.get();
			PVector testVectorBottom = myString.centerOfVector.get();

			testVectorTop.normalize();
			testVectorBottom.normalize();

			testVectorTop.mult(testArea);
			testVectorBottom.mult(-testArea);

			testVectorTop.add(myString.centerOfVector.get());
			testVectorBottom.add(myString.centerOfVector.get());

			if (debug) {
				p.ellipse(testVectorTop.x, testVectorTop.y, 10, 10);
				p.ellipse(testVectorBottom.x, testVectorBottom.y, 10, 10);
				p.line(testVectorTop.x, testVectorTop.y, testVectorBottom.x, testVectorBottom.y);

				p.pushStyle();
				p.fill(255, 0, 0);

				p.fill(255, 0, 255);
				p.popStyle();

			}

			testVectorTop.normalize();
			testVectorBottom.normalize();

			PVector tempCenter = myString.centerOfVector.get();
			tempCenter.normalize();

			float dotProduct = v2.dot(ov2);

			myString.dotProduct = dotProduct;

			if (dotProduct > 0) {
				// System.out.println("# " + myString.id + " over");
			}

			if (dotProduct < 0) {
				// System.out.println("# " + myString.id + " under");
				// midi.playMidi(myString.id);
			}
		}
	}

	public void draw(Player player) {
		p.stroke(255, 0, 255);
		p.strokeWeight(2);

		for (GuitarString myString : _myStrings) {
			p.stroke(0, 255, 255);
			p.line(myString.start().x, myString.start().y, myString.end().x, myString.end().y);
		}

		// translate(-player.centerOfMass.x, -player.centerOfMass.y);

		p.ellipse(player.handRight.x, player.handRight.y, 10, 10);
	}

}