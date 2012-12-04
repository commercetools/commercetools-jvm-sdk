package de.commercetools.internal;

import de.commercetools.internal.request.ProductRequestFactory;
import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.filters.expressions.FilterExpression;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.shop.ProductService;
import de.commercetools.sphere.client.SearchRequest;
import de.commercetools.sphere.client.shop.model.Product;
import static de.commercetools.internal.util.ListUtil.list;

import com.google.common.collect.ImmutableList;
import net.jcip.annotations.Immutable;


@Immutable
public final class ProductServiceImpl extends ProjectScopedAPI implements ProductService {
    private final ProductRequestFactory requestFactory;

    public ProductServiceImpl(ProductRequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    /** {@inheritDoc}  */
    @Override public FetchRequest<Product> byId(String id) {
        return requestFactory.createFetchRequest(endpoints.product(id));
    }

    /** {@inheritDoc}  */
    @Override public FetchRequest<Product> bySlug(String slug) {
        return requestFactory.createFetchRequestBasedOnQuery(endpoints.productBySlug(slug));
    }

    private static final ImmutableList<FilterExpression> noFilters = ImmutableList.of();
    /** {@inheritDoc}  */
    @Override public SearchRequest<Product> all() {
        return filtered(noFilters);
    }

    /** {@inheritDoc}  */
    @Override public SearchRequest<Product> filtered(FilterExpression filter, FilterExpression... filters) {
        return filtered(list(filter, filters));
    }

    /** {@inheritDoc}  */
    @Override public SearchRequest<Product> filtered(Iterable<FilterExpression> filters) {
        return requestFactory.createSearchRequest(endpoints.productSearch(), filters);
    }
}