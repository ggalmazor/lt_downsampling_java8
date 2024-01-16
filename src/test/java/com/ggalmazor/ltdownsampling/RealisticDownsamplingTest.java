package com.ggalmazor.ltdownsampling;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;

public class RealisticDownsamplingTest {
  @Test
  public void complex_downsampling_scenario() throws URISyntaxException, IOException {
    URI uri = LTThreeBuckets.class.getResource("/daily-foreign-exchange-rates-31-.csv").toURI();
    List<DateSeriesPoint> series = Files.lines(Paths.get(uri))
      .map(line -> line.split(";"))
      .map(cols -> {
        LocalDate date = LocalDate.parse(cols[0]);
        double value = Double.parseDouble(cols[1]);
        return new DateSeriesPoint(date, value);
      })
      .sorted(comparing(Point::getX))
      .collect(toList());

    assertThat(LTThreeBuckets.sorted(series, 10), Matchers.contains(
      new DateSeriesPoint(LocalDate.of(1979, 12, 31), Double.parseDouble("1.726")),
      new DateSeriesPoint(LocalDate.of(1981, 8, 10), Double.parseDouble("2.571")),
      new DateSeriesPoint(LocalDate.of(1982, 11, 8), Double.parseDouble("2.596")),
      new DateSeriesPoint(LocalDate.of(1985, 2, 25), Double.parseDouble("3.453")),
      new DateSeriesPoint(LocalDate.of(1985, 9, 18), Double.parseDouble("2.907")),
      new DateSeriesPoint(LocalDate.of(1987, 12, 31), Double.parseDouble("1.571")),
      new DateSeriesPoint(LocalDate.of(1991, 2, 11), Double.parseDouble("1.445")),
      new DateSeriesPoint(LocalDate.of(1992, 9, 2), Double.parseDouble("1.391")),
      new DateSeriesPoint(LocalDate.of(1995, 3, 7), Double.parseDouble("1.374")),
      new DateSeriesPoint(LocalDate.of(1995, 4, 19), Double.parseDouble("1.357")),
      new DateSeriesPoint(LocalDate.of(1997, 8, 5), Double.parseDouble("1.881")),
      new DateSeriesPoint(LocalDate.of(1998, 12, 31), Double.parseDouble("1.667"))
    ));
  }
}
