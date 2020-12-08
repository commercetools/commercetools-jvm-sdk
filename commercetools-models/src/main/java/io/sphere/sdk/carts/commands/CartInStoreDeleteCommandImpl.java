package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class CartInStoreDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<Cart, CartInStoreDeleteCommand, CartExpansionModel<Cart>> implements CartInStoreDeleteCommand {

    CartInStoreDeleteCommandImpl(final String storeKey, final Versioned<Cart> versioned, final boolean eraseData) {
        super(versioned,eraseData, JsonEndpoint.of(Cart.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/carts"), CartExpansionModel.of(), CartInStoreDeleteCommandImpl::new);
    }

    CartInStoreDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<Cart, CartInStoreDeleteCommand, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
}
