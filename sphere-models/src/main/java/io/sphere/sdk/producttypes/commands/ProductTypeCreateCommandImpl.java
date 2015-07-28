package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;

/**
 Command to create a {@link ProductType} in the backend.


  <p>{@link ProductType}s can be created in the backend by executing a {@link ProductTypeCreateCommandImpl}:</p>

  {@include.example io.sphere.sdk.producttypes.commands.ProductTypeCreateCommandTest#execution()}

  {@include.example io.sphere.sdk.producttypes.Example#createDemo()}

  {@include.example io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier}

  <p>To create attribute definitions refer to {@link io.sphere.sdk.attributes.AttributeDefinition}.</p>

 */
public class ProductTypeCreateCommandImpl extends CreateCommandImpl<ProductType, ProductTypeDraft> {

    private ProductTypeCreateCommandImpl(final ProductTypeDraft draft) {
        super(draft, ProductTypeEndpoint.ENDPOINT);
    }

    public static ProductTypeCreateCommandImpl of(final ProductTypeDraft draft) {
        return new ProductTypeCreateCommandImpl(draft);
    }
}
