package project.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import project.util.Animator;

/**
 * An example to model for a simple visualization.
 * The model contains roads organized in a matrix.
 * See {@link #Model(AnimatorBuilder, int, int)}.
 */
public class Model extends Observable {
  private List<Agent> _agents;
  private Animator _animator;
  private boolean _disposed;
  private double _time;

  /** Creates a model to be visualized using the <code>builder</code>.
   *  If the builder is null, no visualization is performed.
   *  The number of <code>rows</code> and <code>columns</code>
   *  indicate the number of {@link Light}s, organized as a 2D
   *  matrix.  These are separated and surrounded by horizontal and
   *  vertical {@link RoadSegment}s.  For example, calling the constructor with 1
   *  row and 2 columns generates a model of the form:
   *  <pre>
   *     |  |
   *   --@--@--
   *     |  |
   *  </pre>
   *  where <code>@</code> is a {@link Light}, <code>|</code> is a
   *  vertical {@link RoadSegment} and <code>--</code> is a horizontal {@link RoadSegment}.
   *  Each road has one {@link Car}.
   *
   *  <p>
   *  The {@link AnimatorBuilder} is used to set up an {@link
   *  Animator}.
   *  {@link AnimatorBuilder#getAnimator()} is registered as
   *  an observer of this model.
   *  <p>
   */
  public Model(AnimatorBuilder builder, int rows, int columns) {
    if (rows < 0 || columns < 0 || (rows == 0 && columns == 0)) {
      throw new IllegalArgumentException();
    }
    if (builder == null) {
      builder = new NullAnimatorBuilder();
    }
    _agents = new ArrayList<Agent>();
    setup(builder, rows, columns);
    _animator = builder.getAnimator();
    super.addObserver(_animator);
  }

  /**
   * Run the simulation for <code>duration</code> model seconds.
   */
  public void run(double duration) {
    if (_disposed)
      throw new IllegalStateException();
    for (int i=0; i<duration; i++) {
      _time++;
      // iterate through a copy because _agents may change during iteration...
      for (Agent a : _agents.toArray(new Agent[0])) {
        a.run(_time);
      }
      super.setChanged();
      super.notifyObservers();
    }
  }

  /**
   * Throw away this model.
   */
  public void dispose() {
    _animator.dispose();
    _disposed = true;
  }

  /**
   * Construct the model, establishing correspondences with the visualizer.
   */
  private void setup(AnimatorBuilder builder, int rows, int columns) {
    List<RoadSegment> roads = new ArrayList<RoadSegment>();
    Intersection[][] intersections = new Intersection[rows][columns];
    Boolean reverse;
    Light li;
    // Add Lights
    for (int i=0; i<rows; i++) {
      for (int j=0; j<columns; j++) {
    	li = new Light();
        intersections[i][j] = new Intersection();
        intersections[i][j].setLight(li);
        builder.addLight(li, i, j);
        _agents.add(li);
      }
    }

    // Add Horizontal Roads
    boolean eastToWest = false;
    Road prevSeg = null;
    for (int i=0; i<rows; i++) {
    	prevSeg = null;	
      for (int j=0; j<=columns; j++) {
        RoadSegment l = new RoadSegment();
        //l.setOrientation(((eastToWest)? RoadOrientation.East_West: RoadOrientation.West_East ));
        l.setOrientation(RoadOrientation.East_West);
        builder.addHorizontalRoad(l, i, j, eastToWest);
        roads.add(l);
        if ((j == 0 && eastToWest == false) || (j == columns && eastToWest )) {
        	//if this is a brand new road being created, create a source too
        	Source s = new Source(l, _agents);
        	//also add it to the agents array because its an agent
        	_agents.add(s);
        }
        if ((j == columns && eastToWest == false) || (j == 0 && eastToWest)) {
        	Sink sink = new Sink();
        	l.setNextSeg(sink);
        }
        if (j == 0) {
        	if(eastToWest) {
        		intersections[i][j].setEWRoad(l);
        	} else {
        		l.setNextSeg(intersections[i][j]);
        	}
        } else if (j == columns) {
        	if(eastToWest) {
        		l.setNextSeg(intersections[i][j-1]);
        	} else {
        		intersections[i][j-1].setEWRoad(l);
        	}
        } else {
        	if(eastToWest) {
        		intersections[i][j].setEWRoad(l);
        		l.setNextSeg(intersections[i][j-1]);
        	} else {
        		l.setNextSeg(intersections[i][j]);
        		intersections[i][j-1].setEWRoad(l);
        	}
        }
        
        prevSeg = l;
      
      }
      eastToWest = !eastToWest;
    }

    // Add Vertical Roads
    boolean southToNorth = false;
    prevSeg = null;
    for (int j=0; j<columns; j++) {
    	prevSeg = null;
      for (int i=0; i<=rows; i++) {
        RoadSegment l = new RoadSegment();
        //l.setOrientation(((southToNorth)? RoadOrientation.South_North: RoadOrientation.North_South ));
        l.setOrientation(RoadOrientation.North_South);
        builder.addVerticalRoad(l, i, j, southToNorth);
        roads.add(l);
        if ((i == 0 && southToNorth == false) || (i == rows && southToNorth )) {
        	//if this is a brand new road being created, create a source too
        	Source source = new Source(l, _agents);
        	//also add it to the agents array because its an agent
        	_agents.add(source);
        }
        if ((i == rows && southToNorth == false) || (i == 0 && southToNorth)) {
        	Sink sink = new Sink();
        	l.setNextSeg(sink);
        }
        if (prevSeg != null) { 
        	if(southToNorth) {
        		l.setNextSeg(prevSeg);
        	} else {
        		prevSeg.setNextSeg(l);
        	}
        }
        prevSeg = l;
      }
      southToNorth = !southToNorth;
    }
    

/*    // Add Cars
    for (Road l : roads) {
      Car car = new Car();
      _agents.add(car);
      l.accept(car);
    } */
  }
}
