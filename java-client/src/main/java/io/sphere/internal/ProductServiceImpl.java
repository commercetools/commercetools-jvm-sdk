package io.sphere.internal;

import io.sphere.client.QueryRequest;
import io.sphere.internal.request.ProductRequestFactory;
import io.sphere.client.FetchRequest;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.ProductService;
import io.sphere.client.SearchRequest;
import io.sphere.client.shop.model.Product;
import static io.sphere.internal.util.ListUtil.list;

import com.google.common.collect.ImmutableList;
import net.jcip.annotations.Immutable;

import java.util.Locale;

@Immutable
public final class ProductServiceImpl extends ProjectScopedAPI implements ProductService {
    private final ProductRequestFactory requestFactory;
    private ApiMode apiMode;
    private final Locale defaultLocale;

    public ProductServiceImpl(ProductRequestFactory requestFactory, ApiMode apiMode, ProjectEndpoints endpoints, Locale defaultLocale) {
        super(endpoints);
        this.requestFactory = requestFactory;
        this.apiMode = apiMode;
        this.defaultLocale = defaultLocale;
    }

    @Override public FetchRequest<Product> byId(String id) {
        return requestFactory.createFetchRequest(endpoints.products.byId(id), this.apiMode);
    }

    @Override public FetchRequest<Product> bySlug(Locale locale, String slug) {
        return requestFactory.createFetchRequestBasedOnQuery(endpoints.products.bySlug(locale, slug), this.apiMode);
    }

    @Override public FetchRequest<Product> bySlug(String slug) {
        return bySlug(defaultLocale, slug);
    }

    private static final ImmutableList<FilterExpression> noFilters = ImmutableList.of();
    @Override public SearchRequest<Product> all(Locale locale) {
        return filter(locale, noFilters);
    }

    @Override
    public SearchRequest<Product> all() {
        return all(defaultLocale);
    }

    @Override public SearchRequest<Product> filter(Locale locale, FilterExpression filter, FilterExpression... filters) {
        return filter(locale, list(filter, filters));
    }

    @Override
    public SearchRequest<Product> filter(FilterExpression filter, FilterExpression... filters) {
        return filter(defaultLocale, filter, filters);
    }

    @Override public SearchRequest<Product> filter(Locale locale, Iterable<FilterExpression> filters) {
        return requestFactory.createSearchRequest(endpoints.products.search(), this.apiMode, filters, locale);
    }

    @Override
    public QueryRequest<Product> query() {
        return requestFactory.createQueryRequest(endpoints.products.root(), this.apiMode);
    }
}
