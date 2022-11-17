package com.ggalmazor.ltdownsampling;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class LargestTriangleThreeBucketsTest {
  @Test
  public void one_bucket_with_one_point_produces_that_point() {
    List<Point> input = asList(
      Point.of(0, 0),
      Point.of(1, 1),
      Point.of(2, 0)
    );

    List<com.ggalmazor.ltdownsampling.Point> output = LTThreeBuckets.sorted(input, input.size(), 1);

    assertThat(output, equalTo(input));
  }

  @Test
  public void one_bucket_with_two_points_with_same_area_produces_the_first_point() {
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
  public void one_bucket_with_two_points_with_different_area_produces_the_point_that_generates_max_area() {
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
  public void two_buckets_with_one_point_each() {
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
  public void two_buckets_non_full_middle_buckets() {
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
  public void two_buckets_full_middle_buckets() {
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
  public void throws_when_more_buckets_than_posible() {
    List<Point> input = asList(
      Point.of(0, 0),
      Point.of(1, 1),
      Point.of(2, 0)
    );
    assertThrows(IllegalArgumentException.class, () -> LTThreeBuckets.sorted(input, input.size(), 2));
  }

  class DateSeriesPoint extends Point {
    private final LocalDate date;
    private final BigDecimal value;

    public DateSeriesPoint(LocalDate date, BigDecimal value) {
      super(BigDecimal.valueOf(date.atStartOfDay().atOffset(ZoneOffset.UTC).toEpochSecond()), value);
      this.date = date;
      this.value = value;
    }

    @Override
    public String toString() {
      return String.format("%s\t%s", date.format(DateTimeFormatter.ISO_DATE), value.toPlainString());
    }
  }

  @Test
  public void complex_downsampling_scenario() throws URISyntaxException, IOException {
    URI uri = LTThreeBuckets.class.getResource("/daily-foreign-exchange-rates-31-.csv").toURI();
    List<DateSeriesPoint> series = Files.lines(Paths.get(uri))
      .map(line -> line.split(";"))
      .map(cols -> {
        LocalDate date = LocalDate.parse(cols[0]);
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(cols[1]));
        return new DateSeriesPoint(date, value);
      })
      .sorted(comparing(Point::getX))
      .collect(toList());

    assertThat(LTThreeBuckets.sorted(series, 10), Matchers.contains(
      new DateSeriesPoint(LocalDate.of(1979, 12, 31), new BigDecimal("1.726")),
      new DateSeriesPoint(LocalDate.of(1981, 8, 10), new BigDecimal("2.571")),
      new DateSeriesPoint(LocalDate.of(1982, 11, 8), new BigDecimal("2.596")),
      new DateSeriesPoint(LocalDate.of(1985, 2, 25), new BigDecimal("3.453")),
      new DateSeriesPoint(LocalDate.of(1985, 9, 18), new BigDecimal("2.907")),
      new DateSeriesPoint(LocalDate.of(1987, 12, 31), new BigDecimal("1.571")),
      new DateSeriesPoint(LocalDate.of(1991, 2, 11), new BigDecimal("1.445")),
      new DateSeriesPoint(LocalDate.of(1992, 9, 2), new BigDecimal("1.391")),
      new DateSeriesPoint(LocalDate.of(1995, 3, 7), new BigDecimal("1.374")),
      new DateSeriesPoint(LocalDate.of(1995, 4, 19), new BigDecimal("1.357")),
      new DateSeriesPoint(LocalDate.of(1997, 8, 5), new BigDecimal("1.881")),
      new DateSeriesPoint(LocalDate.of(1998, 12, 31), new BigDecimal("1.667"))
    ));
  }
}
