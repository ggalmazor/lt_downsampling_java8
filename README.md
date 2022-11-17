# Largest-Triangle downsampling algorithm implementations for Java8

These implementations are based on the paper *"Downsampling Time Series for Visual Representation"* by Sveinn Steinarsson from the Faculty of Industrial Engineering, Mechanical Engineering and Computer Science University of Iceland (2013). You can read the paper [here](http://skemman.is/stream/get/1946/15343/37285/3/SS_MSthesis.pdf)

The goal of Largest-Triangle downsampling algorithms for data visualization is to reduce the number of points in a number series without losing important visual features of the resulting graph. It is important to be aware that **these algorithms are not numerically correct**.

## Download

Latest version: 0.0.7

You can add this library into your Maven/Gradle/SBT/Leiningen project thanks to JitPack.io. Follow the instructions [here](https://jitpack.io/#ggalmazor/lt_downsampling_java8).

### Example Gradle instructions

Add this into your build.gradle file:

```groovy
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
	
dependencies {
  compile 'com.ggalmazor:lt_downsampling_java8:0.0.7'
}
```

## Largest-Triangle Three-Buckets

This version of the algorithm groups numbers in same sized buckets and then selects from each bucket the point that produces the largest area with points on neighbour buckets.

You can produce a downsampled version of an input series with: 

```java
List<Point> input = Arrays.asList(...);
int numberOfBuckets = 200;

List<Point> output = LTThreeBuckets.ofSorted(input, numberOfBuckets);
```

First and last points of the original series are always in the output. Then, the rest are grouped into the defined amount of buckets and the algorithm chooses the best point from each bucket, resulting in a list of 202 elements.

You can translate your domain objects into Point instances and back or extend have your Domain extend Point to make things easier.

## Largest-Triangle Dynamic

Not yet implemented

## Example

This is how a raw timeseries with ~5000 data points and downsampled versions (2000, 500, and 250 buckets) look like (graphed by AirTable)
![image](https://user-images.githubusercontent.com/205913/202478853-180c56ff-41af-43b3-8830-6d51ac7cfbb3.png)
![image](https://user-images.githubusercontent.com/205913/202478930-dd482a9f-0da1-4e6b-8537-f7a2fbe68991.png)
![image](https://user-images.githubusercontent.com/205913/202478994-28ae49ff-6036-43d1-8000-6730a55f8a77.png)
![image](https://user-images.githubusercontent.com/205913/202480858-51ef82fc-6432-4447-942a-65edfa82a742.png)


## Other java implementations you might want to check

 - [drcrane/downsample](https://github.com/drcrane/downsample)
 - [n52.io](http://www.programcreek.com/java-api-examples/index.php?source_dir=sensorweb-rest-api-master/timeseries-io/src/main/java/org/n52/io/generalize/LargestTriangleThreeBucketsGeneralizer.java)
