package kinect;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

@SuppressWarnings("serial")
public class Kinect{
	public SimpleOpenNI kinect;
	public PApplet p;
	public ArrayList<KinectUser> users = new ArrayList<KinectUser>();

	public Kinect(PApplet p) {
		this.p = p;
		this.kinect = new SimpleOpenNI(p,SimpleOpenNI.RUN_MODE_MULTI_THREADED);
		//this.kinect = new SimpleOpenNI(p);
		this.settings();

	}

	public SimpleOpenNI getKinect() {
		if (kinect.init()) {
			return kinect;
		} else
			return null;
	}

	public ArrayList<KinectUser> getUserListe() {
		return users;
	}

	private void settings() {
		// Quellen aktivieren
		kinect.enableDepth();
		// context.enableIR();
		kinect.enableRGB();
		kinect.enableGesture();
		kinect.enableHands();

		// Alle Bones
		// kinect.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
		kinect.enableUser(SimpleOpenNI.SKEL_PROFILE_HEAD_HANDS);

		// Spiegeln
		kinect.setMirror(true);

		// Gesten
		kinect.addGesture("Wave");
		kinect.addGesture("Click");
		kinect.addGesture("RaiseHand");

		kinect.setSmoothingHands(.5f);

		// Enable Scene
		kinect.enableScene(640, 480, 60);
	}

}
