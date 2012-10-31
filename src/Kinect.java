import java.util.ArrayList;

import SimpleOpenNI.SimpleOpenNI;
import processing.core.PApplet;
import processing.core.PVector;

public class Kinect {
	public SimpleOpenNI context;
	public boolean initialized;
	public SimpleOpenNI kinect;
	public PApplet p;
	//ArrayList<PVector> joints= new ArrayList<PVector>();
	//ArrayList<User> users= new ArrayList<User>();	
	
	public Kinect(PApplet p) {
		this.context = new SimpleOpenNI(p,SimpleOpenNI.RUN_MODE_MULTI_THREADED);	
		this.settings();
		this.initialized=false;
		this.p=p;
		this.kinect=new SimpleOpenNI(p,SimpleOpenNI.RUN_MODE_MULTI_THREADED);
	}
	
	public SimpleOpenNI getKinect(){
		if (context.init()) {
			return context;
		}
		else return null;
	}
	
	public boolean checkAvailability(){
		if (context.init()) {
			this.initialized=true;
		}
		return initialized;		
	}
	
	private void settings(){ 
	  // Quellen aktivieren
      context.enableDepth();  
	  //context.enableIR();
	  context.enableRGB();
	  context.enableHands();
	  context.enableGesture();
	  
	  // Alle Bones
	  context.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
	  //context.enableUser(SimpleOpenNI.SKEL_PROFILE_HEAD_HANDS);
	  
	  // Spiegeln
	  context.setMirror(true);
	  
	  // Gesten
	  context.addGesture("Wave");
	  context.addGesture("Click");
	  context.addGesture("RaiseHand");  
	  
	  //Enable Scene
	  context.enableScene(640,480,60);	  
	  
	  //return context;
	}
	
	public void onNewUser(int userId){
		p.println("Neuer User erkannt");
		kinect.startPoseDetection("Psi",userId);
		kinect.startTrackingSkeleton(userId);
		
		// Erkannter User zur Collection hinzufuegen
		//User user= new User(papplet, kinect, userId);
		//users.add(user);		
	}
	
	// Callback Kalibrierungsbeginn
	public void onStartCalibration(int userId){
		p.println("onStartCalibration - userId: " + userId);
	}
	// Callback Kalibrierungsende
	public void onEndCalibration(int userId, boolean successfull){
		p.println("onEndCalibration - userId: " + userId + ", successfull: " + successfull);

		if (successfull){ 
			p.println("  User calibrated !!!");
			kinect.startTrackingSkeleton(userId);
			
		} else { 
			p.println("  Failed to calibrate user !!!");
			p.println("  Start pose detection");
			kinect.startPoseDetection("Psi",userId);
		}
	}
	// Callback Pose Begin 
	public void onStartPose(String pose,int userId){
		p.println("onStartPose - userId: " + userId + ", pose: " + pose);
		p.println(" stop pose detection");

		kinect.stopPoseDetection(userId); 
		kinect.requestCalibrationSkeleton(userId, true);

	}
	// Callback Pose Ende
	public void onEndPose(String pose,int userId){
		p.println("onEndPose - userId: " + userId + ", pose: " + pose);
	}	


}
