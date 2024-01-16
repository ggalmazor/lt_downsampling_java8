package com.ggalmazor.ltdownsampling;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

public class Point {
  private static final MathContext MC = MathContext.UNLIMITED;
  private final double x;
  private final double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public static Point of(Number x, Number y) {
    return new Point(x.doubleValue(), y.doubleValue());
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  protected Point add(Point other) {
    return new Point(
      x + other.x,
      y + other.y
    );
  }

  protected Point subtract(Point other) {
    return new Point(
      x - other.x,
      y - other.y
    );
  }

  protected Point half() {
    return new Point(
      x / 2,
      y / 2
    );
  }

  @Override
  public String toString() {
    return '(' + Double.toString(x) + ',' + Double.toString(y) + ')';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Point point = (Point) o;
    return Objects.equals(x, point.x) &&
      Objects.equals(y, point.y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
