package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.http.ClientRequest;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.commands.DeleteByIdCommandImpl;

/** Deletes a product type.

 <p>Example:</p>
 {@include.example example.QueryProductTypeExamples#delete()}

 */
public final class ProductTypeDeleteByIdCommand extends DeleteByIdCommandImpl<ProductType> {
    public ProductTypeDeleteByIdCommand(final Versioned<ProductType> versioned) {
        super(versioned, ProductTypesEndpoint.ENDPOINT);
    }
}
