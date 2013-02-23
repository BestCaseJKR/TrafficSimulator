package project.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Intersection implements Road {
	
	private Road _nsRoad;
	public Road getNSRoad() {
		return _nsRoad;
	}
	public void setNSRoad(Road r) {
		_nsRoad = r;
	}
	
	private Road _ewRoad;
	
	public Road getEWRoad() {
		return _ewRoad;
	}
	public void setEWRoad(Road r) {
		_ewRoad = r;
	}
	
	private double _length = MP.intersectionLength.getDoubleInRange();
	
	private Light _light;
	public void setLight(Light l) {
		_light = l;
	}
	public Light getLight() {
		return _light;
	}

	private Queue<Car> _cars = new LinkedList<Car>();
	
	@Override
	public boolean isDriveable(Car c) {
		return c.getOrientation().equals(_light.getState().getAllowedOrientation());
	}
	@Override
	public boolean accept(Car d) {
		// TODO Check light and decide to accept
	    if (d == null) { throw new IllegalArgumentException(); }
	    if (!isDriveable(d))  {
	    	return false;
	    }
	    _cars.add(d);
	    return true;
	}
	@Override
	public void remove(Car d) {
		_cars.remove(d);
	}
	
  public void setNextSeg(Road next) {
	  
  }
  public Road getNextSeg(Car c) { 
	  if (c.getOrientation() == RoadOrientation.East_West) {
		  return this.getEWRoad();
	  } else {
		  return this.getNSRoad();
	  }
  }
	@Override
	public void setOrientation(RoadOrientation o) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public RoadOrientation getOrientation() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Queue<Car> getCars() {
		return _cars;
	}
	@Override
	public double getLength() {
		return _length;
	}

	public String toString() {
		
	}
	
}
