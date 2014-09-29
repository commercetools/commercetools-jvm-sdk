package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.producttypes.NewProductType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.commands.CreateCommandImpl;

/**
 Command to create a {@link io.sphere.sdk.producttypes.ProductType} in the backend.


  <p>{@link io.sphere.sdk.producttypes.ProductType}s can be created in the backend by executing a {@link io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand}:</p>

  {@include.example io.sphere.sdk.producttypes.Example#createDemo()}

  {@include.example io.sphere.sdk.suppliers.TShirtNewProductTypeSupplier}

  <p>To create attribute definitions refer to {@link io.sphere.sdk.attributes.AttributeDefinition}.</p>

 */
public class ProductTypeCreateCommand extends CreateCommandImpl<ProductType, NewProductType> {

    public ProductTypeCreateCommand(final NewProductType body) {
        super(body, ProductTypesEndpoint.ENDPOINT);
    }
}
