package com.github.ggalmazor.ltdownsampling;

import io.vavr.collection.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LargestTriangleThreeBucketsTest {
  @Test
  public void one_bucket_with_one_point_produces_that_point() throws Exception {
    List<Point> input = List.of(
        Point.of(0, 0),
        Point.of(1, 1),
        Point.of(2, 0)
    );

    List<Point> output = LTThreeBuckets.sorted(input, input.size(), 1).toList();

    assertThat(output).isEqualTo(input);
  }

  @Test
  public void one_bucket_with_two_points_with_same_area_produces_the_first_point() throws Exception {
    List<Point> input = List.of(
        Point.of(0, 0),
        Point.of(1, 1),
        Point.of(2, -1),
        Point.of(3, 0)
    );

    List<Point> expectedOutput = List.of(
        Point.of(0, 0),
        Point.of(1, 1),
        Point.of(3, 0)
    );

    List<Point> actualOutput = LTThreeBuckets.sorted(input, input.size(), 1).toList();

    assertThat(actualOutput).isEqualTo(expectedOutput);
  }

  @Test
  public void one_bucket_with_two_points_with_different_area_produces_the_point_that_generates_max_area() throws Exception {
    List<Point> input = List.of(
        Point.of(0, 0),
        Point.of(1, 1),
        Point.of(2, 2),
        Point.of(3, 0)
    );

    List<Point> expectedOutput = List.of(
        Point.of(0, 0),
        Point.of(2, 2),
        Point.of(3, 0)
    );

    List<Point> actualOutput = LTThreeBuckets.sorted(input, input.size(), 1).toList();

    assertThat(actualOutput).isEqualTo(expectedOutput);
  }

  @Test
  public void two_buckets_with_one_point_each() throws Exception {
    List<Point> input = List.of(
        Point.of(0, 0),
        Point.of(1, 1),
        Point.of(2, 2),
        Point.of(3, 0)
    );

    List<Point> actualOutput = LTThreeBuckets.sorted(input, input.size(), 2).toList();

    assertThat(actualOutput).isEqualTo(input);
  }

  @Test
  public void two_buckets_non_full_middle_buckets() throws Exception {
    List<Point> input = List.of(
        Point.of(0, 0),
        Point.of(1, 1),
        Point.of(2, 2),
        Point.of(3, 1),
        Point.of(4, 5)
    );

    List<Point> expectedOutput = List.of(
        Point.of(0, 0),
        Point.of(1, 1),
        Point.of(3, 1),
        Point.of(4, 5)
    );

    List<Point> actualOutput = LTThreeBuckets.sorted(input, input.size(), 2).toList();

    assertThat(actualOutput).isEqualTo(expectedOutput);
  }

  @Test
  public void two_buckets_full_middle_buckets() throws Exception {
    List<Point> input = List.of(
        Point.of(0, 0),
        Point.of(1, 1),
        Point.of(2, 3),
        Point.of(3, 1),
        Point.of(4, 3),
        Point.of(5, 2),
        Point.of(6, 0)
    );

    List<Point> expectedOutput = List.of(
        Point.of(0, 0),
        Point.of(2, 3),
        Point.of(4, 3),
        Point.of(6, 0)
    );

    List<Point> actualOutput = LTThreeBuckets.sorted(input, input.size(), 2).toList();

    assertThat(actualOutput).isEqualTo(expectedOutput);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throws_when_more_buckets_than_posible() throws Exception {
    List<Point> input = List.of(
        Point.of(0, 0),
        Point.of(1, 1),
        Point.of(2, 0)
    );
    LTThreeBuckets.sorted(input, input.size(), 2);
  }
}
