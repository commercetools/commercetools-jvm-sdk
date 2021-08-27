package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslBuilder;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslImpl;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.expansion.ShoppingListExpansionModel;

import java.util.List;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class ShoppingListInStoreUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<ShoppingList, ShoppingListInStoreUpdateCommand, ShoppingListExpansionModel<ShoppingList>> implements ShoppingListInStoreUpdateCommand {

    ShoppingListInStoreUpdateCommandImpl(final Versioned<ShoppingList> versioned, final String storeKey, final List<? extends UpdateAction<ShoppingList>> updateActions) {
        super(versioned, updateActions, JsonEndpoint.of(ShoppingList.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/shopping-lists"), ShoppingListInStoreUpdateCommandImpl::new, ShoppingListExpansionModel.of());
    }

    ShoppingListInStoreUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<ShoppingList, ShoppingListInStoreUpdateCommand, ShoppingListExpansionModel<ShoppingList>> builder) {
        super(builder);
    }
}
