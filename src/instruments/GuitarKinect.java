package instruments;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import kinect.Kinect;
import kinect.KinectUser;
import SimpleOpenNI.SimpleOpenNI;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.event.MouseEvent;

public class GuitarKinect extends PApplet {
	private PVector handLeft;
	private PVector handRight;

	private PVector centerOfMass;
	private PVector headPoint;
	private PVector strumPoint;
	private int rectWidth = 20;

	private PImage guitar;

	private SimpleOpenNI kinect;
	private Kinect k;
	private ArrayList<KinectUser> userList = new ArrayList<KinectUser>();

	public void setup() {
		size(1024, 768);
		smooth();
		frameRate(60);

		int centerX = this.width / 2;
		int centerY = this.height / 2;
		centerOfMass = new PVector(centerX, centerY);

		guitar = loadImage("guitar.png");
		guitar.resize(300, 300);

		k = new Kinect(this);
		kinect = k.getKinect();
	}

	public void draw() {
		translate(0,0);
		background(0);
		fill(255);
		noStroke();
		float scaleFactor = 1.4f;

		// Kinect updaten jeden Frame --> WICHTIG
		kinect.update();

		// Fetch User
		userList = k.getUserList();

		// if (userList.size() > 0) {
		
		strumPoint = new PVector(mouseX, mouseY);

		for (KinectUser user : userList) {

			if (kinect.isTrackingSkeleton(user.getUserId())) {
				handLeft = user.getLeftHand(true);
				handLeft.mult(scaleFactor);

				handRight = user.getRightHand(true);
				handRight.mult(scaleFactor);
				
				centerOfMass= user.getCenterOfMass(true);
				centerOfMass.mult(scaleFactor);
				
				strumPoint = handLeft;
			}
		}
		
		// https://forum.processing.org/topic/calculating-angles

		// if (strumPoint.mag() > 500 ) strumPoint = new PVector(500,500);

		// Rechtecken
		drawRect(centerOfMass, rectWidth);
		drawRect(strumPoint, rectWidth);
		drawLine(strumPoint, centerOfMass);

		drawCirce(centerOfMass, strumPoint);
		drawBB(strumPoint, centerOfMass);

		// Rotation
		pushMatrix();
		float angle = atan2(centerOfMass.x - strumPoint.x, centerOfMass.y
				- strumPoint.y);
		angle *= -1;

		System.out.println(angle);
		translate(centerOfMass.x, centerOfMass.y);
		rotate(angle);
		translate(-guitar.width / 2, -guitar.height / 2);

		// guitar.resize(400,400);
		image(guitar, 0, 0);

		popMatrix();
	}

	private void drawLine(PVector v1, PVector v2) {
		stroke(255);
		strokeWeight(5);
		line(v1.x, v1.y, v2.x, v2.y);

		float dist = v1.dist(v2);
		// float angle = PVector.angleBetween(v1, v2);
		// angle = radians(angle);

		float angle = degrees(atan2(centerOfMass.x - strumPoint.x,
				centerOfMass.y - strumPoint.y));

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
		// translate(-size / 2, -size / 2);
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

	// Callback Debug
	public void mousePressed() {
		centerOfMass = new PVector(mouseX, mouseY);
	}
}
