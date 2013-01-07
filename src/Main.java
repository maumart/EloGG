
import java.util.ArrayList;

import components.KeyBar;

import kinect.Kinect;
import kinect.KinectUser;
import midi.KeyMapper;
import processing.core.PApplet;
import processing.core.PVector;
import themidibus.MidiBus;
import tiles.TileMatrix;
import SimpleOpenNI.SimpleOpenNI;


@SuppressWarnings("serial")
public class Main extends PApplet {
	private boolean kinectAvailable = false;
	private PVector handLeft;
	private PVector handRight;
	private ArrayList<KeyBar> keyBars = new ArrayList<KeyBar>();
	private ArrayList<KinectUser> userList = new ArrayList<KinectUser>();

	// NEU
	// private ArrayList<TileBar> tileBars= new ArrayList<>();
	private TileMatrix tileMatrix;

	// Kinectstuff
	private SimpleOpenNI kinect;
	private Kinect k;

	// Midi
	public KeyMapper mapper;
	public MidiBus myBus;

	public void setup() {
		if (kinectAvailable) {
			k = new Kinect(this);
			kinect = k.getKinect();
		}

		mapper = new KeyMapper(this);
		// Processing Stuff
		//this.size(640, 480);
		this.size(1024, 768);
		this.stroke(255);
		this.strokeWeight(5);
		this.noFill();
		this.smooth();
		this.frameRate(60);
		// this.perspective(80f, parseFloat(width / height), 10.0f, 150000.0f);

		// Bars erstellen
		createKeyBar(4);

		// NEU
		// tileMatrix = new TileMatrix(this, 4, 3);
	}

	public void draw() {
		this.background(0);

		if (kinectAvailable) {
			// Kinect updaten jeden Frame --> WICHTIG
			kinect.update();

			// Kinectbild
			image(kinect.sceneImage(), 0, 0);

			// Fetch User
			userList = k.getUserList();

			// if (userList.size() > 0) {

			for (KinectUser user : userList) {

				if (kinect.isTrackingSkeleton(user.getUserId())) {

					// if (user.getLeftHand(true) != null
					// && user.getLeftHand(true) != null) {

					handLeft = user.getLeftHand(true);
					handRight = user.getRightHand(true);
					// }

				}
			}

			// } else {
			// handLeft = k.getUntrackedHands();
			// handRight = new PVector(-50, -50, -50);
			// System.out.println(handLeft);
			// }

		} else {

			handLeft = new PVector(mouseX, mouseY, 0);
			// handRight = new PVector(mouseX, mouseY, 0);
			handRight = new PVector(-50, -50, -50);
		}

//		if (handLeft != null && handRight != null) {
//
//			tileMatrix.draw(handLeft, handRight);
//			// tileMatrix.drawTiles();
//
//			// Draw Hands
//			visualizeHands(handLeft);
//			visualizeHands(handRight);
//
//		}

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
					
					int mappedValue = (int) map(handLeft.y, 0, 480, 0, 127);
					mapper.changePitch(mappedValue);
					System.out.println(mappedValue);
				} else {
					bar.hover(false);
				}
				
				visualizeHands(handLeft);
//				visualizeHands(handRight);
				
				//Midi
				

			}

		}

		for (int i = 0; i < tmpLeft.size(); i++) {
			if (tmpLeft.get(i)[1] >= 0) {
				//mapper.map(tmpLeft.get(i));
				return;
			}
		}

		for (int i = 0; i < tmpRight.size(); i++) {
			if (tmpRight.get(i)[1] >= 0) {
				//mapper.map(tmpRight.get(i));
				return;
			}
		}

		// MAU Temp Fix
		if (tmpLeft.size() > 0) {
			//mapper.map(tmpLeft.get(0));
		}

		if (tmpRight.size() > 0) {
			//mapper.map(tmpRight.get(0));
		}
		
		
		

	}

	private void visualizeHands(PVector vect) {
		noStroke();
		fill(255, 0, 0);
		float radius = 5 + vect.z;
		ellipse(vect.x, vect.y, radius * 2, radius * 2);
	}

	private void createKeyBar(int count) {
		int padding = 40;
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
	    PApplet.main(new String[] {"--full-screen","--display=1", "Main" });
	  }

}
