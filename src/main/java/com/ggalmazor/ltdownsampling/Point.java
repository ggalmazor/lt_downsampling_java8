package com.ggalmazor.ltdownsampling;

public interface Point {
  double getX();

  double getY();

  static DoublePoint centerBetween(Point a, Point b) {
    DoublePoint vector = new DoublePoint(b.getX() - a.getX(), b.getY() - a.getY());
    DoublePoint halfVector = new DoublePoint(((Point) vector).getX() / 2, ((Point) vector).getY() / 2);
    return new DoublePoint(a.getX() + halfVector.getX(), a.getY() + halfVector.getY());
  }
}
