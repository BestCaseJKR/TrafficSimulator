package project.model;

public enum LightState {
	GreenNS_RedEW(100),
	YellowNS_RedEW(10),
	RedNS_GreenEW(100),
	RedNS_YellowEW(10);
	
	private LightState(int dur) {
		duration = dur;
	}
	
	private int duration;
	
	public int getDuration() {
		return duration;
	}
	
	public LightState getNext() {
		return values()[(ordinal()+1) % values().length];
	}
	
}
