package instruments;

import java.util.ArrayDeque;
import java.util.Deque;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.event.MouseEvent;

public class Guitar extends PApplet {
	private PVector handLeft;
	private PVector handRight;
	private Deque<Integer> que;

	private PVector centerOfMass;
	private PVector headPoint;
	private PVector strumPoint;
	private int rectWidth = 20;

	private PImage guitar;

	public void setup() {
		size(1024, 768);
		smooth();
		frameRate(60);

		// que = new ArrayDeque<>(25);

		int centerX = this.width / 2;
		int centerY = this.height / 2;
		centerOfMass = new PVector(centerX, centerY);

		guitar = loadImage("guitar.png");
		guitar.resize(300, 300);
	}

	public void draw() {
		background(0);
		fill(255);
		noStroke();

		strumPoint = new PVector(mouseX, mouseY);
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
		strokeWeight(5);
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
