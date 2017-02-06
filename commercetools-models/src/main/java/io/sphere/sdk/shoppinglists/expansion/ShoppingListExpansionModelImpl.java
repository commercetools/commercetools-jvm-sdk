package io.sphere.sdk.shoppinglists.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;

import java.util.List;

public class ShoppingListExpansionModelImpl<T> extends ExpansionModelImpl<T> implements ShoppingListExpansionModel<T> {
    public ShoppingListExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    ShoppingListExpansionModelImpl() {
        super();
    }
}
