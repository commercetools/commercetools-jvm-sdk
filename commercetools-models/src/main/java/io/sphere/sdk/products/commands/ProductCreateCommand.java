package io.sphere.sdk.products.commands;

import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.commands.DraftBasedCreateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductDraft;
import io.sphere.sdk.products.expansion.ProductExpansionModel;

/**
 Creates a product.

 <p>A {@link io.sphere.sdk.products.Product} must belong to a {@link io.sphere.sdk.producttypes.ProductType},
 so you need to {@link io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand create a product type} first if not already done.</p>

 Example usage executing the command:
 {@include.example io.sphere.sdk.products.CreateProductExamples#createWithClient()}

 Create a {@link io.sphere.sdk.products.ProductDraft} instance:
 {@include.example io.sphere.sdk.suppliers.SimpleCottonTShirtProductDraftSupplier}

 @see io.sphere.sdk.products.messages.ProductCreatedMessage
 */
public interface ProductCreateCommand extends DraftBasedCreateCommandDsl<Product, ProductDraft, ProductCreateCommand>, MetaModelReferenceExpansionDsl<Product, ProductCreateCommand, ProductExpansionModel<Product>> {
    static ProductCreateCommand of(final ProductDraft draft) {
        return new ProductCreateCommandImpl(draft);
    }
}
