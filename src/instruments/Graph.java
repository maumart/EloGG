package instruments;

import processing.core.PApplet;
import controlP5.*;

public class Graph extends PApplet {

	ControlP5 cp5;

	Chart myChart;

	public void setup() {
		size(400, 700);
		smooth();
		cp5 = new ControlP5(this);
		cp5.printPublicMethodsFor(Chart.class);
		myChart = cp5.addChart("hello").setPosition(50, 50).setSize(200, 200)
				.setRange(-20, 20).setView(Chart.LINE) // use Chart.LINE,
														// Chart.PIE,
														// Chart.AREA,
														// Chart.BAR_CENTERED
		;

		myChart.getColor().setBackground(color(255, 100));

		myChart.addDataSet("world");
		myChart.setColors("world", color(255, 0, 255), color(255, 0, 0));
		myChart.setData("world", new float[4]);

		myChart.setStrokeWeight(1.5f);

		myChart.addDataSet("earth");
		myChart.setColors("earth", color(255), color(0, 255, 0));
		myChart.updateData("earth", 1, 2, 10, 3);

	}

	public void draw() {
		background(0);
		// unshift: add data from left to right (first in)
		myChart.unshift("world", (sin(frameCount * 0.01f) * 10));

		// push: add data from right to left (last in)
		myChart.push("earth", (sin(frameCount * 0.1f) * 10));
	}
}