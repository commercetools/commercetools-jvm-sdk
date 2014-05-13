package io.sphere.internal;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.common.util.concurrent.ListenableFuture;

/** Adapter from AsyncHttpClient ListenableFuture to Guava ListenableFuture. */
public final class ListenableFutureAdapter<V> implements ListenableFuture<V> {
    private final com.ning.http.client.ListenableFuture<V> ahcFuture;

    public ListenableFutureAdapter(com.ning.http.client.ListenableFuture<V> ahcFuture) {
        this.ahcFuture = ahcFuture;
    }

    @Override public void addListener(Runnable r, Executor exec) {
        this.ahcFuture.addListener(r, exec);
    }

    @Override public V get() throws InterruptedException, ExecutionException { return this.ahcFuture.get(); }
    @Override public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.ahcFuture.get(timeout, unit);
    }
    @Override public boolean isDone() { return this.ahcFuture.isDone(); }
    @Override public boolean isCancelled() { return this.ahcFuture.isCancelled(); }
    @Override public boolean cancel(boolean mayInterruptIfRunning) { return this.ahcFuture.cancel(mayInterruptIfRunning); }
}
