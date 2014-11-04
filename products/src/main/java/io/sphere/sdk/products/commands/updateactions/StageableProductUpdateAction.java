package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductUpdateScope;

class StageableProductUpdateAction extends UpdateAction<Product> {
    final boolean staged;

    StageableProductUpdateAction(final String action, final ProductUpdateScope productUpdateScope) {
        super(action);
        this.staged = productUpdateScope.isOnlyStaged();
    }

    public boolean isStaged() {
        return staged;
    }
}
