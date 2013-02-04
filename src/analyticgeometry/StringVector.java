package analyticgeometry;

import java.util.ArrayList;
import java.util.List;

import model.Player;
import processing.core.PApplet;
import processing.core.PVector;

public class StringVector extends PApplet {

	private interface KinectInstrument {
		public void update(Player player);

		public void draw();
	}

	private class Guitar implements KinectInstrument {
		List<GuitarString> _myStrings = new ArrayList<>();
		private float _myNumbrOfStrings;

		private float _myStringSpace;
		private float _myNeckDistance;
		private float _myFredDistance;

		private PVector centerOfMass = new PVector(320, 240);

		public Guitar(float _myNumbrOfStrings, float _myStringSpace, float _myNeckDistance, float _myFredDistance) {
			super();
			this._myNumbrOfStrings = _myNumbrOfStrings;
			this._myStringSpace = _myStringSpace;
			this._myNeckDistance = _myNeckDistance;
			this._myFredDistance = _myFredDistance;

			strings(_myNumbrOfStrings);
		}

		private void strings(float numberOfStrings) {
			_myStrings.clear();
			if (numberOfStrings < 1)
				return;
			float padding = -(numberOfStrings - 1) / 2;
			for (int i = 0; i < numberOfStrings; i++) {
				_myStrings.add(new GuitarString(padding));
				padding += 1;
			}
		}

		public void update(Player player) {
			// Ausgangsvektoren
			PVector v1 = player.handLeft;

			// Richtungsvektor zu punkt 1 aka Linke Hand
			PVector rv = new PVector(v1.x - centerOfMass.x, v1.y - centerOfMass.y);
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

				// System.out.println(myString.start());
			}

		}

		public void draw() {
			stroke(255, 0, 255);
			strokeWeight(2);

			for (GuitarString myString : _myStrings) {

				line(myString.start().x, myString.start().y, myString.end().x, myString.end().y);
			}

		}
	}

	/* InnerClass Kram Ende */

	public void setup() {
		size(640, 480);
		frameRate(60);
		smooth();
		// translate(0, 0);

		stroke(255, 0, 255);
		strokeWeight(2);
		// p = new Player(0, handLeft, handRight, centerOfMass)
	}

	public void draw() {
		background(0);

		Player p = new Player();
		p.setHandLeft(new PVector(mouseX, mouseY));
		p.setCOM(new PVector(320, 240));

		// System.out.println(p.handLeft);

		pushMatrix();
		translate(p.centerOfMass.x, p.centerOfMass.y);

		Guitar g = new Guitar(4, 20, 200, 100);

		g.update(p);
		g.draw();

		popMatrix();

	}
}
