package example;

import io.sphere.sdk.client.PlayJavaClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.producttypes.NewProductType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeBuilder;
import io.sphere.sdk.producttypes.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.attributes.*;
import org.joda.time.DateTime;
import play.libs.F;

import java.util.Arrays;
import java.util.List;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;

public class CreateTShirtProductTypeExample {

    PlayJavaClient client;
    NewProductType newProductType;

    public void createBackend() {
        ProductTypeCreateCommand command = new ProductTypeCreateCommand(newProductType);
        F.Promise<ProductType> result = client.execute(command);
    }

    public void createProductTypeForUnitTest() {
        DateTime createdAt = new DateTime("2013-11-13T21:39:45.618-08:00");
        DateTime lastModifiedAt = createdAt.plusHours(2);
        //set createdAt/lastModifiedAt/version is optional
        ProductType productType = ProductTypeBuilder.of("product-type-id", newProductType).
                createdAt(createdAt).lastModifiedAt(lastModifiedAt).version(4).build();
    }
}
