package sphere.internal;

import io.sphere.client.ProductSort;
import io.sphere.client.facets.expressions.FacetExpression;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.model.SearchResult;
import sphere.SearchRequest;
import sphere.util.Async;
import play.libs.F.Promise;

import javax.annotation.Nonnull;

/** SearchRequest with Play-specific async methods. */
public class SearchRequestAdapter<T> implements SearchRequest<T> {
    private final io.sphere.client.SearchRequest<T> request;
    public SearchRequestAdapter(@Nonnull io.sphere.client.SearchRequest<T> request) {
        if (request == null) throw new NullPointerException("request");
        this.request = request;
    }

    @Override public SearchResult<T> fetch() {
        return request.fetch();
    }

    @Override public Promise<SearchResult<T>> fetchAsync() {
        return Async.asPlayPromise(request.fetchAsync());
    }

    @Override public SearchRequest<T> page(int page) {
        request.page(page);
        return this;
    }

    @Override public SearchRequest<T> pageSize(int pageSize) {
        request.pageSize(pageSize);
        return this;
    }

    @Override public SearchRequest<T> filter(FilterExpression filter, FilterExpression... filters) {
        request.filter(filter, filters);
        return this;
    }

    @Override public SearchRequest<T> filter(Iterable<FilterExpression> filters) {
        request.filter(filters);
        return this;
    }

    @Override public SearchRequest<T> facet(FacetExpression facet, FacetExpression... facets) {
        request.facet(facet, facets);
        return this;
    }

    @Override public SearchRequest<T> facet(Iterable<FacetExpression> facets) {
        request.facet(facets);
        return this;
    }

    @Override public SearchRequest<T> sort(ProductSort sort) {
        request.sort(sort);
        return this;
    }
}
