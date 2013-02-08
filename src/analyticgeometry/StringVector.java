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
			PVector v1 = new PVector(500, 100);
			PVector vRight = player.handRight.get();

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

				// Temp
				//vRight.x = vRight.x - 0;
				//vRight.y = vRight.y - 0;

				vRight.normalize();
				PVector pv = new PVector(ov.x, ov.y);
				pv.mult(100);
				//line(rv.x, rv.y, pv.x, pv.y);

				pv.normalize();

				float dotProduct = vRight.dot(pv);
				myString.dotProduct= dotProduct;
				
				if (dotProduct > 0) {
					System.out.println("# "+myString.padding+" over");
				}
				
				if (dotProduct < 0) {
					System.out.println("# "+myString.padding+" under");
				}				
				
			}

		}

		public void draw(Player player) {
			stroke(255, 0, 255);
			strokeWeight(2);

			for (GuitarString myString : _myStrings) {
				stroke(0, 255, 255);
				line(myString.start().x, myString.start().y, myString.end().x, myString.end().y);

			}

			//translate(-player.centerOfMass.x, -player.centerOfMass.y);

			ellipse(player.handRight.x, player.handRight.y, 10, 10);
		}

		@Override
		public void draw() {
			// TODO Auto-generated method stub

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

		p.setCOM(new PVector(320, 240));
		p.setHandLeft(new PVector(mouseX-p.centerOfMass.x, mouseY-p.centerOfMass.y));
		p.setHandRight(new PVector(mouseX-p.centerOfMass.x, mouseY-p.centerOfMass.y));
		

		pushMatrix();
		translate(p.centerOfMass.x, p.centerOfMass.y);

		Guitar g = new Guitar(5, 20, 300, 100);

		g.update(p);
		g.draw(p);

		popMatrix();

	}
}
