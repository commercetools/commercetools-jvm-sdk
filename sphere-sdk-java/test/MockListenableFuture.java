package sphere;

import com.ning.http.client.listenable.AbstractListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MockListenableFuture<T> extends AbstractListenableFuture<T> {

    private T result;

    public MockListenableFuture(T result) {
        this.result = result;
    }
    
    public static <T> MockListenableFuture<T> completed(T result) {
        return new MockListenableFuture<T>(result);
    }

    // ListenableFuture
    @Override
    public void done(Callable callable) {
        super.done();
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

    // java.util.concurrent.Future
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
