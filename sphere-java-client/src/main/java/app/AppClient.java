package de.commercetools.sphere.client.app;

import de.commercetools.sphere.client.SphereClient;
import com.ning.http.client.AsyncHttpClient;
import net.jcip.annotations.*;

@ThreadSafe
final public class AppClient implements SphereClient {
    private final AsyncHttpClient httpClient;
    private final AppClientConfig config;

    public AppClient(AsyncHttpClient httpClient, AppClientConfig config) {
        this.httpClient = httpClient;
        this.config = config;
    }

    @Override public AppClientConfig getConfig() { return this.config; }
}
