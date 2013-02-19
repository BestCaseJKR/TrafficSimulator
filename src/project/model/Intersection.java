package project.model;

public class Intersection extends DriveableSurface {
	
	private DriveableSurface _nsRoad;
	
	private DriveableSurface _ewRoad;
	
	private Light _light;
	public void setLight(Light l) {
		_light = l;
	}
	public Light getLight() {
		return _light;
	}

	@Override
	public double requestMove(Car c, double requestedMove) {
		return requestedMove;
	}
	@Override
	public boolean isDriveable(Car c) {
		return true;
	}

}
