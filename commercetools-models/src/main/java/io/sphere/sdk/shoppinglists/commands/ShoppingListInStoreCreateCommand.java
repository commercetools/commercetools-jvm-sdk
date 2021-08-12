package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.commands.DraftBasedCreateCommandDsl;
import io.sphere.sdk.customers.CustomerDraft;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.expansion.CustomerSignInResultExpansionModel;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.ShoppingListDraft;
import io.sphere.sdk.shoppinglists.expansion.ShoppingListExpansionModel;

public interface ShoppingListInStoreCreateCommand extends DraftBasedCreateCommandDsl<ShoppingList, ShoppingListDraft, ShoppingListInStoreCreateCommand>, MetaModelReferenceExpansionDsl<ShoppingList, ShoppingListInStoreCreateCommand, ShoppingListExpansionModel<ShoppingList>> {
    static ShoppingListInStoreCreateCommand of(final String storeKey, final ShoppingListDraft draft) {
        return new ShoppingListInStoreCreateCommandImpl(storeKey, draft);
    }
}
