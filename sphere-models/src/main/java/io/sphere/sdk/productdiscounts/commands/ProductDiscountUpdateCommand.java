package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.productdiscounts.ProductDiscount;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public interface ProductDiscountUpdateCommand extends UpdateCommandDsl<ProductDiscount, ProductDiscountUpdateCommand> {
    static ProductDiscountUpdateCommand of(final Versioned<ProductDiscount> versioned, final UpdateAction<ProductDiscount> updateAction) {
        return of(versioned, asList(updateAction));
    }

    static ProductDiscountUpdateCommand of(final Versioned<ProductDiscount> versioned, final List<? extends UpdateAction<ProductDiscount>> updateActions) {
        return new ProductDiscountUpdateCommandImpl(versioned, updateActions);
    }
}
