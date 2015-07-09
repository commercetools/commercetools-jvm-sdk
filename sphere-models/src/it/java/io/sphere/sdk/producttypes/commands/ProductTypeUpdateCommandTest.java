package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.producttypes.ProductTypeFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductTypeUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeName() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String name = randomKey();
            final ProductType updatedProductType =
                    execute(ProductTypeUpdateCommand.of(productType, ChangeName.of(name)));
            assertThat(updatedProductType.getName()).isEqualTo(name);
            return updatedProductType;
        });
    }

    @Test
    public void changeDescription() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String description = randomKey();
            final ProductType updatedProductType =
                    execute(ProductTypeUpdateCommand.of(productType, ChangeDescription.of(description)));
            assertThat(updatedProductType.getDescription()).isEqualTo(description);
            return updatedProductType;
        });
    }
}