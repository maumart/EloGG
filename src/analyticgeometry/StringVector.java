package analyticgeometry;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class StringVector extends PApplet {	

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

		// Kinect
		KinectData k = new KinectData();
		k.setMousePosition(new PVector(mouseX, mouseY));

		// Player
		Player p = new Player();
		// p.setCOM(new PVector(320, 240));
		p.setCOM(k.centerOfMass());
		p.setHandRight(k.handRight());

		// k.setMousePosition(new PVector(mouseX - p.centerOfMass.x, mouseY -
		// p.centerOfMass.y));

		// p.setHandLeft(new PVector(mouseX - p.centerOfMass.x, mouseY -
		// p.centerOfMass.y));

		// p.setHandRight(new PVector(mouseX - p.centerOfMass.x, mouseY -
		// p.centerOfMass.y));

		pushMatrix();
		translate(p.centerOfMass.x, p.centerOfMass.y);

		Guitar g = new Guitar(1, 20, 300, 100, this);

		g.update(p);
		g.draw(p);

		popMatrix();

	}
}
