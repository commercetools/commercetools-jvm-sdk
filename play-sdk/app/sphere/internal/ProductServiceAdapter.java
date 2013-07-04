package sphere.internal;

import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.shop.model.Product;
import net.jcip.annotations.Immutable;
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
    private final Locale defaultLocale;

    public ProductServiceAdapter(@Nonnull io.sphere.client.shop.ProductService service, @Nonnull Locale defaultLocale) {
        if (service == null) throw new NullPointerException("service");
        if (defaultLocale == null) throw new NullPointerException("defaultLocale");
        this.service = service;
        this.defaultLocale = defaultLocale;
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

    @Override
    public SearchRequest<Product> all() { return all(defaultLocale); };

    @Override public SearchRequest<Product> filter(Locale locale, FilterExpression filter, FilterExpression... filters) {
        return Async.adapt(service.filter(locale, filter,  filters));
    }

    @Override
    public SearchRequest<Product> filter(FilterExpression filter, FilterExpression... filters) {
        return filter(defaultLocale, filter, filters);
    }

    @Override public SearchRequest<Product> filter(Locale locale, Iterable<FilterExpression> filters) {
        return Async.adapt(service.filter(locale, filters));
    }

    @Override
    public SearchRequest<Product> filter(Iterable<FilterExpression> filters) { return filter(defaultLocale, filters); }
}
