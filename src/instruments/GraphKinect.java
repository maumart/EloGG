package instruments;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map.Entry;

import kinect.Kinect;
import kinect.KinectUser;
import SimpleOpenNI.SimpleOpenNI;

import processing.core.PApplet;
import processing.core.PVector;
import controlP5.Chart;
import controlP5.ControlP5;

public class GraphKinect extends PApplet {
	private ControlP5 cp5;

	private HashMap<String, Chart> charts = new HashMap<>();
	private Deque<Integer> queX;
	private Deque<Integer> queY;

	private PVector centerOfMass;
	private PVector handPos;
	
	private SimpleOpenNI kinect;
	private Kinect k;
	private ArrayList<KinectUser> userList = new ArrayList<KinectUser>();
	private PVector handLeft;
	private PVector handRight;

	public void setup() {
		size(1024, 768);
		smooth();
		frameRate(60);
		
		k = new Kinect(this);
		kinect = k.getKinect();

		int centerX = this.width / 2;
		int centerY = this.height / 2;

		centerOfMass = new PVector(centerX, centerY);
		handPos = new PVector(mouseX, mouseY);

		queX = new ArrayDeque<Integer>(10);
		queY = new ArrayDeque<Integer>(10);

		cp5 = new ControlP5(this);
		int sizeWidth = 300;
		int sizeHeight = 150;
		int frameRateCharts = (int) frameRate * 20;

		Chart xpos = cp5.addChart("X-Position").setPosition(50, 50)
				.setSize(sizeWidth, sizeHeight).setRange(0, this.width)
				.setView(Chart.AREA).addDataSet("xpos");

		Chart ypos = cp5.addChart("Y-Position").setPosition(400, 50)
				.setSize(sizeWidth, sizeHeight).setRange(0, this.height)
				.setView(Chart.AREA).addDataSet("ypos");

		Chart xacc = cp5.addChart("X-Acceleration").setPosition(50, 300)
				.setSize(sizeWidth, sizeHeight)
				.setRange(-this.width / 2, this.width / 2).setView(Chart.LINE)
				.addDataSet("xacc");

		Chart yacc = cp5.addChart("Y-Acceleration").setPosition(400, 300)
				.setSize(sizeWidth, sizeHeight)
				.setRange(-this.height / 4, this.height / 4)
				.setView(Chart.LINE).addDataSet("yacc");

		Chart xavg = cp5.addChart("X-Average").setPosition(50, 550)
				.setSize(sizeWidth, sizeHeight).setRange(0, this.width)
				.setView(Chart.LINE).addDataSet("xavg");

		Chart yavg = cp5.addChart("Y-Average").setPosition(400, 550)
				.setSize(sizeWidth, sizeHeight).setRange(0, this.height)
				.setView(Chart.LINE).addDataSet("yavg");

		charts.put("xpos", xpos);
		charts.put("ypos", ypos);
		charts.put("xacc", xacc);
		charts.put("yacc", yacc);
		charts.put("xavg", xavg);
		charts.put("yavg", yavg);

		setStuff(frameRateCharts);
	}

	public void draw() {
		background(0);
		handPos = new PVector(mouseX, mouseY);
		float scaleFactor = 1.4f;
		
		
		
		kinect.update();

		// Fetch User
		userList = k.getUserList();		

		for (KinectUser user : userList) {

			if (kinect.isTrackingSkeleton(user.getUserId())) {
				handLeft = user.getLeftHand(true);
				handLeft.mult(scaleFactor);

				handRight = user.getRightHand(true);
				handRight.mult(scaleFactor);			
				
				handPos = handLeft;
				
				rect(handPos.x,handPos.y,20,20);
				
			}
		}		

		// Update Charts
		charts.get("ypos").push("ypos", (float) handPos.y);
		charts.get("xpos").push("xpos", (float) handPos.x);

		charts.get("xacc").push("xacc", (float) calcAcceleration(queX, mouseX));
		charts.get("yacc").push("yacc", (float) calcAcceleration(queY, mouseY));

		charts.get("xavg").push("xavg", (float) calcAverage(queX, 10));
		charts.get("yavg").push("yavg", (float) calcAverage(queY, 10));

		// Add Ques
		queX.add((int) handPos.x);
		queY.add((int) handPos.y);
	}

	private int calcAcceleration(Deque<Integer> que, int curValue) {
		int acceleration = 0;

		if (que.peekLast() != null) {
			acceleration = que.getLast() - curValue;
		}
		System.out.println(acceleration);
		return acceleration;
	}

	private int calcAverage(Deque<Integer> que, int numFrames) {
		Object[] lastValues = que.toArray();

		int average = 0;

		if (lastValues.length > 0) {

			for (Object value : lastValues) {
				average = average + Integer.parseInt(value.toString());
			}
			average = average / lastValues.length;
		}
		return average;
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