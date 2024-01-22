package com.ggalmazor.ltdownsampling;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

public interface Point {
  double getX();
  double getY();

  static DoublePoint add(Point a, Point b) {
    return new DoublePoint(
      a.getX() + b.getX(),
       a.getY() + b.getY()
    );
  }
  static DoublePoint subtract(Point a, Point b) {
    return new DoublePoint(
      a.getX() - b.getX(),
      a.getY() - b.getY()
    );
  }

  static DoublePoint half(Point p) {
    return new DoublePoint(
      p.getX() / 2,
      p.getY() / 2
    );
  }
}
