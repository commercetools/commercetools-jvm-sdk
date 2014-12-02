package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.commands.DeleteByIdCommandImpl;

/** Deletes a product type.

 <p>Example:</p>
 {@include.example example.QueryProductTypeExamples#delete()}

 */
public final class ProductTypeDeleteByIdCommand extends DeleteByIdCommandImpl<ProductType> {
    private ProductTypeDeleteByIdCommand(final Versioned<ProductType> versioned) {
        super(versioned, ProductTypesEndpoint.ENDPOINT);
    }

    public static ProductTypeDeleteByIdCommand of(final Versioned<ProductType> versioned) {
        return new ProductTypeDeleteByIdCommand(versioned);
    }
}
