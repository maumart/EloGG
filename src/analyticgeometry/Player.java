package analyticgeometry;

import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.DelayQueue;

import processing.core.PVector;

public class Player {
	private int id;
	private PVector elbowLeft = new PVector();
	private PVector elbowRight = new PVector();
	private PVector shoulderLeft = new PVector();
	private PVector shoulderRight = new PVector();
	private PVector head = new PVector();
	private PVector torso = new PVector();
	private Queue<PVector> GuitarStrum;
	private Queue<PVector> GuitarHead;

	public PVector handLeft = new PVector();
	public PVector handRight = new PVector();
	public PVector neck = new PVector();
	public PVector centerOfMass = new PVector();

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

	public Player(int id, PVector handLeft, PVector handRight, PVector centerOfMass,
			PVector shoulderLeft, PVector shoulderRight, PVector elbowLeft, PVector elbowRight) {
		super();
		this.id = id;
		this.handLeft = handLeft;
		this.handRight = handRight;
		this.centerOfMass = centerOfMass;
		this.shoulderLeft = shoulderLeft;
		this.shoulderRight = shoulderRight;
		this.elbowLeft = elbowLeft;
		this.elbowRight = elbowRight;
	}

	public Player(int id, PVector handLeft, PVector handRight, PVector elbowLeft,
			PVector elbowRight, PVector shoulderLeft, PVector shoulderRight, PVector head,
			PVector neck, PVector torso, PVector centerOfMass) {
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

	public PVector getHandRightAbsolute() {
		PVector newVector = new PVector();
		newVector.x = handRight.x - centerOfMass.x;
		newVector.y = handRight.y - centerOfMass.y;

		return newVector;
	}

	public PVector getHandLeftAbsolute() {
		PVector newVector = new PVector();
		newVector.x = handLeft.x - centerOfMass.x;
		newVector.y = handLeft.y - centerOfMass.y;

		return newVector;
	}

	public PVector getNeckAbsolute() {
		PVector newVector = new PVector();
		newVector.x = neck.x - centerOfMass.x;
		newVector.y = neck.y - centerOfMass.y;

		return newVector;
	}

	public PVector getCenterOfTorso() {
		PVector newVector = new PVector();

		return newVector;
	}

	public int armLegth() {
		int armLengthDefault = 200;

		return Math.round(armLengthDefault);

	}

	public PVector elbowHandLeft() {
		PVector elbowHandLeft = new PVector(handLeft.x - elbowLeft.x, handLeft.y - elbowLeft.y);
		return elbowHandLeft;
	}

	public PVector elbowShoulderLeft() {
		PVector elbowShoulderLeft = new PVector(shoulderLeft.x - elbowLeft.x, shoulderLeft.y
				- elbowLeft.y);
		return elbowShoulderLeft;
	}

	public PVector elbowHandRight() {
		PVector elbowHandRight = new PVector(handRight.x - elbowRight.x, handRight.y - elbowRight.y);
		return elbowHandRight;
	}

	public PVector elbowShoulderRight() {
		PVector elbowShoulderRight = new PVector(shoulderRight.x - elbowRight.x, shoulderRight.y
				- elbowRight.y);
		return elbowShoulderRight;
	}

	public void setShoulderLeft(PVector v) {
		shoulderLeft = v.get();
	}

	public void setShoulderRight(PVector v) {
		shoulderRight = v.get();
	}

	public void setElbowLeft(PVector v) {
		elbowLeft = v;
	}

	public void setElbowRight(PVector v) {
		elbowRight = v;
	}

	public PVector getElbowLeft() {
		return elbowLeft.get();
	}

	public PVector getShoulderLeft() {
		return shoulderLeft.get();
	}

	public PVector getElbowRight() {
		return elbowRight.get();
	}

	public PVector getShoulderRight() {
		return shoulderRight.get();
	}

}
