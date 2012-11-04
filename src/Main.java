import java.util.ArrayList;

import midi.KeyMapper;

import processing.core.PApplet;
import processing.core.PVector;
import themidibus.MidiBus;
import SimpleOpenNI.SimpleOpenNI;
import components.KeyBar;
import kinect.Kinect;
import kinect.KinectUser;
import processing.opengl.*;

//import processing.video.*;

@SuppressWarnings("serial")
public class Main extends PApplet {
	private boolean kinectAvailable = true;
	private PVector handLeft;
	private PVector handRight;
	private ArrayList<KeyBar> keyBars = new ArrayList<KeyBar>();
	private ArrayList<KinectUser> userList = new ArrayList<KinectUser>();

	// Kinectstuff
	SimpleOpenNI kinect;
	boolean initialized;
	Kinect k;

	public PVector handVec;
	public String strGesture;

	public KeyMapper mapper;

	// Midi
	public MidiBus myBus;

	public void setup() {
		if (kinectAvailable) {
			k = new Kinect(this);
			kinect = k.getKinect();
		}

		mapper = new KeyMapper(this);
		// Processing Stuff
		this.size(640, 480);
		this.stroke(255);
		this.strokeWeight(5);
		this.noFill();
		this.smooth();
		this.frameRate(60);
		// this.perspective(80f, parseFloat(width / height), 10.0f, 150000.0f);

		// Bars erstellen
		createKeyBar(4);
	}

	public void draw() {
		hint(ENABLE_ACCURATE_2D);
		this.background(0);

		if (kinectAvailable) {
			// Kinect updaten jeden Frame --> WICHTIG
			kinect.update();

			// Kinectbild
			image(kinect.sceneImage(), 0, 0);

			// Fetch User
			userList = k.getUserList();

			for (KinectUser user : userList) {

				if (kinect.isTrackingSkeleton(user.userId)) {

					user.updateLimbs();
					if (user.getLeftHand() != null
							&& user.getLeftHand() != null) {

						handLeft = user.getLeftHand();
						handRight = user.getRightHand();
					}

				}
			}

		} else {

			handLeft = new PVector(mouseX, mouseY, 0);
			// handRight = new PVector(mouseX, mouseY, 0);
			handRight = new PVector(-50, -50, -50);
		}

		// BB Test

		ArrayList<int[]> tmpLeft = new ArrayList<int[]>();
		ArrayList<int[]> tmpRight = new ArrayList<int[]>();

		for (KeyBar bar : keyBars) {

			bar.draw();

			if (handLeft != null && handRight != null) {

				int[] valueLeft = bar.intersects(Math.round(handLeft.x),
						Math.round(handLeft.y), Math.round(handLeft.z), 0);
				int[] valueRight = bar.intersects(Math.round(handRight.x),
						Math.round(handRight.y), Math.round(handRight.z), 1);

				tmpLeft.add(valueLeft);
				tmpRight.add(valueRight);
				// mapper.map(valueLeft);

				// Hoover
				if ((valueLeft[1] != -1) || (valueRight[1] != -1)) {
					bar.hover(true);
				} else {
					bar.hover(false);
				}

				// Draw Hands
				visualizeHands(handLeft, handRight);

			}

		}

		for (int i = 0; i < tmpLeft.size(); i++) {
			if (tmpLeft.get(i)[1] >= 0) {
				mapper.map(tmpLeft.get(i));
				return;
			}
		}

		for (int i = 0; i < tmpRight.size(); i++) {
			if (tmpRight.get(i)[1] >= 0) {
				mapper.map(tmpRight.get(i));
				return;
			}
		}

		// MAU Temp Fix
		if (tmpLeft.size() > 0) {
			mapper.map(tmpLeft.get(0));
		}

		if (tmpRight.size() > 0) {
			mapper.map(tmpRight.get(0));
		}

	}

	public void visualizeHands(PVector handLeft, PVector handRight) {
		fill(255, 0, 0);
		float radius = 10;
		ellipse(handLeft.x, handLeft.y, radius * 2, radius * 2);
		ellipse(handRight.x, handRight.y, radius * 2, radius * 2);
	}

	public void createKeyBar(int count) {
		int padding = 20;
		int gutter = (this.width - padding * 2) / ((count * 2));
		int width = (this.width - padding * 2 - (gutter * (count - 1))) / count;
		int height = this.height - padding * 2;
		int y = padding;

		for (int i = 0; i < count; i++) {
			int x = i * (width + gutter) + padding;

			KeyBar bar = new KeyBar(this, x, y, width, height, i);
			keyBars.add(bar);

		}

	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "Main" });
	}

}
