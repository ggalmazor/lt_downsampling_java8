package com.github.ggalmazor.ltdownsampling;

import javaslang.collection.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LargestTriangleDynamicTest {
  @Test
  public void one_bucket_with_one_point_produces_that_point() throws Exception {
    List<Integer> input = List.of(0, 1, 0);

    List<Integer> output = LTDynamic.sorted(input, 1).toList();

    assertThat(output).isEqualTo(input);
  }

  @Test
  public void one_bucket_with_two_points_with_same_area_produces_the_first_point() throws Exception {
    List<Integer> input = List.of(0, 1, -1, 0);

    List<Integer> expectedOutput = List.of(0, 1, 0);

    List<Integer> actualOutput = LTDynamic.sorted(input, 1).toList();

    assertThat(actualOutput).isEqualTo(expectedOutput);
  }
}
