package instruments;

import java.util.ArrayList;

import kinect.Kinect;
import kinect.KinectUser;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

public class GuitarKinect extends PApplet {
	private PVector handLeft = new PVector();
	private PVector handRight = new PVector();
	private PVector centerOfMass = new PVector();

	private PImage guitar;
	public SimpleOpenNI context;
	public boolean autoCalib = true;

	public void setup() {
		size(640, 480);
		smooth();
		frameRate(60);
		stroke(0, 0, 255);
		strokeWeight(3);

		// Images
		guitar = loadImage("guitar.png");
		guitar.resize(400, 400);

		// Kinect
		context = new SimpleOpenNI(this);
		context.openFileRecording("guitar_long.oni");
		context.seekPlayer(350, SimpleOpenNI.PLAYER_SEEK_CUR);
		context.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
		context.enableScene(640, 480, 60);
		context.mirror();
		System.out.printf("This file has %s frames", context.framesPlayer());
	}

	public void draw() {
		background(0);
		translate(0, 0);
		fill(255);
		noStroke();

		// Kinect update
		context.update();

		// Image
		image(context.rgbImage(), 0, 0);
		// image(context.sceneImage(), 0, 0);

		// Skelett
		int[] userList = context.getUsers();
		for (int i = 0; i < userList.length; i++) {
			if (context.isTrackingSkeleton(userList[i])) {

				// Get joints
				context.getJointPositionSkeleton(userList[i],
						SimpleOpenNI.SKEL_RIGHT_HAND, handRight);
				context.getJointPositionSkeleton(userList[i],
						SimpleOpenNI.SKEL_LEFT_HAND, handLeft);
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
		drawCirce(centerOfMass, handRight);
		drawBB(handRight, centerOfMass);

		// Rotation
		pushMatrix();

		float angle = atan2(centerOfMass.x - handRight.x, centerOfMass.y
				- handRight.y)
				* -1;

		translate(centerOfMass.x, centerOfMass.y);
		rotate(angle);
		translate(-guitar.width / 2, -guitar.height / 2);
		image(guitar, 0, 0);

		popMatrix();
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
		println("onEndCalibration - userId: " + userId + ", successfull: "
				+ successfull);

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
