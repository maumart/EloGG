package components;

import processing.core.PApplet;

public class Button implements Itile {
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Button(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}	

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
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
