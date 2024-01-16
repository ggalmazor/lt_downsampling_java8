package com.ggalmazor.ltdownsampling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateSeriesPoint extends Point {
  private final LocalDate date;
  private final double value;

  public DateSeriesPoint(LocalDate date, double value) {
    super((double)date.atStartOfDay().atOffset(ZoneOffset.UTC).toEpochSecond(), value);
    this.date = date;
    this.value = value;
  }

  @Override
  public String toString() {
    return String.format("%s\t%s", date.format(DateTimeFormatter.ISO_DATE), value);
  }
}
