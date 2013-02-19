package project.model;

import java.util.List;
import java.util.ArrayList;

/**
 * A road holds cars.
 */
public class RoadSegment extends DriveableSurface {
	RoadSegment() { } // Created only by this package

	public double requestMove(Car c, double requestedMove) {
		
		List<Car> cars = this.getCars();
		double pos = -1;
		//System.out.println("Car: " + c.getPosition() + " RM: " + requestedMove);
		for	(Car checkCar: cars) {
			//System.out.println("Check Car: " + checkCar.getPosition());
			if ((checkCar.getPosition() - MP.carLength) <= (c.getPosition() + requestedMove) && 
					(checkCar.getPosition() - MP.carLength) >= c.getPosition()	
			) {
				double tmpPos = (checkCar.getPosition() - MP.carLength) - c.getPosition();
				pos = (pos > tmpPos) ? tmpPos : pos;
			}
		}
		if (pos != -1) {
			//System.out.println("RM: " + requestedMove + " Return: " + pos);
			System.exit(-1);
			return pos;
		}
		//System.out.println("Returning RM: " + requestedMove);
		return requestedMove;
	}

	@Override
	public boolean isDriveable(Car c) {
		// TODO Auto-generated method stub
		return true;
	}
}
