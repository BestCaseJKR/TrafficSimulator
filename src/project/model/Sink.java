package project.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Sink implements Agent, Road {
	private Queue<Car> _cars = new LinkedList<Car>();
	@Override
	public void run(double time) {
		for(Car c: _cars) {
			_cars.remove(c);
			c = null;
		}

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

	@Override
	public Queue<Car> getCars() {
		return _cars;
	}

	@Override
	public double getLength() {
		return 0;
	}

}
