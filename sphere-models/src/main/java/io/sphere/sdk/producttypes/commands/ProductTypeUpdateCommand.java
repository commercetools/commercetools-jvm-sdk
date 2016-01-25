package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;

import java.util.Collections;
import java.util.List;

/**
Updates ProductTypes.

 {@doc.gen list actions}

 */
public interface ProductTypeUpdateCommand extends UpdateCommandDsl<ProductType, ProductTypeUpdateCommand>, MetaModelReferenceExpansionDsl<ProductType, ProductTypeUpdateCommand, ProductTypeExpansionModel<ProductType>> {
    static ProductTypeUpdateCommand of(final Versioned<ProductType> versioned, final List<? extends UpdateAction<ProductType>> updateActions) {
        return new ProductTypeUpdateCommandImpl(versioned, updateActions);
    }

    static ProductTypeUpdateCommand of(final Versioned<ProductType> versioned, final UpdateAction<ProductType> updateAction) {
        return new ProductTypeUpdateCommandImpl(versioned, Collections.singletonList(updateAction));
    }

    static ProductTypeUpdateCommand ofKey(final String key, final Long version, final List<? extends UpdateAction<ProductType>> updateActions) {
        final Versioned<ProductType> versioned = Versioned.of("key=" + key, version);//hack for simple reuse
        return new ProductTypeUpdateCommandImpl(versioned, updateActions);
    }

    static ProductTypeUpdateCommand ofKey(final String key, final Long version, final UpdateAction<ProductType> updateAction) {
        return ofKey(key, version, Collections.singletonList(updateAction));
    }
}
