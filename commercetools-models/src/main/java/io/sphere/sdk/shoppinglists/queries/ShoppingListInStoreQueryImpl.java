package io.sphere.sdk.shoppinglists.queries;

import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.expansion.ShoppingListExpansionModel;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

class ShoppingListInStoreQueryImpl extends MetaModelQueryDslImpl<ShoppingList, ShoppingListInStoreQuery, ShoppingListQueryModel, ShoppingListExpansionModel<ShoppingList>> implements ShoppingListInStoreQuery {

    ShoppingListInStoreQueryImpl(final String storeKey) {
        super("/in-store/key=" + urlEncode(storeKey) + "/shopping-lists", ShoppingListQuery.resultTypeReference(), ShoppingListQueryModel.of(), ShoppingListExpansionModel.of(), ShoppingListInStoreQueryImpl::new);
    }

    private ShoppingListInStoreQueryImpl(final MetaModelQueryDslBuilder<ShoppingList, ShoppingListInStoreQuery, ShoppingListQueryModel, ShoppingListExpansionModel<ShoppingList>> builder) {
        super(builder);
    }

}
