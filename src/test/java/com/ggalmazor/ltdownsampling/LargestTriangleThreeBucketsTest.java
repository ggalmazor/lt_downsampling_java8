package com.ggalmazor.ltdownsampling;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

public class LargestTriangleThreeBucketsTest {
    @Test
    public void one_bucket_with_one_point_produces_that_point() throws Exception {
        List<Point> input = asList(
                Point.of(0, 0),
                Point.of(1, 1),
                Point.of(2, 0)
        );

        List<com.ggalmazor.ltdownsampling.Point> output = LTThreeBuckets.sorted(input, input.size(), 1);

        assertThat(output, equalTo(input));
    }

    @Test
    public void one_bucket_with_two_points_with_same_area_produces_the_first_point() throws Exception {
        List<Point> input = asList(
                Point.of(0, 0),
                Point.of(1, 1),
                Point.of(2, -1),
                Point.of(3, 0)
        );

        List<Point> expectedOutput = asList(
                Point.of(0, 0),
                Point.of(1, 1),
                Point.of(3, 0)
        );

        List<Point> actualOutput = LTThreeBuckets.sorted(input, input.size(), 1);

        assertThat(actualOutput, equalTo(expectedOutput));
    }

    @Test
    public void one_bucket_with_two_points_with_different_area_produces_the_point_that_generates_max_area() throws Exception {
        List<Point> input = asList(
                Point.of(0, 0),
                Point.of(1, 1),
                Point.of(2, 2),
                Point.of(3, 0)
        );

        List<Point> expectedOutput = asList(
                Point.of(0, 0),
                Point.of(2, 2),
                Point.of(3, 0)
        );

        List<Point> actualOutput = LTThreeBuckets.sorted(input, input.size(), 1);

        assertThat(actualOutput, equalTo(expectedOutput));
    }

    @Test
    public void two_buckets_with_one_point_each() throws Exception {
        List<Point> input = asList(
                Point.of(0, 0),
                Point.of(1, 1),
                Point.of(2, 2),
                Point.of(3, 0)
        );

        List<Point> actualOutput = LTThreeBuckets.sorted(input, input.size(), 2);

        assertThat(actualOutput, equalTo(input));
    }

    @Test
    public void two_buckets_non_full_middle_buckets() throws Exception {
        List<Point> input = asList(
                Point.of(0, 0),
                Point.of(1, 1),
                Point.of(2, 2),
                Point.of(3, 1),
                Point.of(4, 5)
        );

        List<Point> expectedOutput = asList(
                Point.of(0, 0),
                Point.of(2, 2),
                Point.of(3, 1),
                Point.of(4, 5)
        );

        List<Point> actualOutput = LTThreeBuckets.sorted(input, input.size(), 2);

        assertThat(actualOutput, equalTo(expectedOutput));
    }

    @Test
    public void two_buckets_full_middle_buckets() throws Exception {
        List<Point> input = asList(
                Point.of(0, 0),
                Point.of(1, 1),
                Point.of(2, 3),
                Point.of(3, 1),
                Point.of(4, 3),
                Point.of(5, 2),
                Point.of(6, 0)
        );

        List<Point> expectedOutput = asList(
                Point.of(0, 0),
                Point.of(2, 3),
                Point.of(4, 3),
                Point.of(6, 0)
        );

        List<Point> actualOutput = LTThreeBuckets.sorted(input, input.size(), 2);

        assertThat(actualOutput, equalTo(expectedOutput));
    }

    @Test
    public void throws_when_more_buckets_than_posible() throws Exception {
        List<Point> input = asList(
                Point.of(0, 0),
                Point.of(1, 1),
                Point.of(2, 0)
        );
        assertThrows(IllegalArgumentException.class, () -> LTThreeBuckets.sorted(input, input.size(), 2));
    }
}
