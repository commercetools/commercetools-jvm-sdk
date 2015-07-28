package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.ProductType;

/** Deletes a product type.

 <p>Example:</p>
 {@include.example io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommandTest#execution()}

 */
final class ProductTypeDeleteCommandImpl extends ByIdDeleteCommandImpl<ProductType> implements ProductTypeDeleteCommand {
    ProductTypeDeleteCommandImpl(final Versioned<ProductType> versioned) {
        super(versioned, ProductTypeEndpoint.ENDPOINT);
    }
}
