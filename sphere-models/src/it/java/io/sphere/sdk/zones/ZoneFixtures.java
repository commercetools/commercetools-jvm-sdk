package io.sphere.sdk.zones;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.shippingmethods.commands.ShippingMethodDeleteCommand;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodQuery;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodQueryModel;
import io.sphere.sdk.zones.commands.ZoneCreateCommand;
import io.sphere.sdk.zones.commands.ZoneDeleteCommand;
import io.sphere.sdk.zones.queries.ZoneQuery;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.test.SphereTestUtils.consumerToFunction;
import static io.sphere.sdk.utils.SphereInternalUtils.setOf;

public class ZoneFixtures {
    public static void withZone(final BlockingSphereClient client, final ZoneDraft draft, final Consumer<Zone> consumer) {
        final Zone zone = client.executeBlocking(ZoneCreateCommand.of(draft));
        consumer.accept(zone);
        client.executeBlocking(ZoneDeleteCommand.of(zone));
    }

    public static synchronized void withZone(final BlockingSphereClient client, final Consumer<Zone> consumer, final CountryCode country, final CountryCode ... moreCountries) {
        withUpdateableZone(client, consumerToFunction(consumer), country, moreCountries);
    }

    public static synchronized void withUpdateableZone(final BlockingSphereClient client, final Function<Zone, Zone> f, final CountryCode country, final CountryCode... moreCountries) {
        final Set<CountryCode> countries = setOf(country, moreCountries);
        final ZoneDraft draft = ZoneDraft.ofCountries("zone " + country, countries, "Zone X");
        withUpdateableZone(client, draft, f);
    }

    public static synchronized void withUpdateableZone(final BlockingSphereClient client, final Function<Zone, Zone> f, final Location location, final Location... moreLocations) {
        final Set<Location> locations = setOf(location, moreLocations);
        final ZoneDraft draft = ZoneDraft.of("zone " + location, locations, "Zone X");
        withUpdateableZone(client, draft, f);
    }

    private static void withUpdateableZone(final BlockingSphereClient client, final ZoneDraft draft, final Function<Zone, Zone> f) {
        final ZoneCreateCommand createCommand = ZoneCreateCommand.of(draft);
        Zone zone = client.executeBlocking(createCommand);
        zone = f.apply(zone);//zone possibly has been updated
        client.executeBlocking(ZoneDeleteCommand.of(zone));
    }

    public static void deleteZonesForCountries(final BlockingSphereClient client, final CountryCode country, final CountryCode ... moreCountries) {
        final Set<CountryCode> countries = setOf(country, moreCountries);
        final ZoneQuery query = ZoneQuery.of();
        final Consumer<Zone> action = zone -> {
            try {
                client.executeBlocking(ZoneDeleteCommand.of(zone));
            } catch (final SphereException e) {
                client.executeBlocking(ShippingMethodQuery.of().withPredicates(ShippingMethodQueryModel.of().zoneRates().zone().is(zone)))
                        .head()
                        .ifPresent(sm -> {
                            client.executeBlocking(ShippingMethodDeleteCommand.of(sm));
                            client.executeBlocking(ZoneDeleteCommand.of(zone));
                        });
            }
        };
        client.executeBlocking(query).getResults().stream()
                .filter(zone -> countries.stream().anyMatch(zone::contains))
                .forEach(action);

    }
}
