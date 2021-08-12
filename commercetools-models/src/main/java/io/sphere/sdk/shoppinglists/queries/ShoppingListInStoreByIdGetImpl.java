package io.sphere.sdk.shoppinglists.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.expansion.ShoppingListExpansionModel;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class ShoppingListInStoreByIdGetImpl extends MetaModelGetDslImpl<ShoppingList, ShoppingList, ShoppingListInStoreByIdGet, ShoppingListExpansionModel<ShoppingList>> implements ShoppingListInStoreByIdGet {

    ShoppingListInStoreByIdGetImpl(final String storeKey, final String id) {
        super(id, JsonEndpoint.of(ShoppingList.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/shopping-lists"), ShoppingListExpansionModel.of(), ShoppingListInStoreByIdGetImpl::new);
    }

    public ShoppingListInStoreByIdGetImpl(final MetaModelGetDslBuilder<ShoppingList, ShoppingList, ShoppingListInStoreByIdGet, ShoppingListExpansionModel<ShoppingList>> builder) {
        super(builder);
    }
}
