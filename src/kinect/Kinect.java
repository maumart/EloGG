package kinect;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

@SuppressWarnings("serial")
public class Kinect {
	public SimpleOpenNI kinect;
	public PApplet p;
	public ArrayList<KinectUser> userList = new ArrayList<KinectUser>();

	public Kinect(PApplet p) {
		this.p = p;
		this.kinect = new SimpleOpenNI(p, SimpleOpenNI.RUN_MODE_MULTI_THREADED);
		// this.kinect = new SimpleOpenNI(p);
		this.settings();

	}

	public SimpleOpenNI getKinect() {
		if (kinect.init()) {
			return kinect;
		} else
			return null;
	}

	public ArrayList<KinectUser> getUserList() {
		return userList;
	}

	private void settings() {
		// Quellen aktivieren
		kinect.enableDepth();
		// context.enableIR();
		kinect.enableRGB();
		kinect.enableGesture(this);
		kinect.enableHands(this);

		// Alle Bones
		// kinect.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
		kinect.enableUser(SimpleOpenNI.SKEL_PROFILE_HEAD_HANDS, this);

		// Spiegeln
		kinect.setMirror(true);

		// Gesten
		kinect.addGesture("Wave");
		kinect.addGesture("Click");
		kinect.addGesture("RaiseHand");

		kinect.setSmoothingHands(.5f);

		// Enable Scene
		kinect.enableScene(640, 480, 60);
	}

	// Callbacks SimpleOpenNI

	// Hands

	public void onCreateHands(int handId, PVector pos, float time) {
		System.out.println("onCreateHands");

	}

	public void onUpdateHands(int handId, PVector pos, float time) {
		System.out.println("onUpdateHandsCb");

	}

	public void onDestroyHands(int handId, float time) {
		System.out.println("onDestroyHandsCb ");
	}

	// Gesture events

	public void onRecognizeGesture(String strGesture, PVector idPosition,
			PVector endPosition) {
		System.out.println("onRecognizeGesture - strGesture: " + strGesture
				+ ", idPosition: " + idPosition + ", endPosition:"
				+ endPosition);

		kinect.removeGesture(strGesture);
		kinect.startTrackingHands(endPosition);
	}

	public void onProgressGesture(String strGesture, PVector position,
			float progress) {
		// println("onProgressGesture - strGesture: " + strGesture +
		// ", position: " + position + ", progress:" + progress);
	}

	// User tracking

	// Callback New User
	public void onNewUser(int userId) {
		System.out.println("Neuer User erkannt");
		kinect.startPoseDetection("Psi", userId);
		kinect.startTrackingSkeleton(userId);

		// Erkannter User zur Collection hinzufuegen
		// KinectUser user = new KinectUser(this, kinect, userId);
		KinectUser user = new KinectUser(p, kinect, userId);
		userList.add(user);
	}

	// Callback Kalibrierungsbeginn
	public void onStartCalibration(int userId) {
		System.out.println("onStartCalibration - userId: " + userId);
	}

	// Callback Kalibrierungsende
	public void onEndCalibration(int userId, boolean successfull) {
		System.out.println("onEndCalibration - userId: " + userId
				+ ", successfull: " + successfull);

		if (successfull) {
			System.out.println("  User calibrated !!!");
			kinect.startTrackingSkeleton(userId);

		} else {
			System.out.println("  Failed to calibrate user !!!");
			System.out.println("  Start pose detection");
			kinect.startPoseDetection("Psi", userId);
		}
	}

	// Callback Pose Begin
	public void onStartPose(String pose, int userId) {
		System.out.println("onStartPose - userId: " + userId + ", pose: "
				+ pose);
		System.out.println(" stop pose detection");

		kinect.stopPoseDetection(userId);
		kinect.requestCalibrationSkeleton(userId, true);

	}

	// Callback Pose Ende
	public void onEndPose(String pose, int userId) {
		System.out.println("onEndPose - userId: " + userId + ", pose: " + pose);
	}

}
