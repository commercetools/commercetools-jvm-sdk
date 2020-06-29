package io.sphere.sdk.shippingmethods;

import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shippingmethods.commands.ShippingMethodCreateCommand;
import io.sphere.sdk.shippingmethods.commands.ShippingMethodDeleteCommand;
import io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommand;
import io.sphere.sdk.shippingmethods.commands.updateactions.AddShippingRate;
import io.sphere.sdk.shippingmethods.commands.updateactions.AddZone;
import io.sphere.sdk.zones.Location;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneDraft;
import io.sphere.sdk.zones.commands.ZoneCreateCommand;
import io.sphere.sdk.zones.queries.ZoneQuery;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singleton;

public class ShippingMethodFixtures {
    public static void withShippingMethod(final BlockingSphereClient client, final ShippingMethodDraft draft, final Consumer<ShippingMethod> consumer) {
        final ShippingMethod shippingMethod = client.executeBlocking(ShippingMethodCreateCommand.of(draft));
        consumer.accept(shippingMethod);
        client.executeBlocking(ShippingMethodDeleteCommand.of(shippingMethod));
    }

    public static void withShippingMethod(final BlockingSphereClient client, final Consumer<ShippingMethod> consumer) {
        withUpdateableShippingMethod(client, consumerToFunction(consumer));
    }

    public static void withUpdateableShippingMethodForGermany(final BlockingSphereClient client, final UnaryOperator<ShippingMethod> consumer) {
        final Optional<Zone> zoneOptional = client.executeBlocking(ZoneQuery.of().byCountry(DE)).head();
        final Zone zone;
        if (zoneOptional.isPresent()) {
            zone = zoneOptional.get();
        } else {
            zone = client.executeBlocking(ZoneCreateCommand.of(ZoneDraft.of("de", singleton(Location.of(DE)))));
        }
        withUpdateableShippingMethod(client, shippingMethodWithOutZone -> {
            final ShippingMethod updated = client.executeBlocking(ShippingMethodUpdateCommand.of(shippingMethodWithOutZone, asList(AddZone.of(zone), AddShippingRate.of(ShippingRate.of(EURO_1), zone))));
            return consumer.apply(updated);
        });
    }

    public static void withShippingMethodForGermany(final BlockingSphereClient client, final Consumer<ShippingMethod> consumer) {
        withUpdateableShippingMethodForGermany(client, m -> {
            consumer.accept(m);
            return m;
        });
    }

    public static void withDynamicShippingMethodForGermany(final BlockingSphereClient client, final CartPredicate cartPredicate, final Consumer<ShippingMethod> consumer) {
        withUpdateableDynamicShippingMethodForGermany(client, cartPredicate, m -> {
            consumer.accept(m);
            return m;
        });
    }

    public static void withUpdateableShippingMethod(final BlockingSphereClient client, final Function<ShippingMethod, ShippingMethod> f) {
        withTaxCategory(client, taxCategory -> {
            final ShippingMethodDraft draft = ShippingMethodDraft.of(randomString(), "test shipping method", taxCategory, asList());
            final ShippingMethod shippingMethod = client.executeBlocking(ShippingMethodCreateCommand.of(draft));
            final ShippingMethod possiblyUpdatedShippingMethod = f.apply(shippingMethod);
            client.executeBlocking(ShippingMethodDeleteCommand.of(possiblyUpdatedShippingMethod));
        });
    }

    public static void withUpdateableDynamicShippingMethodForGermany(final BlockingSphereClient client, final CartPredicate cartPredicate, final UnaryOperator<ShippingMethod> consumer) {
        final Optional<Zone> zoneOptional = client.executeBlocking(ZoneQuery.of().byCountry(DE)).head();
        final Zone zone;
        if (zoneOptional.isPresent()) {
            zone = zoneOptional.get();
        } else {
            zone = client.executeBlocking(ZoneCreateCommand.of(ZoneDraft.of("de", singleton(Location.of(DE)))));
        }
        withUpdateableDynamicShippingMethod(client, cartPredicate, shippingMethodWithOutZone -> {
            final ShippingMethod updated = client.executeBlocking(ShippingMethodUpdateCommand.of(shippingMethodWithOutZone, asList(AddZone.of(zone), AddShippingRate.of(ShippingRate.of(EURO_1), zone))));
            return consumer.apply(updated);
        });
    }

    public static void withUpdateableDynamicShippingMethod(final BlockingSphereClient client, final CartPredicate cartPredicate, final Function<ShippingMethod, ShippingMethod> f) {
        withTaxCategory(client, taxCategory -> {
            final ShippingMethodDraft draft = ShippingMethodDraftBuilder
                    .of(randomString(), "test shipping method", taxCategory.toReference(), asList(), false)
                    .predicate(cartPredicate)
                    .build();
            final ShippingMethod shippingMethod = client.executeBlocking(ShippingMethodCreateCommand.of(draft));
            final ShippingMethod possiblyUpdatedShippingMethod = f.apply(shippingMethod);
            client.executeBlocking(ShippingMethodDeleteCommand.of(possiblyUpdatedShippingMethod));
        });
    }


    public static void withUpdateableShippingMethod(final BlockingSphereClient client, final UnaryOperator<ShippingMethodDraftBuilder> builderMapper, final Function<ShippingMethod, ShippingMethod> f) {
        withTaxCategory(client, taxCategory -> {
            final ShippingMethodDraftBuilder builder = ShippingMethodDraftBuilder.of(randomString(), "test shipping method", taxCategory.toReference(), asList(), false);
            final ShippingMethodDraft draft = builderMapper.apply(builder).build();
            final ShippingMethod shippingMethod = client.executeBlocking(ShippingMethodCreateCommand.of(draft));
            final ShippingMethod possiblyUpdatedShippingMethod = f.apply(shippingMethod);
            client.executeBlocking(ShippingMethodDeleteCommand.of(possiblyUpdatedShippingMethod));
        });
    }

    public static void withUpdateableShippingMethod(final BlockingSphereClient client, final Consumer<ShippingMethod> consumer) {
        withTaxCategory(client, taxCategory -> {
            final ShippingMethodDraft draft = ShippingMethodDraft.of(randomString(), "test shipping method", taxCategory, asList());
            final ShippingMethod shippingMethod = client.executeBlocking(ShippingMethodCreateCommand.of(draft));
            consumer.accept(shippingMethod);
        });
    }
}
