package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.commands.ByIdDeleteCommandImpl;

/** Deletes a product type.

 <p>Example:</p>
 {@include.example io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommandTest#execution()}

 */
public final class ProductTypeDeleteCommand extends ByIdDeleteCommandImpl<ProductType> {
    private ProductTypeDeleteCommand(final Versioned<ProductType> versioned) {
        super(versioned, ProductTypeEndpoint.ENDPOINT);
    }

    public static DeleteCommand<ProductType> of(final Versioned<ProductType> versioned) {
        return new ProductTypeDeleteCommand(versioned);
    }
}
