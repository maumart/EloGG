package secondscreen;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import components.Button;
import components.Itile;

import kinect.Kinect;
import kinect.KinectUser;
import processing.core.PApplet;
import processing.core.PVector;
import tiles.TileMatrix;
import tilesNew.Layout;
import SimpleOpenNI.SimpleOpenNI;
import controlP5.ControlEvent;
import controlP5.ControlP5;

@SuppressWarnings("serial")
public class SecondScreenTest extends PApplet {
	private boolean startGame = false;
	private PVector handLeft;
	private PVector handRight;

	// C5P
	private ControlP5 mainFrame;
	private ControlScreen controlFrame;

	// Layout
	private Layout layout;
	private int background = 0;
	private int paddingTop = 20;
	private int paddingBottom = 20;
	private int margin = 20;
	private int paddingLeft = 20;
	private int paddingRight = 20;
	private int num = 4;

	// Kinect
	private boolean kinectAvailable = false;
	private SimpleOpenNI kinect;
	private Kinect k;
	private ArrayList<KinectUser> userList = new ArrayList<KinectUser>();	

	public static void main(String args[]) {
		PApplet.main(new String[] { "--full-screen", "--display=1",
				"secondscreen.SecondScreenTest" });
	}

	public void setup() {
		// this.size(640, 480);
		this.size(1024, 768);
		this.smooth();
		this.frameRate(60);

		// Second Screen
		mainFrame = new ControlP5(this);
		controlFrame = addControlFrame("controlFrame", this.width, this.height);
	}

	public void draw() {
		this.background(background);

		handLeft = new PVector(mouseX, mouseY, 0);
		handRight = new PVector(0, 0, 0);

		if (!startGame) {
			layout = new Layout(this, num, paddingTop, paddingBottom,
					paddingLeft, paddingRight, margin);
			layout.draw(handLeft, handRight);

		}

		layout.draw(handLeft, handRight);

	}

	public ControlScreen addControlFrame(String name, int width, int height) {
		int border = 25;
		Frame frame = new Frame(name);
		ControlScreen cs = new ControlScreen(this, width, height);
		frame.add(cs);
		cs.init();

		frame.setTitle(name);
		frame.setSize(width, height);
		frame.setLocation(width + border, 0);
		frame.setResizable(false);
		frame.setVisible(true);
		return cs;
	}
}
