package project.model;

import java.util.ArrayList;
import java.util.List;

public abstract class DriveableSurface {
	/**
	 * a List<Record> object used to hold the cars on this surface
	 */
	  private List<Car> _cars = new ArrayList<Car>();
	  /**
	   * The next Driveable object. Basically its a reference to the object which 
	   * follows this segment
	   */
	  private DriveableSurface nextSeg;
	  public void setNextSeg(DriveableSurface next) {
		  this.nextSeg = next;
	  }
	  public DriveableSurface getNextSeg() { return nextSeg; }
	  
	  private DriveableSurfaceOrientation _orientation;
	  public void setOrientation(DriveableSurfaceOrientation o) {
		  _orientation = o;
	  }
	  public DriveableSurfaceOrientation getOrientation() { return _orientation; }
	  
	  public void accept(Car d) {
	    if (d == null) { throw new IllegalArgumentException(); }
	    if (!isDriveable(d)) return;
	    _cars.add(d);
	  }
	  public void remove(Car d) {
		    if (d == null) { throw new IllegalArgumentException(); }
		    _cars.remove(d);
		  }
	  public List<Car> getCars() {
	    return _cars;
	  }
	  
	  public abstract boolean isDriveable(Car c);
	  
	  public abstract double requestMove(Car c, double requestedMove);
}
