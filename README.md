<a href="./LICENSE.md">
<img src="./images/cc0.svg" alt="Creative Commons Public Domain Dedication"
align="right" width="10%" height="auto"/>
</a>

# Layers Kotlin 2

Second experiment for "list of maps" in Kotlin.

[![build](https://github.com/binkley/layers-kt-2/actions/workflows/ci.yml/badge.svg)](https://github.com/binkley/layers-kt-2/actions)
[![coverage](https://github.com/binkley/layers-kt-2/raw/master/images/jacoco.svg)](https://github.com/binkley/layers-kt-2/actions/workflows/ci.yml)
[![pull requests](https://img.shields.io/github/issues-pr/binkley/layers-kt-2.svg)](https://github.com/binkley/layers-kt-2/pulls)
[![issues](https://img.shields.io/github/issues/binkley/layers-kt-2.svg)](https://github.com/binkley/layers-kt-2/issues)
[![vulnerabilities](https://snyk.io/test/github/binkley/layers-kt-2/badge.svg)](https://snyk.io/test/github/binkley/layers-kt-2)
[![license](https://img.shields.io/badge/License-CC0_1.0-lightgrey.svg)](http://creativecommons.org/publicdomain/zero/1.0/)

## Try it

After cloning the project, try [`./run`](./run) for a demonstration.

The build is vanilla [Maven](pom.xml), and includes a `./mvnw` (wrapper)
script so you can work without installing Maven locally.

```
$ ./mvnw clean verify
$ ./run  # a demo
```

> [!NOTE]
> You will need an OWASP NVD API key exported to your environment as
> `OWASP_NVD_API_KEY`.
> This is for running security checks on dependencies as part of the build.
> Alternatively, use the `-Dowasp.skip=true` flag to `./mvn`; there is no
> equivalent for skipping these checks for the Earthly build.
> For running `earthly` this needs to be passed as `--secret
> OWASP_NVD_API_KEY=some-value`.

Test coverage is 100% for lines, branches, and instructions.
Checkout [CI builds](https://github.com/binkley/layers-kt-2/actions) to see what
happens.

If you locally use [Earthly](https://github.com/earthly/earthly) (a
containerized build), you can try:

```
$ earthly +build
$ earthly +run  # a demo
```

If you locally use the [`gh`](https://cli.github.com/) (manage GitHub actions)
and [`act`](https://github.com/nektos/act) (run GH actions locally) tools, you
can try:

```
$ gh act
```

## Development

To aid working with Grok AI (or other coding LLMs), the
[`sync-for-grok.sh`](./sync-for-grok.sh) script outputs recent commits in a
format easily read by humans, and easily read by AI.
Most LLMs do not support directly working with GitHub repos, but
some&mdash;like Grok&mdash;can work with file links into your repo.


----

# Previous README
<a href="LICENSE.md">
<img src="https://unlicense.org/pd-icon.png" alt="Public Domain" align="right"/>
</a>

# Layers KT

[![build](https://github.com/binkley/layers-kt/workflows/build/badge.svg)](https://github.com/binkley/layers-kt/actions)
[![issues](https://img.shields.io/github/issues/binkley/layers-kt.svg)](https://github.com/binkley/layers-kt/issues/)
[![vulnerabilities](https://snyk.io/test/github/binkley/layers-kt/badge.svg)](https://snyk.io/test/github/binkley/layers-kt)
[![license](https://img.shields.io/badge/license-Public%20Domain-blue.svg)](http://unlicense.org/)

_An experiment in style and technique in Kotlin_.

`Layers` is a list of maps that appears as a single map. It uses _rules_
to provide a single value for each key, based on all values for that key in
the list of maps. For example, if the key "BOB" has values 1, 2, and 3 in
different layers, and the rule were "sum", then the value of "BOB" in the map
would be 6. Changing the rule to "latest of" for "BOB" would result in the map
having 3 for the value of that key.

(See [Layers Java](https://github.com/binkley/layers-java) for an older
approach in Java.)

## Build and try

To build, use `./mvnw verify` or `./batect build` (for a CI-like experience).

There are no run-time dependencies.

## Platform

This code relies on JDK 11 or newer.

## Build

* [DependencyCheck](https://github.com/jeremylong/DependencyCheck) scans for
  dependency security issues
* [detekt](https://github.com/arturbosch/detekt) runs static code analysis for
  Kotlin
* [JUnit](https://github.com/junit-team/junit5) runs tests
* [JaCoCo](https://github.com/jacoco/jacoco) measures code coverage
* [ktlint](https://github.com/pinterest/ktlint) keeps code tidy

Use `./mvnw` (Maven) or `./batect build` (Batect) to build, run tests, and
create a demo program. Use `./run.sh` or `./batect run` to run the demo.

[Batect](https://batect.dev/) works "out of the box", however, an important
optimization is to avoid redownloading plugins and dependencies from within a
Docker container.

This shares Maven plugin and dependency downloads with the Docker container
run by Batect.

## API

### General patterns

It is difficult to express in Kotlin the _structure_ of functions without
providing their _names_ (think interfaces; meta-programming may be required),
but for all functions which express map state (construction or mutation),
functions come in three styles:

- `foo(vararg state: Pair<String, Entry<*>>): T`

```kotlin
x = x.foo("a" to 3.toValue()) // Merge into existing mutable map
```

- `foo(state: EntryMap): T`

```kotlin
x = x.foo(mapOf("a" to 3.toValue())) // Merge into existing mutable map
```

- `foo(state: MutableMap<String, Entry<*>>.() -> Unit): T`

```kotlin
x = x.foo {
    this["a"] = 3.toValue() // Mutate existing mutable map
}
```

Example functions following these patterns include `new` and `edit`.

Actual declarations take advantage of these type aliases:

- `EntryMap = Map<String, Entry<*>>`
- `EditMap = MutableMap<String, Entry<*>>`
- `EditBlock = EditMap.() -> Unit`

### Layers

[`Layers`](./layers-kt-lib/src/main/kotlin/hm/binkley/layers/Layers.kt) is a
subtype of
[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/)
.

#### Properties

- `layers` &mdash; an immutable list of layers, ordered from most recent to
  oldest. The top-most in the list is the current layer
- `current` &mdash; the current, _editable_ layer. Make updates against the _
  current_ layer

### Layer

[`Layer`](./layers-kt-lib/src/main/kotlin/hm/binkley/layers/Layer.kt) is a
subtype of
[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/)
.

### Editable layer

[`EditableLayer`](./layers-kt-lib/src/main/kotlin/hm/binkley/layers/EditableLayer.kt)
is a subtype of
[`Layer`](./layers-kt-lib/src/main/kotlin/hm/binkley/layers/Layer.kt).

#### Methods

- `edit` &mdash; edits with a block. All `Map` methods apply. A sample:
  ```kotlin
        editableLayer.edit {
            this["BOB"] = 3.toEntry()
        }

  ```

### Standard rule: Constant

A layers rule which returns a constant value.

See
[`ConstantRule`](./layers-kt-lib/src/main/kotlin/hm/binkley/layers/rules/ConstantRule.kt)
.

### Standard rule: Latest

A layers rule which returns the latest value (the value in the most recently
layer to a `Layers`).

See
[`LatestOfRule`](./layers-kt-lib/src/main/kotlin/hm/binkley/layers/rules/LatestOfRule.kt)
.

### Standard rule: Sum

A layers rule which sums all values added to a `Layers`.

See
[`SumOfRule`](./layers-kt-lib/src/main/kotlin/hm/binkley/layers/rules/SumOfRule.kt)
.

## TODO

* Rationalize uses of `Map` as input _vs_ `MutableMap` reused as a property or
  delegated argument
* Incorporate the spike having generic key and value types
