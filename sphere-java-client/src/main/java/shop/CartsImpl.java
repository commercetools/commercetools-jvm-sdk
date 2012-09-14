package de.commercetools.sphere.client.shop;

import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.internal.RequestFactory;
import de.commercetools.sphere.client.util.RequestBuilderFactory;
import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.util.RequestBuilder;
import org.codehaus.jackson.type.TypeReference;

public class CartsImpl implements Carts {
    private ProjectEndpoints endpoints;
    private ClientCredentials credentials;
    private RequestFactory requestFactory;
    private RequestBuilderFactory requestBuilderFactory;

    public CartsImpl(RequestFactory requestFactory, RequestBuilderFactory requestBuilderFactory, ProjectEndpoints endpoints, ClientCredentials credentials) {
        this.requestFactory = requestFactory;
        this.endpoints = endpoints;
        this.credentials = credentials;
        this.requestBuilderFactory = requestBuilderFactory;
    }

    public RequestBuilder<Cart> byId(String id) {
        return requestBuilderFactory.create(endpoints.carts(id), credentials, new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public Cart addLineItem(String cartId, String productId) {
        requestFactory.createPost(endpoints.lineItems(), credentials);
        return null;
    }

    /** {@inheritDoc}  */
    public ListenableFuture<Cart> addLineItemAsync(String cartId, String productId, int quantity) {
        return null;
    }
}
