package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;

final class ProductTypeCreateCommandImpl extends CreateCommandImpl<ProductType, ProductTypeDraft> implements ProductTypeCreateCommand {

    ProductTypeCreateCommandImpl(final ProductTypeDraft draft) {
        super(draft, ProductTypeEndpoint.ENDPOINT);
    }
}
