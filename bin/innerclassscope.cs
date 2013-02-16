using System;
class B {
  protected object x;
  protected class I {
    protected B parent;
    public I(B parent) { this.parent = parent; }
    public void foo()  { Console.WriteLine(parent.x); }
  }
}
class D : B {
  private class DI : B.I {
    public DI(D parent):base(parent) { }
    public void bar() { Console.WriteLine(((D)parent).x); }
  }
}
class Program {
  public static void Main() { }
}
