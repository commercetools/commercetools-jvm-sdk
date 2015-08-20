package io.sphere.sdk.meta;

import com.ning.http.client.AsyncHttpClient;

/**

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

 <h3 id=configure-underlying-http-client>Configure the underlying http client.</h3>

 {@link io.sphere.sdk.http.HttpClient} is an abstraction to perform http requests.

 <p>{@link io.sphere.sdk.http.AsyncHttpClientAdapter#of(AsyncHttpClient)} wraps a ning {@link AsyncHttpClient} as {@link io.sphere.sdk.http.HttpClient}.</p>

 <p>The following example creates a configured HTTP client and initializes a {@link io.sphere.sdk.client.SphereClient} with it.</p>

  {@include.example io.sphere.sdk.client.CustomClientConfigDemoTest}

 <p>For configuration parameters refer to <a href="https://github.com/AsyncHttpClient/async-http-client">github.com/AsyncHttpClient/async-http-client</a>.</p>

  */
public class SphereClientTuningDocumentation {
    private SphereClientTuningDocumentation() {
    }
}