package model;

import java.util.Queue;

import processing.core.PVector;

public class Player {
	private int id;
	private PVector handLeft;
	private PVector handRight;
	private PVector elbowLeft;
	private PVector elbowRight;
	private PVector shoulderLeft;
	private PVector shoulderRight;
	private PVector head;
	private PVector neck;
	private PVector torso;
	private PVector centerOfMass;

	private Queue<PVector> GuitarStrum;
	private Queue<PVector> GuitarHead;

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
		handLeft=v;
	}

	public void setHandRight(PVector v) {
		handRight=v;
	}
	
	public void setCOM(PVector v) {
		centerOfMass=v;
	}
}
