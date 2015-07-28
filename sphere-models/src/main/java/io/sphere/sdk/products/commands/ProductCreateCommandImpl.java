package io.sphere.sdk.products.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductDraft;

/**
 Creates a product.

 <p>A {@link Product} must belong to a {@link io.sphere.sdk.producttypes.ProductType},
 so you need to {@link io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand create a product type} first if not already done.</p>

 Example usage executing the command:
 {@include.example io.sphere.sdk.products.CreateProductExamples#createWithClient()}

 Create a {@link ProductDraft} instance:
 {@include.example io.sphere.sdk.suppliers.SimpleCottonTShirtProductDraftSupplier}
 */
final class ProductCreateCommandImpl extends CreateCommandImpl<Product, ProductDraft> implements ProductCreateCommand {
    ProductCreateCommandImpl(final ProductDraft body) {
        super(body, ProductEndpoint.ENDPOINT);
    }
}
