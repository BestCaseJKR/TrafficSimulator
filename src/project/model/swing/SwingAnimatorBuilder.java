package project.model.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import project.model.AnimatorBuilder;
import project.model.Car;
import project.model.Light;
import project.model.LightState;
import project.model.MP;
import project.model.RoadSegment;
import project.util.Animator;
import project.util.SwingAnimator;
import project.util.SwingAnimatorPainter;

/** 
 * A class for building Animators.
 */
public class SwingAnimatorBuilder implements AnimatorBuilder {
  MyPainter _painter;
  public SwingAnimatorBuilder() {
    _painter = new MyPainter();
  }
  public Animator getAnimator() {
    if (_painter == null) { throw new IllegalStateException(); }
    Animator returnValue = new SwingAnimator(_painter, "Traffic Simulator",
                                             VP.displayWidth, VP.displayHeight, VP.displayDelay);
    _painter = null;
    return returnValue;
  }
  private static double skipInit = VP.gap;
  private static double skipRoad = VP.gap + MP.roadLength;
  private static double skipCar = VP.gap + VP.elementWidth;
  private static double skipRoadCar = skipRoad + skipCar;
  public void addLight(Light d, int i, int j) {
    double x = skipInit + skipRoad + j*skipRoadCar;
    double y = skipInit + skipRoad + i*skipRoadCar;
    Translator t = new TranslatorWE(x, y, MP.baseCarLength, VP.elementWidth, VP.scaleFactor);
    _painter.addLight(d,t);
  }
  public void addHorizontalRoad(RoadSegment l, int i, int j, boolean eastToWest) {
    double x = skipInit + j*skipRoadCar;
    double y = skipInit + skipRoad + i*skipRoadCar;
    Translator t = eastToWest ? new TranslatorEW(x, y, MP.roadLength, VP.elementWidth, VP.scaleFactor)
                              : new TranslatorWE(x, y, MP.roadLength, VP.elementWidth, VP.scaleFactor);
    _painter.addRoad(l,t);
  }
  public void addVerticalRoad(RoadSegment l, int i, int j, boolean southToNorth) {
    double x = skipInit + skipRoad + j*skipRoadCar;
    double y = skipInit + i*skipRoadCar;
    Translator t = southToNorth ? new TranslatorSN(x, y, MP.roadLength, VP.elementWidth, VP.scaleFactor)
                                : new TranslatorNS(x, y, MP.roadLength, VP.elementWidth, VP.scaleFactor);
    _painter.addRoad(l,t);
  }


  /** Class for drawing the Model. */
  private static class MyPainter implements SwingAnimatorPainter {

    /** Pair of a model element <code>x</code> and a translator <code>t</code>. */
    private static class Element<T> {
      T x;
      Translator t;
      Element(T x, Translator t) {
        this.x = x;
        this.t = t;
      }
    }
    
    private List<Element<RoadSegment>> _roadElements;
    private List<Element<Light>> _lightElements;
    MyPainter() {
      _roadElements = new ArrayList<Element<RoadSegment>>();
      _lightElements = new ArrayList<Element<Light>>();
    }    
    void addLight(Light x, Translator t) {
      _lightElements.add(new Element<Light>(x,t));
    }
    void addRoad(RoadSegment x, Translator t) {
      _roadElements.add(new Element<RoadSegment>(x,t));
    }
    
    public void paint(Graphics g) {
      // This method is called by the swing thread, so may be called
      // at any time during execution...

      // First draw the background elements
      for (Element<Light> e : _lightElements) {
        if (e.x.getState() == LightState.GreenNS_RedEW) {
          g.setColor(Color.GREEN);
        } else if(e.x.getState() == LightState.YellowNS_RedEW) {
          g.setColor(Color.YELLOW);
        } else {
          g.setColor(Color.RED);
        }
        XGraphics.fillOval(g, e.t, 0, 0, MP.baseCarLength, VP.elementWidth);
      }
      g.setColor(Color.BLACK);
      for (Element<RoadSegment> e : _roadElements) {
        XGraphics.fillRect(g, e.t, 0, 0, MP.roadLength, VP.elementWidth);
      }
      
      // Then draw the foreground elements
      for (Element<RoadSegment> e : _roadElements) {
        // iterate through a copy because e.x.getCars() may change during iteration...
        for (Car d : e.x.getCars().toArray(new Car[0])) {
          g.setColor(d.getColor());
          XGraphics.fillOval(g, e.t, d.getPosition(), 0, MP.baseCarLength, VP.elementWidth);
        }
      }
    }
  }
}

