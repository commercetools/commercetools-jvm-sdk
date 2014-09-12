package io.sphere.sdk.client;

import io.sphere.sdk.http.ClientRequest;
import play.libs.F;

import java.io.Closeable;

public interface PlayJavaClient extends Closeable {

    <T> F.Promise<T> execute(final ClientRequest<T> clientRequest);

    void close();
}
