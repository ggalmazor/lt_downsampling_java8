package com.github.ggalmazor.ltdownsampling;

import javaslang.collection.List;
import javaslang.collection.Stream;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LTOBTest {
  @Test
  public void one_bucket_with_one_point_produces_that_point() throws Exception {
    List<DataPoint> input = List.of(
        DataPoint.of(0, 0),
        DataPoint.of(0, 1),
        DataPoint.of(0, 0)
    );

    List<DataPoint> output = LTOB.of(input, 1).toList();

    assertThat(output).isEqualTo(input);
  }

  @Test
  public void one_bucket_with_two_points_with_same_area_produces_the_first_point() throws Exception {
    List<DataPoint> input = List.of(
        DataPoint.of(0, 0),
        DataPoint.of(0, -1),
        DataPoint.of(0, 1),
        DataPoint.of(0, 0)
    );

    List<DataPoint> expectedOutput = List.of(
        DataPoint.of(0, 0),
        DataPoint.of(0, -1),
        DataPoint.of(0, 0)
    );

    List<DataPoint> actualOutput = LTOB.of(input, 1).toList();

    assertThat(actualOutput).isEqualTo(expectedOutput);

  }
}
