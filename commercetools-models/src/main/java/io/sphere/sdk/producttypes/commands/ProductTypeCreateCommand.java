package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;

/**
 Command to create a {@link io.sphere.sdk.producttypes.ProductType} in the backend.


  <p>{@link io.sphere.sdk.producttypes.ProductType}s can be created in the backend by executing a {@link io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand}:</p>

  {@include.example io.sphere.sdk.producttypes.commands.ProductTypeCreateCommandIntegrationTest#execution()}

  {@include.example io.sphere.sdk.producttypes.Example#createDemo()}

  {@include.example io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier}

  <p>To create attribute definitions refer to {@link io.sphere.sdk.products.attributes.AttributeDefinition}.</p>

 */
public interface ProductTypeCreateCommand extends DraftBasedCreateCommand<ProductType, ProductTypeDraft>, MetaModelReferenceExpansionDsl<ProductType, ProductTypeCreateCommand, ProductTypeExpansionModel<ProductType>> {

    static ProductTypeCreateCommand of(final ProductTypeDraft draft) {
        return new ProductTypeCreateCommandImpl(draft);
    }
}
