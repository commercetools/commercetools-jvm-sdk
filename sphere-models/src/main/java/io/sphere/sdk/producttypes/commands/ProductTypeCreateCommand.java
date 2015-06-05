package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.commands.CreateCommandImpl;

/**
 Command to create a {@link io.sphere.sdk.producttypes.ProductType} in the backend.


  <p>{@link io.sphere.sdk.producttypes.ProductType}s can be created in the backend by executing a {@link io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand}:</p>

  {@include.example io.sphere.sdk.producttypes.commands.ProductTypeCreateCommandTest#execution()}

  {@include.example io.sphere.sdk.producttypes.Example#createDemo()}

  {@include.example io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier}

  <p>To create attribute definitions refer to {@link io.sphere.sdk.attributes.AttributeDefinition}.</p>

 */
public class ProductTypeCreateCommand extends CreateCommandImpl<ProductType, ProductTypeDraft> {

    private ProductTypeCreateCommand(final ProductTypeDraft draft) {
        super(draft, ProductTypeEndpoint.ENDPOINT);
    }

    public static ProductTypeCreateCommand of(final ProductTypeDraft draft) {
        return new ProductTypeCreateCommand(draft);
    }
}
