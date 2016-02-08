package io.sphere.sdk.products;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.suppliers.SimpleCottonTShirtProductDraftSupplier;

import java.util.concurrent.CompletionStage;

public class CreateProductExamples {
    SphereClient client;
    ProductDraft productDraft;
    ProductType productType;

    public void createWithClient() {
        final ProductDraft productTemplate = new SimpleCottonTShirtProductDraftSupplier(productType, "demo product").get();
        final ProductCreateCommand command = ProductCreateCommand.of(productTemplate);
        final CompletionStage<Product> result = client.execute(command);
    }
}
