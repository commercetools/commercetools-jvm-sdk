package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.SetCustomShippingMethod;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.projects.CartValueDraftBuilder;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.commands.ProjectIntegrationTest;
import io.sphere.sdk.projects.commands.ProjectUpdateCommand;
import io.sphere.sdk.projects.commands.updateactions.SetShippingRateInputType;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraftBuilder;
import org.junit.Test;

import java.util.Arrays;

import static io.sphere.sdk.carts.CartFixtures.withCartDraft;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingRateCartValueIntegrationTest extends ProjectIntegrationTest {


    @Test
    public void setCartValueShippingRateInput() {

        final Project project = client().executeBlocking(ProjectGet.of());
        final Project updatedProjectCartValue = client().executeBlocking(ProjectUpdateCommand.of(project,
                SetShippingRateInputType.of(CartValueDraftBuilder.of().build())));
        assertThat(updatedProjectCartValue.getShippingRateInputType().getType()).isEqualTo("CartValue");

        final CartDraft draft = CartDraft.of(EUR)
                .withTaxMode(TaxMode.EXTERNAL)
                .withShippingAddress(Address.of(DE));

        withCartDraft(client(), draft, (Cart cart) -> {
            final String taxRateName = "special tax";
            final double taxRate = 0.20;
            final ExternalTaxRateDraft externalTaxRate =
                    ExternalTaxRateDraftBuilder.ofAmount(taxRate, taxRateName, DE).build();
            final ShippingRate shippingRate = ShippingRate.of(EURO_10, null,
                    Arrays.asList(
                            io.sphere.sdk.shippingmethods.CartValueBuilder.of(0L, EURO_30).build(),
                            io.sphere.sdk.shippingmethods.CartValueBuilder.of(1L, EURO_20).build()
                    ));
            final SetCustomShippingMethod action =
                    SetCustomShippingMethod.ofExternalTaxCalculation("name", shippingRate, externalTaxRate);

            final Cart cartWithShippingMethod = client().executeBlocking(CartUpdateCommand.of(cart, action));
            assertThat(cartWithShippingMethod.getShippingRateInput()).isNull();
            assertThat(cartWithShippingMethod.getShippingInfo().getPrice()).isEqualTo(EURO_30);

            return cartWithShippingMethod;
        });
    }
}
