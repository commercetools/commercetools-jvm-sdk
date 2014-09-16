package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.producttypes.NewProductType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.commands.CreateCommandImpl;

/**
 * Command to create a {@link io.sphere.sdk.producttypes.ProductType} in the backend.
 */
public class ProductTypeCreateCommand extends CreateCommandImpl<ProductType, NewProductType> {

    public ProductTypeCreateCommand(final NewProductType body) {
        super(body, ProductTypesEndpoint.ENDPOINT);
    }
}
