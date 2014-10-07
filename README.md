# SPHERE.IO JVM SDK

![SPHERE.IO icon](https://admin.sphere.io/assets/images/sphere_logo_rgb_long.png)

[![Build Status](https://travis-ci.org/sphereio/sphere-jvm-sdk.png?branch=master)](https://travis-ci.org/sphereio/sphere-jvm-sdk)

[SPHERE.IO](http://sphere.io) is the first Platform-as-a-Service solution for eCommerce.

The JVM SDK enables developers to use Java 8 methods and objects to communicate with SPHERE.IO rather than using plain HTTP calls.
Users gain type-safety, encapsulation, IDE auto completion and an internal domain specific language to discover and formulate valid requests.

## Using the SDK 
* install [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* <strong>[Javadoc](http://sphereio.github.io/sphere-jvm-sdk/javadoc/master/index.html)</strong>, there you find also code snippets and insights
    * [Getting Started](http://sphereio.github.io/sphere-jvm-sdk/javadoc/master/io/sphere/sdk/meta/GettingStarted.html)
    * [Release Notes](http://sphereio.github.io/sphere-jvm-sdk/javadoc/master/io/sphere/sdk/meta/ReleaseNotes.html)
 
## Installation

### Play SDK with SBT

````scala
libraryDependencies += "io.sphere.sdk.jvm" %% "play-sdk" % "1.0.0-M5" withSources()
````

### Java SDK with Maven

````xml
<dependency>
  <groupId>io.sphere.sdk.jvm</groupId>
  <artifactId>models</artifactId>
  <version>1.0.0-M5</version>
</dependency>
<dependency>
  <groupId>io.sphere.sdk.jvm</groupId>
  <artifactId>java-client</artifactId>
  <version>1.0.0-M5</version>
</dependency>
````
 
## Developing the SDK
 
 * Contribution Guidelines: https://github.com/sphereio/sphere-sunrise/blob/master/CONTRIBUTING.md
 * `sbt clean genDoc` create Javadoc for all modules, it is available in target/javaunidoc/index.html
 * open directly the project with IntelliJ IDEA Ultimate might not work due to a bug, but using https://github.com/mpeltonen/sbt-idea works

### Short-term roadmap
* https://github.com/sphereio/sphere-jvm-sdk/milestones
