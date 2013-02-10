package analyticgeometry;

public interface KinectInstrument {
	public void update(Player player);
	public void draw(Player player);
	public void checkFredMatch(Player player);
	public void checkNeckMatch(Player player);
}