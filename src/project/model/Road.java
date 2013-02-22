package project.model;

import java.util.Queue;

public interface Road {

	public boolean isDriveable(Car c);
	
	public boolean accept(Car d);
	
	public void remove(Car d);
		
	public void setNextSeg(Road next);
	
	public Road getNextSeg(Car c);
	
	public void setOrientation(RoadOrientation o);
	
	public RoadOrientation getOrientation();
	
	public Queue<Car> getCars();
	
	public double getLength();
	
}
