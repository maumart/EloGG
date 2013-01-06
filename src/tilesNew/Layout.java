package tilesNew;

import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import components.Button;
import components.Itile;
import components.Trackpad;

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

	public void draw() {
		for (Itile tile : tiles) {
			
			tile.hover();
			tile.draw(p);
		}
	}

	private void createButtons(int num) {
		int heightAvailable = p.height - pTop - pBottom;

		int buttonHeight = Math.round((heightAvailable - margin * (num - 1))
				/ num);
		int buttonWidth = buttonHeight;

		for (int i = 0; i < num; i++) {
			int x = pLeft;
			int y = pTop + buttonHeight * i + margin * i;

			Itile tile = new Button(x, y, buttonWidth, buttonHeight);
			tiles.add(tile);
		}

	}

	private void createTrackpad() {
		Button b = (Button) tiles.get(0);
		int x = b.getX() + b.getWidth() + margin;
		int y = pTop;
		int width = p.width - pRight - x;
		int height = p.height - pTop - pBottom;

		Itile trackpad = new Trackpad(x, y, width, height);
		tiles.add(trackpad);
	}
}
