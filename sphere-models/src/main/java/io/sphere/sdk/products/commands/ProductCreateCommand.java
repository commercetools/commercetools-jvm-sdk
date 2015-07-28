package io.sphere.sdk.products.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductDraft;

/**
 Creates a product.

 <p>A {@link io.sphere.sdk.products.Product} must belong to a {@link io.sphere.sdk.producttypes.ProductType},
 so you need to {@link io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand create a product type} first if not already done.</p>

 Example usage executing the command:
 {@include.example io.sphere.sdk.products.CreateProductExamples#createWithClient()}

 Create a {@link io.sphere.sdk.products.ProductDraft} instance:
 {@include.example io.sphere.sdk.suppliers.SimpleCottonTShirtProductDraftSupplier}
 */
public interface ProductCreateCommand extends CreateCommand<Product> {
    static ProductCreateCommand of(final ProductDraft draft) {
        return new ProductCreateCommandImpl(draft);
    }
}
