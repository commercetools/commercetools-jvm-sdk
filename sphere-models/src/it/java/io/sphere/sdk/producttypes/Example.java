package io.sphere.sdk.producttypes;

import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.test.IntegrationTest;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class Example extends IntegrationTest {

    public void createDemo() {
        final ProductTypeCreateCommand command = ProductTypeCreateCommand.of(new TShirtProductTypeDraftSupplier("product-type-name").get());
        final ProductType productType = execute(command);

    }
}
