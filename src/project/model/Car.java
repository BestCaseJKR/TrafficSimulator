package project.model;

import java.util.Queue;

/**
 * A car remembers its position from the beginning of its road.
 * Cars have random velocity and random movement pattern:
 * when reaching the end of a road, the dot either resets its position
 * to the beginning of the road, or reverses its direction.
 */
public class Car implements Agent {
  Car(Road r) { 
	  setCurrentRoad(r, 0);
	  _direction = r.getOrientation();
	  System.out.println("Car Created: " + this);
  } // Created only by this package

  //private boolean _backAndForth = Math.round(Math.random())==1 ? true : false;
  private RoadOrientation _direction;
  private double _position = 0;
  private double _velocity = MP.maxVelocity.getDoubleInRange();
  private double _length = MP.carLength.getDoubleInRange();
  private Road _road;
  private java.awt.Color _color = new java.awt.Color((int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255));
  
  public void setCurrentRoad(Road road, double requestedPostion) {
	  
	  _road = road;
	  _position = 0;
	  _position = requestMove(requestedPostion);
  }
  public Road getCurentRoad() { return _road; }
  public double getPosition() {
    return _position;
  }
  public double getLength() {
	  return _length;
  }
  public java.awt.Color getColor() {
    return _color;
  }
  public void run(double time) {
	  double free = requestMove(_velocity);
      if ((_position + free) >= (MP.roadLength-this.getLength()) ) {

    	  if(!this.equals(_road.getCars().peek())) {
    		  System.out.println("Expected: " + _road.getCars().peek());
    		  System.out.println("Got: " + this);
    		  if (_road.getCars().contains(this)) {
    			  System.out.println("Present in list");
    		  }
    		  try {
    			  int i = 0;
				while(i < 1000) {
					Thread.sleep(8000);
					i++;
				}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		  System.exit(1);
    	  }
    	  
    	  if (sendCarToNextSeg((_position + _velocity) - this.getCurentRoad().getLength() )) {
    		  return;
    	  } 
      }
    _position += free;

  }
  
  public boolean sendCarToNextSeg(double requestedPostion) {
	  if (_road.getNextSeg(this) != null) {
		  if (!isSpaceForCar(_road.getNextSeg(this)) || !_road.getNextSeg(this).accept(this)) {
			  return false;
		  }
		  _road.remove(this);
		  this.setCurrentRoad(_road.getNextSeg(this), requestedPostion);
		  System.out.println("Car: " + this);
		  System.out.println("Entered Road: " + this.getCurentRoad());
		  System.out.println("CR Cars: " + this.getCurentRoad().getCars());
		  
		  return true;
	  }   
	  return false;
  }
  
  public RoadOrientation getOrientation() {
	  return this._direction;
  }
  
  public String toString() {
	  return "Car(" + this.getColor() + ") in " + this.getCurentRoad().getClass() + " hs: " + this.hashCode() + " V = " + _velocity + " P = " + this.getPosition() + " L = " + this.getLength();
  }
  
	public double requestMove(double requestedMove) {
		if ((this.getPosition() + requestedMove) > (MP.roadLength-this.getLength())) {
			requestedMove = (MP.roadLength-this.getLength()) - this.getPosition();
			
		}
		double opening = calculateOpenRoad(requestedMove);
		if ((this.getPosition() + requestedMove) > (MP.roadLength-this.getLength())) {
			return ((this.getPosition() - (MP.roadLength-this.getLength())) > opening) ? opening : (this.getPosition() - (MP.roadLength-this.getLength())) ;
		}
		return opening;
	}
	private double calculateOpenRoad(double requestedMove) {
		Queue<Car> cars = this.getCurentRoad().getCars();
		double pos = -1;
		if (cars == null) {
			return pos;
		}
		for	(Car checkCar: cars) {

			if (!this.equals(checkCar) &&
					(checkCar.getPosition() - checkCar.getLength()) <= (getPosition() + requestedMove) && 
					(checkCar.getPosition() - checkCar.getLength()) >= getPosition() - this.getLength()	
			) {
				double tmpPos = (checkCar.getPosition() - checkCar.getLength()) - getPosition();
				if(tmpPos >= 0) {
					if (pos == -1) {
						pos = tmpPos;
					}
				}
				pos = (tmpPos < pos) ? tmpPos : pos;
			}
		}
		if (pos != -1) {
			//System.out.println("RM: " + requestedMove + " Return: " + pos);
			return pos;
		}
		return requestedMove;
	}
  
	public boolean isSpaceForCar(Road r) {
		
		Queue<Car> cars = r.getCars();
		if (cars == null) {
			return true;
		}
		for (Car c: cars) {
			if ((c.getPosition() - c.getLength()) <= this.getLength()) {
				return false;
			}
		}
		return true;
	}
	
}
