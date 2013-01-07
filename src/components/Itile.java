package components;

import processing.core.PApplet;
import processing.core.PVector;

public interface Itile {
	public void draw(PApplet p);
	
	public void hover(boolean b);
	
	public Event intersects(int posX, int posY, int posZ, int hand);	

}
