# Sphere Play SDK

[![Build Status](https://travis-ci.org/commercetools/sphere-play-sdk.png)](https://travis-ci.org/commercetools/sphere-play-sdk)

[SPHERE.IO](http://sphere.io) is the first Platform-as-a-Service solution for eCommerce.

The Sphere Play SDK comprises of two Java libraries designed for building shopping applications on top of SPHERE.IO:

* standalone Java client for accessing the [Sphere HTTP APIs](http://dev.sphere.io/HTTP_API.html)
* SDK for building online shops using the [Play Framework](http://www.playframework.com/)

## The SDK in action

We provide some example shop implementations on top of our Play SDK:
* [SPHERE snowflake](http://snowflake.sphere.io) - code at [github.com/commercetools/sphere-snowflake](https://github.com/commercetools/sphere-snowflake)
* [SPHERE fedora](http://fedora.sphere.io) - code at [github.com/commercetools/sphere-fedora](https://github.com/commercetools/sphere-fedora)
* [SPHERE donut](http://www.iwantdonuts.com) - code at [github.com/commercetools/sphere-donut](https://github.com/commercetools/sphere-donut)

## Building
This project is built using `sbt`. If you want to execute the tests run
```
sbt test
```

## Getting started

To use the Play SDK in your Play application, add the following to your build file:

Play 2.1.x:

````scala
libraryDependencies += "io.sphere" %% "sphere-play-sdk" % "0.50.1" withSources()
````

Play 2.2.x: (exclude to prevent that two play jars in the classpath)

````scala
libraryDependencies += "io.sphere" %% "sphere-play-sdk" % "0.50.1" withSources() exclude("play", "play_2.10")
````

To get started quickly, check out our [tutorial](http://dev.sphere.io/Play_SDK.html), which includes creation of a fully functional sample shop.
For detailed information please have a look at the [JavaDoc](http://sdk.sphere.io)

If you want to use just the Java client, the Maven dependency is:

````xml
<dependency>
  <groupId>io.sphere</groupId>
  <artifactId>sphere-java-client</artifactId>
  <version>0.50.1</version>
</dependency>
````

These artifacts are deployed to Maven Central.

## License

The Sphere Play SDK is released under the
[Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html).

## Dependencies

This project uses the following open-source libraries:

- [AsyncHttpClient](https://github.com/AsyncHttpClient/async-http-client)
- [Guava](https://code.google.com/p/guava-libraries/)
- [Joda time](http://joda-time.sourceforge.net/)
- [Jackson](http://jackson.codehaus.org/)
- [Apache Commons Codec](http://commons.apache.org/proper/commons-codec/)
- [nv-i18n](https://github.com/TakahikoKawasaki/nv-i18n)

These libraries are all also released under the Apache 2.0 License.

## Using the Java client

The first step to using the Sphere Java client is to create an instance of the `SphereClient`. The client has to be configured
to access your project using the credentials you see in the Developers / API clients section of the
[Merchant center](https://admin.sphere.io).

```java
SphereClient sphere = SphereClient.create(
    new SphereClientConfig.Builder("my-project-key", "my-client-id", "my-client-secret", Locale.ENGLISH).build());
```

### Services

The `SphereClient` provides a set of services, such as `products()`, `customers()`, `orders()` and others. As you would
expect, the `products()` service provides methods to access Products inside your Sphere project, the `customers()`
service lets you create and query Customers, and so on.

### Types of requests

By calling `sphere.products().byId(id).fetch()` you are fetching a Product from the Sphere backend. The `.fetch()` is there
to make it more obvious to the reader that this does a remote call, since the latency to the Sphere backend is not negligible.

So what is the return type of `sphere.products().byId(id)` then? It is a `FetchRequest<Product>`, which apart from the
`fetch()` provides a few other methods, such as `fetchAsync()` which fetches the object without blocking the current thread.

Apart from `FetchRequest` there are three other types of request: the `QueryRequest`, `SearchRequest` and `CommandRequest`.

#### FetchRequest

The `FetchRequest` is used to fetch a single object from the Sphere backend. It is returned by methods such as
`sphere.products().byId(id)`. Its two main methods are:

* `fetch()` which fetches the object synchronously (blocks the current thread until a response from the backend arrives).
* `fetchAsync()` which fetches the object asynchronously (returns Guava's `ListenableFuture` and doesn't block _any_ thread).

The result type of both the synchronous and the asynchronous fetch methods in Guava's `Optional<T>` (where `T` is e.g. a `Product`).
`Optional<T>` clearly expresses the expected result that the object might not be found.

Note: We think that using `Optional` is in some cases (such as this one) nicer than using nulls to signal a missing object.
If you prefer, you can convert an `Optional<T>` to an instance of `T` very easily simply by calling the `.orNull()` method.

Here is an example of fetching a Product synchronously:

```java
Product product = sphere.products().byId(id).fetch().orNull();
if (product != null) {
    // work with the product
} else {
    // product was not found
}
```

And here is the asynchronous version:

```java
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.FutureCallback;

Futures.addCallback(sphere.products().byId(id).fetchAsync(), new FutureCallback<Optional<Product>>() {
    @Override public void onSuccess(Optional<Product> p) {
        if (p.isPresent()) {
            Product product = p.get();
            // ...
        } else {
           // product was not found
        }
    }
    @Override public void onFailure(Throwable t) {
        // unexpected error, such as a 500 response from the backend, or connection timeout
    }
});
```

#### QueryRequest

The `QueryRequest` is similar to the `FetchRequest`, only it fetches multiple objects instead of a single one, and it
supports paging through the result set.
It is returned by methods such as `sphere.orders().forCustomer(customerId)`.

Query requests internally use the [Query HTTP APIs](http://sphere.io/dev/HTTP_API.html) of Sphere and the result type is
a `QueryResult`, which contains the collection of returned objects as well as standard paging information (`offset`, `total`).

The key methods of a `QueryRequest` are:

* `fetch()` which fetches the results synchronously (blocks the current thread).
* `fetchAsync()` which fetches the results asynchronously (doesn't block any thread).
* `page(n)` which specifies the page to be fetched.
* `pageSize(size)` which specifies how many results one page represents.

Example:

```java
QueryResult<Review> reviews =
    sphere.reviews().forProduct(productId).page(0).pageSize(20).fetch();
```

#### SearchRequest

The `SearchRequest` is used to access the
[Product search API](http://sphere.io/dev/HTTP_API_Projects_Products.html#product-projections-by-search) of Sphere.
It supports paging through the result set and has a broad range of filtering and faceting capabilities.

The key methods of a `SearchRequest`, besides `page`, `pageSize`, `fetch` and `fetchAsync` are:

* `filter(filterExpr)` which defines filtering criteria.
* `facet(facetExpr)` which requests aggregated information calculated for given product attributes.
* `sort(sort)` which makes the returned products be sorted by price or name.

Examples:

```java
SearchResult<Product> products =
    sphere.products().
        filter(FilterExpr.fulltext("fulltext query")).
        fetch();
```

```java
SearchResult<Product> products =
    sphere.products().
        filter(FilterExpr.stringAttribute("color").equal("blue")).
        sort(ProductSort.price.asc).
        fetch();
```

### Data objects

All objects returned by the fetch, query and search APIs are pure data objects that have no HTTP connection to the backend.

This means you can't do the following:

```java
Review review = sphere.reviews.byId(id).fetch().orNull();
review.update(...);
```

Instead, all the modifications have to go through services. For example:

```java
Review updated = sphere.reviews.update(review.getIdAndVersion(), ...);
```

or

```java
Cart cart = sphere.currentCart().fetch();
Cart updated = sphere.currentCart().addLineItem(...);
```

### Null values

All getters in the data model that are guaranteed to never return null are annotated using `javax.annotation.Nonnull`.
For example:

```java
/** The sum of prices of all line items. */
@Nonnull public Money getTotalPrice()
```

**Important:** Any getters that are not annotated with `Nonnull` can return null.

#### Collections

Getters returning collections (lists, sets) never return null.

```java
/** Categories this product is in. */
@Nonnull public List<Category> getCategories()
```

#### Strings

We also use the `Nonnull` annotation for String fields that are guaranteed to never be empty, such as:

```java
/** Id of the customer who wrote the product review. */
@Nonnull public String getCustomerId()
```

For empty string fields, we prefer returning empty strings over nulls. Nevertheless, checking for empty strings should
always be done using Guava's `Strings.isNullOrEmpty(s)`, never `s.equals("")` or `s == null`.

