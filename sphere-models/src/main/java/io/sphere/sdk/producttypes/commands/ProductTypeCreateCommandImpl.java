package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandBuilder;
import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandImpl;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;

final class ProductTypeCreateCommandImpl extends ReferenceExpandeableCreateCommandImpl<ProductType, ProductTypeCreateCommand, ProductTypeDraft, ProductTypeExpansionModel<ProductType>> implements ProductTypeCreateCommand {
    ProductTypeCreateCommandImpl(final ReferenceExpandeableCreateCommandBuilder<ProductType, ProductTypeCreateCommand, ProductTypeDraft, ProductTypeExpansionModel<ProductType>> builder) {
        super(builder);
    }

    ProductTypeCreateCommandImpl(final ProductTypeDraft draft) {
        super(draft, ProductTypeEndpoint.ENDPOINT, ProductTypeExpansionModel.of(), ProductTypeCreateCommandImpl::new);
    }
}
