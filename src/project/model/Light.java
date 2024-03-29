package project.model;

/**
 * A light has a boolean state.
 */
public class Light implements Agent {
  Light() { } // Created only by this package
  
  private LightState _state = LightState.GreenNS_RedEW;

  public LightState getState() {
    return _state;
  }
  public void run(double time) {
    if (time%_state.getDuration()==0) {
    	_state = _state.getNext();
      
    }
  }
}

