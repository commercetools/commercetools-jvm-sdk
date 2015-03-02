package io.sphere.sdk.products;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;

import java.util.concurrent.CompletableFuture;

public class ProductDeleteExample {
    private SphereClient client;
    private Product product;

    public void delete() {
        final DeleteCommand<Product> command = ProductDeleteCommand.of(product);
        final CompletableFuture<Product> deletedProduct = client.execute(command);
    }
}
