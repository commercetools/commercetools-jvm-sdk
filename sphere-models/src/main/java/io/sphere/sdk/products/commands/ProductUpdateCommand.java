package io.sphere.sdk.products.commands;

import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;

import java.util.List;

import static java.util.Arrays.asList;

/**

 {@doc.gen list actions}

 */
public class ProductUpdateCommand extends UpdateCommandDslImpl<Product> {
    private ProductUpdateCommand(final Versioned<Product> versioned, final List<? extends UpdateAction<Product>> updateActions) {
        super(versioned, updateActions, ProductEndpoint.ENDPOINT);
    }

    public static ProductUpdateCommand of(final Versioned<Product> versioned, final UpdateAction<Product> updateAction) {
        return of(versioned, asList(updateAction));
    }

    public static ProductUpdateCommand of(final Versioned<Product> versioned, final List<? extends UpdateAction<Product>> updateActions) {
        return new ProductUpdateCommand(versioned, updateActions);
    }
}
