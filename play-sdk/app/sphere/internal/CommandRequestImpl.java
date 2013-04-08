package sphere.internal;

import play.libs.F.Promise;
import sphere.CommandRequest;
import sphere.util.Async;

import javax.annotation.Nonnull;

/** CommandRequest with Play-specific async methods. */
public class CommandRequestImpl<T> implements CommandRequest<T> {
    private final io.sphere.client.CommandRequest<T> request;
    public CommandRequestImpl(@Nonnull io.sphere.client.CommandRequest<T> request) {
        if (request == null) throw new NullPointerException("request");
        this.request = request;
    }

    @Override public T execute() {
        return request.execute();
    }

    @Override public Promise<T> executeAsync() {
        return Async.asPlayPromise(request.executeAsync());
    }
}
