package control.screen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map.Entry;

import com.jogamp.opengl.util.texture.TextureData.Flusher;

import analyticgeometry.Player;

import SimpleOpenNI.SimpleOpenNI;

import controlP5.Chart;
import controlP5.ControlP5;
import controlP5.Textlabel;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class SecondScreen extends PApplet {
	private int width, height;
	private PApplet p;
	private Player player;
	private SimpleOpenNI context;

	public boolean kinectReady = false;

	Textlabel myTextlabelA;

	// Graph
	private HashMap<String, Chart> charts = new HashMap<>();
	private Deque<Float> queAngleLeft;
	private Deque<Float> queAcceleration;

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

		int sizeWidth = 300;
		int sizeHeight = 150;
		int frameRateCharts = (int) frameRate * 50;

		// Charts & Labels
		Chart angleLeftChart = cp5.addChart("angle Left")
				.setPosition(50, 50)
				.setSize(sizeWidth, sizeHeight)
				.setRange(0, 200)
				.setView(Chart.LINE)
				.addDataSet("angle Left");
		
		Textlabel angleLeftLabel = cp5.addTextlabel("Angle Left Chart")
				.setText(angleLeftChart.getLabel())
				.setPosition(50, 50).setColor(color(255, 0, 255))
				.setFont(createFont("Verdana", 16, true))
				.setColorBackground(0);
		
		Chart accelerationLeftChart = cp5.addChart("angle Left Acceleration")
				.setPosition(50, 250)
				.setSize(sizeWidth, sizeHeight)
				.setColorBackground(color(255,0,255))
				.setRange(-90, +90)
				.setView(Chart.LINE)
				.addDataSet("angle Left Acceleration");
		
		Textlabel accelerationLeftLabel = cp5.addTextlabel("Acceleration Left Chart")
				.setText(accelerationLeftChart.getLabel())
				.setPosition(50, 250).setColor(color(255, 0, 255))
				.setFont(createFont("Verdana", 16, true))
				.setColorBackground(0);		
		
		Chart velocityLeftChart = cp5.addChart("angle Left Velocity")
				.setPosition(50, 450)
				.setSize(sizeWidth, sizeHeight)
				.setColorBackground(color(255,0,255))
				.setRange(-90, +90)
				.setView(Chart.LINE)
				.addDataSet("angle Left Velocity");		

		charts.put("angle Left", angleLeftChart);
		charts.put("angle Left Acceleration", accelerationLeftChart);
		charts.put("angle Left Velocity", velocityLeftChart);
		//charts.put("xavg", xavg);

		// Ques
		queAngleLeft = new ArrayDeque<Float>(10);
		queAcceleration = new ArrayDeque<Float>(10);

		// Set Graphes
		setStuff(frameRateCharts);

		/*
		myTextlabelA = cp5.addTextlabel("Angle Chart").setText(angleChart.getLabel())
				.setPosition(50, 50).setColor(color(255, 0, 255))
				.setFont(createFont("Verdana", 16, true)).setColorBackground(0);
			*/

		ArrayList t = (ArrayList) cp5.getAll();
		System.out.println(t.size());
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

		charts.get("angle Left").push("angle Left", angle);
		charts.get("angle Left Acceleration").push("angle Left Acceleration", calcAcceleration(queAngleLeft, angle));
		charts.get("angle Left Velocity").push("angle Left Velocity", calcAcceleration(queAngleLeft, angle));

		// Add Ques
		queAngleLeft.add(angle);	

	}

	private void setStuff(int frameRateCharts) {
		for (Entry<String, Chart> e : charts.entrySet()) {
			e.getValue().getColor().setBackground(color(255, 100));
			e.getValue().setStrokeWeight(2f);
			e.getValue().setColors(e.getKey(), color(255), color(0, 255, 0));
			e.getValue().setData(e.getKey(), new float[frameRateCharts]);
		}

	}

	private float calcAcceleration(Deque<Float> queAngle, float curValue) {
		float acceleration = 0;

		if (queAngle.peekLast() != null) {
			acceleration = queAngle.getLast() - curValue;
		}

		return acceleration;
	}
	
	private float calcVelocity(Deque<Float> queAngle, float curValue) {
		float acceleration = 0;

		if (queAngle.peekLast() != null) {
			acceleration = queAngle.getLast() - curValue;
		}

		return acceleration;
	}

}
