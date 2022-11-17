package com.ggalmazor.ltdownsampling;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class RealisticDownsamplingTest {
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
