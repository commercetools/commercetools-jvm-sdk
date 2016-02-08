package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.*;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;

import java.util.List;

final class ProductTypeUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<ProductType, ProductTypeUpdateCommand, ProductTypeExpansionModel<ProductType>> implements ProductTypeUpdateCommand {
    ProductTypeUpdateCommandImpl(final Versioned<ProductType> versioned, final List<? extends UpdateAction<ProductType>> updateActions) {
        super(versioned, updateActions, ProductTypeEndpoint.ENDPOINT, ProductTypeUpdateCommandImpl::new, ProductTypeExpansionModel.of());
    }

    ProductTypeUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<ProductType, ProductTypeUpdateCommand, ProductTypeExpansionModel<ProductType>> builder) {
        super(builder);
    }
}
