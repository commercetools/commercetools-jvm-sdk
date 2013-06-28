package sphere.internal;

import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.shop.model.Product;
import net.jcip.annotations.Immutable;
import org.springframework.beans.factory.CannotLoadBeanClassException;
import sphere.FetchRequest;
import sphere.ProductService;
import sphere.SearchRequest;
import sphere.util.Async;

import javax.annotation.Nonnull;
import java.util.Locale;

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

    @Override public FetchRequest<Product> bySlug(String slug, Locale locale) {
        return Async.adapt(service.bySlug(slug, locale));
    }

    @Override public SearchRequest<Product> all(Locale locale) {
        return Async.adapt(service.all(locale));
    }

    @Override public SearchRequest<Product> filter(Locale locale, FilterExpression filter, FilterExpression... filters) {
        return Async.adapt(service.filter(locale, filter,  filters));
    }

    @Override public SearchRequest<Product> filter(Locale locale, Iterable<FilterExpression> filters) {
        return Async.adapt(service.filter(locale, filters));
    }
}
