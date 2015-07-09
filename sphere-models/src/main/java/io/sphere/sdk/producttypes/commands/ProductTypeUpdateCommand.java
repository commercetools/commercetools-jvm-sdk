package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

import static java.util.Arrays.asList;

public class ProductTypeUpdateCommand extends UpdateCommandDslImpl<ProductType> {
    private ProductTypeUpdateCommand(final Versioned<ProductType> versioned, final List<? extends UpdateAction<ProductType>> updateActions) {
        super(versioned, updateActions, ProductTypeEndpoint.ENDPOINT);
    }

    public static ProductTypeUpdateCommand of(final Versioned<ProductType> versioned, final List<? extends UpdateAction<ProductType>> updateActions) {
        return new ProductTypeUpdateCommand(versioned, updateActions);
    }

    public static ProductTypeUpdateCommand of(final Versioned<ProductType> versioned, final UpdateAction<ProductType> updateAction) {
        return new ProductTypeUpdateCommand(versioned, asList(updateAction));
    }
}
