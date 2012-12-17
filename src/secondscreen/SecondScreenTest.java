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
import SimpleOpenNI.SimpleOpenNI;
import controlP5.ControlP5;

@SuppressWarnings("serial")
public class SecondScreenTest extends PApplet {
	private PVector handLeft;
	private PVector handRight;
	private List<Itile> tiles = new ArrayList<>();
	private TileMatrix tileMatrix;

	// C5P
	private ControlP5 mainFrame;
	private ControlScreen controlFrame;

	// Matrix
	private int background = 0;
	private int margin = 60;
	private int gutter = 40;

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
		this.stroke(255);
		this.strokeWeight(5);
		// this.noFill();
		this.smooth();
		this.frameRate(60);

		// generateTiles(5);

		// Second Screen
		mainFrame = new ControlP5(this);
		controlFrame = addControlFrame("controlFrame", this.width, this.height);

	}

	public void draw() {
		this.background(background);

		handLeft = new PVector(mouseX, mouseY, 0);
		// handRight = new PVector(mouseX, mouseY, 0);		

		/*
		 * for (Itile tile : tiles) { // tile.draw(this);
		 * 
		 * }
		 */

		tileMatrix = new TileMatrix(this, 4, 5, margin, gutter);

		// Tiles draw
		tileMatrix.draw(handLeft, handLeft);

		// Button b = new Button((int)handLeft.x, (int) handLeft.y, 50, 50);
		// b.draw(this);
		// rect(handLeft.x, 50, 50, 50);

	}

	private void generateTiles(int n) {
		int padding = 40;
		int gutter = (this.width - padding * 2) / ((n * 2));
		int width = (this.width - padding * 2 - (gutter * (n - 1))) / n;
		int height = this.height - padding * 2;
		int y = padding;

		for (int i = 0; i < n; i++) {
			int x = i * (width + gutter) + padding;

			Itile tile = new Button(x, y, width, height);
			tiles.add(tile);

		}

	}

	public ControlScreen addControlFrame(String name, int width, int height) {
		int border = 25;
		Frame frame = new Frame(name);
		ControlScreen p = new ControlScreen(this, width, height);
		frame.add(p);
		p.init();
		frame.setTitle(name);
		frame.setSize(width, height);
		frame.setLocation(width + border, 0);
		frame.setResizable(false);
		frame.setVisible(true);
		return p;
	}

}
