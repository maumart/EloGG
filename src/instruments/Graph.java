package instruments;

import java.util.HashMap;
import java.util.Map.Entry;

import processing.core.PApplet;
import processing.core.PVector;
import controlP5.Chart;
import controlP5.ControlP5;

public class Graph extends PApplet {
	ControlP5 cp5;

	HashMap<String, Chart> charts = new HashMap<>();

	PVector centerOfMass;
	PVector handPos;

	public void setup() {
		size(1024, 768);
		smooth();
		frameRate(60);

		int centerX = this.width / 2;
		int centerY = this.height / 2;

		centerOfMass = new PVector(centerX, centerY);
		handPos = new PVector(mouseX, mouseY);

		cp5 = new ControlP5(this);
		int sizeWidth = 400;
		int sizeHeight = 150;
		int frameRateCharts = (int) frameRate * 20;

		Chart xpos = cp5.addChart("X-Position").setPosition(50, 50)
				.setSize(sizeWidth, sizeHeight).setRange(0, this.width)
				.setView(Chart.AREA).addDataSet("xpos");

		Chart ypos = cp5.addChart("Y-Position").setPosition(500, 50)
				.setSize(sizeWidth, sizeHeight).setRange(0, this.height)
				.setView(Chart.AREA).addDataSet("ypos");

		charts.put("xpos", xpos);
		charts.put("ypos", ypos);

		setStuff(frameRateCharts);
	}

	public void draw() {
		background(0);
		handPos = new PVector(mouseX, mouseY);

		charts.get("ypos").push("ypos", (float) handPos.y);
		charts.get("xpos").push("xpos", (float) handPos.x);
	}

	private void setStuff(int frameRateCharts) {
		// Color
		for (Entry<String, Chart> e : charts.entrySet()) {
			e.getValue().getColor().setBackground(color(255, 100));
			e.getValue().setStrokeWeight(1f);
			e.getValue().setColors(e.getKey(), color(255), color(0, 255, 0));
			e.getValue().setData(e.getKey(), new float[frameRateCharts]);
		}

	}
}