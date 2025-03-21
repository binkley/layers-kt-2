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
# Or:
$ earthly +build
$ earthly +run  # a demo
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
