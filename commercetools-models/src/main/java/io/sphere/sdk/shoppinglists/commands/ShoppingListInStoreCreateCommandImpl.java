package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.ShoppingListDraft;
import io.sphere.sdk.shoppinglists.expansion.ShoppingListExpansionModel;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class ShoppingListInStoreCreateCommandImpl extends MetaModelCreateCommandImpl<ShoppingList, ShoppingListInStoreCreateCommand, ShoppingListDraft, ShoppingListExpansionModel<ShoppingList>> implements ShoppingListInStoreCreateCommand {

    ShoppingListInStoreCreateCommandImpl(final MetaModelCreateCommandBuilder<ShoppingList, ShoppingListInStoreCreateCommand, ShoppingListDraft, ShoppingListExpansionModel<ShoppingList>> builder) {
        super(builder);
    }

    ShoppingListInStoreCreateCommandImpl(final String storeKey, final ShoppingListDraft body) {
        super(body, JsonEndpoint.of(ShoppingList.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/shopping-lists"), ShoppingListExpansionModel.of(), ShoppingListInStoreCreateCommandImpl::new);
    }

    @Override
    public ShoppingListInStoreCreateCommand withDraft(final ShoppingListDraft draft) {
        return new ShoppingListInStoreCreateCommandImpl(copyBuilder().draft(draft));
    }
}
