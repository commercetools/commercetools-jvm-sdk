package de.commercetools.internal;

import de.commercetools.internal.request.ProductRequestFactory;
import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.filters.expressions.FilterExpression;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.shop.ApiMode;
import de.commercetools.sphere.client.shop.ProductService;
import de.commercetools.sphere.client.SearchRequest;
import de.commercetools.sphere.client.shop.model.Product;
import static de.commercetools.internal.util.ListUtil.list;

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

    /** {@inheritDoc}  */
    @Override public FetchRequest<Product> byId(String id) {
        return requestFactory.createFetchRequest(endpoints.products.byId(id));
    }

    /** {@inheritDoc}  */
    @Override public FetchRequest<Product> bySlug(String slug) {
        return requestFactory.createFetchRequestBasedOnQuery(endpoints.products.bySlug(slug));
    }

    private static final ImmutableList<FilterExpression> noFilters = ImmutableList.of();
    /** {@inheritDoc}  */
    @Override public SearchRequest<Product> all() {
        return filter(noFilters);
    }

    /** {@inheritDoc}  */
    @Override public SearchRequest<Product> filter(FilterExpression filter, FilterExpression... filters) {
        return filter(list(filter, filters));
    }

    /** {@inheritDoc}  */
    @Override public SearchRequest<Product> filter(Iterable<FilterExpression> filters) {
        return requestFactory.createSearchRequest(endpoints.products.search(), this.apiMode, filters);
    }
}