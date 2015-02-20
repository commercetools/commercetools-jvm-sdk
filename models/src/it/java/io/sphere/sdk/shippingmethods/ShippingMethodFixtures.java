package io.sphere.sdk.shippingmethods;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.shippingmethods.commands.ShippingMethodCreateCommand;
import io.sphere.sdk.shippingmethods.commands.ShippingMethodDeleteByIdCommand;
import io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommand;
import io.sphere.sdk.shippingmethods.commands.updateactions.AddZone;
import io.sphere.sdk.zones.Location;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneDraft;
import io.sphere.sdk.zones.commands.ZoneCreateCommand;
import io.sphere.sdk.zones.queries.ZoneQuery;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SetUtils.asSet;

public class ShippingMethodFixtures {
    public static void withShippingMethod(final TestClient client, final Consumer<ShippingMethod> consumer){
        withUpdateableShippingMethod(client, consumerToFunction(consumer));
    }

    public static void withShippingMethodForGermany(final TestClient client, final Consumer<ShippingMethod> consumer) {
        final Optional<Zone> zoneOptional = client.execute(ZoneQuery.of().byCountry(DE)).head();
        final Zone zone;
        if (zoneOptional.isPresent()) {
            zone = zoneOptional.get();
        } else {
            zone = client.execute(ZoneCreateCommand.of(ZoneDraft.of("de", asSet(Location.of(DE)))));
        }
        withUpdateableShippingMethod(client, shippingMethodWithOutZone -> {
            final ShippingMethod updated = client.execute(ShippingMethodUpdateCommand.of(shippingMethodWithOutZone, AddZone.of(zone)));
            return updated;
        });
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
