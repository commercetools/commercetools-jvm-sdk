package io.sphere.sdk.client;

import io.sphere.sdk.requests.ClientRequest;
import play.libs.F;

public interface PlayJavaClient extends AutoCloseable {

    <T> F.Promise<T> execute(final ClientRequest<T> clientRequest);

    void close();
}
