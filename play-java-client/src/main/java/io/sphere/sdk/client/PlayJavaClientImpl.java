package io.sphere.sdk.client;

import com.typesafe.config.Config;
import io.sphere.sdk.http.ClientRequest;
import io.sphere.sdk.http.HttpClient;
import play.Configuration;
import play.libs.F;

public class PlayJavaClientImpl implements PlayJavaClient {
    private final ScalaClient scalaClient;

    public PlayJavaClientImpl(final Config config) {
        this(config, new NingAsyncHttpClient(config));
    }

    public PlayJavaClientImpl(final Configuration configuration) {
        this(configuration.underlying(), new NingAsyncHttpClient(configuration.underlying()));
    }

    public PlayJavaClientImpl(final Config config, final HttpClient httpClient) {
        this(config, new HttpSphereRequestExecutor(httpClient, config));
    }

    public PlayJavaClientImpl(final Configuration configuration, final HttpClient httpClient) {
        this(configuration.underlying(), new HttpSphereRequestExecutor(httpClient, configuration.underlying()));
    }

    public PlayJavaClientImpl(final Config config, final SphereRequestExecutor sphereRequestExecutor) {
        scalaClient = new ScalaClientImpl(config, sphereRequestExecutor);
    }

    public PlayJavaClientImpl(final Configuration configuration, final SphereRequestExecutor sphereRequestExecutor) {
        scalaClient = new ScalaClientImpl(configuration.underlying(), sphereRequestExecutor);
    }

    @Override
    public <T> F.Promise<T> execute(final ClientRequest<T> clientRequest) {
        return F.Promise.<T>wrap(scalaClient.execute(clientRequest));
    }

    @Override
    public void close() {
        scalaClient.close();
    }

}
