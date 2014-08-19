package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.commands.UpdateAction;

public class UnPublish extends UpdateAction<Product> {
    private UnPublish() {
        super("unpublish");
    }

    public static UnPublish of() {
        return new UnPublish();
    }
}
