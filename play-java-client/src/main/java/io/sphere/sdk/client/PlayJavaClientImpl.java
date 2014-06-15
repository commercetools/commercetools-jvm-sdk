package io.sphere.sdk.client;

import com.google.common.base.Optional;
import com.typesafe.config.Config;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
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
    public <I, R> F.Promise<Optional<I>> execute(Fetch<I, R> fetch) {
        return F.Promise.wrap(scalaClient.execute(fetch));
    }

    @Override
    public <I, R> F.Promise<PagedQueryResult<I>> execute(Query<I, R> query) {
        return F.Promise.wrap(scalaClient.execute(query));
    }

    @Override
    public <T, V> F.Promise<T> execute(Command<T, V> command) {
        return F.Promise.wrap(scalaClient.execute(command));
    }

    @Override
    public void close() {
        scalaClient.close();
    }

}
