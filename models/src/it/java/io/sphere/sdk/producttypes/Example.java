package io.sphere.sdk.producttypes;

import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.test.IntegrationTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Example extends IntegrationTest {

    public void createDemo() {
        final ProductTypeCreateCommand command = new ProductTypeCreateCommand(new TShirtProductTypeDraftSupplier("product-type-name").get());
        final ProductType productType = execute(command);

    }

    public void builderDemo() {
        final Instant createdAt = Instant.parse("2001-09-11T14:00:00.000Z");
        final Instant lastModifiedAt = createdAt.plus(2, ChronoUnit.HOURS);
        //set createdAt/lastModifiedAt/version is optional
        final ProductTypeDraft productTypeDraft = new TShirtProductTypeDraftSupplier("product-type-name").get();
        final ProductType productType = ProductTypeBuilder.of("product-type-id", productTypeDraft).
                createdAt(createdAt).lastModifiedAt(lastModifiedAt).version(4).build();

    }
}
