package secondscreen;

import processing.core.PApplet;
import controlP5.ControlP5;

public class Slider2D extends PApplet{
	ControlP5 cp5;

	controlP5.Slider2D s;

	public void setup() {
		size(700, 400);
		cp5 = new ControlP5(this);
		s = cp5.addSlider2D("wave").setPosition(30, 40).setSize(100, 100)
				.setArrayValue(new float[] { 50, 50 })
		// .disableCrosshair()
		;

		smooth();
	}
	
	float cnt;
	public void draw() {
	  background(0);
	  pushMatrix();
	  translate(160,140);
	  noStroke();
	  fill(50);
	  rect(0, -100, 400,200);
	  strokeWeight(1);
	  line(0,0,200, 0);
	  stroke(255);
	  
	  for(int i=1;i<400;i++) {
	    float y0 = cos(map(i-1,0,s.arrayValue()[0],-PI,PI)) * s.arrayValue()[1]; 
	    float y1 = cos(map(i,0,s.arrayValue()[0],-PI,PI)) * s.arrayValue()[1];
	    line((i-1),y0,i,y1);
	  }
	  
	  popMatrix();
	}

}
