package com.github.ggalmazor.ltdownsampling;


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

public class Point {
  private static final MathContext MC = MathContext.UNLIMITED;
  private final BigDecimal x;
  private final BigDecimal y;

  public Point(BigDecimal x, BigDecimal y) {
    this.x = x;
    this.y = y;
  }

  public static Point of(Number x, Number y) {
    return new Point(toBigDecimal(x), toBigDecimal(y));
  }

  private static BigDecimal toBigDecimal(Number number) {
    return new BigDecimal(number.doubleValue(), MC);
  }

  protected BigDecimal getX() {
    return x;
  }

  protected BigDecimal getY() {
    return y;
  }

  protected Point add(Point other) {
    return new Point(
        x.add(other.x),
        y.add(other.y)
    );
  }

  protected Point subtract(Point other) {
    return new Point(
        x.subtract(other.x),
        y.subtract(other.y)
    );
  }

  protected Point divide(Number divisor) {
    return new Point(
        x.divide(toBigDecimal(divisor), MC),
        y.divide(toBigDecimal(divisor), MC)
    );
  }

  @Override
  public String toString() {
    return '(' + x.toString() + ',' + y.toString() + ')';
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
