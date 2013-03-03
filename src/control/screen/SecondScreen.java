package control.screen;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map.Entry;

import analyticgeometry.Player;

import SimpleOpenNI.SimpleOpenNI;

import controlP5.Chart;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class SecondScreen extends PApplet {
	private int width, height;
	private PApplet p;
	private Player player;
	private SimpleOpenNI context;

	public boolean kinectReady = false;

	// Graph
	private HashMap<String, Chart> charts = new HashMap<>();
	private Deque<Float> queAngle;
	private Deque<Integer> queY;

	private ControlP5 cp5;

	public SecondScreen(PApplet p, int width, int height, Player player) {
		this.p = p;
		this.width = width;
		this.height = height;
		this.player = player;
	}

	public void setup() {
		size(width, height);
		frameRate(60);

		// Charts
		cp5 = new ControlP5(this);

		int sizeWidth = 400;
		int sizeHeight = 140;
		int frameRateCharts = (int) frameRate * 50;

		Chart angleChart = cp5.addChart("Angle").setPosition(50, 50).setSize(sizeWidth, sizeHeight)
				.setRange(0, 200).setView(Chart.AREA).addDataSet("angle");

		Chart accelerationChart = cp5.addChart("Angle-Acceleration").setPosition(50, 250)
				.setSize(sizeWidth, sizeHeight).setRange(-90, +90).setView(Chart.LINE)
				.addDataSet("xacc");

		Chart xavg = cp5.addChart("X-Average").setPosition(50, 450).setSize(sizeWidth, sizeHeight)
				.setRange(0, this.width).setView(Chart.LINE).addDataSet("xavg");

		charts.put("angle", angleChart);
		charts.put("xacc", accelerationChart);
		charts.put("xavg", xavg);

		// Ques
		queAngle = new ArrayDeque<Float>(10);

		// Set Graphes
		setStuff(frameRateCharts);
	}

	public void draw() {
		background(0);

		PVector elbowHandLeft = player.elbowHandLeft().get();
		PVector elbowHandRight = player.elbowHandRight().get();

		PVector elbowShoulderLeft = player.elbowShoulderLeft().get();
		PVector elbowShoulderRight = player.elbowShoulderRight();

		elbowHandLeft.normalize();
		elbowShoulderLeft.normalize();

		float dotProduct = elbowHandLeft.dot(elbowShoulderLeft);
		float angle = (float) Math.acos(dotProduct);
		angle = degrees(angle);

		charts.get("angle").push("angle", angle);
		charts.get("xacc").push("xacc", calcAcceleration(queAngle, angle));

		// Add Ques
		queAngle.add(angle);

	}

	private void setStuff(int frameRateCharts) {
		for (Entry<String, Chart> e : charts.entrySet()) {
			e.getValue().getColor().setBackground(color(255, 100));
			e.getValue().setStrokeWeight(2f);
			e.getValue().setColors(e.getKey(), color(255), color(0, 255, 0));
			e.getValue().setData(e.getKey(), new float[frameRateCharts]);
		}

	}

	private float calcAcceleration(Deque<Float> queAngle2, float curValue) {
		float acceleration = 0;

		if (queAngle2.peekLast() != null) {
			acceleration = queAngle2.getLast() - curValue;
		}
		// System.out.println(acceleration);
		return acceleration;
	}

}
