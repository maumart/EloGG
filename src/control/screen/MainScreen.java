package control.screen;

import java.awt.Frame;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map.Entry;

import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;
import analyticgeometry.KinectData;
import analyticgeometry.Midi;
import analyticgeometry.Player;
import controlP5.Chart;
import controlP5.ControlP5;

public class MainScreen extends PApplet {
	private Player p;
	private KinectData k;

	private Midi midi;
	private SimpleOpenNI context;

	private boolean autoCalib = true;
	private boolean kinectReady = false;

	ControlP5 mainFrame;
	SecondScreen controlFrame;

	// Graph
	private HashMap<String, Chart> charts = new HashMap<>();
	private Deque<Float> queAngle;
	private Deque<Integer> queY;

	private ControlP5 cp5;

	public static void main(String args[]) {
		PApplet.main(new String[] { "--full-screen", "--display=1", "control.screen.MainScreen" });
	}

	public void setup() {
		// Processing Stuff´
		size(640, 480);
		frameRate(60);
		smooth();
		stroke(255, 0, 255);
		strokeWeight(2);

		// New Player
		p = new Player();

		// Kinect
		k = new KinectData();

		context = new SimpleOpenNI(this, SimpleOpenNI.RUN_MODE_SINGLE_THREADED);
		context.openFileRecording("smite1.oni");
		context.seekPlayer(150, SimpleOpenNI.PLAYER_SEEK_CUR);		
		context.enableDepth();
		context.enableRGB();
		context.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
		context.enableScene(640, 480, 60);
		context.setSmoothingHands(0.2f);
		context.setSmoothingSkeleton(0.2f);
		context.mirror();

		// Midi
		// midi = new Midi(this);

		// Graph
		queAngle = new ArrayDeque<Float>(10);
		queY = new ArrayDeque<Integer>(10);

		cp5 = new ControlP5(this);
		int frameRateCharts = (int) frameRate * 20;

		/*
		 * 
		 * int sizeWidth = 300; int sizeHeight = 150;
		 * 
		 * Chart angleChart = cp5.addChart("Angle").setPosition(50,
		 * 50).setSize(sizeWidth, sizeHeight) .setRange(0,
		 * 200).setView(Chart.AREA).addDataSet("angle");
		 * 
		 * Chart accelerationChart =
		 * cp5.addChart("Angle-Acceleration").setPosition(50, 300)
		 * .setSize(sizeWidth, sizeHeight).setRange(-90,
		 * +90).setView(Chart.LINE) .addDataSet("xacc");
		 * 
		 * Chart xavg = cp5.addChart("X-Average").setPosition(50,
		 * 550).setSize(sizeWidth, sizeHeight) .setRange(0,
		 * this.width).setView(Chart.LINE).addDataSet("xavg");
		 * 
		 * charts.put("angle", angleChart); charts.put("xacc",
		 * accelerationChart); charts.put("xavg", xavg);
		 */

		setStuff(frameRateCharts);

		mainFrame = new ControlP5(this);
		SecondScreen controlFrame = addControlFrame("Controll", 768, 800, p);
	}

	public void draw() {
		background(0);

		// Kinect update
		context.update();

		// Kinectbild
		image(context.rgbImage(), 0, 0);

		// Userdaten updaten
		getuserLimbs();

		// Draw
		strokeWeight(5);
		stroke(255, 0, 255);

		if (kinectReady) {

			pushMatrix();

			PVector elbowHandLeft = p.elbowHandLeft().get();
			PVector elbowHandRight = p.elbowHandRight().get();

			PVector elbowShoulderLeft = p.elbowShoulderLeft().get();
			PVector elbowShoulderRight = p.elbowShoulderRight();

			// Draw

			line(p.getElbowLeft().x, p.getElbowLeft().y, p.getShoulderLeft().x,
					p.getShoulderLeft().y);
			line(p.getElbowLeft().x, p.getElbowLeft().y, p.handLeft.x, p.handLeft.y);

			stroke(0, 255, 0);
			line(p.getElbowRight().x, p.getElbowRight().y, p.getShoulderRight().x,
					p.getShoulderRight().y);
			line(p.getElbowRight().x, p.getElbowRight().y, p.handRight.x, p.handRight.y);

			// System.out.println(p.getShoulderLeft().equals(p.getShoulderRight()));

			// Angle
			// elbowHandLeft.normalize();
			// elbowShoulderLeft.normalize();

			// float dotProduct = elbowHandLeft.dot(elbowShoulderLeft);
			// float angle = (float) Math.acos(dotProduct);
			// angle = degrees(angle);

			// Update Charts
			// charts.get("angle").push("angle", angle);
			// charts.get("xacc").push("xacc", calcAcceleration(queAngle,
			// angle));
			// charts.get("yacc").push("yacc", (float) calcAcceleration(queY,
			// (int) elbowHand.y));

			// charts.get("xavg").push("xavg", (float) calcAverage(queX, 10));
			// charts.get("yavg").push("yavg", (float) calcAverage(queY, 10));

			// Add Ques
			// queAngle.add(angle);
			// queY.add((int) elbowHand.y);

			// System.out.println(degrees(angle));

			popMatrix();
		}
	}

