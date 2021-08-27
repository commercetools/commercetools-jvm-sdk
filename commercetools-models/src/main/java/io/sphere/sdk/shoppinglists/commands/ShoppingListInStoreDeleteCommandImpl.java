package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.expansion.ShoppingListExpansionModel;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class ShoppingListInStoreDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<ShoppingList, ShoppingListInStoreDeleteCommand, ShoppingListExpansionModel<ShoppingList>> implements ShoppingListInStoreDeleteCommand {

    ShoppingListInStoreDeleteCommandImpl(final String storeKey, final Versioned<ShoppingList> versioned, final boolean eraseData) {
        super(versioned,eraseData, JsonEndpoint.of(ShoppingList.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/shopping-lists"), ShoppingListExpansionModel.of(), ShoppingListInStoreDeleteCommandImpl::new);
    }

    ShoppingListInStoreDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<ShoppingList, ShoppingListInStoreDeleteCommand, ShoppingListExpansionModel<ShoppingList>> builder) {
        super(builder);
    }
}
