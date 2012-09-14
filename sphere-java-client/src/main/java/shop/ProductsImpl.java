package de.commercetools.sphere.client.shop;

import org.codehaus.jackson.type.TypeReference;

import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.shop.model.Product;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.util.RequestBuilderFactory;
import de.commercetools.sphere.client.util.SearchRequestBuilder;
import de.commercetools.sphere.client.util.SearchRequestBuilderFactory;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.model.SearchResult;

public class ProductsImpl extends ProjectScopedAPI implements Products {

    private RequestBuilderFactory requestBuilderFactory;
    private SearchRequestBuilderFactory searchRequestBuilderFactory;

    public ProductsImpl(
            RequestBuilderFactory requestBuilderFactory,
            SearchRequestBuilderFactory searchRequestBuilderFactory,
            ProjectEndpoints endpoints,
            ClientCredentials credentials) {
        super(credentials, endpoints);
        this.requestBuilderFactory = requestBuilderFactory;
        this.searchRequestBuilderFactory = searchRequestBuilderFactory;
    }

    /** {@inheritDoc}  */
    public RequestBuilder<Product> byId(String id) {
        return requestBuilderFactory.create(endpoints.product(id), credentials, new TypeReference<Product>() {});
    }

    /** {@inheritDoc}  */
    public RequestBuilder<QueryResult<Product>> all() {
        return requestBuilderFactory.create(endpoints.products(), credentials, new TypeReference<QueryResult<Product>>() {});
    }

    /** {@inheritDoc}  */
    public SearchRequestBuilder<Product> search(String fullTextQuery) {
        return searchRequestBuilderFactory.create(fullTextQuery, endpoints.productSearch(), credentials, new TypeReference<SearchResult<Product>>() {});
    }

    /** {@inheritDoc}  */
    public SearchRequestBuilder<Product> search() {
        return search("");
    }
}
