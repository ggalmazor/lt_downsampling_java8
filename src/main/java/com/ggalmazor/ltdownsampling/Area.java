package com.ggalmazor.ltdownsampling;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

class Area<T extends Point> {
  private final T generator;
  private final double value;

  private Area(T generator, double value) {
    this.generator = generator;
    this.value = value;
  }

  static <U extends Point> Area<U> ofTriangle(Point a, U b, Point c) {
    // area of a triangle = |[Ax(By - Cy) + Bx(Cy - Ay) + Cx(Ay - By)] / 2|
    List<Double> addends = Arrays.asList(
      a.getX() * (b.getY() - c.getY()),
      b.getX() * (c.getY() - a.getY()),
      c.getX() * (a.getY() - b.getY())
    );
    double sum = addends.stream().reduce(0d, Double::sum);
    double value = abs(sum / 2);
    return new Area<>(b, value);
  }

  T getGenerator() {
    return generator;
  }

  public double getValue() {
    return value;
  }
}
