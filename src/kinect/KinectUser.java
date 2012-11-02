package kinect;

import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

public class KinectUser {
	public SimpleOpenNI kinect;
	public int userId;
	public PApplet p;
	public PVector leftHand;
	public PVector rightHand;

	public KinectUser(PApplet p, SimpleOpenNI kinect, int userId) {
		this.kinect = kinect;
		this.userId = userId;
		this.p = p;
	}

	public PVector getLeftHand() {
		return leftHand;
	}

	public PVector getRightHand() {
		return rightHand;
	}

	public void updateLimbs() {
		PVector rightHandReal = new PVector();
		PVector rightHandProjected = new PVector();

		PVector leftHandReal = new PVector();
		PVector leftHandProjected = new PVector();

		// PVector leftHandProjectedUnscalled = new PVector();
		// PVector rightHandProjectedUnscalled = new PVector();

		// Handposition auslesen
		kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_RIGHT_HAND,
				rightHandReal);
		kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_LEFT_HAND,
				leftHandReal);

		// Pos Konvertieren
		kinect.convertRealWorldToProjective(rightHandReal, rightHandProjected);
		kinect.convertRealWorldToProjective(leftHandReal, leftHandProjected);

		// Handmittelpunkt
		p.fill(255, 0, 0);
		float radius = 10;

		p.ellipse(leftHandProjected.x, leftHandProjected.y, radius * 2,
				radius * 2);
		p.ellipse(rightHandProjected.x, rightHandProjected.y, radius * 2,
				radius * 2);

		this.leftHand = leftHandProjected;
		this.rightHand = rightHandProjected;
	}
}
