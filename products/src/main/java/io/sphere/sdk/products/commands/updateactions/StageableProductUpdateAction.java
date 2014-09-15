package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.products.Product;

class StageableProductUpdateAction extends UpdateAction<Product> {
    final boolean staged;

    StageableProductUpdateAction(final String action, final boolean staged) {
        super(action);
        this.staged = staged;
    }
}
