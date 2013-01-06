package components;

import processing.core.PApplet;
import processing.core.PVector;

public class Button implements Itile {
	private int x;
	private int y;
	private int width;
	private int height;
	private int id;

	private int colorNormal = 25;
	private int colorHover = 255;
	private int colorCurrent = 25;

	public Button(int id, int x, int y, int width, int height) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public int getWidth() {
		return width;
	}

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		p.fill(colorCurrent);
		p.rect(x, y, width, height);
	}

	@Override
	public void hover() {
		colorCurrent = colorHover;
		// TODO Auto-generated method stub

	}

	@Override
	public Effect intersects(int x, int y, int z, int hand) {
		// TODO Auto-generated method stub
		Effect e = new Effect(id, x, y, z);
		
		return e;
	}

	public Effect intersect(PVector handLeft, PVector handRight) {
		// TODO Auto-generated method stub
		return null;
	}

}
