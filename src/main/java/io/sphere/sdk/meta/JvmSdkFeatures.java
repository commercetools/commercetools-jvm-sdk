package io.sphere.sdk.meta;

import io.sphere.sdk.models.Base;

/**

<h3>Embracing Java 8</h3>
 <p>The SDK API uses:</p>
 <ul>
    <li>{@link java.util.concurrent.CompletableFuture}</li>
    <li>{@link java.util.Optional}</li>
    <li>Java Date API: {@link java.time.Instant}, {@link java.time.LocalDate} and {@link java.time.LocalTime}</li>
    <li>{@link java.util.function.Function}</li>
 </ul>

<h3>Good defaults for toString(), equals() and hashCode()</h3>
<p>SDK implementation classes extends {@link Base} which provides default implementations for the methods by using
 reflection following the suggestions of
 <a href="http://www.oracle.com/technetwork/java/effectivejava-136174.html">Effective Java</a>.</p>

<h3>Domain models are immutable</h3>
<p>Domain models are no plain old Java objects, since the client does not poses control over them but needs to send
 update commands to SPHERE.IO, so setters would pretend a functionality which is not possible.</p>
<p>Clients for <em>other</em> cloud services provide setters and synchronize the model in the background. This approach still
 blocks the caller thread and makes it hard to impossible to tune error handling, timeouts and recover strategies.
 Our approach makes it explicit, that an operation can be performed in the background, takes time and might fail.</p>

 <h3>Domain models are interfaces</h3>
<p>Since domain models are interfaces, you can use them in design patterns or add convenience methods.</p>

 <h3>Testability</h3>
 <p>The clients and the models are interfaces, so they can be replaced with test doubles.
 In addition the SDK provides builders and JSON converters to create models for unit tests.</p>

 <h3>Domain specific languages to create requests</h3>
 <p>For example {@link io.sphere.sdk.queries.QueryDsl} helps to formulate valid queries and discover
 for which attributes can be in which way queried and sorted.</p>

 */
public final class JvmSdkFeatures extends Base {
    private JvmSdkFeatures() {
    }
}
