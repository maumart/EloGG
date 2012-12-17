package secondscreen;

import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import processing.core.PFont;
import controlP5.ControlP5;
import controlP5.Slider;

public class ControlScreen extends PApplet {
	private ControlP5 cp5;
	private PApplet p;
	private List<Slider> sliderList = new ArrayList<>();
	private int width;
	private int height;
	private int controlMargin = 50;

	private int background = 0;
	private int margin = 60;
	private int gutter = 40;
	private int heightSlider = 250;
	private int widthSlider = 40;

	private Slider sliderBackground;
	private Slider sliderMargin;
	private Slider sliderGutter;

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
			Slider slider = sliderList.get(i);			
			slider.setWidth(widthSlider);
			slider.setHeight(heightSlider);
			slider.setPosition(controlMargin + slider.getWidth() * 4*i, controlMargin);
		}

	}

	private void addControlElements(ControlP5 cp5) {
		sliderBackground = cp5.addSlider("Background").plugTo(p, "background")
				.setRange(0, 255).setValue(background);
		sliderMargin = cp5.addSlider("Margin").plugTo(p, "margin")
				.setRange(10, 150).setValue(margin);
		sliderGutter = cp5.addSlider("Gutter").plugTo(p, "gutter")
				.setRange(10, 100).setValue(gutter);

		sliderList.add(sliderBackground);
		sliderList.add(sliderMargin);
		sliderList.add(sliderGutter);
	}

	public ControlP5 control() {
		return cp5;
	}

}