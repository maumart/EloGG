package tilesNew;

import processing.core.PApplet;

public class Trackpad implements Itile {
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Trackpad(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}	

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		p.fill(200);
		p.rect(x, y, width, height);
	}

	@Override
	public void hover() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean intersects() {
		// TODO Auto-generated method stub
		return false;
	}

}
