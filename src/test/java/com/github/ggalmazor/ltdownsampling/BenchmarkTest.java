package com.github.ggalmazor.ltdownsampling;

import io.vavr.collection.List;
import io.vavr.control.Try;
import org.junit.Ignore;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

public class BenchmarkTest {

  @Test
  @Ignore
  public void launchBenchmark() throws Exception {
    Options opt = new OptionsBuilder()
        // Specify which benchmarks to run.
        // You can be more specific if you'd like to run only one benchmark per test.
        .include(this.getClass().getName() + ".*")
        // Set the following options as needed
        .mode(Mode.AverageTime)
        .timeUnit(TimeUnit.MICROSECONDS)
        .warmupIterations(10)
        .measurementIterations(10)
        .threads(5)
        .forks(2)
        .shouldFailOnError(true)
        .shouldDoGC(true)
        //.jvmArgs("-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining")
        //.addProfiler(WinPerfAsmProfiler.class)
        .build();

    new Runner(opt).run();
  }

  @State(Scope.Benchmark)
  public static class BenchmarkState {
    public final List<Point> series;
    public final int size;

    public BenchmarkState() {
      URI uri = Try.of(() -> BenchmarkTest.class.getResource("/daily-foreign-exchange-rates-31-.csv").toURI()).get();
      series = List.ofAll(Try.of(() -> Files.lines(Paths.get(uri))).get()
          .map(line -> line.split(";"))
          .map(cols -> {
            long x = LocalDate.parse(cols[0]).atStartOfDay().atOffset(ZoneOffset.UTC).toEpochSecond();
            double y = Double.parseDouble(cols[1]);
            return Point.of(x, y);
          })
          .collect(toList()))
          .sortBy(Point::getX);
      size = series.size();
    }

  }

  @Benchmark
  public void benchmarkList(BenchmarkState state, Blackhole bh) {
    bh.consume(LTThreeBuckets.sorted(state.series, state.size, 200));
  }

}