package com.github.ggalmazor.ltdownsampling;

import javaslang.collection.Stream;

public final class LTDynamic {

  public static <T extends Number> Stream<T> sorted(Iterable<T> input, int buckets) {
    return Stream.ofAll(input);
  }
}
