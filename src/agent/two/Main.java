package agent.two;
import agent.Agent;
import agent.TimeServer;
import agent.TimeServerLinked;

public class Main {
  public static void main (String[] args) {
    World w = World.instance;
    Agent a = new Tiger();
    w.time().enqueue(0,a);
    w.time().run(100);
  }
}

interface World {
  public static final World instance = new WorldObj();
  public TimeServer time();
  public Object[][] space();
}

class WorldObj implements World {
  private TimeServer _time = new TimeServerLinked();
  private Object[][] _space = new Object[100][100];
  public TimeServer time()  { return _time; }
  public Object[][] space() { return _space; }
}
  
class Tiger implements Agent {
  public void run() {
    World w = World.instance;
    System.out.println("It's " + w.time().currentTime() + " and I'm alive!");
    w.time().enqueue(10+w.time().currentTime(), this);
  }
}  
