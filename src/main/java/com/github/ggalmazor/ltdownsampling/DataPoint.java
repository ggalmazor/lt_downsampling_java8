package com.github.ggalmazor.ltdownsampling;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

public final class DataPoint {
  private final BigDecimal position;
  private final BigDecimal value;

  DataPoint(BigDecimal x, BigDecimal value) {
    this.position = x;
    this.value = value;
  }

  static DataPoint of(Number position, Number value) {
    return new DataPoint(
        new BigDecimal(position.doubleValue(), MathContext.UNLIMITED),
        new BigDecimal(value.doubleValue(), MathContext.UNLIMITED)
    );
  }

  @Override
  public String toString() {
    return '(' + position.toString() + ',' + value.toString() + ')';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DataPoint dataPoint = (DataPoint) o;
    return Objects.equals(position, dataPoint.position) &&
        Objects.equals(value, dataPoint.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(position, value);
  }
}
