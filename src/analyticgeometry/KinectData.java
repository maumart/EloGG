package analyticgeometry;

import processing.core.PVector;

public class KinectData {
	private PVector handLeft;
	private PVector handRight;
	private PVector centerOfMass;
	private Object kinect;
	private boolean kinectAvailable = false;

	public KinectData() {
		if (kinectAvailable) {

		} else {
			centerOfMass = new PVector(320, 240);
			handLeft = new PVector(500, 100);
		}
	}

	public void setMousePosition(PVector mouse) {
		if (!kinectAvailable) {
			this.handRight = new PVector();
			this.handRight.x = mouse.x - centerOfMass.x;
			this.handRight.y = mouse.y - centerOfMass.y;
		}
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

}
