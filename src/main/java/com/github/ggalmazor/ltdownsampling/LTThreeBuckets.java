package com.github.ggalmazor.ltdownsampling;

import java.util.ArrayList;
import java.util.List;

import static com.github.ggalmazor.ltdownsampling.tools.CustomCollectors.sliding;

public final class LTThreeBuckets {

  public static <T extends Point> List<T> sorted(List<T> input, int desiredBuckets) {
    return sorted(input, input.size(), desiredBuckets);
  }

  public static <T extends Point> List<T> sorted(List<T> input, int inputSize, int desiredBuckets) {
    List<T> results = new ArrayList<>();

    OnePassBucketizer.bucketize(input, inputSize, desiredBuckets)
        .stream()
        .collect(sliding(3, 1))
        .stream()
        .map(Triangle::of)
        .forEach(triangle -> {
          if (results.size() == 0)
            results.add(triangle.getFirst());

          results.add(triangle.getResult());

          if (results.size() == desiredBuckets + 1)
            results.add(triangle.getLast());
        });

    return results;
  }

}