	public SecondScreen addControlFrame(String name, int sWidth, int sHeight, Player p) {
		int border = 20;
		Frame frame = new Frame(name);
		SecondScreen cs;

		cs = new SecondScreen(this, sWidth, sHeight, p);

		frame.add(cs);
		cs.init();

		frame.setTitle(name);
		frame.setSize(sWidth, sHeight);
		frame.setLocation(width + border, 0);
		frame.setResizable(false);
		frame.setVisible(true);
		return cs;
	}

	private float calcAcceleration(Deque<Float> queAngle2, float curValue) {
		float acceleration = 0;

		if (queAngle2.peekLast() != null) {
			acceleration = queAngle2.getLast() - curValue;
		}
		// System.out.println(acceleration);
		return acceleration;
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

	public void getuserLimbs() {
		int[] userList = context.getUsers();
		for (int i = 0; i < userList.length; i++) {
			if (context.isTrackingSkeleton(userList[i])) {

				PVector handRight = new PVector();
				PVector handLeft = new PVector();

				PVector centerOfMass = new PVector();

				PVector elbowLeft = new PVector();
				PVector elbowRight = new PVector();

				PVector shoulderLeft = new PVector();
				PVector shoulderRight = new PVector();

				PVector neck = new PVector();

				// Switch Hands
				context.getJointPositionSkeleton(userList[i], SimpleOpenNI.SKEL_LEFT_HAND, handLeft);
				context.getJointPositionSkeleton(userList[i], SimpleOpenNI.SKEL_RIGHT_HAND,
						handRight);

				context.getJointPositionSkeleton(userList[i], SimpleOpenNI.SKEL_LEFT_ELBOW,
						elbowLeft);
				context.getJointPositionSkeleton(userList[i], SimpleOpenNI.SKEL_LEFT_SHOULDER,
						shoulderLeft);

				context.getJointPositionSkeleton(userList[i], SimpleOpenNI.SKEL_RIGHT_ELBOW,
						elbowRight);
				context.getJointPositionSkeleton(userList[i], SimpleOpenNI.SKEL_RIGHT_SHOULDER,
						shoulderRight);

				context.getJointPositionSkeleton(userList[i], SimpleOpenNI.SKEL_NECK, neck);

				context.getCoM(userList[i], centerOfMass);

				// Convert joints
				context.convertRealWorldToProjective(centerOfMass, centerOfMass);
				context.convertRealWorldToProjective(handLeft, handLeft);
				context.convertRealWorldToProjective(handRight, handRight);

				context.convertRealWorldToProjective(elbowLeft, elbowLeft);
				context.convertRealWorldToProjective(elbowRight, elbowRight);

				context.convertRealWorldToProjective(shoulderLeft, shoulderLeft);
				context.convertRealWorldToProjective(shoulderRight, shoulderRight);

				context.convertRealWorldToProjective(neck, neck);

				p.setCOM(centerOfMass);
				p.setHandRight(handRight);
				p.setHandLeft(handLeft);
				p.setNeck(neck);	

				p.setElbowLeft(elbowLeft);
				p.setElbowRight(elbowRight);

				p.setShoulderLeft(shoulderLeft);
				p.setShoulderRight(shoulderRight);		

				kinectReady = true;
			}
		}
	}

	private void setStuff(int frameRateCharts) {
		for (Entry<String, Chart> e : charts.entrySet()) {
			e.getValue().getColor().setBackground(color(255, 100));
			e.getValue().setStrokeWeight(2f);
			e.getValue().setColors(e.getKey(), color(255), color(0, 255, 0));
			e.getValue().setData(e.getKey(), new float[frameRateCharts]);
		}

	}

}
