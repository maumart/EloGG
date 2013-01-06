package components;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Layout extends PApplet {
	private List<Itile> tiles = new ArrayList<>();
	private PApplet p;
	private int pTop;
	private int pBottom;
	private int pLeft = 20;
	private int pRight = 20;
	private int margin = 50;

	public Layout(PApplet p, int num, int paddingTop, int paddingBottom,
			int paddingLeft, int paddingRight, int margin) {
		this.p = p;
		this.pTop = paddingTop;
		this.pBottom = paddingBottom;
		this.margin = margin;

		createButtons(num);
		createTrackpad();
	}

	public void draw(PVector handLeft, PVector handRight) {
		for (Itile tile : tiles) {

			Effect effectLeft = tile.intersects(Math.round(handLeft.x),
					Math.round(handLeft.y), Math.round(handLeft.z), 0);

			// Effect hoverRight = tile.intersects(Math.round(handRight.x),
			// Math.round(handRight.y), Math.round(handRight.z), 1);

			tile.draw(p);
		}

		// Hand
		p.fill(255, 0, 255);
		p.ellipse(handLeft.x, handLeft.y, 15, 15);
	}

	private void createButtons(int num) {
		int heightAvailable = p.height - pTop - pBottom;
		int buttonSize = Math.round((heightAvailable - margin * (num - 1))
				/ num);

		for (int i = 0; i < num; i++) {
			int x = pLeft;
			int y = pTop + buttonSize * i + margin * i;

			Itile tile = new Button(i, x, y, buttonSize, buttonSize);
			tiles.add(tile);
		}

	}

	private void createTrackpad() {
		Button b = (Button) tiles.get(0);
		int x = b.getX() + b.getWidth() + margin;
		int y = pTop;
		int width = p.width - pRight - x;
		int height = p.height - pTop - pBottom;

		Itile trackpad = new Trackpad(-1, x, y, width, height);
		tiles.add(trackpad);
	}
}
