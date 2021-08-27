package io.sphere.sdk.shoppinglists.queries;


import io.sphere.sdk.queries.MetaModelGetDsl;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.expansion.ShoppingListExpansionModel;

public interface ShoppingListInStoreByKeyGet extends MetaModelGetDsl<ShoppingList, ShoppingList, ShoppingListInStoreByKeyGet, ShoppingListExpansionModel<ShoppingList>> {

    static ShoppingListInStoreByKeyGet of(final String storeKey, final String id) {
        return new ShoppingListInStoreByKeyGetImpl(storeKey, id);
    }
    
}
