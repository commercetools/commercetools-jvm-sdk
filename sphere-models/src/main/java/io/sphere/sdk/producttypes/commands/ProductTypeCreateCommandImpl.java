package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;

final class ProductTypeCreateCommandImpl extends MetaModelCreateCommandImpl<ProductType, ProductTypeCreateCommand, ProductTypeDraft, ProductTypeExpansionModel<ProductType>> implements ProductTypeCreateCommand {
    ProductTypeCreateCommandImpl(final MetaModelCreateCommandBuilder<ProductType, ProductTypeCreateCommand, ProductTypeDraft, ProductTypeExpansionModel<ProductType>> builder) {
        super(builder);
    }

    ProductTypeCreateCommandImpl(final ProductTypeDraft draft) {
        super(draft, ProductTypeEndpoint.ENDPOINT, ProductTypeExpansionModel.of(), ProductTypeCreateCommandImpl::new);
    }
}
