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
    * [Contributing](http://sphereio.github.io/sphere-jvm-sdk/javadoc/master/io/sphere/sdk/meta/ContributorDocumentation.html)
 
## Installation

### Java SDK with Maven

````xml
<dependency>
  <groupId>io.sphere.sdk.jvm</groupId>
  <artifactId>sphere-models</artifactId>
  <version>1.0.0-M13</version>
</dependency>
<dependency>
  <groupId>io.sphere.sdk.jvm</groupId>
  <artifactId>sphere-java-client</artifactId>
  <version>1.0.0-M13</version>
</dependency>
````

### Modules
* `sphere-java-client`: alias for sphere-java-client-ning-1_9
* `sphere-java-client-apache-async`: uses Apache HTTP client
* `sphere-java-client-ning-1_8`: uses Ning async HTTP client 1.9
* `sphere-java-client-ning-1_9`: uses Ning async HTTP client 1.9 (incompatible to 1.8)
* `sphere-models`: models which do not depend to a client implementation

### Play/Scala SDK with SBT

see https://github.com/sphereio/sphere-jvm-sdk-scala-add-ons

### Snapshots

* experimental, risk of breaking changes, no support, snapshots can vanish
* look at https://oss.sonatype.org/content/repositories/snapshots/io/sphere/sdk/jvm/models/ for new snapshots, they are ordered by next version and date
* add resolver "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

## Short-term roadmap
* https://github.com/sphereio/sphere-jvm-sdk/milestones
