package control.screen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map.Entry;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;
import analyticgeometry.Player;
import controlP5.Chart;
import controlP5.ControlP5;
import controlP5.Textlabel;

public class SecondScreen extends PApplet {
	private int width, height;
	private PApplet p;
	private Player player;
	private SimpleOpenNI context;

	public boolean kinectReady = false;

	// Graph
	private HashMap<String, Chart> charts = new HashMap<>();
	
	private Deque<Float> queAngleLeft;
	private Deque<Float> queAccelerationLeft;
	private Deque<Float> queVelocityLeft;
	private Deque<Float> quePositionLeft;

	private Deque<Float> queAngleRight;
	private Deque<Float> queAccelerationRight;
	private Deque<Float> queVelocityRight;	
	private Deque<Float> quePositionRight;

	private ControlP5 cp5;

	public SecondScreen(PApplet p, int width, int height, Player player) {
		this.p = p;
		this.width = width;
		this.height = height;
		this.player = player;
	}
	
	private void addLeftControlls(int sizeWidth, int sizeHeight, int frameRateCharts, PFont font){		

		Chart angleLeftChart = cp5.addChart("Angle Left")
				.setPosition(50, 50)
				.setSize(sizeWidth, sizeHeight)
				.setRange(0, 200)
				.setView(Chart.LINE)
				.addDataSet("angle Left");
		
		Textlabel angleLeftLabel = cp5.addTextlabel("Angle Left Label")
				.setText(angleLeftChart.getLabel())
				.setPosition(50, 50).setColor(color(255, 0, 255))
				.setFont(font)
				.setColorBackground(0);
		
		Chart accelerationLeftChart = cp5.addChart("Acceleration Left")
				.setPosition(50, 250)
				.setSize(sizeWidth, sizeHeight)
				.setColorBackground(color(255,0,255))
				.setRange(-90, +90)
				.setView(Chart.LINE)
				.addDataSet("acceleration Left");
		
		Textlabel accelerationLeftLabel = cp5.addTextlabel("Acceleration Left Label")
				.setText(accelerationLeftChart.getLabel())
				.setPosition(50, 250).setColor(color(255, 0, 255))
				.setFont(font)
				.setColorBackground(0);		
		
		Chart velocityLeftChart = cp5.addChart("Velocity Left")
				.setPosition(50, 450)
				.setSize(sizeWidth, sizeHeight)
				.setColorBackground(color(255,0,255))
				.setRange(-90, +90)
				.setView(Chart.LINE)
				.addDataSet("velocity Left");	
		
		Textlabel velocityLeftLabel = cp5.addTextlabel("Velocity Left Label")
				.setText(velocityLeftChart.getLabel())
				.setPosition(50, 450).setColor(color(255, 0, 255))
				.setFont(font)
				.setColorBackground(0);	
		
		Chart positionLeftChart = cp5.addChart("Position Left")
				.setPosition(50, 650)
				.setSize(sizeWidth, sizeHeight)
				.setColorBackground(color(255,0,255))
				.setRange(0, 640)
				.setView(Chart.LINE)
				.addDataSet("position Left");
		
		Textlabel positionLeftLabel = cp5.addTextlabel("Position Left Label")
				.setText(positionLeftChart.getLabel())
				.setPosition(50, 650).setColor(color(255, 0, 255))
				.setFont(font)
				.setColorBackground(0);	

		// Add to HashMap
		charts.put("angle Left", angleLeftChart);
		charts.put("acceleration Left", accelerationLeftChart);
		charts.put("velocity Left", velocityLeftChart);
		charts.put("position Left", positionLeftChart);

		// Ques
		queAngleLeft = new ArrayDeque<Float>(10);
		queAccelerationLeft = new ArrayDeque<Float>(10);
		queVelocityLeft = new ArrayDeque<Float>(10);
		quePositionLeft = new ArrayDeque<Float>(10);

		// Set Graphes
		setStuff(frameRateCharts);
	}
	
	private void addRightControlls(int sizeWidth, int sizeHeight, int frameRateCharts, PFont font){		

		Chart angleRightChart = cp5.addChart("Angle Right")
				.setPosition(450, 50)
				.setSize(sizeWidth, sizeHeight)
				.setRange(0, 200)
				.setView(Chart.LINE)
				.addDataSet("angle Right");
		
		Textlabel angleRightLabel = cp5.addTextlabel("Angle Right Label")
				.setText(angleRightChart.getLabel())
				.setPosition(450, 50).setColor(color(255, 0, 255))
				.setFont(font)
				.setColorBackground(0);
		
		Chart accelerationRightChart = cp5.addChart("Acceleration Right")
				.setPosition(450, 250)
				.setSize(sizeWidth, sizeHeight)
				.setColorBackground(color(255,0,255))
				.setRange(-90, +90)
				.setView(Chart.LINE)
				.addDataSet("acceleration Right");
		
		Textlabel accelerationRightLabel = cp5.addTextlabel("Acceleration Right Label")
				.setText(accelerationRightChart.getLabel())
				.setPosition(450, 250).setColor(color(255, 0, 255))
				.setFont(font)
				.setColorBackground(0);		
		
		Chart velocityRightChart = cp5.addChart("Velocity Right")
				.setPosition(450, 450)
				.setSize(sizeWidth, sizeHeight)
				.setColorBackground(color(255,0,255))
				.setRange(-90, +90)
				.setView(Chart.LINE)
				.addDataSet("velocity Right");	
		
		Textlabel velocityRightLabel = cp5.addTextlabel("Velocity Right Label")
				.setText(velocityRightChart.getLabel())
				.setPosition(450, 450).setColor(color(255, 0, 255))
				.setFont(font)
				.setColorBackground(0);	
		
		Chart positionRightChart = cp5.addChart("Position Right")
				.setPosition(450, 650)
				.setSize(sizeWidth, sizeHeight)
				.setColorBackground(color(255,0,255))
				.setRange(0, 640)
				.setView(Chart.LINE)
				.addDataSet("position Right");
		
		Textlabel positionLeftLabel = cp5.addTextlabel("Position Right Label")
				.setText(positionRightChart.getLabel())
				.setPosition(450, 650).setColor(color(255, 0, 255))
				.setFont(font)
				.setColorBackground(0);	

		// Add to HashMap
		charts.put("angle Right", angleRightChart);
		charts.put("acceleration Right", accelerationRightChart);
		charts.put("velocity Right", velocityRightChart);
		charts.put("position Right", positionRightChart);

		// Ques
		queAngleRight = new ArrayDeque<Float>(10);
		queAccelerationRight = new ArrayDeque<Float>(10);
		queVelocityRight = new ArrayDeque<Float>(3);
		quePositionRight = new ArrayDeque<Float>(10);

		// Set Graphes
		setStuff(frameRateCharts);
	}

