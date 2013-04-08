package sphere.internal;

import com.google.common.base.Optional;
import play.libs.F.Promise;
import sphere.FetchRequest;
import sphere.util.Async;

import javax.annotation.Nonnull;

/** FetchRequest with Play-specific async methods. */
public class FetchRequestAdapter<T> implements FetchRequest<T> {
    private final io.sphere.client.FetchRequest<T> request;
    public FetchRequestAdapter(@Nonnull io.sphere.client.FetchRequest<T> request) {
        if (request == null) throw new NullPointerException("request");
        this.request = request;
    }

    @Override public Optional<T> fetch() {
        return request.fetch();
    }

    @Override public Promise<Optional<T>> fetchAsync() {
        return Async.asPlayPromise(request.fetchAsync());
    }

    @Override public FetchRequest<T> expand(String... paths) {
        request.expand(paths);
        return this;
    }
}
