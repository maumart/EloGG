package secondscreen;

import java.awt.Frame;
import java.util.ArrayList;

import kinect.Kinect;
import kinect.KinectUser;
import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

import components.Layout;

import controlP5.ControlP5;

@SuppressWarnings("serial")
public class SecondScreenMain extends PApplet {
	private boolean startGame = false;
	private PVector handLeft;
	private PVector handRight;

	// Layout
	private Layout layout;
	private int paddingTop = 20;
	private int paddingBottom = 20;
	private int margin = 20;
	private int paddingLeft = 20;
	private int paddingRight = 20;
	private int num = 4;
	private float scaleFactor = 1.333f;

	// Kinect
	private boolean kinectAvailable = false;
	private SimpleOpenNI kinect;
	private Kinect k;
	private ArrayList<KinectUser> userList = new ArrayList<KinectUser>();

	public static void main(String args[]) {
		PApplet.main(new String[] { "--full-screen", "--display=1",
				"secondscreen.SecondScreenMain" });
	}

	public void setup() {
		if (kinectAvailable) {
			k = new Kinect(this);
			kinect = k.getKinect();
		}

		size(1024, 768);
		// size(640, 480);
		smooth();
		frameRate(60);

		// Second Screen
		ControlP5 mainFrame = new ControlP5(this);
		ControlScreen controlFrame = addControlFrame("controlFrame",
				this.width, this.height);
	}

	public void draw() {
		background(0);

		// Kinect
		if (kinectAvailable) {
			// Kinect updaten jeden Frame --> WICHTIG
			kinect.update();

			// Kinectbild
			// image(kinect.sceneImage(), 0, 0);

			// Fetch User
			userList = k.getUserList();

			// if (userList.size() > 0) {

			for (KinectUser user : userList) {

				if (kinect.isTrackingSkeleton(user.getUserId())) {
					handLeft = user.getLeftHand(true);
					handLeft.mult(scaleFactor);

					handRight = user.getRightHand(true);
					handRight.mult(scaleFactor);
				}
			}

		} else {
			handLeft = new PVector(mouseX, mouseY, 0);
			handRight = new PVector(0, 0, 0);
		}

		// Layout Change
		if (!startGame) {
			layout = new Layout(this, num, paddingTop, paddingBottom,
					paddingLeft, paddingRight, margin);
		}

		// Game Loop
		layout.draw(handLeft, handRight);

	}

	public ControlScreen addControlFrame(String name, int width, int height) {
		int border = 25;
		Frame frame = new Frame(name);
		ControlScreen cs;

		if (kinectAvailable) {
			cs = new ControlScreen(this, width, height, k);
		} else {
			cs = new ControlScreen(this, width, height);
		}

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
