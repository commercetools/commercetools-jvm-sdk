package de.commercetools.internal;

import de.commercetools.sphere.client.FilterExpression;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.shop.Products;
import de.commercetools.sphere.client.shop.model.Product;
import de.commercetools.sphere.client.RequestBuilder;
import de.commercetools.sphere.client.SearchRequestBuilder;
import de.commercetools.sphere.client.model.SearchResult;
import org.codehaus.jackson.type.TypeReference;
import net.jcip.annotations.Immutable;

import java.util.*;

import static de.commercetools.internal.util.SearchUtil.list;

@Immutable
public final class ProductsImpl extends ProjectScopedAPI implements Products {
    private final RequestFactory requestFactory;

    public ProductsImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    /** {@inheritDoc}  */
    public RequestBuilder<Product> byId(String id) {
        return requestFactory.createQueryRequest(endpoints.product(id), new TypeReference<Product>() {});
    }

    private static final List<FilterExpression> noFilters = Collections.unmodifiableList(new ArrayList<FilterExpression>());
    /** {@inheritDoc}  */
    public SearchRequestBuilder<Product> all() {
        return filtered(noFilters);
    }

    /** {@inheritDoc}  */
    public SearchRequestBuilder<Product> filtered(FilterExpression filter, FilterExpression... filters) {
        return filtered(list(filter, filters));
    }

    /** {@inheritDoc}  */
    public SearchRequestBuilder<Product> filtered(Iterable<FilterExpression> filters) {
        return requestFactory.createSearchRequest(endpoints.productSearch(), filters, new TypeReference<SearchResult<Product>>() {});
    }
}