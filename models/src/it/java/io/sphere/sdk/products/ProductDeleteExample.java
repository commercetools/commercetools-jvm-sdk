package io.sphere.sdk.products;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.products.commands.ProductDeleteByIdCommand;

import java.util.concurrent.CompletableFuture;

public class ProductDeleteExample {
    private SphereClient client;
    private Product product;

    public void delete() {
        final ProductDeleteByIdCommand command = ProductDeleteByIdCommand.of(product);
        final CompletableFuture<Product> deletedProduct = client.execute(command);
    }
}
