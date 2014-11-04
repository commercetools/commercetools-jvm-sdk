package io.sphere.sdk.products;

import io.sphere.sdk.client.JavaClient;
import io.sphere.sdk.products.commands.ProductDeleteByIdCommand;

import java.util.concurrent.CompletableFuture;

public class ProductDeleteExample {
    private JavaClient client;
    private Product product;

    public void delete() {
        final ProductDeleteByIdCommand command = new ProductDeleteByIdCommand(product);
        final CompletableFuture<Product> deletedProduct = client.execute(command);
    }
}
