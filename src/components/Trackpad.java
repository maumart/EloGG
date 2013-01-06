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
		p.fill(colorCurrent);
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

	// private int mapValue(int value) {
	// float start = 0;
	// float stop = 127;
	//
	// int mappedValue = (int) PApplet.map((float) value, (float) x,
	// (float) (x + width), start, stop);
	// return mappedValue;
	// }

	@Override
	public Effect intersects(int posX, int posY, int posZ, int hand) {
		// TODO Auto-generated method stub
		hover(false);
		Effect effect = new Effect(-1, -1, -1, -1);

		if (posX >= x && posX <= x + width && posY >= y && posY <= y + height) {
			hover(true);
			int mappedX = (int) PApplet.map((float) posX, (float) x,
					(float) (x + width), 0, 127);
			int mappedY = (int) PApplet.map((float) posY, (float) y,
					(float) (y + height), 0, 127);
			int mappedZ = 0;

			effect = new Effect(id, mappedX, mappedY, mappedZ);

			//System.out.println(effect.toString());
		}

		return effect;
	}

	public Effect intersect(PVector handLeft, PVector handRight) {
		// TODO Auto-generated method stub
		return null;
	}

}
