package sphere.internal;

import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.shop.model.Product;
import net.jcip.annotations.Immutable;
import sphere.FetchRequest;
import sphere.ProductService;
import sphere.SearchRequest;
import sphere.util.Async;

import javax.annotation.Nonnull;

/** ProductService with Play-specific async methods. */
@Immutable
public class ProductServiceAdapter implements ProductService {
    private final io.sphere.client.shop.ProductService service;
    public ProductServiceAdapter(@Nonnull io.sphere.client.shop.ProductService service) {
        if (service == null) throw new NullPointerException("service");
        this.service = service;
    }

    @Override public FetchRequest<Product> byId(String id) {
        return Async.adapt(service.byId(id));
    }

    @Override public FetchRequest<Product> bySlug(String slug) {
        return Async.adapt(service.bySlug(slug));
    }

    @Override public SearchRequest<Product> all() {
        return Async.adapt(service.all());
    }

    @Override public SearchRequest<Product> filter(FilterExpression filter, FilterExpression... filters) {
        return Async.adapt(service.filter(filter,  filters));
    }

    @Override public SearchRequest<Product> filter(Iterable<FilterExpression> filters) {
        return Async.adapt(service.filter(filters));
    }
}
