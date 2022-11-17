package com.ggalmazor.ltdownsampling.tools;

import java.util.List;
import java.util.stream.Collector;

public class CustomCollectors {

  public static <T> Collector<T, ?, List<List<T>>> sliding(int size) {
    return new SlidingCollector<>(size, 1);
  }

  public static <T> Collector<T, ?, List<List<T>>> sliding(int size, int step) {
    return new SlidingCollector<>(size, step);
  }

}
