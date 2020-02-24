package io.sphere.sdk.meta;

import io.sphere.sdk.client.SphereRequest;
import org.asynchttpclient.AsyncHttpClient;

/**
 <h3 id=blocking-client>Blocking Client</h3>
 <h4 id=usage-blocking-client>Usage of the Blocking Client</h4>
 In a lot of frameworks there is no support for asynchronous execution and so it is necessary to wait for the responses.

 <p>The {@link io.sphere.sdk.client.BlockingSphereClient} can wait for responses with {@link io.sphere.sdk.client.BlockingSphereClient#executeBlocking(SphereRequest)}. This method enforces a timeout for resilience
 and throws directly {@link io.sphere.sdk.models.SphereException}s. For creation refer to {@link io.sphere.sdk.client.BlockingSphereClient}.</p>

 {@include.example io.sphere.sdk.meta.BlockingClientValueGetDemo}

 <h4 id=bad-blocking-example>Examples of bad usages of the default client in a blocking manner</h4>
 <p>Here follow some examples how to <strong>NOT</strong> use the {@link io.sphere.sdk.client.SphereClient}:</p>

 <p>This examples lacks a timeout, so in an unfortunate case the thread is blocked forever:</p>

 {@include.example io.sphere.sdk.client.WrongBlockingWithJoin}

 <p>The following examples are even worse because they lack a timeout and they need to deal with checked exceptions:</p>

 {@include.example io.sphere.sdk.client.WrongBlockingWithGetAndSwallowing}


 {@include.example io.sphere.sdk.client.WrongBlockingWithGetAndSignature}



 <h3 id=timeout-client>Timeout Client</h3>
 See {@link io.sphere.sdk.client.TimeoutSphereClientDecorator}.

 <h3 id=retry-client>Retry Client</h3>
 See {@link io.sphere.sdk.client.RetrySphereClientDecorator}.

 <h3 id=queue-client>Limit the amount of parallel requests</h3>
 See {@link io.sphere.sdk.client.QueueSphereClientDecorator}.

 <h3 id=add-functionality-to-the-client>Using design patterns to add functionality to the clients</h3>
 <p>The clients are interfaces which have a default implementation (add "Impl" to the interface name).<br>
 This enables you to use the <a href="https://en.wikipedia.org/wiki/Decorator_pattern">decorator pattern</a> to configure the cross concern behaviour of the client:</p>

 <ul>
 <li>setup recover mechanisms like returning empty lists or retry the request</li>
 <li>log events</li>
 <li>set timeouts (depending on the future implementation)</li>
 <li>return fake answers for tests</li>
 <li>configure throttling.</li>
 </ul>

 <p>The following listing shows a pimped client which updates metrics on responses, retries commands and sets default values:</p>

 {@include.example io.sphere.sdk.client.WrappedClientDemo}

 <h3 id=configure-underlying-http-client>Configure the underlying http client.</h3>

 {@link io.sphere.sdk.http.HttpClient} is an abstraction to perform http requests.

 <p>{@code io.sphere.sdk.http.AsyncHttpClientAdapter#of(AsyncHttpClient)} wraps an {@code AsyncHttpClient} as {@link io.sphere.sdk.http.HttpClient}.</p>

 <p>The following example creates a configured HTTP client and initializes a {@link io.sphere.sdk.client.SphereClient} with it.</p>

  {@include.example io.sphere.sdk.client.CustomClientConfigDemoIntegrationTest}

 <p>For configuration parameters refer to <a href="https://github.com/AsyncHttpClient/async-http-client">github.com/AsyncHttpClient/async-http-client</a>.</p>

  */
public final class SphereClientTuningDocumentation {
    private SphereClientTuningDocumentation() {
    }
}
