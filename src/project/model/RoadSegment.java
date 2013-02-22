package project.model;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

/**
 * A road holds cars.
 */
public class RoadSegment implements Road {
	RoadSegment() { } // Created only by this package
	/**
	 * a List<Record> object used to hold the cars on this surface
	 */
	  private Queue<Car> _cars = new LinkedList<Car>();
	  /**
	   * The next Driveable object. Basically its a reference to the object which 
	   * follows this segment
	   */
	  private Road nextSeg;
	  public void setNextSeg(Road next) {
		  this.nextSeg = next;
	  }
	  public Road getNextSeg(Car c) { return nextSeg; }
	  
	  private RoadOrientation _orientation;
	  public void setOrientation(RoadOrientation o) {
		  _orientation = o;
	  }
	  public RoadOrientation getOrientation() { return _orientation; }
	  
	  private double _length = MP.roadSegmentLength.getDoubleInRange();
	  
	  public boolean accept(Car d) {
	    if (d == null) { throw new IllegalArgumentException(); }
	    if (!isDriveable(d)) return false;
	    _cars.add(d);
	    return true;
	  }
	  
	  public void remove(Car d) {
		    if (d == null) { throw new IllegalArgumentException(); }
		    _cars.remove(d);
		  }
	  public Queue<Car> getCars() {
	    return _cars;
	  }

	public boolean isDriveable(Car c) {

		return true;
	}
	
	public double getLength() {
		return _length;
	}
	
}
