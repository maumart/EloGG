import java.util.ArrayList;



import SimpleOpenNI.SimpleOpenNI;
import processing.core.PApplet;
import processing.core.PVector;
import themidibus.MidiBus;


public class Main extends PApplet {
	public boolean kinectAvailable=true;
	public PVector handLeft;
	public PVector handRight;
	public ArrayList<KeyBar> keyBars= new ArrayList<KeyBar>();
	public ArrayList<KinectUser> userList = new ArrayList<KinectUser>();
	
	//Kinectstuff
	SimpleOpenNI kinect;
	boolean initialized;
	Kinect k;	
	
	//Midi
	public MidiBus myBus;	
	
	public void setup(){		
		if (kinectAvailable) {
			k = new Kinect(this);
			kinect = k.getKinect();	
		}	
		
		
		//Processing Stuff
		this.size(640,480);
		this.stroke(255);
		this.strokeWeight(5);
		this.noFill();  
		this.smooth();
		this.frameRate(60);
		
		//Bars erstellen
		createKeyBar(4);
	}
	
	
	public void draw(){
		this.background(0);	
		
		if (kinectAvailable) {		
			// Kinect updaten jeden Frame --> WICHTIG
			kinect.update();
			
			//Kinectbild
			image(kinect.sceneImage(),0,0);
			
			for (KinectUser user: userList){
				
				
				
				if (kinect.isTrackingSkeleton(user.userId)){					
					
					user.updateLimbs();
					if (user.getLeftHand() != null 
							&& user.getLeftHand() != null) { 
						
					System.out.println(user.getLeftHand());			
				
					handLeft=user.getLeftHand();
					handRight=user.getRightHand();			
					}
			
				}
			}
			//System.out.println(userList.size());
			
		} else {
			
			handLeft= new PVector(mouseX,mouseY,0);
			handRight= new PVector(mouseX,mouseY,0);
			
			
		}
		
		
		
		
			//BB Test		
			for (KeyBar bar: keyBars){
				
				if (handLeft != null 
						&& handRight != null) { 
					
					int val = bar.intersects(Math.round(handLeft.x), Math.round(handLeft.y));
					if (val > 0) {
						//System.out.println(val);
						bar.currColor=bar.hoverColor;			
						
					} else {
						bar.currColor=bar.normalColor;				
					}			
					//Print
				
				}
				
				
				bar.draw();
			}		
		
		}
	
	
	public void createKeyBar(int count){				
		//Algorithmus
		int height=this.height;		
		int y=0;
		int gutter=this.width/(count*2);		
		int width=(this.width-(gutter*(count-1)))/count;
		
		for (int i=0;i<count; i++){
			int x=i*width+gutter*i;
			KeyBar bar= new KeyBar(this,x,y,width,height);
			keyBars.add(bar);			
			
		}	
		
	}
	
	//Callbacks Simple openni
	
	public void onNewUser(int userId){
		println("Neuer User erkannt");
		kinect.startPoseDetection("Psi",userId);
		kinect.startTrackingSkeleton(userId);
		
		// Erkannter User zur Collection hinzufuegen
		KinectUser user= new KinectUser(this, kinect, userId);
		userList.add(user);		
	}
	
	// Callback Kalibrierungsbeginn
	public void onStartCalibration(int userId){
		println("onStartCalibration - userId: " + userId);
	}
	// Callback Kalibrierungsende
	public void onEndCalibration(int userId, boolean successfull){
		println("onEndCalibration - userId: " + userId + ", successfull: " + successfull);

		if (successfull){ 
			println("  User calibrated !!!");
			kinect.startTrackingSkeleton(userId);
			
		} else { 
			println("  Failed to calibrate user !!!");
			println("  Start pose detection");
			kinect.startPoseDetection("Psi",userId);
		}
	}
	// Callback Pose Begin 
	public void onStartPose(String pose,int userId){
		println("onStartPose - userId: " + userId + ", pose: " + pose);
		println(" stop pose detection");

		kinect.stopPoseDetection(userId); 
		kinect.requestCalibrationSkeleton(userId, true);

	}
	// Callback Pose Ende
	public void onEndPose(String pose,int userId){
		println("onEndPose - userId: " + userId + ", pose: " + pose);
	}	

}
