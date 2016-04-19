# Largest-Triangle downsampling algorithm implementations for Java8

These implementations are based on the paper *"Downsampling Time Series for Visual Representation"* by Sveinn Steinarsson from the Faculty of Industrial Engineering, Mechanical Engineering and Computer Science University of Iceland (2013). You can read the paper [here](http://skemman.is/stream/get/1946/15343/37285/3/SS_MSthesis.pdf)

The goal of Largest-Triangle downsampling algorithms for data visualization is to reduce the number of points in a number series without losing important visual features of the resulting graph. It is important to be aware that **these algorithms are not numerically correct**.

## Largest-Triangle Three-Buckets

This version of the algorithm groups numbers in same sized buckets and then selects from each bucket the point that produces the largest area with points on neighbour buckets.

You can produce a downsampled version of an input series with: 

```
List<Point> input = List.of(...);
int numberOfBuckets = 200;

List<Point> output = LTThreeBuckets.ofSorted(input, numberOfBuckets);
```

First and last points of the original series are always in the output. Then, the rest are grouped into the defined amount of buckets and the algorithm chooses the best point from each bucket, resulting in a list of 202 elements.

## Largest-Triangle Dynamic

Not yet implemented

## Other java implementations you might want to check

 - [drcrane/downsample](https://github.com/drcrane/downsample)
 - [jvsdfx](https://apache.googlesource.com/incubator-geode/+/sga2/jvsdfx-mm/src/main/java/com/pivotal/chart/util/LargestTriangleThreeBuckets.java)
 - [n52.io](http://www.programcreek.com/java-api-examples/index.php?source_dir=sensorweb-rest-api-master/timeseries-io/src/main/java/org/n52/io/generalize/LargestTriangleThreeBucketsGeneralizer.java)
 - [lmgtfy](http://lmgtfy.com/?q=java+largest+triangle+three+buckets)
