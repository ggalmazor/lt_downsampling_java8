# Largest-Triangle downsampling algorithm implementations for Java8
[![](https://jitpack.io/v/ggalmazor/lt_downsampling_java8.svg)](https://jitpack.io/#ggalmazor/lt_downsampling_java8)
<a href="https://www.buymeacoffee.com/ggalmazor" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/default-orange.png" alt="Buy Me A Coffee" height="41" width="174"></a>


These implementations are based on the paper *"Downsampling Time Series for Visual Representation"* by Sveinn Steinarsson from the Faculty of Industrial Engineering, Mechanical Engineering and Computer Science University of Iceland (2013). You can read the paper [here](http://skemman.is/stream/get/1946/15343/37285/3/SS_MSthesis.pdf)

The goal of Largest-Triangle downsampling algorithms for data visualization is to reduce the number of points in a number series without losing important visual features of the resulting graph. It is important to be aware that **these algorithms are not numerically correct**.

## Download

Latest version: 0.1.0

You can add this library into your Maven/Gradle/SBT/Leiningen project using a couple of source repositories

### JitPack.io

Please follow the instructions at the [JitPack.io page for this project](https://jitpack.io/#ggalmazor/lt_downsampling_java8). Gradle example:

```groovy
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  implementation 'com.github.ggalmazor:lt_downsampling_java8:0.1.0'
}
```

### GithHub Package Repository

⚠️ Warning: Access to Maven repos hosted by GitHub requires authentication. More information at https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry. 

Please follow the instructions at the [GitHub Package Repository for this project](https://github.com/ggalmazor/lt_downsampling_java8/packages). Gradle example:

```groovy
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/ggalmazor/lt_downsampling_java")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") ?: System.getenv("TOKEN")
        }
   }
}

dependencies {
  implementation 'com.github.ggalmazor:lt_downsampling_java8:0.1.0'
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

## Notes on Point types

- This library requires to provide lists of instances of the `Point` supertype.
- It also provides and uses internally the `DoublePoint` subtype, which can also be used to feed data to the library.
- However, users are free to create implementations of `Point` that best fit their Domain.

## Largest-Triangle Dynamic

Not yet implemented

## Example

This is how a raw timeseries with ~5000 data points and downsampled versions (2000, 500, and 250 buckets) look like (graphed by AirTable)
![image](https://user-images.githubusercontent.com/205913/202478853-180c56ff-41af-43b3-8830-6d51ac7cfbb3.png)
![image](https://user-images.githubusercontent.com/205913/202478930-dd482a9f-0da1-4e6b-8537-f7a2fbe68991.png)
![image](https://user-images.githubusercontent.com/205913/202478994-28ae49ff-6036-43d1-8000-6730a55f8a77.png)
![image](https://user-images.githubusercontent.com/205913/202480858-51ef82fc-6432-4447-942a-65edfa82a742.png)

These are closeups for 250, 500, 1000, and 2000 buckets with raw data in the back:
![image](https://user-images.githubusercontent.com/205913/202486056-25a612b1-7294-4967-9714-000cfcd5177e.png)
![image](https://user-images.githubusercontent.com/205913/202486255-b42f7e90-29fc-45f9-be54-f30b4a6d1e07.png)
![image](https://user-images.githubusercontent.com/205913/202486337-b402dd24-44dd-4456-af3d-add931e7fbd7.png)
![image](https://user-images.githubusercontent.com/205913/202486396-ff3772d3-ef69-4c69-b56c-4ac16964ed04.png)


## Other java implementations you might want to check

 - [drcrane/downsample](https://github.com/drcrane/downsample)
 - [n52.io](http://www.programcreek.com/java-api-examples/index.php?source_dir=sensorweb-rest-api-master/timeseries-io/src/main/java/org/n52/io/generalize/LargestTriangleThreeBucketsGeneralizer.java)
