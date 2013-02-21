package project.model;

import java.util.Random;

/**
 * Static class for model parameters.
 */
public class MP {
  private MP() {}
  /** the delay used to space out car generation  */
  public static double sourceGenerationDelay = 50;
  /** Length of cars, in meters */
  public static double carLength = 10;
  /** Length of roads, in meters */
  public static double roadLength = 200;
  public static double minMaxVelocity = 10;
  public static double maxMaxVelocity = 30;
  /** Maximum car velocity, in meters/second */
  public static double maxVelocity = 5;
  //public static double maxVelocity = Random.(maxMaxVelocity - minMaxVelocity + 1) + minMaxVelocity;
}  

