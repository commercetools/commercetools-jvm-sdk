package io.sphere.sdk.shippingmethods;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.shippingmethods.commands.ShippingMethodCreateCommand;
import io.sphere.sdk.shippingmethods.commands.ShippingMethodDeleteByIdCommand;

import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ShippingMethodFixtures {
    public static void withShippingMethod(final TestClient client, final Consumer<ShippingMethod> consumer){
        final Function<ShippingMethod, ShippingMethod> f = shippingMethod -> {
            consumer.accept(shippingMethod);
            return shippingMethod;
        };
        withUpdateableShippingMethod(client, f);
    }

    public static void withUpdateableShippingMethod(final TestClient client, final Function<ShippingMethod, ShippingMethod> f){
        withTaxCategory(client, taxCategory -> {
            final ShippingMethodDraft draft = ShippingMethodDraft.of(randomString(), "test shipping method", taxCategory, asList());
            final ShippingMethod shippingMethod = client.execute(ShippingMethodCreateCommand.of(draft));
            final ShippingMethod possiblyUpdatedShippingMethod = f.apply(shippingMethod);
            client.execute(ShippingMethodDeleteByIdCommand.of(possiblyUpdatedShippingMethod));
        });
    }
}
