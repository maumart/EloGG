package analyticgeometry;

import processing.core.PVector;

public class DrumSingle {
	private PVector vectorStart;
	private PVector vectorEnde;
	public PVector centerOfVector; 
	
	public int	id;	
	
	public float padding;
	public float dotProduct=1;	
	
	public DrumSingle(float padding, int id) {
		super();
		this.vectorStart = new PVector();
		this.vectorEnde = new PVector();
		this.padding = padding;
		this.id = id;
	}
	
	public PVector start(){
		return vectorStart;
	}
	
	public PVector end(){
		return vectorEnde;
	}	
	

}
