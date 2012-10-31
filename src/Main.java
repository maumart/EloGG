import java.util.ArrayList;


import SimpleOpenNI.SimpleOpenNI;
import processing.core.PApplet;
import processing.core.PVector;


public class Main extends PApplet {
	public boolean kinectAvailable=false;
	public PVector handLeft;
	public PVector handRight;
	public ArrayList<KeyBar> keyBars= new ArrayList<KeyBar>();
	
	//Kinectstuff
	SimpleOpenNI kinect;
	boolean initialized;
	Kinect k;	
	
	
	public void setup(){		
		if (kinectAvailable) {
			k = new Kinect(this);
			kinect = k.getKinect();			
		}	
		
		//Processing Stuff
		this.size(1440, 720);
		this.stroke(255);
		this.strokeWeight(5);
		this.noFill();  
		this.smooth();
		this.frameRate(60);		
		
	}
	
	public void draw(){
		this.background(0);
		
		handLeft= new PVector(mouseX,mouseY,0);
		handRight= new PVector(mouseX,mouseY,0);	
		
		//KeyBar kb= new KeyBar(p, x, y, WIDTH, HEIGHT);
		//this.rect(handLeft.x,handLeft.y,50,50);
		
		if (kinectAvailable) {		
			// Kinect updaten jeden Frame --> WICHTIG
			kinect.update();			
		}		
		
	}	
	
	public void createKeyBar(int count){
		
		int width=100;
		int gutter=80;
		int height=400;
		int boxNumber=4;
		
		for (int i=0;i<count; i++){
			int x=i*width+gutter*i;			
			
			KeyBar bar= new KeyBar(this,x,y,width,height);
			keyBars.add(bar);
			//System.out.println(x);
			
		}
		
		
	}

}
