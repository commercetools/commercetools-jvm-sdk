package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

class CartInStoreCreateCommandImpl extends MetaModelCreateCommandImpl<Cart, CartInStoreCreateCommand, CartDraft, CartExpansionModel<Cart>> implements CartInStoreCreateCommand {

    CartInStoreCreateCommandImpl(final MetaModelCreateCommandBuilder<Cart, CartInStoreCreateCommand, CartDraft, CartExpansionModel<Cart>> builder) {
        super(builder);
    }

    CartInStoreCreateCommandImpl(final String storeKey, final CartDraft body) {
        super(body, JsonEndpoint.of(Cart.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/carts"), CartExpansionModel.of(), CartInStoreCreateCommandImpl::new);
    }

    @Override
    public CartInStoreCreateCommandImpl withDraft(final CartDraft draft) {
        return new CartInStoreCreateCommandImpl(copyBuilder().draft(draft));
    }

}
