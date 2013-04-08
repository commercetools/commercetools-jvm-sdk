package sphere.internal;

import io.sphere.client.model.QueryResult;
import play.libs.F.Promise;
import sphere.QueryRequest;
import sphere.util.Async;

import javax.annotation.Nonnull;

/** QueryRequest with Play-specific async methods. */
public class QueryRequestImpl<T> implements QueryRequest<T> {
    private final io.sphere.client.QueryRequest<T> request;
    public QueryRequestImpl(@Nonnull io.sphere.client.QueryRequest<T> request) {
        if (request == null) throw new NullPointerException("request");
        this.request = request;
    }

    @Override public QueryResult<T> fetch() {
        return request.fetch();
    }

    @Override public Promise<QueryResult<T>> fetchAsync() {
        return Async.asPlayPromise(request.fetchAsync());
    }

    @Override public QueryRequest<T> page(int page) {
        request.page(page);
        return this;
    }

    @Override public QueryRequest<T> pageSize(int pageSize) {
        request.pageSize(pageSize);
        return this;
    }

    @Override public QueryRequest<T> expand(String... paths) {
        request.expand(paths);
        return this;
    }
}
