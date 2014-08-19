package io.sphere.sdk.meta;

import io.sphere.sdk.models.Base;

/**
 <h3>About the clients</h3>
 <p>The client communicates asynchronously with the SPHERE.IO backend via HTTPS.</p>

 <p>The client has a method {@code execute} which takes a request as parameter and returns a future of the response type.</p>

 <p>There are different clients for different future interfaces:</p>

 <table>
 <caption>Clients and future implementations</caption>
 <tr><th>Client</th><th>Future implementation</th></tr>
 <tr><td>{@link io.sphere.sdk.client.JavaClient}</td><td>{@code java.util.concurrent.CompletableFuture}</td></tr>
 <tr><td>{@link io.sphere.sdk.client.ScalaClient}</td><td>{@code scala.concurrent.Future}</td></tr>
 <tr><td>{@link io.sphere.sdk.client.PlayJavaClient}</td><td>{@code play.libs.F.Promise}</td></tr>
 </table>

 <h3>Instantiation</h3>

 <h4>Java 8 client</h4>

 {@include.example example.JavaClientInstantiationExample#instantiate()}

 <h4>Play Java client</h4>


 {@include.example example.PlayJavaClientInstantiationExample#instantiate()}


 <p>For integration tests you can also use directly Typesafe Config to create a client:</p>


 {@include.example example.PlayJavaClientInstantiationExample#forIntegrationTest()}


 <h3>Perform requests</h3>

 <p>A client works on the abstraction level of one HTTP request for one project.
 With one client you can start multiple requests in parallel, it is thread-safe.</p>
 <p>The clients have a method {@link io.sphere.sdk.client.PlayJavaClient#execute(io.sphere.sdk.http.ClientRequest)}, which takes a {@link io.sphere.sdk.http.ClientRequest} as parameter.</p>

 <p>You can create {@link io.sphere.sdk.http.ClientRequest} yourself or use the given ones.
 To find the given ones navigate to {@link io.sphere.sdk.models.DefaultModel} and look for all known subinterfaces,
 these should include {@link io.sphere.sdk.products.Product}, {@link io.sphere.sdk.categories.Category},
 {@link io.sphere.sdk.taxcategories.TaxCategory} and some more.
 The package of these models contain subpackages {@code queries} and {@code commands} which include typical requests like {@link io.sphere.sdk.taxcategories.queries.TaxCategoryQuery}. Example:</p>

 {@include.example example.TaxCategoryQueryExample#exampleQuery()}

 <h3>How to work with asynchronous code</h3>

 <p>For Play Framework {@code F.Promise}s will be mapped into a {@code Result} or other types as described in the
 <a href="http://www.playframework.com/documentation/2.3.x/JavaAsync">Play Framework documentation</a>:</p>

 {@include.example io.sphere.sdk.queries.QueryDemo#clientShowAsyncProcessing()}

 <h3>Using design patterns to add functionality to the clients</h3>
 <p>The clients are interfaces which have a default implementation (add "Impl" to the interface name).<br>
 This enables you to use the <a href="http://en.wikipedia.org/wiki/Decorator_pattern">decorator pattern</a> to configure the cross concern behaviour of the client:</p>

 <ul>
 <li>setup recover mechanisms like returning empty lists or retry the request</li>
 <li>log events</li>
 <li>set timeouts (depending on the future implementation)</li>
 <li>return fake answers for tests</li>
 <li>configure throttling.</li>
 </ul>

 <p>The following listing shows a pimped client which updates metrics on responses, retries commands and sets default values:</p>

 {@include.example io.sphere.sdk.client.WrappedClientDemo}

 <h3>Client test doubles for unit tests</h3>

 <p>Since the clients are interfaces you can implement them to provide test doubles.</p>
 <p>Here are some example to provide fake client responses in tests:</p>

 {@include.example io.sphere.sdk.client.TestsDemo#withInstanceResults()}

 {@include.example io.sphere.sdk.client.TestsDemo#modelInstanceFromJson()}

 {@include.example io.sphere.sdk.client.TestsDemo#withJson()}

 <h3>Conventions</h3>

 <p>Builders are mutable and use setters like {@link io.sphere.sdk.models.AddressBuilder#streetName(String)} and by calling
 them it changes the internal state of the builders.</p>

 <p>Some immutable models contain methods starting with {@code with} such as {@link io.sphere.sdk.models.Address#withEmail(String)}. By calling this method a copy the address will be returned which has the same values of the original address but it has another email address.</p>
 */
public final class GettingStarted extends Base {
    private GettingStarted() {
    }
}
