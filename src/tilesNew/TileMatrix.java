package tilesNew;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class TileMatrix {
	private ArrayList<Tile> tiles = new ArrayList<>();
	private PApplet p;

	public TileMatrix(PApplet p, int cols, int rows, int margin, int gutter) {
		this.p = p;

		// createTiles(4, 8);
		createTiles(cols, rows, margin, gutter);

	}

	private void createTiles(int cols, int rows, int margin, int gutter) {
		int maxHeight = p.height;
		int maxWidth = p.width;
		//int margin = 60;
		//int gutter = 40;
		int gutterVertical = 20;

		// Tile dimensions
		int height = (maxHeight - (margin * 2) - gutterVertical * (rows - 1))
				/ rows;
		int width = (maxWidth - (margin * 2) - gutter * (cols - 1)) / cols;

		// Vertical
		for (int k = 0; k < rows; k++) {
			int y = k * (height + gutterVertical) + margin;
			// Horizontal
			for (int i = 0; i < cols; i++) {
				int x = i * (width + gutter) + margin;
				int color = Math.round(p.random(100, 255));

				Tile tile = new Tile(p, x, y, width, height, i, color);
				tiles.add(tile);

			}

		}

	}

	public void draw(PVector handLeft, PVector handRight) {

		for (Tile tile : tiles) {

			// int[] valueLeft = tile.intersects((int) handLeft.x,
			// (int) handLeft.y, (int) handLeft.z, 0);
			//
			// if (valueLeft[1] != -1) {
			// tile.hover(true);
			// } else {
			// tile.hover(false);
			// }

			int hoverLeft = tile.intersectTest(Math.round(handLeft.x),
					Math.round(handLeft.y), Math.round(handLeft.z), 0);

			int hoverRight = tile.intersectTest(Math.round(handRight.x),
					Math.round(handRight.y), Math.round(handRight.z), 1);

			if ((hoverLeft == 1) || (hoverRight == 1)) {
				tile.hover(true);
			} else {
				tile.hover(false);
			}

			// System.out.println(hoverLeft);
			//System.out.println(hoverRight);

			tile.draw();

		}

	}

}
