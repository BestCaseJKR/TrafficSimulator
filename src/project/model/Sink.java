package project.model;

import java.util.ArrayList;
import java.util.List;

public class Sink implements Agent, Road {
	private List<Car> _cars = new ArrayList<Car>();
	@Override
	public void run(double time) {
		for(Car c: _cars) {
			_cars.remove(c);
			c = null;
		}

	}

	@Override
	public double requestMove(Car c, double requestedMove) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDriveable(Car c) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setNextSeg(Road next) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean accept(Car d) {
		if(d == null) throw new IllegalArgumentException();
		
		_cars.add(d);
		
		return true;
		
		
	}

	@Override
	public void remove(Car d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Road getNextSeg(Car c) {
		// TODO Auto-generated method stub
		return null;
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

}
