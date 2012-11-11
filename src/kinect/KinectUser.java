package kinect;

import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

public class KinectUser {
	public SimpleOpenNI kinect;
	public int userId;
	public PVector leftHand;
	public PVector rightHand;

	public KinectUser(SimpleOpenNI kinect, int userId) {
		this.kinect = kinect;
		this.userId = userId;
		this.leftHand = new PVector();
		this.rightHand = new PVector();
	}

	public PVector getLeftHand(boolean convertToProjection) {	
		kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_LEFT_HAND,
				leftHand);

		if (convertToProjection) {
			PVector leftHandProjected = new PVector();
			kinect.convertRealWorldToProjective(leftHand, leftHandProjected);
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
			return rightHandProjected;
		}

		return rightHand;
	}

	/*
	public PVector getLeftHand() {
		return leftHand;
	}

	public PVector getRightHand() {
		return rightHand;
	}
	

	public void getProjectedHands() {
		PVector rightHandReal = new PVector();
		PVector rightHandProjected = new PVector();

		PVector leftHandReal = new PVector();
		PVector leftHandProjected = new PVector();

		// Handposition auslesen
		kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_RIGHT_HAND,
				rightHandReal);
		kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_LEFT_HAND,
				leftHandReal);

		// Pos Konvertieren
		kinect.convertRealWorldToProjective(rightHandReal, rightHandProjected);
		kinect.convertRealWorldToProjective(leftHandReal, leftHandProjected);

		this.leftHand = leftHandProjected;
		this.rightHand = rightHandProjected;
	}
	*/

	/*
	 * public void getRealHands() { PVector rightHandReal = new PVector();
	 * PVector leftHandReal = new PVector();
	 * 
	 * this.leftHand = leftHandReal; this.rightHand = rightHandReal; }
	 */

	private void calculateDepth() {

	}
}
