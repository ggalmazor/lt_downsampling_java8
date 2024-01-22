package com.ggalmazor.ltdownsampling;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class PointMatcher extends TypeSafeMatcher<Point> {
  private final Double x;
  private final Double y;

  public PointMatcher(Double x, Double y) {
    this.x = x;
    this.y = y;
  }

  public static Matcher<Point> pointAt(Number x, Number y) {
    return new PointMatcher(x.doubleValue(), y.doubleValue());
  }

  @Override
  protected boolean matchesSafely(Point point) {
    return point.getX() == x && point.getY() == y;
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("A point with x:" + x + " and y:" + y + " values");
  }
}
