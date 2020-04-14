
public class Pairs {
	private int cityA;
	private int cityB;
	private int cityDistance;
	
	
	public Pairs() {
		this(0, 0, 0);
	}
	
	public Pairs(int cityA, int cityB, int cityDistance) {
		this.cityA = cityA;
		this.cityB = cityB;
		this.cityDistance = cityDistance;
	}
	
	
	public int getCityDistance() {
		return this.cityDistance;
	}
	
	public void setCityDistance(int cityDistance) {
		this.cityDistance = cityDistance;
	}
	
	public int getCityA() {
		return this.cityA;
	}
	
	public int getCityB() {
		return this.cityB;
	}
	
	public String toString() {
		return cityA +" " +cityB +" " +cityDistance;
	}
}
