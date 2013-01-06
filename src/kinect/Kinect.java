package kinect;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

@SuppressWarnings("serial")
public class Kinect {
	private SimpleOpenNI kinect;	
	private ArrayList<KinectUser> userList = new ArrayList<KinectUser>();
	private PVector posProjected = new PVector();
	private int handCount = 0;
	private int handId = 0;

	public Kinect(PApplet p) {		
		this.kinect = new SimpleOpenNI(p, SimpleOpenNI.RUN_MODE_MULTI_THREADED);
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
		// kinect.enableIR();
		kinect.enableRGB();
		kinect.enableGesture(this);
		kinect.enableHands(this);

		// Alle Bones
		// kinect.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
		kinect.enableUser(SimpleOpenNI.SKEL_PROFILE_UPPER, this);

		// Spiegeln
		kinect.setMirror(true);

		// Gesten
		// kinect.addGesture("Wave");
		// kinect.addGesture("Click");
		//kinect.addGesture("RaiseHand");

		kinect.setSmoothingHands(.1f);

		// Enable Scene
		kinect.enableScene(640, 480, 60);
	}

	public PVector getUntrackedHands() {
		return posProjected;
	}

	// Callbacks SimpleOpenNI

	// Hands

	public void onCreateHands(int handId, PVector pos, float time) {
		this.handId = handId;
		// System.out.println("onCreateHands");
	}

	public void onUpdateHands(int handId, PVector pos, float time) {
		kinect.convertRealWorldToProjective(pos, posProjected);
		System.out.println("Hands" + handId);

	}

	public void onDestroyHands(int handId, float time) {
		System.out.println("onDestroyHandsCb ");
		kinect.addGesture("RaiseHand");
	}

	// Gestures

	public void onRecognizeGesture(String strGesture, PVector idPosition,
			PVector endPosition) {
		if (handCount > 1) {
			kinect.removeGesture(strGesture);
		}
		kinect.startTrackingHands(endPosition);
		handCount++;
	}

	public void onProgressGesture(String strGesture, PVector position,
			float progress) {
	}

	// User tracking

	// Callback New User
	public void onNewUser(int userId) {
		System.out.println("New user detected");
		kinect.startPoseDetection("Psi", userId);
		kinect.startTrackingSkeleton(userId);

		KinectUser user = new KinectUser(kinect, userId);
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
			kinect.removeGesture("RaiseHand");
			kinect.stopTrackingHands(handId);

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
