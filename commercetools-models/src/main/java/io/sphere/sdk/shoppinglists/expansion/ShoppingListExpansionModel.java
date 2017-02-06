package io.sphere.sdk.shoppinglists.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.List;

public interface ShoppingListExpansionModel<T> extends ExpansionPathContainer<T> {

    static ShoppingListExpansionModel<ShoppingList> of() {
        return new ShoppingListExpansionModelImpl<>();
    }

    static <T> ShoppingListExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new ShoppingListExpansionModelImpl<>(parentPath, path);
    }
}
