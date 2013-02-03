package instruments;

import processing.core.PVector;
import controlP5.Chart;

public class GraphChart {
	private Chart chart;
	
	public GraphChart(Chart chart){
		this.chart = chart;		
	}
	
	public PVector calcAngle(PVector v1, PVector v2, PVector v3){
		v1 = v1.sub(v2);
		
		
	}	
	
}
