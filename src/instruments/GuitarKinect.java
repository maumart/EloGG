package instruments;

import java.awt.Frame;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;
import controlP5.ControlP5;

public class GuitarKinect extends PApplet {
	private PVector handLeft = new PVector();
	private PVector handRight = new PVector();
	private PVector centerOfMass = new PVector();

	private PImage instrument;
	private SimpleOpenNI context;
	private boolean autoCalib = true;

	public void setup() {
		size(640, 480);
		smooth();
		frameRate(60);
		stroke(0, 0, 255);
		strokeWeight(3);

		// Images
		instrument = loadImage("guitar.png");
		instrument.resize(500, 500);

		// Kinect
		context = new SimpleOpenNI(this);
		context.openFileRecording("guitar_long.oni");
		context.seekPlayer(350, SimpleOpenNI.PLAYER_SEEK_CUR);
		context.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
		context.enableScene(640, 480, 60);
		context.mirror();

		// Second Screen
		ControlP5 mainFrame = new ControlP5(this);
		ControlScreen controlFrame = addControlFrame("controlFrame", this.width, this.height);
	}

	public void draw() {
		background(0);
		translate(0, 0);
		fill(255);
		noStroke();

		// Kinect update
		context.update();

		// Image
		pushStyle();

		image(context.rgbImage(), 0, 0);

		popStyle();
		// image(context.sceneImage(), 0, 0);

		// Skelett
		getuserLimbs();
	}

	public void getuserLimbs() {
		int[] userList = context.getUsers();
		for (int i = 0; i < userList.length; i++) {
			if (context.isTrackingSkeleton(userList[i])) {

				// // Get joints
				context.getJointPositionSkeleton(userList[i], SimpleOpenNI.SKEL_RIGHT_HAND, handRight);
				context.getJointPositionSkeleton(userList[i], SimpleOpenNI.SKEL_LEFT_HAND, handLeft);
				context.getCoM(userList[i], centerOfMass);

				// Convert joints
				context.convertRealWorldToProjective(centerOfMass, centerOfMass);
				context.convertRealWorldToProjective(handLeft, handLeft);
				context.convertRealWorldToProjective(handRight, handRight);

				// Draw joints
				drawOverlay();
			}
		}
	}

	private void drawOverlay() {
		int rectWidth = 15;

		// Rectangles
		drawRect(centerOfMass, rectWidth);
		drawRect(handLeft, rectWidth);
		drawRect(handRight, rectWidth);

		// Lines and cirles
		drawLine(handRight, centerOfMass);
		calculateLength(handRight, centerOfMass);

		drawCirce(centerOfMass, handRight);
		drawBB(handRight, centerOfMass);

		// Rotation
		pushMatrix();

		float angle = atan2(centerOfMass.x - handRight.x, centerOfMass.y - handRight.y) * -1;

		translate(centerOfMass.x, centerOfMass.y);
		rotate(angle);
		translate(-instrument.width / 2, -instrument.height / 2);
		image(instrument, 0, 0);

		popMatrix();
	}

	private void calculateLength(PVector v1, PVector v2) {
		int maxWidth = 250;
		int minWidth = 50;

		int minMapping = 0;
		int maxMapping = 5;

		float dist = v1.dist(v2);
		int distMapped = Math.round(map(dist, minWidth, maxWidth, minMapping, maxMapping));
		// System.out.println("Guitar Mapping " + distMapped);
	}

	private void drawLine(PVector v1, PVector v2) {
		stroke(255);
		strokeWeight(5);
		line(v1.x, v1.y, v2.x, v2.y);

		float dist = v1.dist(v2);
		float angle = degrees(atan2(v1.x - v2.x, v1.y - v2.y));

		text(dist, 50, 50);
		text(angle, 50, 100);
	}

	private void drawRect(PVector v1, int size) {
		pushMatrix();
		translate(-size / 2, -size / 2);
		rect(v1.x, v1.y, size, size);
		popMatrix();
	}

	private void drawCirce(PVector v1, PVector v2) {
		pushMatrix();
		stroke(255, 0, 125);
		strokeWeight(2);
		noFill();

		float distance = dist(v1.x, v1.y, v2.x, v2.y);
		ellipse(v1.x, v1.y, distance * 2, distance * 2);
		popMatrix();
	}

	private void drawBB(PVector v1, PVector v2) {
		pushMatrix();
		pushStyle();
		rectMode(CORNERS);

		rect(v1.x, v1.y, v2.x, v2.y);
		popStyle();
		popMatrix();
	}

	public ControlScreen addControlFrame(String name, int width, int height) {
		int border = 25;
		Frame frame = new Frame(name);
		ControlScreen cs;

		cs = new ControlScreen(this, width, height, context);

		frame.add(cs);
		cs.init();

		frame.setTitle(name);
		frame.setSize(width, height);
		frame.setLocation(width + border, 0);
		frame.setResizable(false);
		frame.setVisible(true);
		return cs;
	}

	// Callbacks SimpleOpenNI

	public void onNewUser(int userId) {
		println("onNewUser - userId: " + userId);
		println("  start pose detection");

		if (autoCalib)
			context.requestCalibrationSkeleton(userId, true);
		else
			context.startPoseDetection("Psi", userId);
	}

	public void onLostUser(int userId) {
		println("onLostUser - userId: " + userId);
	}

	public void onExitUser(int userId) {
		println("onExitUser - userId: " + userId);
	}

	public void onReEnterUser(int userId) {
		println("onReEnterUser - userId: " + userId);
	}

	public void onStartCalibration(int userId) {
		println("onStartCalibration - userId: " + userId);
	}

	public void onEndCalibration(int userId, boolean successfull) {
		println("onEndCalibration - userId: " + userId + ", successfull: " + successfull);

		if (successfull) {
			println("  User calibrated !!!");
			context.startTrackingSkeleton(userId);
		} else {
			println("  Failed to calibrate user !!!");
			println("  Start pose detection");
			context.startPoseDetection("Psi", userId);
		}
	}

	public void onStartPose(String pose, int userId) {
		println("onStartPose - userId: " + userId + ", pose: " + pose);
		println(" stop pose detection");

		context.stopPoseDetection(userId);
		context.requestCalibrationSkeleton(userId, true);
	}

	public void onEndPose(String pose, int userId) {
		println("onEndPose - userId: " + userId + ", pose: " + pose);
	}
}
