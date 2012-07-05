package de.commercetools.sphere.client;

import com.ning.http.client.AsyncHttpClient;

@net.jcip.annotations.ThreadSafe
final public class SphereAppClient implements SphereClient {
    private final AsyncHttpClient httpClient;
    private final SphereAppClientConfig config;

    public SphereAppClient(AsyncHttpClient httpClient, SphereAppClientConfig config) {
        this.httpClient = httpClient;
        this.config = config;
    }

    @Override public SphereAppClientConfig getConfig() { return this.config; }
}
