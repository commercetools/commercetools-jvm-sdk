package sphere.internal;

import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.shop.model.Product;
import net.jcip.annotations.Immutable;
import sphere.FetchRequest;
import sphere.ProductService;
import sphere.SearchRequest;

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
        return new FetchRequestAdapter<Product>(service.byId(id));
    }

    @Override public FetchRequest<Product> bySlug(String slug) {
        return new FetchRequestAdapter<Product>(service.bySlug(slug));
    }

    @Override public SearchRequest<Product> all() {
        return new SearchRequestAdapter<Product>(service.all());
    }

    @Override public SearchRequest<Product> filter(FilterExpression filter, FilterExpression... filters) {
        return new SearchRequestAdapter<Product>(service.filter(filter,  filters));
    }

    @Override public SearchRequest<Product> filter(Iterable<FilterExpression> filters) {
        return new SearchRequestAdapter<Product>(service.filter(filters));
    }
}
