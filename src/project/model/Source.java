package project.model;

import java.util.List;

public class Source implements Agent {
	private RoadSegment _road;
	private List<Agent> _agents;
	public Source(RoadSegment r, List<Agent> agents) {
		if (r == null) {
			throw new IllegalArgumentException("Invalid Road Supplied"); 
		}
		_road = r;
		_agents = agents;
	}
	public void run(double time) {
		if(time%MP.sourceGenerationDelay == 0) {
		      Car car = new Car(_road);
		      _agents.add(car);
		      _road.accept(car);
		}

	}

}
