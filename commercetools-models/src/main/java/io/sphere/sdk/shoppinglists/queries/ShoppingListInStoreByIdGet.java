package io.sphere.sdk.shoppinglists.queries;


import io.sphere.sdk.queries.MetaModelGetDsl;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.expansion.ShoppingListExpansionModel;

public interface ShoppingListInStoreByIdGet extends MetaModelGetDsl<ShoppingList, ShoppingList, ShoppingListInStoreByIdGet, ShoppingListExpansionModel<ShoppingList>> {
    
    static ShoppingListInStoreByIdGet of(final String storeKey, final String id) {
        return new ShoppingListInStoreByIdGetImpl(storeKey, id);
    }
    
}
