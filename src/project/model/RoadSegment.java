package project.model;

import java.util.List;
import java.util.ArrayList;

/**
 * A road holds cars.
 */
public class RoadSegment implements Road {
	RoadSegment() { } // Created only by this package
	/**
	 * a List<Record> object used to hold the cars on this surface
	 */
	  private List<Car> _cars = new ArrayList<Car>();
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
	  public List<Car> getCars() {
	    return _cars;
	  }
	  
	public double requestMove(Car c, double requestedMove) {
		if ((c.getPosition() + requestedMove) > (MP.roadLength-MP.carLength)) {
			requestedMove = (MP.roadLength-MP.carLength) - c.getPosition();
			System.out.println("BEYOND LENGTH OF ROAD1111(" + c.hashCode() + ") " + c.getPosition() + " " + requestedMove);
			System.out.println(c.toString());
			
		}
		double opening = calculateOpenRoad(c, requestedMove);
		//System.out.println("Returning RM: " + requestedMove + " pos(" + c.getPosition() + ")");
		if ((c.getPosition() + requestedMove) > (MP.roadLength-MP.carLength)) {
			System.out.println("BEYOND LENGTH OF ROAD(" + MP.roadLength + ") " + c.getPosition() + " " + requestedMove + " " + opening);
			System.out.println(c.toString());
			System.out.println("Return " + (((c.getPosition() - MP.roadLength) > opening) ? opening : (c.getPosition() - MP.roadLength)));
			return ((c.getPosition() - (MP.roadLength-MP.carLength)) > opening) ? opening : (c.getPosition() - (MP.roadLength-MP.carLength)) ;
		}
		System.out.println("Opening = " + opening);
		return opening;
	}
	private double calculateOpenRoad(Car c, double requestedMove) {
		List<Car> cars = this.getCars();
		double pos = -1;
		System.out.println("Car(" + c.hashCode() + "): " + c.getPosition() + " RM: " + requestedMove);
		
		for	(Car checkCar: cars) {
			System.out.println("Check Car" + checkCar.hashCode() + "): " + checkCar.getPosition());
			if (!c.equals(checkCar) &&
					(checkCar.getPosition() - MP.carLength) <= (c.getPosition() + requestedMove) && 
					(checkCar.getPosition() - MP.carLength) >= c.getPosition() - MP.carLength	
			) {
				double tmpPos = (checkCar.getPosition() - MP.carLength) - c.getPosition();
				System.out.println("pos tmppos " + pos + " " + tmpPos);
				if(tmpPos >= 0) {
					if (pos == -1) {
						pos = tmpPos;
					}
				}
				pos = (tmpPos < pos) ? tmpPos : pos;
				System.out.println("pos = " + pos);
			}
		}
		if (pos != -1) {
			//System.out.println("RM: " + requestedMove + " Return: " + pos);
			return pos;
		}
		return requestedMove;
	}

	public boolean isDriveable(Car c) {
		return true;
	}
	
}
