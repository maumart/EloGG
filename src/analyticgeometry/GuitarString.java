package analyticgeometry;

import processing.core.PVector;

public class GuitarString {
	private PVector vectorStart;
	private PVector vectorEnde;
	 float padding;
	

	public GuitarString(float padding) {
		super();
		this.vectorStart = new PVector();
		this.vectorEnde = new PVector();
		this.padding = padding;
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