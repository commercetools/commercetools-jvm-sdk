package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.SetCustomShippingMethod;
import io.sphere.sdk.carts.commands.updateactions.SetShippingRateInput;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.projects.*;
import io.sphere.sdk.projects.commands.ProjectIntegrationTest;
import io.sphere.sdk.projects.commands.ProjectUpdateCommand;
import io.sphere.sdk.projects.commands.updateactions.SetShippingRateInputType;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraftBuilder;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static io.sphere.sdk.carts.CartFixtures.withCartDraft;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingRateClassificationIntegrationTest extends ProjectIntegrationTest {



    @Test
    public void setClassificationShippingRateInput() {

        final Set<LocalizedEnumValue> set = new HashSet<>();
        set.add(LocalizedEnumValue.of("Small", LocalizedString.of(Locale.ENGLISH, "Small", Locale.GERMANY, "Klein")));
        set.add(LocalizedEnumValue.of("Medium", LocalizedString.of(Locale.ENGLISH, "Medium", Locale.GERMANY, "Mittel")));
        set.add(LocalizedEnumValue.of("Heavy", LocalizedString.of(Locale.ENGLISH, "Heavy", Locale.GERMANY, "Schwergut")));

        final Project project = client().executeBlocking(ProjectGet.of());
        final Project updatedProjectCartClassification = client().executeBlocking(ProjectUpdateCommand.of(project,
                SetShippingRateInputType.of(CartClassificationDraftBuilder.of(set).build())));
        assertThat(updatedProjectCartClassification.getShippingRateInputType().getType()).isEqualTo("CartClassification");

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
                            io.sphere.sdk.shippingmethods.CartClassificationBuilder.of("Small", EURO_20).build(),
                            io.sphere.sdk.shippingmethods.CartClassificationBuilder.of("Heavy", EURO_30).build()
                    ));
            final SetCustomShippingMethod action =
                    SetCustomShippingMethod.ofExternalTaxCalculation("name", shippingRate, externalTaxRate);
            assertEventually(Duration.ofSeconds(60), Duration.ofMillis(100), () -> {
                final Cart cart1 = client().executeBlocking(CartByIdGet.of(cart));
                try {
                    final Cart cartWithShippingMethod = client().executeBlocking(CartUpdateCommand.of(cart1, action));

                    final Cart cartWithShippingMethodWithClassification = client().executeBlocking(CartUpdateCommand.of(cartWithShippingMethod,
                            SetShippingRateInput.of(ClassificationShippingRateInputDraftBuilder.of("Small").build())));
                    assertThat(cartWithShippingMethodWithClassification.getShippingRateInput()).isInstanceOf(ClassificationShippingRateInput.class);
                    assertThat(cartWithShippingMethodWithClassification.getShippingInfo().getPrice()).isEqualTo(EURO_20);
                } catch (ErrorResponseException e) {
                    throw new AssertionError(e);
                }
            });

            return client().executeBlocking(CartByIdGet.of(cart));
        });
    }


}
