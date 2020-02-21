package io.sphere.sdk.meta;

import io.sphere.sdk.models.Base;

/**

<h3>Embracing Java 8</h3>
 <p>The SDK API uses:</p>
 <ul>
    <li>{@link java.util.concurrent.CompletionStage}</li>
    <li>{@link java.util.Optional}</li>
    <li>Java Date API: {@link java.time.ZonedDateTime}, {@link java.time.LocalDate} and {@link java.time.LocalTime}</li>
    <li>{@link java.util.function.Function}</li>
 </ul>

<h3>Good defaults for toString(), equals() and hashCode()</h3>
<p>The SDK's implementation classes extend {@link Base} which provides default implementations for the methods by using
 reflection following the suggestions of
 <a href="https://www.oracle.com/technetwork/java/effectivejava-136174.html">Effective Java</a>.</p>

<h3>Domain models are immutable</h3>
<p>Domain models are no plain old Java objects since the client does not pose control over them, but needs to send
 update commands to commercetools. Thus setters, as provided by <em>other</em> cloud services are not applicable in the platform.</p>
 <p>The approach to synchronize the model in the background blocks the caller thread and makes it hard to impossible to tune error handling, timeouts and recover strategies.
 Our approach makes it explicit, that an operation can be performed in the background, that the operation takes time and that the operation might fail.</p>

 <h3>Domain models are interfaces</h3>
<p>Since domain models are interfaces you can use them in design patterns of your choice or to add convenience methods.</p>

 <h3>Testability</h3>
 <p>Since the clients and the models are interfaces they can be replaced with test doubles.
 In addition the SDK provides builders and JSON converters to create models for unit tests.</p>

 <h3>Domain specific languages to create requests</h3>
 <p>For example, {@link io.sphere.sdk.queries.QueryDsl} assists in formulating valid queries and to find out which attributes can be used in which way for querying and sorting.</p>

 */
public final class JvmSdkFeatures extends Base {
    private JvmSdkFeatures() {
    }
}
