package io.sphere.sdk.products.commands.actions;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.requests.UpdateAction;

public class UnPublish extends UpdateAction<Product> {
    private UnPublish() {
        super("unpublish");
    }

    public static UnPublish of() {
        return new UnPublish();
    }
}
