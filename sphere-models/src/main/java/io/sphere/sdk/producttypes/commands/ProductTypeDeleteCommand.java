package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.ProductType;

/** Deletes a product type.

 <p>Example:</p>
 {@include.example io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommandTest#execution()}

 */
public interface ProductTypeDeleteCommand extends ByIdDeleteCommand<ProductType> {
    static DeleteCommand<ProductType> of(final Versioned<ProductType> versioned) {
        return new ProductTypeDeleteCommandImpl(versioned);
    }
}
