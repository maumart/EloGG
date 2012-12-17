package tilesNew;

import processing.core.PApplet;

public class Tile {
	private int x;
	private int y;
	private int width;
	private int height;
	private int hoverColor = 255;
	private int currColor = 50;
	private int normalColor = 50;
	private int key;
	private PApplet p;

	public Tile(PApplet p, int x, int y, int width, int height, int key,
			int color) {
		this.p = p;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.key = key;
		this.currColor = color;
		this.currColor = normalColor;
	}

	public void draw() {
		p.noStroke();
		p.fill(currColor);
		p.rect(x, y, width, height, 0);

	}

	public int intersectTest(int posX, int posY, int posZ, int hand) {

		if (posX >= x && posX <= x + width && posY >= y && posY <= y + height) {
			//hover(true);
			return 1;

		} else {
			//hover(false);
			return 0;

		}

	}

	public int[] intersects(int posX, int posY, int posZ, int hand) {

		int[] keyPitchDepth = new int[4];

		keyPitchDepth[0] = hand;
		keyPitchDepth[1] = key;
		keyPitchDepth[3] = posZ;

		if (posX >= x && posX <= x + width && posY >= y
				&& posY <= y + height * 0.25) {
			int pitch = 3;
			keyPitchDepth[2] = pitch;

			System.out.println("1");
			return keyPitchDepth;

		} else if (posX >= x && posX <= x + width
				&& posY >= y + height * (1 / 4) && posY <= y + height * 0.5) {
			int pitch = 2;
			keyPitchDepth[2] = pitch;

			System.out.println("2");
			return keyPitchDepth;

		} else if (posX >= x && posX <= x + width
				&& posY >= y + height * (2 / 4) && posY <= y + height * 0.75) {
			int pitch = 1;
			keyPitchDepth[2] = pitch;

			System.out.println("3");
			return keyPitchDepth;

		} else if (posX >= x && posX <= x + width
				&& posY >= y + height * (3 / 4) && posY <= y + height) {
			int pitch = 0;
			keyPitchDepth[2] = pitch;

			System.out.println("4");
			return keyPitchDepth;

		} else {

			keyPitchDepth[1] = -1;
			keyPitchDepth[2] = -1;
			keyPitchDepth[3] = -1;

			return keyPitchDepth;
		}

	}

	public void hover(boolean hover) {
		if (hover) {
			this.currColor = this.hoverColor;
		} else {
			this.currColor = this.normalColor;

		}
	}

}
