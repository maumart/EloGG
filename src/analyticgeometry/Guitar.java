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

	public Guitar(float _myNumbrOfStrings, float _myStringSpace, float _myNeckDistance, float _myFredDistance, PApplet p) {
		super();
		this._myNumbrOfStrings = _myNumbrOfStrings;
		this._myStringSpace = _myStringSpace;
		this._myNeckDistance = _myNeckDistance;
		this._myFredDistance = _myFredDistance;
		this.p = p;

		generateStrings(_myNumbrOfStrings);
	}

	private void generateStrings(float numberOfStrings) {
		_myStrings.clear();
		if (numberOfStrings < 1)
			return;
		float padding = -(numberOfStrings - 1) / 2;
		for (int i = 0; i < numberOfStrings; i++) {
			_myStrings.add(new GuitarString(padding,i));
			padding += 1;
			
			System.out.println(padding);
		}
	}

	public void update(Player player) {
		// Ausgangsvektoren
		PVector v1 = new PVector(500, 100);
		PVector vRight = player.handRight.get();

		// Richtungsvektor zu punkt 1 aka Linke Hand
		// PVector rv = new PVector(v1.x - centerOfMass.x, v1.y -
		// centerOfMass.y);
		PVector rv = new PVector(v1.x - player.centerOfMass.x, v1.y - player.centerOfMass.y);
		rv.normalize();
		PVector ov = new PVector(rv.y, -rv.x);

		PVector neckPos = new PVector(rv.x, rv.y);
		neckPos.mult(_myNeckDistance);

		PVector fredPos = new PVector(rv.x, rv.y);
		fredPos.mult(-_myFredDistance);

		for (GuitarString myString : _myStrings) {
			PVector translation = new PVector(ov.x, ov.y);
			translation.mult(myString.padding * _myStringSpace);

			myString.start().set(neckPos);
			myString.start().add(translation);
			myString.end().set(fredPos);
			myString.end().add(translation);

			// Temp
			// vRight.x = vRight.x - 0;
			// vRight.y = vRight.y - 0;

			vRight.normalize();
			PVector pv = new PVector(ov.x, ov.y);
			pv.mult(100);
			// line(rv.x, rv.y, pv.x, pv.y);

			pv.normalize();

			float dotProduct = vRight.dot(pv);
			myString.dotProduct = dotProduct;

			if (dotProduct > 0) {
				//System.out.println("# " + myString.padding + " over");

			}

			if (dotProduct < 0) {
				//System.out.println("# " + myString.padding + " under");
			}
			//System.out.println(myString.padding);

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