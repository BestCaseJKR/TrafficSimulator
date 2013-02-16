package agent.four;

public class Main {
  public static void main (String[] args) {
    World w = WorldF.instance(100,100);
    int i = 0;
    while (i<20) {
      try {
        Tiger t = new Tiger
          (Integer.toString(i), Util.random(w.maxx()), Util.random(w.maxy()));
        i++;
      } catch (SpaceOccupiedException e) {
      }
    }
    w.run(1000);
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
  public int maxx();
  public int maxy();
  public Visible get(int i, int j);
  public boolean set(int i, int j, Visible a);
}

class WorldF {
  private static World _W;
  public static World instance(int maxx, int maxy) {
    if (_W != null)
      throw new IllegalStateException();
    _W = new WorldObj(maxx, maxy);
    return _W;
  }
  public static World instance() {
    if (_W == null)
      throw new IllegalStateException();
    return _W;
  }
}

class WorldObj implements World {
  private final int _maxx;
  private final int _maxy;
  private final TimeServer _time;
  private final Visible[][] _space;

  WorldObj(int maxx, int maxy) {
    _maxx = maxx;
    _maxy = maxy;
    _time = new TimeServerLinked();
    _space = new Visible[_maxx][_maxy];
    for (int x = 0; x < _maxx; x++ )
      for (int y = 0; y < _maxy; y++ )
        _space[x][y] = NullVisible.instance;
  }
  public int maxx() { return _maxx; }
  public int maxy() { return _maxy; }     
  public Visible get(int x, int y) {
    return _space[(x+_maxx)%_maxx][(y+_maxy)%_maxy];
  }
  public boolean set(int x, int y, Visible a){
    if (a == null) {
      a = NullVisible.instance;
    } else if (get(x,y) != NullVisible.instance) {
      return false;
    }
    _space[(x+_maxx)%_maxx][(y+_maxy)%_maxy] = a;
    return true;
  }
  public long currentTime()              { return _time.currentTime(); }
  public void enqueue(long t, Agent a)   { _time.enqueue(t,a); }
  public void run(int d)                 { _time.run(d); }
}

interface Visible {
  public final static int NULL = 0;
  public final static int TIGER = 1;
  public int type();
}
  
class NullVisible implements Visible {
  private NullVisible() {}
  public final static Visible instance = new NullVisible();
  public int type() { return Visible.NULL; }
  public String toString() { return "Null"; }
}  

class SpaceOccupiedException extends RuntimeException {
  private static final long serialVersionUID = 2008L;
};

class Tiger implements Agent, Visible {
  private String _name;
  private int _x;
  private int _y;
  private World _w = WorldF.instance();

  public Tiger(String name, int x, int y)
    throws SpaceOccupiedException
  {
    _name = name;
    if (!_w.set(x,y,this))
      throw new SpaceOccupiedException();
    _x = x;
    _y = y;
    _w.enqueue(1+_w.currentTime(), this);
  }
  
  public String toString() { return _name + "@(" + _x + "," + _y + ")"; }

  public int type() { return Visible.TIGER; }
  public void check() {
    checkAjacent();
  }
  public void run() {
    //System.out.print(this + " moves to ");
    moveRandom();
    //System.out.println(this);
    _w.enqueue(10+_w.currentTime(), this);
  }

  private void checkAjacent() {
    for (int i=-1; i<=1; i++) {
      for (int j=-1; j<=1; j++) {
        if (i==0 && j==0)
          continue;
        if (_w.get(_x+i,_y+j).type() == Visible.TIGER)
          System.out.println(this + " roars at " + _w.get(_x+i,_y+j) + " at " + _w.currentTime());
      }
    }
  }
  private void moveRandom() {
    _w.set(_x,_y,null);
    int x, y; 
    do {
      x = (_w.maxx() + _x -1 + Util.random(2)) % _w.maxx();
      y = (_w.maxy() + _y -1 + Util.random(2)) % _w.maxy();
      //System.out.println("Trying (" + x + "," + y + ")");
    } while (!_w.set(x,y,this));
    _x = x; _y = y;
  }
}  
