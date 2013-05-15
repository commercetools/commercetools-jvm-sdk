package io.sphere.internal;

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

@Immutable
public final class ProductServiceImpl extends ProjectScopedAPI implements ProductService {
    private final ProductRequestFactory requestFactory;
    private ApiMode apiMode;

    public ProductServiceImpl(ProductRequestFactory requestFactory, ApiMode apiMode, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
        this.apiMode = apiMode;
    }

    @Override public FetchRequest<Product> byId(String id) {
        return requestFactory.createFetchRequest(endpoints.products.byId(id), this.apiMode);
    }

    @Override public FetchRequest<Product> bySlug(String slug) {
        return requestFactory.createFetchRequestBasedOnQuery(endpoints.products.bySlug(slug), this.apiMode);
    }

    private static final ImmutableList<FilterExpression> noFilters = ImmutableList.of();
    @Override public SearchRequest<Product> all() {
        return filter(noFilters);
    }

    @Override public SearchRequest<Product> filter(FilterExpression filter, FilterExpression... filters) {
        return filter(list(filter, filters));
    }

    @Override public SearchRequest<Product> filter(Iterable<FilterExpression> filters) {
        return requestFactory.createSearchRequest(endpoints.products.search(), this.apiMode, filters);
    }
}
