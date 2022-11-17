package com.ggalmazor.ltdownsampling;

import static java.util.Comparator.comparing;

import java.util.List;

class Triangle<T extends Point> {
  private final Bucket<T> left, center, right;

  private Triangle(Bucket<T> left, Bucket<T> center, Bucket<T> right) {
    this.left = left;
    this.center = center;
    this.right = right;
  }

  static <U extends Point> Triangle<U> of(List<Bucket<U>> buckets) {
    return new Triangle<>(
      buckets.get(0),
      buckets.get(1),
      buckets.get(2)
    );
  }

  T getFirst() {
    return left.getFirst();
  }

  T getLast() {
    return right.getLast();
  }

  T getResult() {
    return center.map(b -> Area.ofTriangle(left.getResult(), b, right.getCenter()))
      .stream()
      .max(comparing(Area::getValue))
      .orElseThrow(() -> new RuntimeException("Can't obtain max area triangle"))
      .getGenerator();
  }
}
