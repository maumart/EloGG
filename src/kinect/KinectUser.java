package kinect;

import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

public class KinectUser {
	private SimpleOpenNI kinect;
	private PVector leftHand;
	private PVector rightHand;
	private PVector centerOfMass;
	private int userId;

	public KinectUser(SimpleOpenNI kinect, int userId) {
		this.kinect = kinect;
		this.userId = userId;
		this.leftHand = new PVector();
		this.rightHand = new PVector();
		this.centerOfMass = new PVector();
	}

	public int getUserId() {
		return userId;
	}

	public PVector getLeftHand(boolean convertToProjection) {
		kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_LEFT_HAND,
				leftHand);

		if (convertToProjection) {
			PVector leftHandProjected = new PVector();
			kinect.convertRealWorldToProjective(leftHand, leftHandProjected);
			leftHandProjected.z = calculateStretch(leftHandProjected);

			return leftHandProjected;
		}

		return leftHand;
	}

	public PVector getRightHand(boolean convertToProjection) {
		kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_RIGHT_HAND,
				rightHand);

		if (convertToProjection) {
			PVector rightHandProjected = new PVector();
			kinect.convertRealWorldToProjective(rightHand, rightHandProjected);
			rightHandProjected.z = calculateStretch(rightHandProjected);

			return rightHandProjected;
		}

		return rightHand;
	}

	private float calculateStretch(PVector vect) {
		kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_TORSO,
				centerOfMass);

		PVector centerOfMassProjected = new PVector();
		kinect.convertRealWorldToProjective(centerOfMass, centerOfMassProjected);

		// float distance = vect1.dist(centerOfMassProjected);

		float distance = PApplet.map((centerOfMassProjected.z - vect.z), 0,
				700, 0, 40);
		// System.out.println(distance);

		if (distance < 0)
			distance = 0;

		return distance;
	}

	public PVector getCenterOfMass(boolean convertToProjection) {
		kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_TORSO,
				centerOfMass);

		if (convertToProjection) {
			PVector centerOfMassProjected = new PVector();
			kinect.convertRealWorldToProjective(centerOfMass,
					centerOfMassProjected);
			centerOfMassProjected.z = calculateStretch(centerOfMassProjected);

			return centerOfMassProjected;
		}

		return centerOfMass;
	}

	/*
	 * public PVector getLeftHand() { return leftHand; }
	 * 
	 * public PVector getRightHand() { return rightHand; }
	 * 
	 * 
	 * public void getProjectedHands() { PVector rightHandReal = new PVector();
	 * PVector rightHandProjected = new PVector();
	 * 
	 * PVector leftHandReal = new PVector(); PVector leftHandProjected = new
	 * PVector();
	 * 
	 * // Handposition auslesen kinect.getJointPositionSkeleton(userId,
	 * SimpleOpenNI.SKEL_RIGHT_HAND, rightHandReal);
	 * kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_LEFT_HAND,
	 * leftHandReal);
	 * 
	 * // Pos Konvertieren kinect.convertRealWorldToProjective(rightHandReal,
	 * rightHandProjected); kinect.convertRealWorldToProjective(leftHandReal,
	 * leftHandProjected);
	 * 
	 * this.leftHand = leftHandProjected; this.rightHand = rightHandProjected; }
	 */

	/*
	 * public void getRealHands() { PVector rightHandReal = new PVector();
	 * PVector leftHandReal = new PVector();
	 * 
	 * this.leftHand = leftHandReal; this.rightHand = rightHandReal; }
	 */

}
