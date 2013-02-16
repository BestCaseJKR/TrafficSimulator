// from http://en.csharp-online.net/ECMA-334:_26.4_Implementation_example
// See also http://msdn.microsoft.com/msdnmag/issues/06/00/C20/default.aspx

using System;
using System.Collections;
using System.Collections.Generic;

class Tree<T> : IEnumerable<T>
{
  private T value;
  private Tree<T> left;
  private Tree<T> right;
  public Tree(T value, Tree<T> left, Tree<T> right) {
    this.value = value;
    this.left = left;
    this.right = right;
  }
  // Returns the elements postorder
  IEnumerator<T> IEnumerable<T>.GetEnumerator() {
    if (left != null) foreach (T x in left) yield return x;
    if (right != null) foreach (T x in right) yield return x;
    yield return value;
  }
  IEnumerator IEnumerable.GetEnumerator() {
    return  ((IEnumerable<T>)this).GetEnumerator();
  }
}

class Program
{
  private static Tree<T> MakeTree<T>(params T[] items) {
    return MakeTree(items, 0, items.Length - 1);
  }
  private static Tree<T> MakeTree<T>(T[] items, int left, int right) {
    if (left > right) return null;
    int i = (left + right) / 2;
    return new Tree<T>(items[i],
                       MakeTree(items, left, i - 1),
                       MakeTree(items, i + 1, right));
  }


  public static void Main() {
    {
      Tree<int> t = MakeTree(1, 2, 3, 4, 5, 6, 7, 8, 9);
      foreach (int i in t) Console.Write("{0} ", i);
      Console.WriteLine();
    }
    {
      Tree<string> t = MakeTree("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
      foreach (string s in t) Console.Write("{0} ", s);
      Console.WriteLine();
    }
  }
}
