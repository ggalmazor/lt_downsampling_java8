package com.github.ggalmazor.ltdownsampling;

import javaslang.Tuple2;
import javaslang.collection.List;
import javaslang.collection.Stream;

import java.util.ArrayList;

class OnePassBucketizer {

  static <T extends Point> List<Bucket<T>> bucketize(Iterable<T> input, int inputSize, int desiredBuckets) {
    int middleSize = inputSize - 2;
    int bucketSize = middleSize / desiredBuckets;
    int rest = middleSize % desiredBuckets;

    if (bucketSize == 0) {
      throw new IllegalArgumentException("Can't produce " + desiredBuckets + " buckets from an input series of " + (middleSize + 2) + " elements");
    }

    java.util.List<Bucket<T>> buckets = new ArrayList<>();

    Stream<T> s = Stream.ofAll(input);

    // Add first point as the only point in the first bucket
    buckets.add(Bucket.of(s.head()));
    s = s.tail();

    // Add middle buckets.
    // Last middle bucket gets the rest of points when inputSize is not a multiple of desiredBuckets
    while (buckets.size() < desiredBuckets + 1) {
      int size = buckets.size() == desiredBuckets ? bucketSize + rest : bucketSize;
      Tuple2<Stream<T>, Stream<T>> branches = s.splitAt(size);
      buckets.add(Bucket.of(branches._1));
      s = branches._2;
    }

    // Add last point as the only point in the last bucket
    buckets.add(Bucket.of(s.head()));

    return List.ofAll(buckets);
  }
}
