package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.SetCustomShippingMethod;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.projects.CartScore;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.ShippingRateInputType;
import io.sphere.sdk.projects.commands.ProjectIntegrationTest;
import io.sphere.sdk.projects.commands.ProjectUpdateCommand;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.shippingmethods.PriceFunction;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraftBuilder;
import org.junit.Test;

import java.util.Arrays;

import static io.sphere.sdk.carts.CartFixtures.withCartDraft;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.test.SphereTestUtils.EURO_20;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingRateScoreIntegrationTest extends ProjectIntegrationTest {


    @Test
    public void setScoreShippingRateInput() {

        final ShippingRateInputType shippingRateInputType = CartScore.of();
        final Project project = client().executeBlocking(ProjectGet.of());
        final Project updatedProjectCartValue = client().executeBlocking(ProjectUpdateCommand.of(project, SetShippingRateInputType.of(shippingRateInputType)));
        assertThat(updatedProjectCartValue.getShippingRateInputType().getType()).isEqualTo(shippingRateInputType.getType());
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
                            io.sphere.sdk.shippingmethods.CartScore.ofPriceFunction(PriceFunction.of(EUR.getCurrencyCode(), "(50 * x) + 750"), 0L),
                            io.sphere.sdk.shippingmethods.CartScore.ofScore(EURO_20, 1L),
                            io.sphere.sdk.shippingmethods.CartScore.ofScore(EURO_20, 2L)
                    ));

            final SetCustomShippingMethod action =
                    SetCustomShippingMethod.ofExternalTaxCalculation("name", shippingRate, externalTaxRate);
            final Cart cartWithShippingMethod = client().executeBlocking(CartUpdateCommand.of(cart, action));

            final ShippingRateInput shippingRateInput = ScoreShippingRateInput.of(11L);
            SphereJsonUtils.toJsonString(ScoreShippingRateInput.of(11L));
            final Cart cartWithShippingMethodWithScore = client().executeBlocking(CartUpdateCommand.of(cartWithShippingMethod, SetShippingRateInput.of(shippingRateInput)));
            return cartWithShippingMethodWithScore;
        });
    }

}
