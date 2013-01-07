package components;

public class Event {
	private int fx;
	private int x;
	private int y;
	private int z;

	public Event(int fx, int x, int y, int z) {
		super();
		this.fx = fx;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getFx() {
		return fx;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public void setFx(int fx) {
		this.fx = fx;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public String toString() {
		String s = fx + "-" + x + "-" + y + "-" + z;
		return s;

	}

}
