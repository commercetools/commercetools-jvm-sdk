package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.SetCustomShippingMethod;
import io.sphere.sdk.carts.commands.updateactions.SetShippingRateInput;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.projects.CartScoreDraftBuilder;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.ShippingRateInputTypeDraft;
import io.sphere.sdk.projects.commands.ProjectIntegrationTest;
import io.sphere.sdk.projects.commands.ProjectUpdateCommand;
import io.sphere.sdk.projects.commands.updateactions.SetShippingRateInputType;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.shippingmethods.PriceFunctionBuilder;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraftBuilder;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;

import static io.sphere.sdk.carts.CartFixtures.withCartDraft;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingRateScoreIntegrationTest extends ProjectIntegrationTest {


    @Test
    public void setScoreShippingRateInput() {

        final Project project = client().executeBlocking(ProjectGet.of());
        final Project updatedProjectCartScore = client().executeBlocking(ProjectUpdateCommand.of(project,
                SetShippingRateInputType.of(CartScoreDraftBuilder.of().build())));
        assertThat(updatedProjectCartScore.getShippingRateInputType().getType()).isEqualTo("CartScore");

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
                            io.sphere.sdk.shippingmethods.CartScoreBuilder.of( 0L, PriceFunctionBuilder.of(EUR.getCurrencyCode(), "(50 * x) + 4000").build()).build(),
                            io.sphere.sdk.shippingmethods.CartScoreBuilder.of(1L, EURO_20).build(),
                            io.sphere.sdk.shippingmethods.CartScoreBuilder.of(2L, EURO_30).build()
                    ));

            final SetCustomShippingMethod action =
                    SetCustomShippingMethod.ofExternalTaxCalculation("name", shippingRate, externalTaxRate);

            assertEventually(Duration.ofSeconds(60), Duration.ofMillis(100), () -> {
                final Cart cart1 = client().executeBlocking(CartByIdGet.of(cart));
                try {
                    final Cart cartWithShippingMethod = client().executeBlocking(CartUpdateCommand.of(cart1, action));

                    final Cart cartWithShippingMethodWithScore1 = client().executeBlocking(CartUpdateCommand.of(cartWithShippingMethod,
                            SetShippingRateInput.of(ScoreShippingRateInputDraftBuilder.of(1L).build())));
                    assertThat(cartWithShippingMethodWithScore1.getShippingRateInput()).isInstanceOf(ScoreShippingRateInput.class);
                    assertThat(cartWithShippingMethodWithScore1.getShippingInfo().getPrice()).isEqualTo(EURO_20);

                    final Cart cartWithShippingMethodWithScore0 = client().executeBlocking(CartUpdateCommand.of(cartWithShippingMethodWithScore1,
                            SetShippingRateInput.of(ScoreShippingRateInputDraftBuilder.of(0L).build())));
                    assertThat(cartWithShippingMethodWithScore0.getShippingRateInput()).isInstanceOf(ScoreShippingRateInput.class);
                    assertThat(cartWithShippingMethodWithScore0.getShippingInfo().getPrice()).isEqualTo(EURO_40);
                } catch (ErrorResponseException e) {
                    throw new AssertionError(e);
                }
            });

            return client().executeBlocking(CartByIdGet.of(cart));
        });
    }

}
