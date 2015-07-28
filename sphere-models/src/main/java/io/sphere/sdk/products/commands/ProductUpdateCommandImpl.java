package io.sphere.sdk.products.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;

import java.util.List;

import static java.util.Arrays.asList;

/**

 {@doc.gen list actions}

 */
public class ProductUpdateCommandImpl extends UpdateCommandDslImpl<Product, ProductUpdateCommandImpl> {
    private ProductUpdateCommandImpl(final Versioned<Product> versioned, final List<? extends UpdateAction<Product>> updateActions) {
        super(versioned, updateActions, ProductEndpoint.ENDPOINT);
    }

    public static ProductUpdateCommandImpl of(final Versioned<Product> versioned, final UpdateAction<Product> updateAction) {
        return of(versioned, asList(updateAction));
    }

    public static ProductUpdateCommandImpl of(final Versioned<Product> versioned, final List<? extends UpdateAction<Product>> updateActions) {
        return new ProductUpdateCommandImpl(versioned, updateActions);
    }
}
