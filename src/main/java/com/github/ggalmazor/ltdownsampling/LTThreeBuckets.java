package com.github.ggalmazor.ltdownsampling;

import javaslang.Lazy;
import javaslang.collection.List;
import javaslang.control.Option;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.function.Function;

public final class LTThreeBuckets {

  public static <T extends Point> List<T> sorted(List<T> input, int bucketAmount) {
    java.util.List<T> results = new ArrayList<>();

    int size = input.size() - 2;
    int bucketSize = size / bucketAmount;
    int rest = size % bucketAmount;

    if (bucketSize == 0) {
      throw new IllegalArgumentException("Can't produce " + bucketAmount + " buckets from an input series of " + (size + 2) + " elements");
    }

    Bucket<T> firstBucket = Bucket.of(input.head());
    List<Bucket<T>> middleBuckets = input.subSequence(1, 1 + size - rest - bucketSize).grouped(bucketSize).map(Bucket::of).toList();
    Bucket<T> beforeLastBucket = Bucket.of(input.subSequence(1 + size - rest - bucketSize, size + 1));
    Bucket<T> lastBucket = Bucket.of(input.last());
    List<Bucket<T>> of = List.of(
        List.of(firstBucket),
        middleBuckets,
        List.of(beforeLastBucket),
        List.of(lastBucket)
    ).flatMap(Function.identity());


    List<List<Bucket<T>>> sliding = of.sliding(3, 1).toList();
    List<Triangle<T>> map = sliding.map(Triangle::of).toList();
    map
        .forEach(triangle -> {
          if (results.size() == 0)
            results.add(triangle.getFirst());
          results.add(triangle.getResult());
          if (results.size() == bucketAmount + 1)
            results.add(triangle.getLast());
        });

    return List.ofAll(results);
  }

  static class Bucket<T extends Point> {
    private final List<T> data;
    private final Lazy<T> first;
    private final Lazy<Point> center;
    private final Lazy<T> last;
    private Option<T> result = Option.none();

    Bucket(List<T> data) {
      this.data = data;
      this.first = Lazy.of(data::head);
      this.last = Lazy.of(data::last);
      this.center = Lazy.of(() -> first.get().add(last.get().subtract(first.get()).divide(2)));
    }

    public static <U extends Point> Bucket<U> of(List<U> ts) {
      return new Bucket<>(ts);
    }

    @SafeVarargs
    public static <U extends Point> Bucket<U> of(U... ts) {
      return new Bucket<>(List.of(ts));
    }

    public T getResult() {
      return result.getOrElse(first);
    }

    public void setResult(T result) {
      this.result = Option.of(result);
    }

    public T getFirst() {
      return first.get();
    }

    public T getLast() {
      return last.get();
    }

    public Point getCenter() {
      return center.get();
    }

    public <U> List<U> map(Function<T, U> mapper) {
      return data.map(mapper);
    }
  }

  static class Triangle<T extends Point> {
    private final Bucket<T> left, center, right;

    Triangle(Bucket<T> left, Bucket<T> center, Bucket<T> right) {
      this.left = left;
      this.center = center;
      this.right = right;
    }

    public static <U extends Point> Triangle<U> of(List<Bucket<U>> buckets) {
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
      T a = left.getResult();
      Point c = right.getCenter();

      List<Area<T>> areas = center.map((T b) -> Area.ofTriangle(a, b, c));

      Area<T> maxArea = areas.maxBy(Area::compareTo).get();

      return maxArea.getGenerator();
    }
  }

  static class Area<T extends Point> implements Comparable<Area<T>> {
    private final T generator;
    private final BigDecimal value;

    Area(T generator, BigDecimal value) {
      this.generator = generator;
      this.value = value;
    }

    public static <U extends Point> Area<U> ofTriangle(Point a, U b, Point c) {
      // Area = mod of [Ax(By-Cy)+Bx(Cy-Ay)+Cx(Ay-By)] / 2
      List<BigDecimal> addends = List.of(
          a.getX().multiply(b.getY().subtract(c.getY())),
          b.getX().multiply(c.getY().subtract(a.getY())),
          c.getX().multiply(a.getY().subtract(b.getY()))
      );
      BigDecimal sum = addends.fold(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal half = sum
          .divide(BigDecimal.valueOf(2), MathContext.UNLIMITED);
      BigDecimal value = half.abs();
      return new Area<>(b, value);
    }

    public T getGenerator() {
      return generator;
    }

    @Override
    public int compareTo(Area<T> o) {
      return value.compareTo(o.value);
    }
  }
}
