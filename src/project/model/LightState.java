package project.model;

public enum LightState {
	GreenNS_RedEW(100, RoadOrientation.North_South),
	YellowNS_RedEW(10, RoadOrientation.North_South),
	RedNS_GreenEW(100, RoadOrientation.East_West),
	RedNS_YellowEW(10, RoadOrientation.East_West);
	
	private LightState(int dur, RoadOrientation or) {
		duration = dur;
		allowedOrientation = or;
	}
	
	private int duration;
	
	public int getDuration() {
		return duration;
	}
	
	private RoadOrientation allowedOrientation;
	
	public RoadOrientation getAllowedOrientation() {
		return this.allowedOrientation;
	}
	
	public LightState getNext() {
		return values()[(ordinal()+1) % values().length];
	}
}
