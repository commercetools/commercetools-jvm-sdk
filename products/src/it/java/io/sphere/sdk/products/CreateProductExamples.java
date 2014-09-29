package io.sphere.sdk.products;

import io.sphere.sdk.client.PlayJavaClient;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.suppliers.SimpleCottonTShirtNewProductSupplier;
import play.libs.F;

public class CreateProductExamples {
    PlayJavaClient client;
    NewProduct newProduct;
    ProductType productType;

    public void createWithClient() {
        final NewProduct productTemplate = new SimpleCottonTShirtNewProductSupplier(productType, "demo product").get();
        final ProductCreateCommand command = new ProductCreateCommand(productTemplate);
        final F.Promise<Product> result = client.execute(command);
    }
}
