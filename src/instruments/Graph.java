package instruments;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map.Entry;

import processing.core.PApplet;
import processing.core.PVector;
import controlP5.Chart;
import controlP5.ControlP5;

public class Graph extends PApplet {
	private ControlP5 cp5;

	private HashMap<String, Chart> charts = new HashMap<>();
	private Deque<Integer> queX;
	private Deque<Integer> queY;

	private PVector centerOfMass;
	private PVector handPos;

	public void setup() {
		size(1024, 768);
		smooth();
		frameRate(60);

		int centerX = this.width / 2;
		int centerY = this.height / 2;

		centerOfMass = new PVector(centerX, centerY);
		handPos = new PVector(mouseX, mouseY);

		queX = new ArrayDeque<Integer>(10);
		queY = new ArrayDeque<Integer>(10);

		cp5 = new ControlP5(this);
		int sizeWidth = 400;
		int sizeHeight = 200;
		int frameRateCharts = (int) frameRate * 20;

		Chart xpos = cp5.addChart("X-Position").setPosition(50, 50)
				.setSize(sizeWidth, sizeHeight).setRange(0, this.width)
				.setView(Chart.AREA).addDataSet("xpos");

		Chart ypos = cp5.addChart("Y-Position").setPosition(500, 50)
				.setSize(sizeWidth, sizeHeight).setRange(0, this.height)
				.setView(Chart.AREA).addDataSet("ypos");

		Chart xacc = cp5.addChart("X-Acceleration").setPosition(50, 400)
				.setSize(sizeWidth, sizeHeight)
				.setRange(-this.width / 2, this.width / 2).setView(Chart.LINE)
				.addDataSet("xacc");

		Chart yacc = cp5.addChart("Y-Acceleration").setPosition(500, 400)
				.setSize(sizeWidth, sizeHeight)
				.setRange(-this.height / 4, this.height / 4)
				.setView(Chart.LINE).addDataSet("yacc");

		charts.put("xpos", xpos);
		charts.put("ypos", ypos);
		charts.put("xacc", xacc);
		charts.put("yacc", yacc);

		setStuff(frameRateCharts);
	}

	public void draw() {
		background(0);
		handPos = new PVector(mouseX, mouseY);

		charts.get("ypos").push("ypos", (float) handPos.y);
		charts.get("xpos").push("xpos", (float) handPos.x);

		charts.get("xacc").push("xacc", (float) calcAcceleration(queX, mouseX));
		charts.get("yacc").push("yacc", (float) calcAcceleration(queY, mouseY));

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
	

	private void setStuff(int frameRateCharts) {
		for (Entry<String, Chart> e : charts.entrySet()) {
			e.getValue().getColor().setBackground(color(255, 100));
			e.getValue().setStrokeWeight(1f);
			e.getValue().setColors(e.getKey(), color(255), color(0, 255, 0));
			e.getValue().setData(e.getKey(), new float[frameRateCharts]);
		}

	}
}