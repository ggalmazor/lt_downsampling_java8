package com.github.ggalmazor.ltdownsampling;


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

public class Point {
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
    return new BigDecimal(number.doubleValue(), MathContext.UNLIMITED);
  }

  public BigDecimal getX() {
    return x;
  }

  public BigDecimal getY() {
    return y;
  }

  public Point add(Point other) {
    BigDecimal newX = x.add(other.x);
    BigDecimal newY = y.add(other.y);
    return new Point(
        newX,
        newY
    );
  }

  public Point subtract(Point other) {
    BigDecimal newX = x.subtract(other.x);
    BigDecimal newY = y.subtract(other.y);
    return new Point(
        newX,
        newY
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

  public Point divide(Number divisor) {
    return new Point(
        x.divide(toBigDecimal(divisor), MathContext.UNLIMITED),
        y.divide(toBigDecimal(divisor), MathContext.UNLIMITED)
    );
  }
}
