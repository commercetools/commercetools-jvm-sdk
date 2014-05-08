package io.sphere.client;

import com.ning.http.client.listenable.AbstractListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 *
 * @deprecated use http://docs.guava-libraries.googlecode.com/git-history/release/javadoc/com/google/common/util/concurrent/Futures.html#immediateFuture(V) instead
 */
@Deprecated
public class MockListenableFuture<T> extends AbstractListenableFuture<T> {
    private T result;

    public MockListenableFuture(final T result) {
        this.result = result;
        this.done();
    }
    
    public static <T> MockListenableFuture<T> completed(T result) {
        return new MockListenableFuture<T>(result);
    }

    @Override
    public void done() {
        runListeners();
    }
    @Override
    public void abort(Throwable t) {
    }
    @Override
    public void content(T res) {
        this.result = res;
    }
    @Override
    public void touch() {
    }
    @Override
    public boolean getAndSetWriteHeaders(boolean writeHeader) {
        return false;
    }
    @Override
    public boolean getAndSetWriteBody(boolean writeBody) {
        return false;
    }

    @Override
    public boolean cancel(boolean b) {
        return false; 
    }
    @Override
    public boolean isCancelled() {
        return false;
    }
    @Override
    public boolean isDone() {
        return true;
    }
    @Override
    public T get() throws InterruptedException, ExecutionException {
        return result;
    }
    @Override
    public T get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return result;
    }
}
