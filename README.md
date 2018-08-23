# commercetools JVM SDK



<img src="http://dev.commercetools.com/assets/img/CT-logo.svg" width="550px" alt="CT-logo"></img>

[![][travis img]][travis]
[![][maven img]][maven]
[![][snyk img]][snyk]
[![][license img]][license]

The JVM SDK enables developers to use Java 8 methods and objects to communicate with the [commercetools platform](http://www.commercetools.com/) (formerly SPHERE.IO) rather than using plain HTTP calls.
Users gain type-safety, encapsulation, IDE auto completion and an internal domain specific language to discover and formulate valid requests.

## Using the SDK 
* install [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* (since May 19, 2016 new link) [<strong>Javadoc</strong>](http://commercetools.github.io/commercetools-jvm-sdk/apidocs/index.html), there you find also code snippets and insights
    * [Getting Started](http://commercetools.github.io/commercetools-jvm-sdk/apidocs/io/sphere/sdk/meta/GettingStarted.html)
    * [Release Notes](http://commercetools.github.io/commercetools-jvm-sdk/apidocs/io/sphere/sdk/meta/ReleaseNotes.html)
    * [Contributing](http://commercetools.github.io/commercetools-jvm-sdk/apidocs/io/sphere/sdk/meta/ContributorDocumentation.html)
 
## Installation

### Java SDK with Maven

````xml
<dependency>
  <groupId>com.commercetools.sdk.jvm.core</groupId>
  <artifactId>commercetools-models</artifactId>
  <version>1.35.0</version>
</dependency>
<dependency>
  <groupId>com.commercetools.sdk.jvm.core</groupId>
  <artifactId>commercetools-java-client</artifactId>
  <version>1.35.0</version>
</dependency>

<!-- experimental -->
<dependency>
  <groupId>com.commercetools.sdk.jvm.core</groupId>
  <artifactId>commercetools-convenience</artifactId>
  <version>1.35.0</version>
</dependency>
````
* http://search.maven.org/#artifactdetails%7Ccom.commercetools.sdk.jvm.core%7Ccommercetools-models%7C1.35.0%7Cjar
* http://search.maven.org/#artifactdetails%7Ccom.commercetools.sdk.jvm.core%7Ccommercetools-java-client%7C1.35.0%7Cjar
* http://search.maven.org/#artifactdetails%7Ccom.commercetools.sdk.jvm.core%7Ccommercetools-convenience%7C1.35.0%7Cjar

### Modules
* `commercetools-java-client`: alias for commercetools-java-client-ahc-2_0
* `commercetools-java-client-apache-async`: uses Apache HTTP client
* `commercetools-java-client-ahc-1_8`: uses async HTTP client 1.8
* `commercetools-java-client-ahc-1_9`: uses async HTTP client 1.9 (AHC 1.9 is incompatible to AHC 1.8)
* `commercetools-java-client-ahc-2_0`: uses async HTTP client 2.0 (do not mix it with the other AHC modules)
* `commercetools-models`: models which do not depend to a client implementation

### Java SDK with gradle

````
repositories {
    mavenCentral()
}

dependencies {
    def jvmSdkVersion = "1.35.0"
    compile "com.commercetools.sdk.jvm.core:commercetools-models:$jvmSdkVersion"
    compile "com.commercetools.sdk.jvm.core:commercetools-java-client:$jvmSdkVersion"    
    compile "com.commercetools.sdk.jvm.core:commercetools-convenience:$jvmSdkVersion"
}
````

### Play/Scala SDK with SBT

see https://github.com/commercetools/commercetools-jvm-sdk-scala-add-ons

### reactive streams
* https://github.com/commercetools/commercetools-jvm-sdk-reactive-streams-add-ons

### JVM SDK Contrib

Useful code from external developers

* https://github.com/commercetools/commercetools-jvm-sdk-contrib
* contains also ProductImageUploadCommand from http://dev.commercetools.com/http-api-projects-products.html#upload-a-product-image


## Short-term roadmap
* https://github.com/commercetools/commercetools-jvm-sdk/milestones
* https://waffle.io/commercetools/commercetools-jvm-sdk

## Open Source Examples
* [Sunrise Java](https://github.com/commercetools/commercetools-sunrise-java) - a shop using Play Framework 2.x with Handlebars.java as template engine, Google Guice for DI
* [Donut](https://github.com/commercetools/commercetools-donut) - single product subscription shop example with Play Framework 2.x and Twirl (Plays default) as template engine
* [commercetools Spring MVC archetype](https://github.com/commercetools/commercetools-spring-mvc-archetype) - template integrating the SDK with Spring DI and Spring MVC and showing just some products, thymeleaf template engine
* [Reproducer Example](https://github.com/commercetools/commercetools-jvm-sdk-reproducer-example) - a demo which shows how to reproduce errors

## OSGi support

* The JVM SDK is OSGi compatible, the module structure is as follows:
    * Bundle `sdk-http` responsible for http client features, this bundle has the following fragments 
        * Fragment `sdk-htt-apache-async` which provide an implementation of the http clients.
    * Bundle `commercetools-sdk-base` that contains the base types used for the sdk models, this bundle has the following fragments
        * `commercetools-java-client-core`
        * `commercetools-java-client-apache-async` with the previous fragment, it allow to publish a service describing the http client implementation for our API.
        * `commercetools-models` contains a description model of the commercetools backend and the different actions that alows interaction with it.
* A demo test that shows a minimum configuration for use in production in an OSGi setup can be found here: 
[DemoOSGiMinimalConfigTest](https://github.com/commercetools/commercetools-jvm-sdk/blob/master/osgi-support/sdk-osgi-test-campaign/src/test/java/io/sphere/sdk/test/DemoOSGiMinimalConfigTest.java)
 
## Stability

1. Experimental features in the API are also experimental features of the SDK.
    * this includes for example
        * payments
        * nested product attributes
1. The dependencies will only be updated in the next major version to improve stability. Of course, if bugs in libraries *occur*, we may need to update.
1. JVM SDK test dependencies and build tools can be updated because they don't affect the production code.
1. The JVM SDK has an abstract HTTP client layer so old or new http client versions can be used.
1. order import is experimental
1. the search methods with lambda parameters are beta `ProductProjectionSearch.ofCurrent().withQueryFilters(m -> m.categories().id().containsAll(categoryIds1))`
1. getters of draft objects might change since new HTTP API features can introduce polymorphism
1. final classes without public constructors can be transformed into an interface

## Executing integration tests

1. create a NEW API client in the Admin Center (`https://admin.sphere.io/YOUR_PROJECT_KEY/developers/clients`) with all available permissions (at the time of writing it is manage_payments manage_my_profile manage_orders view_products view_customers view_payments view_types manage_my_orders manage_types manage_customers manage_products view_orders manage_project), the by default created client has just `manage_project`
1. in the Admin Center in the "DANGER ZONE" activate the checkbox "ENABLE MESSAGES" within the "Messages Settings"
1. set "de", "de-AT", "en" as languages in the Admin Center
1. set at least one country in the Admin Center
1. create a file "integrationtest.properties" inside the project root
1. fill it with the credentials of a new empty commercetools project which is for testing;

```
projectKey=YOUR project key without quotes
clientId=YOUR client id without quotes
clientSecret=YOUR client secret without quotes
apiUrl=https://api.sphere.io
authUrl=https://auth.sphere.io
```

1. use `./mvnw verify` to execute all integration tests
1. use `./mvnw -Dit.test=*Product* -DfailIfNoTests=false verify` to execute all integration test classes which have "Product" in the class name
1. for running the unit tests use `./mvnw test`
1. alternatively use your IDE to execute the tests
1. for some IDEs like IntelliJ IDEA you need to add the Javac flag "-parameters", then also rebuild the whole project to apply the change


[](definitions for the top badges)

[travis]:https://travis-ci.org/commercetools/commercetools-jvm-sdk
[travis img]:https://travis-ci.org/commercetools/commercetools-jvm-sdk.svg?branch=master

[snyk]:https://snyk.io/test/github/commercetools/commercetools-jvm-sdk
[snyk img]:https://snyk.io/test/github/commercetools/commercetools-jvm-sdk/badge.svg

[maven]:http://search.maven.org/#search|gav|1|g:"com.commercetools.sdk.jvm.core"%20AND%20a:"commercetools-models"
[maven img]:https://maven-badges.herokuapp.com/maven-central/com.commercetools.sdk.jvm.core/commercetools-models/badge.svg

[license]:LICENSE.md
[license img]:https://img.shields.io/badge/License-Apache%202-blue.svg
