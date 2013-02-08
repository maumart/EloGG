package analyticgeometry;

import processing.core.PVector;

public class GuitarString {
	private PVector vectorStart;
	private PVector vectorEnde;
	public float padding;
	public float dotProduct=1;
	

	public GuitarString(float padding) {
		super();
		this.vectorStart = new PVector();
		this.vectorEnde = new PVector();
		this.padding = padding;
	}
	
	public PVector halfVektor(){
		 PVector pv = new PVector(vectorEnde.x-vectorStart.x,vectorEnde.y-vectorStart.y);		 
		 
		 return pv;
	}
	
	public PVector orthogonalVector(){
		 PVector pv = new PVector(-vectorEnde.y-vectorStart.y,vectorEnde.x-vectorStart.x);
		 //pv.normalize();
		 //PVector rv = new PVector(vectorEnde.x-vectorStart.x,vectorEnde.y-vectorStart.y);		 
		 
		 return pv;
	}
	
	public PVector start(){
		return vectorStart;
	}
	
	public PVector end(){
		return vectorEnde;
	}	
	
	public void draw(){
		
		
	}
}