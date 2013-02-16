package agent.one;
import agent.Agent;
import agent.TimeServer;
import agent.TimeServerLinked;

public class Main {
  public static void main (String[] args) {
    TimeServer time = new TimeServerLinked();
    Agent a = new Tiger(time);
    time.enqueue(0,a);
    time.run(100);
  }
}

class Tiger implements Agent {
  private TimeServer _time;
  public Tiger(TimeServer time) { _time = time; }
  public void run() {
    System.out.println("It's " + _time.currentTime() + " and I'm alive!");
    _time.enqueue(10+_time.currentTime(), this);
  }
}  
