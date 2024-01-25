# Changelog

## `main`

## Release 0.1.0

- This lib no longer relies on `BigDecimal` to improve its performance and memory usage
- `Point` has been divided into an interface called `Point` and an implementation called `DoublePoint`
  - Users can now implement their own `Point` class, or extend `DoublePoint` and interact with them with this library
  - Implementing `Point` only requires to provide a `Double getX()` and `Double getY()` methods, simplifying the
    adaptation work

## Release 0.0.7

- CHANGED: Distribute points uniformly into all middle buckets #2
