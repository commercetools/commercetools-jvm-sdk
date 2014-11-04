package io.sphere.sdk.products;

import io.sphere.sdk.client.JavaClient;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.suppliers.SimpleCottonTShirtNewProductSupplier;

import java.util.concurrent.CompletableFuture;

public class CreateProductExamples {
    JavaClient client;
    NewProduct newProduct;
    ProductType productType;

    public void createWithClient() {
        final NewProduct productTemplate = new SimpleCottonTShirtNewProductSupplier(productType, "demo product").get();
        final ProductCreateCommand command = new ProductCreateCommand(productTemplate);
        final CompletableFuture<Product> result = client.execute(command);
    }
}
