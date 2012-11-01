package kinect;
import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

public class KinectUser {
	public SimpleOpenNI kinect;
	public int userId;	
	public PApplet p;
	public PVector leftHand;
	public PVector rightHand;
	
	public KinectUser(PApplet p, SimpleOpenNI kinect, int userId){
		this.kinect=kinect;
		this.userId=userId;
		this.p= p;	
	}
	
	public PVector getLeftHand(){		
		return leftHand;
	}
	
	public PVector getRightHand(){		
		return rightHand;
	}
	
	public void updateLimbs(){		
		PVector rightHandReal = new PVector();
		PVector rightHandProjected =  new PVector();
		
		PVector leftHandReal = new PVector();
		PVector leftHandProjected =  new PVector();
		
		PVector leftHandProjectedUnscalled = new PVector();
		PVector rightHandProjectedUnscalled =  new PVector();
		
		//Handposition auslesen 
		kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_RIGHT_HAND,rightHandReal);
		kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_LEFT_HAND,leftHandReal);

		// Pos Konvertieren
		kinect.convertRealWorldToProjective(rightHandReal, rightHandProjected);
		kinect.convertRealWorldToProjective(rightHandReal, rightHandProjectedUnscalled);
		
		kinect.convertRealWorldToProjective(leftHandReal, leftHandProjected);
		kinect.convertRealWorldToProjective(leftHandReal, leftHandProjected);
		kinect.convertRealWorldToProjective(leftHandReal, leftHandProjectedUnscalled);		
		
		p.color(123,123,123);
		p.fill(0,255);	

		// SKalierungsfaktor fuer 3D
		//float sfaktorr= (p.map(rightHandProjected.z,300,3000,7,1));			

		// Handboxen zeichnen x y z
		float rectSize=50;		
		p.rect(leftHandProjected.x-rectSize/2,leftHandProjected.y-rectSize/2,rectSize,rectSize);
		p.rect(rightHandProjected.x-rectSize/2,rightHandProjected.y-rectSize/2,rectSize,rectSize);		
		
		this.rightHand=rightHandProjectedUnscalled;
		this.leftHand=leftHandProjectedUnscalled;		
	}	
}
