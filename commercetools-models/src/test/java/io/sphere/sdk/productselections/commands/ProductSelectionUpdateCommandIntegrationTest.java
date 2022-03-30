package io.sphere.sdk.productselections.commands;


import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.productselections.commands.updateactions.AddProduct;
import io.sphere.sdk.productselections.commands.updateactions.ChangeName;
import io.sphere.sdk.productselections.commands.updateactions.RemoveProduct;
import io.sphere.sdk.productselections.commands.updateactions.SetKey;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.productselections.ProductSelectionFixtures.withUpdateableProductSelection;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSelectionUpdateCommandIntegrationTest extends IntegrationTest {

    @Test
    public void changeName() throws Exception {
        withUpdateableProductSelection(client(), productSelection -> {
            final LocalizedString name  = en("Summer");
            final ProductSelection updatedProductSelection =
                    client().executeBlocking(ProductSelectionUpdateCommand.of(productSelection, ChangeName.of(name)));
            assertThat(updatedProductSelection.getName()).isEqualTo(name);
            return updatedProductSelection;
        });
    }

    @Test
    public void setKey() throws Exception {
        withUpdateableProductSelection(client(), productSelection -> {
            final String newKey = randomKey();
            final ProductSelection updatedProductSelection =
                    client().executeBlocking(ProductSelectionUpdateCommand.of(productSelection, SetKey.of(newKey)));
            assertThat(updatedProductSelection.getKey()).isEqualTo(newKey);
            return updatedProductSelection;
        });
    }

    @Test
    public void addAndRemoveProduct() throws Exception {
        ProductFixtures.withProduct(client(), product -> {
            withUpdateableProductSelection(client(), productSelection -> {
                final ProductSelection updatedProductSelection =
                        client().executeBlocking(ProductSelectionUpdateCommand.of(productSelection, AddProduct.of(product.toResourceIdentifier())));
                assertThat(updatedProductSelection.getProductCount()).isEqualTo(1);

                final ProductSelection updatedProductSelectionRemoveProduct =
                        client().executeBlocking(ProductSelectionUpdateCommand.of(productSelection, RemoveProduct.of(product.toResourceIdentifier())));
                assertThat(updatedProductSelectionRemoveProduct.getProductCount()).isEqualTo(0);

                return updatedProductSelection;
            });
        });
    }
}