package com.github.ggalmazor.ltdownsampling;

import java.util.List;

import static java.util.Comparator.comparing;

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
    T result = center.map(b -> Area.ofTriangle(left.getResult(), b, right.getCenter()))
        .stream()
        .max(comparing(Area::getValue))
        .orElseThrow(() -> new RuntimeException("Can't obtain max area triangle"))
        .getGenerator();
    center.setResult(result);
    return result;
  }
}
