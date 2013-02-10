package analyticgeometry;

import SimpleOpenNI.SimpleOpenNI;
import processing.core.PVector;

public class KinectData {
	private PVector handLeft;
	private PVector handRight;
	private PVector centerOfMass;
	private Object kinect;
	private boolean kinectAvailable = false;
	public SimpleOpenNI context;
	private boolean kinectReady = false;

	public KinectData() {
		centerOfMass = new PVector(320, 240);
		handLeft = new PVector(500, 100);
	}

	public KinectData(SimpleOpenNI context) {
		this.context = context;
	}

	public void setMousePosition(PVector mouse) {
		this.handRight = new PVector();
		this.handRight.x = mouse.x - centerOfMass.x;
		this.handRight.y = mouse.y - centerOfMass.y;
	}

	public void setMousePositionUnTansitioned(PVector mouse) {
		this.handLeft = new PVector();
		handLeft.x = mouse.x;
		handLeft.y = mouse.y;
	}

	public PVector centerOfMass() {
		return centerOfMass;
	}

	public PVector handLeft() {
		return handLeft;
	}

	public PVector handRight() {
		return handRight;
	}

	public boolean kinectReady() {
		return kinectReady;
	}

	public void updateUser() {
		// getuserLimbs();
	}

	/*
	 * public void getuserLimbs() { System.out.println(context); int[] userList
	 * = context.getUsers();
	 * 
	 * System.out.println("d"); for (int i = 0; i < userList.length; i++) { if
	 * (context.isTrackingSkeleton(userList[i])) {
	 * 
	 * // // Get joints context.getJointPositionSkeleton(userList[i],
	 * SimpleOpenNI.SKEL_RIGHT_HAND, handRight);
	 * context.getJointPositionSkeleton(userList[i],
	 * SimpleOpenNI.SKEL_LEFT_HAND, handLeft); context.getCoM(userList[i],
	 * centerOfMass);
	 * 
	 * // Convert joints context.convertRealWorldToProjective(centerOfMass,
	 * centerOfMass); context.convertRealWorldToProjective(handLeft, handLeft);
	 * context.convertRealWorldToProjective(handRight, handRight);
	 * 
	 * kinectReady= true;
	 * 
	 * System.out.println("fd"); } } }
	 */

}
