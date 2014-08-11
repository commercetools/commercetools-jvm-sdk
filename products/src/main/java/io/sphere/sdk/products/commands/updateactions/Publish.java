package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.requests.UpdateAction;

public class Publish  extends UpdateAction<Product> {
    private Publish() {
        super("publish");
    }

    public static Publish of() {
        return new Publish();
    }
}
