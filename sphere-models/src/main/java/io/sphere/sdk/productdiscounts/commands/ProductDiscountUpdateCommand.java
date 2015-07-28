package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.productdiscounts.ProductDiscount;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class ProductDiscountUpdateCommand extends UpdateCommandDslImpl<ProductDiscount, ProductDiscountUpdateCommand> {
    private ProductDiscountUpdateCommand(final Versioned<ProductDiscount> versioned, final List<? extends UpdateAction<ProductDiscount>> updateActions) {
        super(versioned, updateActions, ProductDiscountEndpoint.ENDPOINT);
    }

    public static ProductDiscountUpdateCommand of(final Versioned<ProductDiscount> versioned, final UpdateAction<ProductDiscount> updateAction) {
        return of(versioned, asList(updateAction));
    }

    public static ProductDiscountUpdateCommand of(final Versioned<ProductDiscount> versioned, final List<? extends UpdateAction<ProductDiscount>> updateActions) {
        return new ProductDiscountUpdateCommand(versioned, updateActions);
    }
}
