/**
 * <p>The client communicates asynchronously with the SPHERE.IO backend via HTTPS.</p>
 *
 * <p>The client has a method {@code execute} which takes a request as parameter and returns a future of the response type.</p>
 *
 * <p>There are different clients for different future interfaces:</p>
 *
 * <table>
 *     <caption>Clients and future implementations</caption>
 *     <tr><th>Client</th><th>Future implementation</th></tr>
 *     <tr><td>{@link io.sphere.sdk.client.JavaClient}</td><td>{@code com.google.common.util.concurrent.ListenableFuture}</td></tr>
 *     <tr><td>{@link io.sphere.sdk.client.ScalaClient}</td><td>{@code scala.concurrent.Future}</td></tr>
 *     <tr><td>{@link io.sphere.sdk.client.PlayJavaClient}</td><td>{@code play.libs.F.Promise}</td></tr>
 * </table>
 *
 * <p>For Play Framework {@code F.Promise}s will be mapped into a {@code Reesult} or other types as described in the
 * <a href="http://www.playframework.com/documentation/2.3.x/JavaAsync">Play Framework documentation</a>:</p>
 *
 * {@include.example io.sphere.sdk.queries.QueryDemo#clientShowAsyncProcessing()}
 *
 * <p>The clients are interfaces which have a default implementation (add "Impl" to the interface name).<br>
 * This enables you to use the <a href="http://en.wikipedia.org/wiki/Decorator_pattern">decorator pattern</a> to configure the cross concern behaviour of the client:</p>
 *
 * <ul>
 *     <li>setup recover mechanisms like returning empty lists or retry the request</li>
 *     <li>log events</li>
 *     <li>set timeouts (depending on the future implementation)</li>
 *     <li>return fake answers for tests</li>
 *     <li>configure throttling.</li>
 * </ul>
 *
 * <p>The following listing shows a pimped client which updates metrics on responses, retries commands and sets default values:</p>
 *
 * {@include.example io.sphere.sdk.client.WrappedClientDemo}
 *
 * <p>Since the clients are interfaces you can implement them to provide test doubles.</p>
 * <p>Here are some example to provide fake client responses in tests:</p>
 *
 * {@include.example io.sphere.sdk.client.TestsDemo#withInstanceResults()}
 *
 * {@include.example io.sphere.sdk.client.TestsDemo#modelInstanceFromJson()}
 *
 * {@include.example io.sphere.sdk.client.TestsDemo#withJson()}
 *
 */
package io.sphere.sdk.client;