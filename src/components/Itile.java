package components;

import processing.core.PApplet;
import processing.core.PVector;

public interface Itile {
	public void draw(PApplet p);
	
	public void hover();
	
	public Effect intersects(int x, int y, int z, int hand);	

}
