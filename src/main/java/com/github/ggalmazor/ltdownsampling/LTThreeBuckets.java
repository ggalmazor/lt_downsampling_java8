package com.github.ggalmazor.ltdownsampling;

import javaslang.collection.List;

import java.util.ArrayList;

public final class LTThreeBuckets {

  public static <T extends Point> List<T> sorted(List<T> input, int inputSize, int desiredBuckets) {
    java.util.List<T> results = new ArrayList<>();

    OnePassBucketizer.bucketize(input, inputSize, desiredBuckets)
        .sliding(3, 1)
        .map(Triangle::of)
        .forEach(triangle -> {
          if (results.size() == 0)
            results.add(triangle.getFirst());

          results.add(triangle.getResult());

          if (results.size() == desiredBuckets + 1)
            results.add(triangle.getLast());
        });

    return List.ofAll(results);
  }

}