	public void setup() {
		size(width, height);
		frameRate(60);

		// Charts
		cp5 = new ControlP5(this);
		
		// Settings
		int sizeWidth = 300;
		int sizeHeight = 150;
		int frameRateCharts = (int) frameRate * 50;
		PFont font = createFont("Verdana", 16, true);

		addLeftControlls(sizeWidth,sizeHeight,frameRateCharts,font);
		addRightControlls(sizeWidth,sizeHeight,frameRateCharts,font);		
	}

	public void draw() {
		background(0);

		PVector elbowHandLeft = player.elbowHandLeft();
		PVector elbowHandRight = player.elbowHandRight();

		PVector elbowShoulderLeft = player.elbowShoulderLeft();
		PVector elbowShoulderRight = player.elbowShoulderRight();
		
		PVector handLeft = player.handLeft;
		PVector handRight = player.handRight;

		elbowHandLeft.normalize();
		elbowShoulderLeft.normalize();
		
		elbowHandRight.normalize();
		elbowShoulderRight.normalize();

		float dotProductLeft = elbowHandLeft.dot(elbowShoulderLeft);
		float dotProductRight = elbowHandRight.dot(elbowShoulderRight);
		
		float angleLeft = degrees((float) Math.acos(dotProductLeft));
		float accelerationLeft = calcAcceleration(queAngleLeft, angleLeft);
		float velocityLeft = calcVelocity(queAccelerationLeft, accelerationLeft);
		
		float angleRight = degrees((float) Math.acos(dotProductRight));
		float accelerationRight = calcAcceleration(queAngleRight, angleRight);
		float velocityRight = calcVelocity(queAccelerationRight, accelerationRight);
				
		float positionLeft = handLeft.y;
		float positionRight = handRight.y;

		// Push Values Charts
		charts.get("angle Left").push("angle Left", angleLeft);
		charts.get("acceleration Left").push("acceleration Left", accelerationLeft);
		charts.get("velocity Left").push("velocity Left", velocityLeft);
		charts.get("position Left").push("position Left", positionLeft);
		
		charts.get("angle Right").push("angle Right", angleRight);
		charts.get("acceleration Right").push("acceleration Right", accelerationRight);
		charts.get("velocity Right").push("velocity Right", velocityRight);
		charts.get("position Right").push("position Right", positionRight);

		// Add Ques
		queAngleLeft.add(angleLeft);	
		queAccelerationLeft.add(accelerationLeft);
		queVelocityLeft.add(velocityLeft);
		
		queAngleRight.add(angleRight);	
		queAccelerationRight.add(accelerationRight);
		queVelocityRight.add(velocityRight);
		
		//Hit Detection
		hitDetection(queAngleLeft,queAccelerationLeft, queVelocityLeft, "Left");
		hitDetection(queAngleRight,queAccelerationRight, queVelocityRight, "Right");
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
	
	private float calcVelocity(Deque<Float> queAcceleration, float curValue) {
		float velocity = 0;

		if (queAcceleration.peekLast() != null) {
			velocity = queAcceleration.getLast() - curValue;
		}		

		return velocity;
	}
	
	private void hitDetection(Deque<Float> queAngle,Deque<Float> queAcceleration, 
			Deque<Float> queVelocity, String hand ){
		if (queAngle.peekLast() != null && queAcceleration.peekLast() != null) {
			
			float thresholdAngle = 50;
			float thresholdAcceleration = 10;
			float thresholdVelocity = -10;
			
			if (queAngle.getLast() > thresholdAngle) {
				
				if (queAcceleration.getLast() > thresholdAcceleration) {
				
					if (queVelocity.getLast() < thresholdVelocity) {
						System.out.println("Hit " + hand);
					}					
				}				
			}			
			
			if (queVelocity.getLast() > 0 && queVelocity.getLast() < 0.5f ) {
				//System.out.println(queVelocity.getLast());
			}			
			
			if (queAngle.getLast() > thresholdAngle 
					&& queAcceleration.getLast() > thresholdAcceleration
						 && queVelocity.getLast() < thresholdVelocity
							) {				
					
			}
			
		}		
	}
}
