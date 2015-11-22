package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;

/** Deletes a product type.

 <p>Delete by ID:</p>
 {@include.example io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommandTest#execution()}
 <p>Delete by key:</p>
 {@include.example io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommandTest#executionByKey()}

 */
public interface ProductTypeDeleteCommand extends ByIdDeleteCommand<ProductType>, MetaModelExpansionDsl<ProductType, ProductTypeDeleteCommand, ProductTypeExpansionModel<ProductType>> {
    static ProductTypeDeleteCommand of(final Versioned<ProductType> versioned) {
        return new ProductTypeDeleteCommandImpl(versioned);
    }

    static ProductTypeDeleteCommand ofKey(final String key, final Long version) {
        final Versioned<ProductType> versioned = Versioned.of("key=" + key, version);//hack for simple reuse
        return of(versioned);
    }
}
