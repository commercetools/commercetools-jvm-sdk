package io.sphere.sdk.producttypes;

import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.test.IntegrationTest;

public class Example extends IntegrationTest {

    public void createDemo() {
        final ProductTypeCreateCommand command = ProductTypeCreateCommand.of(new TShirtProductTypeDraftSupplier("product-type-name").get());
        final ProductType productType = client().executeBlocking(command);

    }
}
