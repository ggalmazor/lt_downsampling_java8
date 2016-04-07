package com.github.ggalmazor.ltdownsampling;

import javaslang.Lazy;
import javaslang.collection.Stream;
import javaslang.control.Option;

import java.util.function.Function;

class Bucket<T extends Point> {
  private final Stream<T> data;
  private final Lazy<T> first;
  private final Lazy<Point> center;
  private final Lazy<T> last;
  private Option<T> result = Option.none();

  private Bucket(Stream<T> data) {
    this.data = data;
    this.first = Lazy.of(data::head);
    this.last = Lazy.of(data::last);
    this.center = Lazy.of(() -> first.get().add(last.get().subtract(first.get()).divide(2)));
  }

  static <U extends Point> Bucket<U> of(Iterable<U> ts) {
    return new Bucket<>(Stream.ofAll(ts));
  }

  @SafeVarargs
  static <U extends Point> Bucket<U> of(U... ts) {
    return new Bucket<>(Stream.of(ts));
  }

  T getResult() {
    return result.getOrElse(first);
  }

  void setResult(T result) {
    this.result = Option.of(result);
  }

  T getFirst() {
    return first.get();
  }

  T getLast() {
    return last.get();
  }

  Point getCenter() {
    return center.get();
  }

  <U> Stream<U> map(Function<T, U> mapper) {
    return data.map(mapper);
  }
}
