package project.model;

import java.util.List;

public class Sink extends DriveableSurface implements Agent {

	@Override
	public void run(double time) {
		List<Car> list = this.getCars();
		for(Car c: list) {
			list.remove(c);
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

}
