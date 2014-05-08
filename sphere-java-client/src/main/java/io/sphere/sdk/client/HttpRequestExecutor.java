package io.sphere.sdk.client;

import com.google.common.util.concurrent.ListenableFuture;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public interface HttpRequestExecutor {
    <T> ListenableFuture<HttpResponse> execute(Requestable<T> request);
}
