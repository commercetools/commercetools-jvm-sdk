package io.sphere.sdk.shoppinglists.expansion;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.List;

public interface ShoppingListExpansionModel<T> extends ExpansionPathContainer<T> {


    static ShoppingListExpansionModel<ShoppingList> of() {
        return new ShoppingListExpansionModel<ShoppingList>() {

            @Override
            public List<ExpansionPath<ShoppingList>> expansionPaths() {
                return null;
            }
        };
    }

}
