package de.commercetools.internal;

import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.shop.Products;
import de.commercetools.sphere.client.shop.model.Product;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.util.SearchRequestBuilder;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.model.SearchResult;
import org.codehaus.jackson.type.TypeReference;
import net.jcip.annotations.Immutable;

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

    /** {@inheritDoc}  */
    public RequestBuilder<QueryResult<Product>> all() {
        return requestFactory.createQueryRequest(endpoints.products(), new TypeReference<QueryResult<Product>>() {});
    }

    /** {@inheritDoc}  */
    public SearchRequestBuilder<Product> search(String fullTextQuery) {
        return requestFactory.createSearchRequest(fullTextQuery, endpoints.productSearch(), new TypeReference<SearchResult<Product>>() {});
    }

    /** {@inheritDoc}  */
    public SearchRequestBuilder<Product> search() {
        return search("");
    }
}