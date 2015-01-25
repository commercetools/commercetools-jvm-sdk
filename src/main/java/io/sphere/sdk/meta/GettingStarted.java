package io.sphere.sdk.meta;

import io.sphere.sdk.models.Base;

/**
 <h3 id=about-clients>About the clients</h3>
 <p>The SPHERE.IO client communicates asynchronously with the SPHERE.IO backend via HTTPS.</p>

 <p>There are different client flavors for different future implementations:</p>

 <table>
 <caption>Clients and future implementations</caption>
 <tr><th>Client</th><th>Future implementation</th></tr>
 <tr><td>{@link io.sphere.sdk.client.SphereClient} (default)</td><td>{@code java.util.concurrent.CompletableFuture}</td></tr>
 <tr><td><a href=https://github.com/sphereio/sphere-jvm-sdk-scala-add-ons>SphereScalaClient</a></td><td>{@code scala.concurrent.Future}</td></tr>
 <tr><td><a href=https://github.com/sphereio/sphere-jvm-sdk-scala-add-ons>SpherePlayJavaClient</a></td><td>{@code play.libs.F.Promise}</td></tr>
 </table>

 <h3 id=preparation>Preparation</h3>

 <p>You need to <a href="https://admin.sphere.io">create a project in SPHERE.IO</a>.
 Then you need to go to the "Developers" section and open the tab "API clients".
 There you find the credentials to access the project with the client.</p>

 <p>This is an example for the client credentials page (client secret has been modified):</p>

 <img src="{@docRoot}/documentation-resources/images/merchant-center/project-credentials.png" alt="Merchant Center with project credentials view">

<p>For this example the configuration values are:</p>

 <pre><code>project key: jvm-sdk-dev-1
client ID: ELqF0rykXD2fyS8s-IhIPKfQ
client secret: 222222222222222222222222222222226</code></pre>

 <h3 id=instantiation>Instantiation</h3>

 {@include.example example.JavaClientInstantiationExample}

 <h3 id=perform-requests>Perform requests</h3>

 <p>A client works on the abstraction level of one HTTP request for one project.
 With one client you can start multiple requests in parallel, it is thread-safe.</p>
 <p>The clients have a method {@link io.sphere.sdk.client.SphereClient#execute(io.sphere.sdk.client.ClientRequest)}, which takes a {@link io.sphere.sdk.client.ClientRequest} as parameter.</p>

 <p>You can create {@link io.sphere.sdk.client.ClientRequest} yourself or use the given ones which are listed on {@link io.sphere.sdk.meta.SphereResources}.</p>
 <p>Example:</p>

 {@include.example example.TaxCategoryQueryExample#exampleQuery()}

 <h3 id=add-functionality-to-the-client>Using design patterns to add functionality to the clients</h3>
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

 <h3 id=client-test-doubles>Client test doubles for unit tests</h3>

 <p>Since the clients are interfaces you can implement them to provide test doubles.</p>
 <p>Here are some example to provide fake client responses in tests:</p>

 {@include.example io.sphere.sdk.client.TestsDemo#withInstanceResults()}

 {@include.example io.sphere.sdk.client.TestsDemo#modelInstanceFromJson()}

 {@include.example io.sphere.sdk.client.TestsDemo#withJson()}

 <h3 id=conventions>Conventions</h3>

 <p>Builders are mutable and use setters like {@link io.sphere.sdk.models.AddressBuilder#streetName(String)} and by calling
 them it changes the internal state of the builders.</p>

 <p>Methods starting with the prefix {@code with} such as {@link io.sphere.sdk.models.Address#withEmail(String)} will return a new instance which has the same values of the original object (here address) but it has a modified value, in this case another email address.</p>
 */
public final class GettingStarted extends Base {
    private GettingStarted() {
    }
}
