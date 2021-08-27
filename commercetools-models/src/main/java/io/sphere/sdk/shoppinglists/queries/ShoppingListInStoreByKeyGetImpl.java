package io.sphere.sdk.shoppinglists.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.expansion.ShoppingListExpansionModel;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class ShoppingListInStoreByKeyGetImpl extends MetaModelGetDslImpl<ShoppingList, ShoppingList, ShoppingListInStoreByKeyGet, ShoppingListExpansionModel<ShoppingList>> implements ShoppingListInStoreByKeyGet {

    ShoppingListInStoreByKeyGetImpl(final String storeKey, final String key) {
        super("key=" + key, JsonEndpoint.of(ShoppingList.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/shopping-lists"), ShoppingListExpansionModel.of(), ShoppingListInStoreByKeyGetImpl::new);
    }

    public ShoppingListInStoreByKeyGetImpl(final MetaModelGetDslBuilder<ShoppingList, ShoppingList, ShoppingListInStoreByKeyGet, ShoppingListExpansionModel<ShoppingList>> builder) {
        super(builder);
    }

}
