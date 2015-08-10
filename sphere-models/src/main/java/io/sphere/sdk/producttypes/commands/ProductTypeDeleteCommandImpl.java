package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;

final class ProductTypeDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<ProductType, ProductTypeDeleteCommand, ProductTypeExpansionModel<ProductType>> implements ProductTypeDeleteCommand {
    ProductTypeDeleteCommandImpl(final Versioned<ProductType> versioned) {
        super(versioned, ProductTypeEndpoint.ENDPOINT, ProductTypeExpansionModel.of(), ProductTypeDeleteCommandImpl::new);
    }


    ProductTypeDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<ProductType, ProductTypeDeleteCommand, ProductTypeExpansionModel<ProductType>> builder) {
        super(builder);
    }
}
