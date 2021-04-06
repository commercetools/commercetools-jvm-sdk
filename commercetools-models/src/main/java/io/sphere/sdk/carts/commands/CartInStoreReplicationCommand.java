package io.sphere.sdk.carts.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartEndpoint;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.json.SphereJsonUtils;

import static io.sphere.sdk.http.HttpMethod.POST;
import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

/**
 * Creates a new cart based on an existing cart or order.
 */
public final class CartInStoreReplicationCommand extends CommandImpl<Cart> {


    final CartReplicationDraft cartReplicationDraft;

    final String storeKey;


    private CartInStoreReplicationCommand(final String storeKey, final CartReplicationDraft cartReplicationDraft){
        this.cartReplicationDraft = cartReplicationDraft;
        this.storeKey = storeKey;
    }

    public static CartInStoreReplicationCommand of(final String storeKey, final CartReplicationDraft cartReplicationDraft){
        return new CartInStoreReplicationCommand(storeKey, cartReplicationDraft);
    }


    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(Cart.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/in-store/key=" + urlEncode(storeKey) + CartEndpoint.ENDPOINT.endpoint() + "/replicate", SphereJsonUtils.toJsonString(cartReplicationDraft));
    }

    public CartReplicationDraft getCartReplicationDraft() {
        return cartReplicationDraft;
    }
}
