package project.model;

/**
 * A car remembers its position from the beginning of its road.
 * Cars have random velocity and random movement pattern:
 * when reaching the end of a road, the dot either resets its position
 * to the beginning of the road, or reverses its direction.
 */
public class Car implements Agent {
  Car(DriveableSurface r) { setCurrentRoad(r); } // Created only by this package

  //private boolean _backAndForth = Math.round(Math.random())==1 ? true : false;
  private double _position = 0;
  private double _velocity = (int) Math.ceil(Math.random() * MP.maxVelocity);
  private DriveableSurface _road;
  private java.awt.Color _color = new java.awt.Color((int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255));
  
  public void setCurrentRoad(DriveableSurface road) {
	  _position = 0;
	  _road = road;
  }
  
  public double getPosition() {
    return _position;
  }
  public java.awt.Color getColor() {
    return _color;
  }
  public void run(double time) {
//    if (_backAndForth) {
//      if (((_position + _velocity) < 0) || ((_position + _velocity) > (MP.roadLength-MP.carLength)))
//        _velocity *= -1;
//    } else {
      if ((_position + _velocity) > (MP.roadLength-MP.carLength)) {
    	  sendCarToNextSeg();
    	  return;
      }
    	  //_position = 0;
 //   }
    _position += _road.requestMove(this, _velocity);
    //_position += _velocity;
  }
  
  public void sendCarToNextSeg() {
	  if (_road.getNextSeg() != null) {
		  _road.getNextSeg().accept(this);
		  _road.remove(this);
		  this.setCurrentRoad(_road.getNextSeg());
	  }   
  }
  
}
