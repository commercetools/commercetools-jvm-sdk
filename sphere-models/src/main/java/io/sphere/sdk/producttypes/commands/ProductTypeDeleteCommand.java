package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;

/** Deletes a product type.

 <p>Example:</p>
 {@include.example io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommandTest#execution()}

 */
public interface ProductTypeDeleteCommand extends ByIdDeleteCommand<ProductType>, MetaModelExpansionDsl<ProductType, ProductTypeDeleteCommand, ProductTypeExpansionModel<ProductType>> {
    static ProductTypeDeleteCommand of(final Versioned<ProductType> versioned) {
        return new ProductTypeDeleteCommandImpl(versioned);
    }
}
