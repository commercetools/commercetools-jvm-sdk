package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.SetCustomShippingMethod;
import io.sphere.sdk.carts.commands.updateactions.SetShippingAddress;
import io.sphere.sdk.carts.commands.updateactions.SetShippingMethod;
import io.sphere.sdk.carts.commands.updateactions.SetShippingRateInput;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.projects.*;
import io.sphere.sdk.projects.commands.ProjectIntegrationTest;
import io.sphere.sdk.projects.commands.ProjectUpdateCommand;
import io.sphere.sdk.projects.commands.updateactions.SetShippingRateInputType;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.shippingmethods.PriceFunction;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodsByCartGet;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraftBuilder;
import io.sphere.sdk.test.utils.VrapRequestDecorator;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static io.sphere.sdk.carts.CartFixtures.GERMAN_ADDRESS;
import static io.sphere.sdk.carts.CartFixtures.withCart;
import static io.sphere.sdk.carts.CartFixtures.withCartDraft;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethodForGermany;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingRateClassificationIntegrationTest extends ProjectIntegrationTest {



    @Test
    public void setClassificationShippingRateInput() {

        Map<String, LocalizedString> classificationMap = new HashMap<>();
        classificationMap.put("Small", LocalizedString.of(Locale.ENGLISH, "Small", Locale.GERMANY, "Klein"));
        classificationMap.put("Medium", LocalizedString.of(Locale.ENGLISH, "Medium", Locale.GERMANY, "Mittel"));
        classificationMap.put("Heavy", LocalizedString.of(Locale.ENGLISH, "Heavy", Locale.GERMANY, "Schwergut"));

        ShippingRateInputType classificationInputType = CartClassification.of(classificationMap);

        final Project project = client().executeBlocking(ProjectGet.of());
        final Project updatedProjectCartValue = client().executeBlocking(ProjectUpdateCommand.of(project, SetShippingRateInputType.of(classificationInputType)));
        assertThat(updatedProjectCartValue.getShippingRateInputType().getType()).isEqualTo(classificationInputType.getType());


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
                            io.sphere.sdk.shippingmethods.CartClassification.of(EURO_1, "Small")
                    ));
            final SetCustomShippingMethod action =
                    SetCustomShippingMethod.ofExternalTaxCalculation("name", shippingRate, externalTaxRate);

            final Cart cartWithShippingMethod = client().executeBlocking(CartUpdateCommand.of(cart, action));
            final ShippingRateInput shippingRateInput = ClassificationShippingRateInput.of("Small");
            final Cart cartWithShippingMethodWithScore = client().executeBlocking(CartUpdateCommand.of(cartWithShippingMethod, SetShippingRateInput.of(shippingRateInput)));

            return cartWithShippingMethodWithScore;
        });
    }


}
