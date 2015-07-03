package io.sphere.sdk.shippingmethods.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.shippingmethods.ShippingMethod;

import java.util.List;

import static io.sphere.sdk.http.HttpMethod.GET;

/**
 * Retrieves all the shipping methods that can ship to the shipping address of the given cart.
 *
 * {@include.example io.sphere.sdk.shippingmethods.queries.GetShippingMethodsByCartTest#execution()}
 */
public class ShippingMethodsByCartGet extends SphereRequestBase implements SphereRequest<List<ShippingMethod>> {
    private final String cartId;

    private ShippingMethodsByCartGet(final String cartId) {
        this.cartId = cartId;
    }

    @Override
    public List<ShippingMethod> deserialize(final HttpResponse httpResponse) {
        return resultMapperOf(new TypeReference<List<ShippingMethod>>() {
            @Override
            public String toString() {
                return "TypeReference<List<ShippingMethod>>";
            }
        }).apply(httpResponse);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(GET, "/shipping-methods?cartId=" + cartId);
    }

    public static ShippingMethodsByCartGet of(final Referenceable<Cart> cart) {
        return of(cart.toReference().getId());
    }

    public static ShippingMethodsByCartGet of(final String cartId) {
        return new ShippingMethodsByCartGet(cartId);
    }
}
