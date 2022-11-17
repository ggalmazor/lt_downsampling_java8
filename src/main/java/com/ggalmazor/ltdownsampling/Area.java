package com.ggalmazor.ltdownsampling;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;

class Area<T extends Point> implements Comparable<Area<T>> {
    private final T generator;
    private final BigDecimal value;

    private Area(T generator, BigDecimal value) {
        this.generator = generator;
        this.value = value;
    }

    static <U extends Point> Area<U> ofTriangle(Point a, U b, Point c) {
        // area of a triangle = |[Ax(By - Cy) + Bx(Cy - Ay) + Cx(Ay - By)] / 2|
        List<BigDecimal> addends = Arrays.asList(
                a.getX().multiply(b.getY().subtract(c.getY())),
                b.getX().multiply(c.getY().subtract(a.getY())),
                c.getX().multiply(a.getY().subtract(b.getY()))
        );
        BigDecimal sum = addends.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal half = sum
                .divide(BigDecimal.valueOf(2), MathContext.UNLIMITED);
        BigDecimal value = half.abs();
        return new Area<>(b, value);
    }

    T getGenerator() {
        return generator;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public int compareTo(Area<T> o) {
        return value.compareTo(o.value);
    }
}
