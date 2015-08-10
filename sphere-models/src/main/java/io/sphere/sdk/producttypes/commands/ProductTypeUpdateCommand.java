package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;

import java.util.Collections;
import java.util.List;

/**

 {@doc.gen list actions}

 */
public interface ProductTypeUpdateCommand extends UpdateCommandDsl<ProductType, ProductTypeUpdateCommand>, MetaModelExpansionDsl<ProductType, ProductTypeUpdateCommand, ProductTypeExpansionModel<ProductType>> {
    static ProductTypeUpdateCommand of(final Versioned<ProductType> versioned, final List<? extends UpdateAction<ProductType>> updateActions) {
        return new ProductTypeUpdateCommandImpl(versioned, updateActions);
    }

    static ProductTypeUpdateCommand of(final Versioned<ProductType> versioned, final UpdateAction<ProductType> updateAction) {
        return new ProductTypeUpdateCommandImpl(versioned, Collections.singletonList(updateAction));
    }
}
