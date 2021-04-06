package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartEndpoint;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslBuilder;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslImpl;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Versioned;

import java.util.List;
import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

class CartInStoreUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<Cart, CartInStoreUpdateCommand, CartExpansionModel<Cart>> implements CartInStoreUpdateCommand {

    CartInStoreUpdateCommandImpl(final String storeKey, final Versioned<Cart> versioned, final List<? extends UpdateAction<Cart>> updateActions) {
        super(versioned, updateActions, JsonEndpoint.of(Cart.typeReference(), "/in-store/key=" + urlEncode(storeKey) + CartEndpoint.ENDPOINT.endpoint()), CartInStoreUpdateCommandImpl::new, CartExpansionModel.of());
    }

    CartInStoreUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<Cart, CartInStoreUpdateCommand, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
}
