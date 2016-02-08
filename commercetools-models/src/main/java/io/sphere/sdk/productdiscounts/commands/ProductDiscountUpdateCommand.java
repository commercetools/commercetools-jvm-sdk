package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;

import java.util.Collections;
import java.util.List;

/**
 {@doc.gen list actions}
 */
public interface ProductDiscountUpdateCommand extends UpdateCommandDsl<ProductDiscount, ProductDiscountUpdateCommand>, MetaModelReferenceExpansionDsl<ProductDiscount, ProductDiscountUpdateCommand, ProductDiscountExpansionModel<ProductDiscount>> {
    static ProductDiscountUpdateCommand of(final Versioned<ProductDiscount> versioned, final UpdateAction<ProductDiscount> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }

    static ProductDiscountUpdateCommand of(final Versioned<ProductDiscount> versioned, final List<? extends UpdateAction<ProductDiscount>> updateActions) {
        return new ProductDiscountUpdateCommandImpl(versioned, updateActions);
    }
}
