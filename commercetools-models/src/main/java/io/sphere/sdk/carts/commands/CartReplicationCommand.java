package io.sphere.sdk.carts.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartEndpoint;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.json.SphereJsonUtils;

import static io.sphere.sdk.http.HttpMethod.POST;

/**
 * Creates a new cart based on an existing cart or order.
 */
public final class CartReplicationCommand extends CommandImpl<Cart> {


    final CartReplicationDraft cartReplicationDraft;


    private CartReplicationCommand(final CartReplicationDraft cartReplicationDraft){
        this.cartReplicationDraft = cartReplicationDraft;
    }

    public static CartReplicationCommand of(final CartReplicationDraft cartReplicationDraft){
        return new CartReplicationCommand(cartReplicationDraft);
    }


    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(Cart.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, CartEndpoint.ENDPOINT.endpoint() + "/replicate", SphereJsonUtils.toJsonString(cartReplicationDraft));
    }

    public CartReplicationDraft getCartReplicationDraft() {
        return cartReplicationDraft;
    }
}
