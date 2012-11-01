package de.commercetools.internal;

import de.commercetools.internal.request.ProductRequestFactory;
import de.commercetools.sphere.client.filters.expressions.FilterExpression;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.shop.CategoryTree;
import de.commercetools.sphere.client.shop.Products;
import de.commercetools.sphere.client.model.products.BackendProduct;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.SearchRequest;
import de.commercetools.sphere.client.model.SearchResult;
import com.google.common.collect.ImmutableList;
import de.commercetools.sphere.client.shop.model.Product;
import org.codehaus.jackson.type.TypeReference;
import net.jcip.annotations.Immutable;

import static de.commercetools.internal.util.SearchUtil.list;

@Immutable
public final class ProductsImpl extends ProjectScopedAPI implements Products {
    private final ProductRequestFactory requestFactory;

    public ProductsImpl(ProductRequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    /** {@inheritDoc}  */
    public QueryRequest<Product> byId(String id) {
        return requestFactory.createQueryRequest(endpoints.product(id));
    }

    private static final ImmutableList<FilterExpression> noFilters = ImmutableList.<FilterExpression>of();
    /** {@inheritDoc}  */
    public SearchRequest<Product> all() {
        return filtered(noFilters);
    }

    /** {@inheritDoc}  */
    public SearchRequest<Product> filtered(FilterExpression filter, FilterExpression... filters) {
        return filtered(list(filter, filters));
    }

    /** {@inheritDoc}  */
    public SearchRequest<Product> filtered(Iterable<FilterExpression> filters) {
        return requestFactory.createSearchRequest(endpoints.productSearch(), filters);
    }
}