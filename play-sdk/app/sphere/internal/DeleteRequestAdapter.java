package sphere.internal;

import com.google.common.base.Optional;
import play.libs.F.Promise;
import sphere.DeleteRequest;
import sphere.FetchRequest;
import sphere.util.Async;

import javax.annotation.Nonnull;

/** Delete with Play-specific async methods. */
public class DeleteRequestAdapter<T> implements DeleteRequest<T> {
    private final io.sphere.client.DeleteRequest<T> request;

    public DeleteRequestAdapter(@Nonnull io.sphere.client.DeleteRequest<T> request) {
        if (request == null) throw new NullPointerException("request");
        this.request = request;
    }

    @Override public Optional<T> execute() {
        return request.execute();
    }

    @Override public Promise<Optional<T>> executeAsync() {
        return Async.asPlayPromise(request.executeAsync());
    }
}
