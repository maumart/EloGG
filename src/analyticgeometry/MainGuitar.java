package analyticgeometry;

import processing.core.PApplet;
import processing.core.PVector;

public class MainGuitar extends PApplet {
	private Player p;
	private KinectData k;
	private KinectInstrument instrument;
	private Midi midi;

	public void setup() {
		// Processing Stuff
		size(640, 480);
		frameRate(60);
		smooth();
		stroke(255, 0, 255);
		strokeWeight(2);

		// New Player
		p = new Player();

		// Kinect
		k = new KinectData();

		// Midi
		midi = new Midi();

		// New Guitar
		instrument = new Guitar(1, 100, 100, 100, this, midi);
	}

	public void draw() {
		background(0);

		// Workaround
		k.setMousePosition(new PVector(mouseX, mouseY));

		// Personen Koordinaten updaten
		p.setCOM(k.centerOfMass());
		p.setHandRight(k.handRight());
		p.setHandLeft(k.handLeft());

		// k.setMousePosition(new PVector(mouseX - p.centerOfMass.x, mouseY -
		// p.centerOfMass.y));
		// p.setHandLeft(new PVector(mouseX - p.centerOfMass.x, mouseY -
		// p.centerOfMass.y));
		// p.setHandRight(new PVector(mouseX - p.centerOfMass.x, mouseY -
		// p.centerOfMass.y));

		pushMatrix();

		// Ursprung zum COM verschieben
		translate(p.centerOfMass.x, p.centerOfMass.y);

		instrument.update(p);
		instrument.draw(p);
		instrument.checkMatch(p);

		popMatrix();
	}
}