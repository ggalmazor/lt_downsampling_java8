package com.github.ggalmazor.ltdownsampling;

import javaslang.collection.Stream;

public final class LTOB {

  public static Stream<DataPoint> of(Iterable<DataPoint> input, int buckets) {
    return Stream.ofAll(input);
  }
}
