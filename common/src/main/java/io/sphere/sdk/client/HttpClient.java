package io.sphere.sdk.client;

import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.sdk.requests.HttpResponse;
import io.sphere.sdk.requests.Requestable;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public interface HttpClient extends AutoCloseable {
    <T> ListenableFuture<HttpResponse> execute(Requestable request);

    void close();
}
