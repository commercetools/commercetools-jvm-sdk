package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

import static java.util.Arrays.asList;

/**

 {@doc.gen list actions}

 */
public class ProductTypeUpdateCommandImpl extends UpdateCommandDslImpl<ProductType, ProductTypeUpdateCommandImpl> {
    private ProductTypeUpdateCommandImpl(final Versioned<ProductType> versioned, final List<? extends UpdateAction<ProductType>> updateActions) {
        super(versioned, updateActions, ProductTypeEndpoint.ENDPOINT);
    }

    public static ProductTypeUpdateCommandImpl of(final Versioned<ProductType> versioned, final List<? extends UpdateAction<ProductType>> updateActions) {
        return new ProductTypeUpdateCommandImpl(versioned, updateActions);
    }

    public static ProductTypeUpdateCommandImpl of(final Versioned<ProductType> versioned, final UpdateAction<ProductType> updateAction) {
        return new ProductTypeUpdateCommandImpl(versioned, asList(updateAction));
    }
}
