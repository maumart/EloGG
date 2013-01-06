package components;

import processing.core.PApplet;
import processing.core.PVector;

public class Trackpad implements Itile {
	private int id;
	private int x;
	private int y;
	private int width;
	private int height;

	public Trackpad(int id, int x, int y, int width, int height) {

		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		// p.fill(200);
		p.rect(x, y, width, height);
	}

	@Override
	public void hover() {
		// TODO Auto-generated method stub

	}

	@Override
	public Effect intersects(int x, int y, int z, int hand) {
		// TODO Auto-generated method stub
		return null;
	}

	public Effect intersect(PVector handLeft, PVector handRight) {
		// TODO Auto-generated method stub
		return null;
	}

}
