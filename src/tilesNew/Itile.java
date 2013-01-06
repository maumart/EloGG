package tilesNew;

import processing.core.PApplet;

public interface Itile {
	public void draw(PApplet p);
	
	public void hover();
	
	public boolean intersects();	

}
