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
* (since Feb 26, 2016 new link) [<strong>Javadoc</strong>](http://sphereio.github.io/sphere-jvm-sdk/apidocs/index.html), there you find also code snippets and insights
    * [Getting Started](http://sphereio.github.io/sphere-jvm-sdk/apidocs/io/sphere/sdk/meta/GettingStarted.html)
    * [Release Notes](http://sphereio.github.io/sphere-jvm-sdk/apidocs/io/sphere/sdk/meta/ReleaseNotes.html)
    * [Contributing](http://sphereio.github.io/sphere-jvm-sdk/apidocs/io/sphere/sdk/meta/ContributorDocumentation.html)
 
## Installation

### Java SDK with Maven

````xml
<dependency>
  <groupId>com.commercetools.sdk.jvm.core</groupId>
  <artifactId>commercetools-models</artifactId>
  <version>1.0.0-RC1</version>
</dependency>
<dependency>
  <groupId>com.commercetools.sdk.jvm.core</groupId>
  <artifactId>commercetools-java-client</artifactId>
  <version>1.0.0-RC1</version>
</dependency>

<!-- experimental -->
<dependency>
  <groupId>com.commercetools.sdk.jvm.core</groupId>
  <artifactId>commercetools-convenience</artifactId>
  <version>1.0.0-RC1</version>
</dependency>
````
* http://search.maven.org/#artifactdetails%7Cicom.commercetools.sdk.jvm.core%7Ccommercetools-models%7C1.0.0-RC1%7Cjar
* http://search.maven.org/#artifactdetails%7Ccom.commercetools.sdk.jvm.core%7Ccommercetools-java-client%7C1.0.0-RC1%7Cjar
* http://search.maven.org/#artifactdetails%7Ccom.commercetools.sdk.jvm.core%7Ccommercetools-convenience%7C1.0.0-RC1%7Cjar

### Modules
* `commercetools-java-client`: alias for sphere-java-client-ahc-2_0
* `commercetools-java-client-apache-async`: uses Apache HTTP client
* `commercetools-java-client-ahc-1_8`: uses async HTTP client 1.8
* `commercetools-java-client-ahc-1_9`: uses async HTTP client 1.9 (AHC 1.9 is incompatible to AHC 1.8)
* `commercetools-java-client-ahc-2_0`: uses async HTTP client 2.0 (do not mix it with the other AHC modules)
* `commercetools-models`: models which do not depend to a client implementation

### Play/Scala SDK with SBT

see https://github.com/sphereio/sphere-jvm-sdk-scala-add-ons

### reactive streams
* since RC1-SNAPSHOT, formerly in sphere-convenience-module
* https://github.com/sphereio/commercetools-jvm-sdk-reactive-streams-addons

### JVM SDK Contrib

Useful code from external developers

* https://github.com/sphereio/commercetools-jvm-sdk-contrib

### Experimental Add-Ons
* ProductImageUploadCommand from http://dev.sphere.io/http-api-projects-products.html#upload-product-image

see https://github.com/sphereio/sphere-jvm-sdk-experimental-java-add-ons

## Short-term roadmap
* https://github.com/sphereio/sphere-jvm-sdk/milestones
* https://waffle.io/sphereio/sphere-jvm-sdk

## Open Source Examples
* [Sunrise Java](https://github.com/sphereio/commercetools-sunrise-java) - a shop using Play Framework 2.x with Handlebars.java as template engine, Google Guice for DI
* [Donut](https://github.com/commercetools/sphere-donut) - single product subscription shop example with Play Framework 2.x and Twirl (Plays default) as template engine
* [commercetools Spring MVC archetype](https://github.com/sphereio/commercetools-spring-mvc-archetype) - template integrating the SDK with Spring DI and Spring MVC and showing just some products, thymeleaf template engine
* [Reproducer Example](https://github.com/sphereio/commercetools-jvm-sdk-reproducer-example) - a demo which shows how to reproduce errors

## Stability

1. Experimental features in the API are also experimental features of the SDK.
    * this includes for example
        * cart discounts
        * custom fields
        * discount codes
        * types endpoint/custom fields
        * payments
        * nested product attribute
    * the stable and unstable features can be mixed
        * a cart contains the reference to its discounts
1. The dependencies will only be updated in the next major version to improve stability. Of course, if bugs in libraries *occur*, we may need to update.
1. JVM SDK test dependencies and build tools can be updated because they don't affect the production code.
1. The JVM SDK has an abstract HTTP client layer so old or new http client versions can be used.
1. order import is experimental
1. getters of draft objects might change since the API allows in upgrades different objects
1. final classes without public constructors can be transformed into an interface

## Executing integration tests

1. create an API client in the Merchant Center with all available permissions (at the time of writing it is manage_payments manage_my_profile manage_orders view_products view_customers view_payments view_types manage_my_orders manage_types manage_customers manage_products view_orders manage_project)
1. create a file "integrationtest.properties" inside the project root
1. fill it with the credentials of a new empty commercetools project which is for testing;

```
projectKey=YOUR project key without quotes
clientId=YOUR client id without quotes
clientSecret=YOUR client secret without quotes
apiUrl=https://api.sphere.io
authUrl=https://auth.sphere.io
```

1. use `mvn verify` to execute all integration tests
1. use `mvn -Dit.test=*Product* -DfailIfNoTests=false verify` to execute all integration test classes which have "Product" in the class name
1. for running the unit tests use `mvn test`
1. alternatively use your IDE to execute the tests


[](definitions for the top badges)

[travis]:https://travis-ci.org/sphereio/sphere-jvm-sdk
[travis img]:https://travis-ci.org/sphereio/sphere-jvm-sdk.svg?branch=master

[maven]:http://search.maven.org/#search|gav|1|g:"com.commercetools.sdk.jvm.core"%20AND%20a:"commercetools-jvm-sdk"
[maven img]:https://maven-badges.herokuapp.com/maven-central/com.commercetools.sdk.jvm.core/commercetools-jvm-sdk/badge.svg

[license]:LICENSE.md
[license img]:https://img.shields.io/badge/License-Apache%202-blue.svg
