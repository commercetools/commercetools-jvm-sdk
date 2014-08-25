package example;

import io.sphere.sdk.client.PlayJavaClient;
import io.sphere.sdk.producttypes.NewProductType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeBuilder;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import play.libs.F;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class CreateTShirtProductTypeExample {

    PlayJavaClient client;
    NewProductType newProductType;

    public void createBackend() {
        ProductTypeCreateCommand command = new ProductTypeCreateCommand(newProductType);
        F.Promise<ProductType> result = client.execute(command);
    }

    public void createProductTypeForUnitTest() {
        Instant createdAt = Instant.parse("2001-09-11T14:00:00.000Z");
        Instant lastModifiedAt = createdAt.plus(2, ChronoUnit.HOURS);
        //set createdAt/lastModifiedAt/version is optional
        ProductType productType = ProductTypeBuilder.of("product-type-id", newProductType).
                createdAt(createdAt).lastModifiedAt(lastModifiedAt).version(4).build();
    }
}
