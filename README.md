# SPHERE.IO JVM SDK

![SPHERE.IO icon](https://admin.sphere.io/assets/images/sphere_logo_rgb_long.png)

[![Build Status](https://travis-ci.org/sphereio/sphere-jvm-sdk.png?branch=master)](https://travis-ci.org/sphereio/sphere-jvm-sdk)

[SPHERE.IO](http://sphere.io) is the first Platform-as-a-Service solution for eCommerce.

The JVM SDK enables developers to use Java 8 methods and objects to communicate with SPHERE.IO rather than using plain HTTP calls.
Users gain type-safety, encapsulation, IDE auto completion and an internal domain specific language to discover and formulate valid requests.

There are different bundles for different purposes:

* ```java-sdk``` a standalone Java 8 client with all models
* ```scala-sdk``` a standalone Scala 2.10 client with all models (we will add Scala 2.11), which can be used with Scala Frameworks, e.g., Play Framework Scala
* ```play-sdk``` a client for the [Play Framework](http://www.playframework.com/) Java API (2.3.x) with all models
 
## Installation

### Play SDK with SBT

````scala
libraryDependencies += "io.sphere.jvmsdk" %% "play-sdk" % "1.0.0-M2" withSources()
````

### Java SDK with Maven

````xml
<dependency>
  <groupId>io.sphere.jvmsdk</groupId>
  <artifactId>java-sdk</artifactId>
  <version>1.0.0-M2</version>
</dependency>
````


## Using the SDK 
* install [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Javadoc](http://sphereio.github.io/sphere-jvm-sdk/javadoc/master/index.html)
 
## Developing the SDK
 
 * Contribution Guidelines: https://github.com/sphereio/sphere-sunrise/blob/master/CONTRIBUTING.md
 * `sbt clean genDoc` create Javadoc for all modules, it is available in target/javaunidoc/index.html
 * open directly the project with IntelliJ IDEA Ultimate might not work due to a bug, but using https://github.com/mpeltonen/sbt-idea works

### Short-term roadmap
* https://github.com/sphereio/sphere-jvm-sdk/milestones/1.0.0-M3
