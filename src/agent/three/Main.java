package agent.three;
import agent.Agent;
import agent.TimeServer;
import agent.TimeServerLinked;
import java.util.Observer;

public class Main {
  public static void main (String[] args) {
    World w = World.instance;
    Agent a = new Tiger();
    w.enqueue(0,a);
    w.set(0,0,a);
    w.run(100);
  }
}

class Util {
  private Util() {}
  private final static long SEED = 2497;
  private final static java.util.Random _r = new java.util.Random(SEED);
  static int random(int n) { return _r.nextInt(n); }
  static boolean randomBoolean() { return _r.nextBoolean(); }
}  

interface World extends TimeServer {
  public static final World instance = new WorldObj();
  public int maxx();
  public int maxy();
  public Object get(int i, int j);
  public void set(int i, int j, Object a);
}

class WorldObj implements World {
  private final static int MAXX = 100;
  private final static int MAXY = 100;
  private TimeServer _time = new TimeServerLinked();
  private Object[][] _space = new Object[MAXX][MAXY];

  public int maxx()                      { return MAXX; }
  public int maxy()                      { return MAXY; }     
  public Object get(int x, int y)        { return _space[x%MAXX][y%MAXY]; }
  public void set(int x, int y, Object a){ _space[x%MAXX][y%MAXY] = a; }
  public double currentTime()            { return _time.currentTime(); }
  public void enqueue(double t, Agent a) { _time.enqueue(t,a); }
  public void run(double d)              { _time.run(d); }
  public void addObserver(Observer o)    { _time.addObserver(o); }
  public void deleteObserver(Observer o) { _time.deleteObserver(o); }
}
  
/* BUGS HERE */
class Tiger implements Agent {
  private int _x;
  private int _y;
  private World _w = World.instance;
  private void moveRandom() {
    _w.set(_x,_y,null);
    if (Util.randomBoolean()) { _x = (_x+1)%_w.maxx(); }
    if (Util.randomBoolean()) { _y = (_y+1)%_w.maxy(); }
    _w.set(_x,_y,this);
  }
  public void run() {
    moveRandom();
    System.out.println("It's " + _w.currentTime() + " and I'm alive at (" + _x + "," + _y + ")!");
    _w.enqueue(10+_w.currentTime(), this);
  }
  
}  
