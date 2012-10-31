import processing.core.PApplet;

public class KeyBar {
	public int x;
	public int y;
	public int width;
	public int height;
	public PApplet p;
	public String id;

	public KeyBar(PApplet p, int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.p=p;
	}
	
	public void draw(){
		p.rect(x,y,width,height);
		
	}

	public int intersects(int posX, int posY) {
		if (posX >= x && posX <= x + width && posY >= y
				&& posY <= y + height * 0.25) {
			return 4;
		} else if (posX >= x && posX <= x + width
				&& posY >= y + height * (1 / 4) && posY <= y + height * 0.5) {
			return 3;
		} else if (posX >= x && posX <= x + width
				&& posY >= y + height * (2 / 4) && posY <= y + height * 0.75) {
			return 2;
		} else if (posX >= x && posX <= x + width
				&& posY >= y + height * (3 / 4) && posY <= y + height) {
			return 1;
		} else {
			return -1;
		}

	}

}
