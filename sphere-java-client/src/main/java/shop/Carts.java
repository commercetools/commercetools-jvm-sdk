package de.commercetools.sphere.client.shop;

import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.util.RequestBuilder;

/** Sphere HTTP API for working with shopping carts in a given project. */
public interface Carts {
    /** Creates a request builder that finds a cart by id. */
    RequestBuilder<Cart> byId(String id);

    /** Adds a line item to a cart and returns the updated Cart. */
    Cart addLineItem(String cartId, String productId);

    /** Adds a line item to a cart in a non-blocking way and returns a future that provides a notification
     *  when the updated cart comes back from the backend. */
    ListenableFuture<Cart> addLineItemAsync(String cartId, String productId, int quantity);
}
