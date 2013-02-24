package analyticgeometry;

import processing.core.PVector;

public class DrumSingle {
	private PVector vectorStart;
	private PVector vectorEnde;
	private PVector vectorCenter;

	public int id;

	public float padding;
	public float dotProduct = 1;

	public DrumSingle(float padding, int id) {
		super();
		this.vectorStart = new PVector();
		this.vectorEnde = new PVector();
		this.vectorCenter = new PVector();
		this.padding = padding;
		this.id = id;
	}

	public PVector rv(boolean normalized) {
		PVector rv = new PVector(vectorStart.x - vectorCenter.x, vectorStart.y - vectorCenter.y);

		if (normalized) {
			rv.normalize();
		}

		return rv;
	}

	public PVector ov(boolean normalized) {
		PVector ov = new PVector(vectorStart.y - vectorCenter.y, -vectorStart.x - vectorCenter.x);

		if (normalized) {
			ov.normalize();
		}

		return ov;
	}

	public PVector start() {
		return vectorStart;
	}

	public PVector end() {
		return vectorEnde;
	}

	public PVector center() {
		return vectorCenter;
	}

	public float distance() {
		float distance = vectorStart.dist(vectorEnde) / 2;
		return distance;
	}
}
