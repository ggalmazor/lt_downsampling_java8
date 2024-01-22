package com.ggalmazor.ltdownsampling;

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
