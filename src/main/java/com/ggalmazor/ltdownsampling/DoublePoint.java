package com.ggalmazor.ltdownsampling;

public class DoublePoint implements Point {
  private final double x;
  private final double y;

  public DoublePoint(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public static DoublePoint of(Number x, Number y) {
    return new DoublePoint(x.doubleValue(), y.doubleValue());
  }

  @Override
  public double getX() {
    return x;
  }

  @Override
  public double getY() {
    return y;
  }

  @Override
  public String toString() {
    return "DoublePoint{" +
      "x=" + x +
      ", y=" + y +
      '}';
  }
}
