package analyticgeometry;

import java.util.Queue;

import processing.core.PVector;

public class Player {
	private int id;

	private PVector elbowLeft;
	private PVector elbowRight;
	private PVector shoulderLeft;
	private PVector shoulderRight;
	private PVector head;	
	private PVector torso;

	private Queue<PVector> GuitarStrum;
	private Queue<PVector> GuitarHead;

	public PVector handLeft;
	public PVector handRight;
	public PVector neck;
	public PVector centerOfMass;

	public Player() {

	}

	public Player(int id) {
		super();
		this.id = id;
	}

	public Player(int id, PVector handLeft, PVector handRight, PVector centerOfMass) {
		super();
		this.id = id;
		this.handLeft = handLeft;
		this.handRight = handRight;
		this.centerOfMass = centerOfMass;
	}

	public Player(int id, PVector handLeft, PVector handRight, PVector elbowLeft, PVector elbowRight,
			PVector shoulderLeft, PVector shoulderRight, PVector head, PVector neck, PVector torso, PVector centerOfMass) {
		super();
		this.id = id;
		this.handLeft = handLeft;
		this.handRight = handRight;
		this.elbowLeft = elbowLeft;
		this.elbowRight = elbowRight;
		this.shoulderLeft = shoulderLeft;
		this.shoulderRight = shoulderRight;
		this.head = head;
		this.neck = neck;
		this.torso = torso;
		this.centerOfMass = centerOfMass;
	}

	public void addGuitarStrum(PVector pv) {
		GuitarStrum.add(pv);
	}

	public PVector getLastGuitarStrum(PVector pv) {
		return GuitarStrum.peek();
	}

	public void setHandLeft(PVector v) {
		handLeft = v;
	}

	public void setHandRight(PVector v) {
		handRight = v;
	}

	public void setElbow(PVector v) {
		this.elbowLeft = v;
	}

	public void setShoulder(PVector v) {
		this.shoulderLeft = v;
	}

	public void setCOM(PVector v) {
		centerOfMass = v;
	}
	
	public void setNeck(PVector v) {
		neck = v;
	}
	
	public PVector getHandRightAbsolute(){
		PVector newVector = new PVector();
		newVector.x = handRight.x-centerOfMass.x;
		newVector.y = handRight.y-centerOfMass.y;
				
		return newVector;
	}
	
	public PVector getHandLeftAbsolute(){
		PVector newVector = new PVector();
		newVector.x = handLeft.x-centerOfMass.x;
		newVector.y = handLeft.y-centerOfMass.y;
				
		return newVector;
	}
	
	public PVector getNeckAbsolute(){
		PVector newVector = new PVector();
		newVector.x = neck.x-centerOfMass.x;
		newVector.y = neck.y-centerOfMass.y;
				
		return newVector;
	}

	public int armLegth() {
		int armLengthDefault = 200;
		
		return Math.round(armLengthDefault);
		
		/*
		float armLengthCalculated = handLeft.mag() + elbowLeft.mag() + shoulderLeft.mag();
		if (armLengthCalculated > armLengthDefault) {
			return Math.round(armLengthCalculated);
		} else {
			return Math.round(armLengthDefault);
		}
		*/
	}
}
