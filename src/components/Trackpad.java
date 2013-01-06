package components;

import processing.core.PApplet;
import processing.core.PVector;

public class Trackpad implements Itile {
	private int id;
	private int x;
	private int y;
	private int width;
	private int height;
	
	private int colorNormal = 25;
	private int colorHover = 255;
	private int colorCurrent = 25;


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
	public void hover(boolean b) {	
		// TODO Auto-generated method stub
		if (b) {
			colorCurrent = colorHover;
		} else {
			colorCurrent = colorNormal;
		}		
	}

	@Override
	public Effect intersects(int posX, int posY, int posZ, int hand) {		
		// TODO Auto-generated method stub
		// int mappedX = PApplet.map(x, istart, istop, ostart, ostop);
		hover(false);

		if (posX >= x && posX <= x + width && posY >= y && posY <= y + height) {
			System.out.println(id);
			hover(true);
		}

		Effect e = new Effect(id, x, y, 0);
		return e;
	}

	public Effect intersect(PVector handLeft, PVector handRight) {
		// TODO Auto-generated method stub
		return null;
	}

}
