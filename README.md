# valhalla-test

Value class experiments.

## Setup

Requires an early access build of [Valhalla](https://jdk.java.net/valhalla/).

Can be developed using Intellij IDEA, although it will of course display a lot of incorrect compiler errors.

## ValueList

Immutable lists implemented as `value class`,
with the purpose of comparing its performance with `ArrayList`.

### Benchmarks

See [benchmark/results.txt](benchmark/results.txt).

Benchmarks are located at `/src/jmh/java`.
