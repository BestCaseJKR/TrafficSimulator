package project.model;

public interface Road {

	public boolean isDriveable(Car c);
	
	public boolean accept(Car d);
	
	public void remove(Car d);
	
	public double requestMove(Car c, double requestedMove);
		
	public void setNextSeg(Road next);
	
	public Road getNextSeg(Car c);
	
	public void setOrientation(RoadOrientation o);
	
	public RoadOrientation getOrientation();
}
