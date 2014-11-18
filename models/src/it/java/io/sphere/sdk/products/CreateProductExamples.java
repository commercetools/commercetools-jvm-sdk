package io.sphere.sdk.products;

import io.sphere.sdk.client.JavaClient;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.suppliers.SimpleCottonTShirtProductDraftSupplier;

import java.util.concurrent.CompletableFuture;

public class CreateProductExamples {
    JavaClient client;
    ProductDraft productDraft;
    ProductType productType;

    public void createWithClient() {
        final ProductDraft productTemplate = new SimpleCottonTShirtProductDraftSupplier(productType, "demo product").get();
        final ProductCreateCommand command = new ProductCreateCommand(productTemplate);
        final CompletableFuture<Product> result = client.execute(command);
    }
}
