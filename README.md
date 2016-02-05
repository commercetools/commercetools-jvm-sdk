# SPHERE.IO JVM SDK

![SPHERE.IO icon](https://admin.sphere.io/assets/images/sphere_logo_rgb_long.png)

[![][travis img]][travis]
[![][maven img]][maven]
[![][license img]][license]

[](the link definitions are at the bottom)

[SPHERE.IO](http://sphere.io) is the first Platform-as-a-Service solution for eCommerce.

The JVM SDK enables developers to use Java 8 methods and objects to communicate with SPHERE.IO rather than using plain HTTP calls.
Users gain type-safety, encapsulation, IDE auto completion and an internal domain specific language to discover and formulate valid requests.

## Using the SDK 
* install [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* <strong>Javadoc</strong> [(latest release)](http://sphereio.github.io/sphere-jvm-sdk/javadoc/v1.0.0-M26/index.html) or [(master)](http://sphereio.github.io/sphere-jvm-sdk/javadoc/master/index.html)</strong>, there you find also code snippets and insights
    * [Getting Started](http://sphereio.github.io/sphere-jvm-sdk/javadoc/v1.0.0-M26/io/sphere/sdk/meta/GettingStarted.html)
    * [Release Notes](http://sphereio.github.io/sphere-jvm-sdk/javadoc/v1.0.0-M26/io/sphere/sdk/meta/ReleaseNotes.html)
    * [Contributing](http://sphereio.github.io/sphere-jvm-sdk/javadoc/v1.0.0-M26/io/sphere/sdk/meta/ContributorDocumentation.html)
 
## Installation

### Java SDK with Maven

````xml
<dependency>
  <groupId>io.sphere.sdk.jvm</groupId>
  <artifactId>sphere-models</artifactId>
  <version>1.0.0-M26</version>
</dependency>
<dependency>
  <groupId>io.sphere.sdk.jvm</groupId>
  <artifactId>sphere-java-client</artifactId>
  <version>1.0.0-M26</version>
</dependency>

<!-- experimental, includes for example reactive streams support -->
<dependency>
  <groupId>io.sphere.sdk.jvm</groupId>
  <artifactId>sphere-convenience</artifactId>
  <version>1.0.0-M26</version>
</dependency>
````
* http://search.maven.org/#artifactdetails%7Cio.sphere.sdk.jvm%7Csphere-models%7C1.0.0-M26%7Cjar
* http://search.maven.org/#artifactdetails%7Cio.sphere.sdk.jvm%7Csphere-java-client%7C1.0.0-M26%7Cjar
* http://search.maven.org/#artifactdetails%7Cio.sphere.sdk.jvm%7Csphere-convenience%7C1.0.0-M26%7Cjar

### Modules
* `sphere-java-client`: alias for sphere-java-client-ahc-2_0
* `sphere-java-client-apache-async`: uses Apache HTTP client
* `sphere-java-client-ahc-1_8`: uses async HTTP client 1.8
* `sphere-java-client-ahc-1_9`: uses async HTTP client 1.9 (AHC 1.9 is incompatible to AHC 1.8)
* `sphere-java-client-ahc-2_0`: uses async HTTP client 2.0 (do not mix it with the other AHC modules)
* `sphere-models`: models which do not depend to a client implementation

### Play/Scala SDK with SBT

see https://github.com/sphereio/sphere-jvm-sdk-scala-add-ons

### JVM SDK Contrib

Useful code from external developers

* https://github.com/sphereio/commercetools-jvm-sdk-contrib

### Experimental Add-Ons
* ProductImageUploadCommand from http://dev.sphere.io/http-api-projects-products.html#upload-product-image

see https://github.com/sphereio/sphere-jvm-sdk-experimental-java-add-ons

### Snapshots

* experimental, risk of breaking changes, no support, snapshots can vanish
* look at https://oss.sonatype.org/content/repositories/snapshots/io/sphere/sdk/jvm/models/ for new snapshots, they are ordered by next version and date
* add resolver "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

## Short-term roadmap
* https://github.com/sphereio/sphere-jvm-sdk/milestones
* https://waffle.io/sphereio/sphere-jvm-sdk

## Open Source Examples
* [Sunrise Java](https://github.com/sphereio/sphere-sunrise) - a shop using Play Framework 2.x with Handlebars.java as template engine, Google Guice for DI
* [Donut](https://github.com/commercetools/sphere-donut) - single product subscription shop example with Play Framework 2.x and Twirl (Plays default) as template engine
* [commercetools Spring MVC archetype](https://github.com/sphereio/commercetools-spring-mvc-archetype) - template integrating the SDK with Spring DI and Spring MVC and showing just some products, thymeleaf template engine

## Stability

1. Experimental features in the API are also experimental features of the SDK.
    * this includes for example
        * cart discounts
        * custom fields
        * discount codes
        * types endpoint/custom fields
        * payment
    * the stable and unstable features can be mixed
        * a cart contains the reference to its discounts
1. The dependencies will only be updated in the next major version to improve stability. Of course, if bugs in libraries *occur*, we may need to update.
1. JVM SDK test dependencies and build tools can be updated because they don't affect the production code.
1. The JVM SDK has an abstract HTTP client layer so old or new http client versions can be used.
1. class names containing the word "Experimental" can change at any time.
1. order import is experimental
1. getters of draft objects might change since the API allows in upgrades different objects

## Executing integration tests

1. create a file "integrationtest.properties" inside the project root
1. fill it with the credentials of a new empty commercetools project which is for testing;

```
projectKey=YOUR project key without quotes
clientId=YOUR client id without quotes
clientSecret=YOUR client secret without quotes
apiUrl=https://api.sphere.io
authUrl=https://auth.sphere.io
```

1. use `sbt it:test` to execute all integration tests
1. use `sbt "it:testOnly *Products*"` to execute all integration test classes which have "Product" in the class name
1. for running the unit tests use `sbt test`
1. alternatively use your IDE to execute the tests
    * Eclipse: generate the project files with `sbt "eclipse with-source=true"`, see also https://www.playframework.com/documentation/2.4.x/IDE
    * IntelliJ IDEA has a SBT plugin
    

[](definitions for the top badges)

[travis]:https://travis-ci.org/sphereio/sphere-jvm-sdk
[travis img]:https://travis-ci.org/sphereio/sphere-jvm-sdk.svg?branch=master

[maven]:http://search.maven.org/#search|gav|1|g:"io.sphere.sdk.jvm"%20AND%20a:"sphere-jvm-sdk"
[maven img]:https://maven-badges.herokuapp.com/maven-central/io.sphere.sdk.jvm/sphere-jvm-sdk/badge.svg

[license]:LICENSE.md
[license img]:https://img.shields.io/badge/License-Apache%202-blue.svg
