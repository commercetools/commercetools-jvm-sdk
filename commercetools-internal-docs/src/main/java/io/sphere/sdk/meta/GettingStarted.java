package io.sphere.sdk.meta;

import io.sphere.sdk.models.Base;

/**
 <h3 id=about-clients>About the clients</h3>
 <p>The commercetools platform client communicates asynchronously with the API via HTTPS and it takes care about authentication.</p>
 <p>The client uses Java objects to formulate an HTTP request, performs the request and maps the JSON response into a Java object.
 The resulting Java object is not directly accessible as object, but it is embedded in a Future/Promise for asynchronous programming.
 Since the client is thread-safe you need only one client to perform multiple requests in parallel.</p>

 <p>There are different client flavors for different future implementations:</p>

 <table border="1">
 <caption>Clients and future implementations</caption>
 <tr><th>Client</th><th>Future implementation</th></tr>
 <tr><td>{@link io.sphere.sdk.client.SphereClient} (default)</td><td>{@code java.util.concurrent.CompletionStage}</td></tr>
 <tr><td><a href=https://github.com/commercetools/commercetools-jvm-sdk-scala-add-ons#scala-client>SphereScalaClient</a></td><td>{@code scala.concurrent.Future}</td></tr>
 <tr><td><a href=https://github.com/commercetools/commercetools-jvm-sdk-scala-add-ons>SpherePlayJavaClient</a></td><td>{@code play.libs.F.Promise} (deprecated in Play 2.5.x)</td></tr>
 </table>

 <h3 id=preparation>Preparation</h3>

 <p>Follow the instructions at <a href=https://docs.commercetools.com/getting-started.html>Getting Started</a> to create a CTP project and to retrieve the API credentials needed
 for the following sections.</p>

 <h3 id=instantiation>Instantiation</h3>

 <p id=instantiation-simple>Simple instantiation:</p>
 {@include.example example.JavaClientInstantiationExample}

 <p id=instantiation-blocking>Simple instantiation with blocking client:</p>
 {@include.example example.BlockingJavaClientInstantiationExample}

 <p id=instantiation-blocking>For projects in the USA you should specify in addition to the previous parameters the auth and api urls:</p>
 {@include.example example.JavaClientInstantiationExampleInUSA}

 <p id=instantiation-spring>Spring example</p>

 <pre><code class=java>{@include.file commercetools-internal-docs/src/main/resources/SpringCommercetoolsConfig.java}</code></pre>

 <p id=instantiation-spring-batch>Spring Batch example</p>

 <pre><code class=java>{@include.file commercetools-internal-docs/src/main/resources/SpringBatchCommercetoolsClientConfiguration.java}</code></pre>

 <h3 id=perform-requests>Performing requests</h3>

 <p>A client works on the abstraction level of one HTTP request for one project.
 With one client you can start multiple requests in parallel, it is <strong>thread-safe</strong>.</p>
 <p>The client method {@link io.sphere.sdk.client.SphereClient#execute(io.sphere.sdk.client.SphereRequest)} takes a {@link io.sphere.sdk.client.SphereRequest} as parameter.</p>

 <p>You can create a {@link io.sphere.sdk.client.SphereRequest} yourself or use the predefined ones listed on {@link io.sphere.sdk.meta.SphereResources}.</p>
 <p>Example:</p>

 {@include.example example.TaxCategoryQueryExample#exampleQuery()}

 <h3 id=closing>Closing the client</h3>

 The client holds resources like thread pools and IO connections, so call {@link io.sphere.sdk.client.SphereClient#close()} to release them.

 <h3 id=further-client-infos>Further client information</h3>
 <ul>
 <li>{@link SphereClientTuningDocumentation Tuning the client} (blocking requests, timeouts, rate limiting, automatic error handling)</li>
 <li>{@link io.sphere.sdk.meta.TestingDocumentation Writing unit tests with the client}</li>
 </ul>
 */
public final class GettingStarted extends Base {
    private GettingStarted() {
    }
}
