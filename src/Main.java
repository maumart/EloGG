import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import themidibus.MidiBus;
import SimpleOpenNI.SimpleOpenNI;
import components.KeyBar;
import kinect.Kinect;
import kinect.KinectUser;

// foo
@SuppressWarnings("serial")
public class Main extends PApplet {
	public boolean kinectAvailable = false;
	public PVector handLeft;
	public PVector handRight;
	public ArrayList<KeyBar> keyBars = new ArrayList<KeyBar>();
	public ArrayList<KinectUser> userList = new ArrayList<KinectUser>();

	// Kinectstuff
	SimpleOpenNI kinect;
	boolean initialized;
	Kinect k;

	// Midi
	public MidiBus myBus;

	public void setup() {
		if (kinectAvailable) {
			k = new Kinect(this);
			kinect = k.getKinect();
		}

		// Processing Stuff
		this.size(640, 480);
		this.stroke(255);
		this.strokeWeight(5);
		this.noFill();
		this.smooth();
		this.frameRate(60);

		// Bars erstellen
		createKeyBar(4);
	}

	public void draw() {
		this.background(0);

		if (kinectAvailable) {
			// Kinect updaten jeden Frame --> WICHTIG
			kinect.update();

			// Kinectbild
			image(kinect.sceneImage(), 0, 0);

			for (KinectUser user : userList) {

				if (kinect.isTrackingSkeleton(user.userId)) {

					user.updateLimbs();
					if (user.getLeftHand() != null
							&& user.getLeftHand() != null) {

						System.out.println(user.getLeftHand());

						handLeft = user.getLeftHand();
						handRight = user.getRightHand();
					}

				}
			}
			// System.out.println(userList.size());

		} else {

			handLeft = new PVector(mouseX, mouseY, 0);
			handRight = new PVector(mouseX, mouseY, 0);

		}

		// BB Test
		for (KeyBar bar : keyBars) {

			if (handLeft != null && handRight != null) {

				int[] valueLeft = bar.intersects(Math.round(handLeft.x),
						Math.round(handLeft.y), Math.round(handLeft.z), 0);
				int[] valueRight = bar.intersects(Math.round(handRight.x),
						Math.round(handRight.y), Math.round(handRight.z), 1);

				if (valueLeft[1] != -1) {
					System.out.print("Hand: " + valueLeft[0]);
					System.out.print(" Note: " + valueLeft[1]);
					System.out.print(" Y-Wert: " + valueLeft[2]);
					System.out.println(" Z-Wert: " + valueLeft[3]);
				}

				if (valueRight[1] != -1) {
					System.out.print("Hand: " + valueRight[0]);
					System.out.print(" Note: " + valueRight[1]);
					System.out.print(" Y-Wert: " + valueRight[2]);
					System.out.println(" Z-Wert: " + valueRight[3]);

				}

				// Hoover
				if ((valueLeft[1] != -1) || (valueRight[1] != -1)) {
					bar.currColor = bar.hoverColor;
				} else {
					bar.currColor = bar.normalColor;
				}

			}

			bar.draw();

		}

	}

	public void createKeyBar(int count) {
		int padding = 20;
		int gutter = (this.width - padding * 2) / (count * 2);
		int width = (this.width - padding * 2 - (gutter * (count - 1))) / count;
		int height = this.height - padding * 2;
		int y = padding;

		for (int i = 0; i < count; i++) {
			int x = i * (width + gutter) + padding;

			KeyBar bar = new KeyBar(this, x, y, width, height, i);
			keyBars.add(bar);

		}

	}

	// Callbacks Simple openni

	public void onNewUser(int userId) {
		println("Neuer User erkannt");
		kinect.startPoseDetection("Psi", userId);
		kinect.startTrackingSkeleton(userId);

		// Erkannter User zur Collection hinzufuegen
		KinectUser user = new KinectUser(this, kinect, userId);
		userList.add(user);
	}

	// Callback Kalibrierungsbeginn
	public void onStartCalibration(int userId) {
		println("onStartCalibration - userId: " + userId);
	}

	// Callback Kalibrierungsende
	public void onEndCalibration(int userId, boolean successfull) {
		println("onEndCalibration - userId: " + userId + ", successfull: "
				+ successfull);

		if (successfull) {
			println("  User calibrated !!!");
			kinect.startTrackingSkeleton(userId);

		} else {
			println("  Failed to calibrate user !!!");
			println("  Start pose detection");
			kinect.startPoseDetection("Psi", userId);
		}
	}

	// Callback Pose Begin
	public void onStartPose(String pose, int userId) {
		println("onStartPose - userId: " + userId + ", pose: " + pose);
		println(" stop pose detection");

		kinect.stopPoseDetection(userId);
		kinect.requestCalibrationSkeleton(userId, true);

	}

	// Callback Pose Ende
	public void onEndPose(String pose, int userId) {
		println("onEndPose - userId: " + userId + ", pose: " + pose);
	}

}
