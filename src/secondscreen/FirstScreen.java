package secondscreen;

import java.awt.Frame;

import processing.core.PApplet;
import controlP5.ControlP5;

@SuppressWarnings("serial")
public class FirstScreen extends PApplet {
	// SecondScreen s2;
	private ControlP5 cp5;
	ControlFrame cf;

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "secondscreen.FirstScreen" });
	}

	public void setup() {
		size(400, 400);
		cp5 = new ControlP5(this);

		// by calling function addControlFrame() a
		// new frame is created and an instance of class
		// ControlFrame is instanziated.
		cf = addControlFrame("SecondScreen", 400, 400);

		// add Controllers to the 'extra' Frame inside
		// the ControlFrame class setup() method below.
		
	}

	public void draw() {
		background(0);
		stroke(0);
		fill(255, 0, 0);
		rect(0, 20, frameCount % width, 10);
		text("" + mouseX + ", " + mouseY, 20, 60);
	}

	public ControlFrame addControlFrame(String theName, int theWidth,
			int theHeight) {
		Frame f = new Frame(theName);
		ControlFrame p = new ControlFrame(this, theWidth, theHeight);
		f.add(p);
		p.init();
		f.setTitle(theName);
		f.setSize(p.w, p.h);
		f.setLocation(100, 100);
		f.setResizable(false);
		f.setVisible(true);
		return p;
	}

}
