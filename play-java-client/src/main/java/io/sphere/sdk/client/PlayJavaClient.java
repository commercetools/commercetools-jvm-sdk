package io.sphere.sdk.client;

import play.libs.F;

public interface PlayJavaClient {

    <T> F.Promise<T> execute(final ClientRequest<T> clientRequest);

    void close();
}
