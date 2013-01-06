package secondscreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import processing.core.PApplet;
import processing.core.PFont;
import controlP5.ControlP5;
import controlP5.ControllerInterface;
import controlP5.Slider;

public class ControlScreen extends PApplet {
	private ControlP5 cp5;
	private PApplet p;
	private List<ControllerInterface> sliderList = new ArrayList<>();
	private int width;
	private int height;
	private int controlMargin = 50;

	// initial values
	// private int background = 0;
	private int paddingTop = 20;
	private int paddingBottom = 20;
	private int margin = 20;
	private int heightSlider = 250;
	private int widthSlider = 40;
	private int num = 4;

	public ControlScreen(PApplet p, int width, int height) {
		this.p = p;
		this.width = width;
		this.height = height;
	}

	public void setup() {
		size(width, height);
		frameRate(60);
		cp5 = new ControlP5(this);
		PFont p = createFont("Verdana", 16, true);
		cp5.setFont(p);
		addControlElements(cp5);
	}

	public void draw() {
		this.background(0);

		int length = sliderList.size();
		for (int i = 0; i < length; i++) {
			Slider slider = (Slider) sliderList.get(i);
			slider.setWidth(widthSlider);
			slider.setHeight(heightSlider);
			slider.setPosition(controlMargin + slider.getWidth() * 4 * i,
					controlMargin);
		}

	}

	private void addControlElements(ControlP5 cp5) {

		// Slider sliderBackground = cp5.addSlider("Background")
		// .plugTo(p, "background").setRange(0, 255).setValue(background);

		Slider slidernumber = cp5.addSlider("Effects#").plugTo(p, "num")
				.setRange(2, 6).setValue(num);

		Slider sliderPaddingTop = cp5.addSlider("Padding-Top")
				.plugTo(p, "paddingTop").setRange(0, 100).setValue(paddingTop);

		Slider sliderPaddingBottom = cp5.addSlider("Padding-Bottom")
				.plugTo(p, "paddingBottom").setRange(0, 100)
				.setValue(paddingBottom);

		Slider slidermargin = cp5.addSlider("Margin").plugTo(p, "margin")
				.setRange(10, 100).setValue(margin);

		// sliderList.add(sliderBackground);

		sliderList.add(slidernumber);
		sliderList.add(sliderPaddingTop);
		sliderList.add(sliderPaddingBottom);
		sliderList.add(slidermargin);

		// Collections.reverse(sliderList);
	}

	public ControlP5 control() {
		return cp5;
	}

}