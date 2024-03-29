package project.model.text;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import project.model.AnimatorBuilder;
import project.model.Car;
import project.model.Light;
import project.model.LightState;
import project.model.MP;
import project.model.RoadSegment;
import project.util.Animator;

/** 
 * A class for building Animators.
 */
public class TextAnimatorBuilder implements AnimatorBuilder {
  TextAnimator _animator;
  public TextAnimatorBuilder() {
    _animator = new TextAnimator();
  }
  public Animator getAnimator() {
    if (_animator == null) { throw new IllegalStateException(); }
    Animator returnValue = _animator;
    _animator = null;
    return returnValue;
  }
  public void addLight(Light d, int i, int j) {
    _animator.addLight(d,i,j);
  }
  public void addHorizontalRoad(RoadSegment l, int i, int j, boolean eastToWest) {
    _animator.addRoad(l,i,j);
  }
  public void addVerticalRoad(RoadSegment l, int i, int j, boolean southToNorth) {
    _animator.addRoad(l,i,j);
  }


  /** Class for drawing the Model. */
  private static class TextAnimator implements Animator {

    /** Triple of a model element <code>x</code> and coordinates. */
    private static class Element<T> {
      T x;
      int i;
      int j;
      Element(T x, int i, int j) {
        this.x = x;
        this.i = i;
        this.j = j;
      }
    }
    
    private List<Element<RoadSegment>> _roadElements;
    private List<Element<Light>> _lightElements;
    TextAnimator() {
      _roadElements = new ArrayList<Element<RoadSegment>>();
      _lightElements = new ArrayList<Element<Light>>();
    }    
    void addLight(Light x, int i, int j) {
      _lightElements.add(new Element<Light>(x,i,j));
    }
    void addRoad(RoadSegment x, int i, int j) {
      _roadElements.add(new Element<RoadSegment>(x,i,j));
    }
    
    public void dispose() { }
    
    public void update(Observable o, Object arg) {
      for (Element<Light> e : _lightElements) {
        System.out.print("Light at (" + e.i + "," + e.j + "): ");
        if (e.x.getState() == LightState.GreenNS_RedEW) {
        	System.out.println("Green");
          } else if(e.x.getState() == LightState.YellowNS_RedEW) {
        	  System.out.println("Yellow");
          } else {
        	  System.out.println("Red");
          }
      }
      for (Element<RoadSegment> e : _roadElements) {
        for (Car d : e.x.getCars()) {
          System.out.print("Road at (" + e.i + "," + e.j + "): ");
          System.out.println("Car at " + d.getPosition());
        }
      }
    }
  }
}
