package instruments;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import SimpleOpenNI.SimpleOpenNI;
import controlP5.ControlP5;
import controlP5.ControllerInterface;
import controlP5.Slider;
import controlP5.Toggle;

public class ControlScreen extends PApplet {
	private ControlP5 cp5;
	private PApplet p;
	private List<ControllerInterface> controllList = new ArrayList<>();
	private int width;
	private int height;
	private int controlMargin = 40;
	private SimpleOpenNI context;

	// initial values
	private int paddingTop = 20;
	private int paddingBottom = 20;
	private int margin = 20;
	private int heightControll = 250;
	private int widthControll = 40;
	private int num = 4;
	private float scaleFactor = 1.333f;

	public ControlScreen(PApplet p, int width, int height) {
		this.p = p;
		this.width = width;
		this.height = height;
	}

	public ControlScreen(PApplet p, int width, int height, SimpleOpenNI context) {
		this.p = p;
		this.width = width;
		this.height = height;
		this.context = context;
	}

	public void setup() {
		size(width, height);
		frameRate(60);
		cp5 = new ControlP5(this);
		PFont p = createFont("Verdana", 12, true);
		cp5.setFont(p);
		// addControlElements(cp5);
	}

	public void draw() {
		this.background(0);

		/*
		 * int length = controllList.size(); for (int i = 0; i < length; i++) {
		 * Controller controllElement = (Controller) controllList.get(i);
		 * controllElement.setWidth(widthControll);
		 * controllElement.setHeight(heightControll);
		 * controllElement.setPosition( controlMargin +
		 * controllElement.getWidth() * 3 * i, controlMargin); }
		 */

		if (context != null) {
			if (context.isInit()) {
				PImage scene = context.sceneImage();
				// scene.resize(320, 240);
				image(scene, margin, margin, 320, 240);
			}
		}

	}

	private void drawControlLimbs() {

	}

	private void addControlElements(ControlP5 cp5) {
		Slider slidernumber = cp5.addSlider("Effects #").plugTo(p, "num")
				.setRange(2, 6).setValue(num);

		Slider sliderPaddingTop = cp5.addSlider("Padding-Top")
				.plugTo(p, "paddingTop").setRange(0, 100).setValue(paddingTop);

		Slider sliderPaddingBottom = cp5.addSlider("Padding-Bottom")
				.plugTo(p, "paddingBottom").setRange(0, 100)
				.setValue(paddingBottom);

		Slider sliderMargin = cp5.addSlider("Margin").plugTo(p, "margin")
				.setRange(10, 100).setValue(margin);

		Slider sliderScaleFactor = cp5.addSlider("Scale")
				.plugTo(p, "scaleFactor").setRange(1f, 3f)
				.setValue(scaleFactor);

		Toggle onOffToggle = cp5.addToggle("Launch").plugTo(p, "startGame")
				.setSize(50, 20).setValue(false).setMode(ControlP5.SWITCH);

		controllList.add(slidernumber);
		controllList.add(sliderPaddingTop);
		controllList.add(sliderPaddingBottom);
		controllList.add(sliderMargin);

		controllList.add(sliderScaleFactor);
		controllList.add(onOffToggle);

		// Collections.reverse(sliderList);
	}

	public ControlP5 control() {
		return cp5;
	}

}