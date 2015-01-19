package io.sphere.sdk.zones;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.ReferenceExistsException;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.shippingmethods.commands.ShippingMethodDeleteByIdCommand;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodQuery;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodQueryModel;
import io.sphere.sdk.zones.commands.ZoneCreateCommand;
import io.sphere.sdk.zones.commands.ZoneDeleteByIdCommand;
import io.sphere.sdk.zones.queries.ZoneQuery;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.utils.SetUtils.setOf;

public class ZoneFixtures {
    public static synchronized void withZone(final TestClient client, final Consumer<Zone> consumer, final CountryCode country, final CountryCode ... moreCountries) {
        final Function<Zone, Zone> f = zone -> {
            consumer.accept(zone);
            return zone;
        };
        withUpdateableZone(client, f, country, moreCountries);
    }

    public static synchronized void withUpdateableZone(final TestClient client, final Function<Zone, Zone> f, final CountryCode country, final CountryCode... moreCountries) {
        final Set<CountryCode> countries = setOf(country, moreCountries);
        final ZoneDraft draft = ZoneDraft.ofCountries("zone " + country, countries, "Zone X");
        withUpdateableZone(client, draft, f);
    }

    public static synchronized void withUpdateableZone(final TestClient client, final Function<Zone, Zone> f, final Location location, final Location... moreLocations) {
        final Set<Location> locations = setOf(location, moreLocations);
        final ZoneDraft draft = ZoneDraft.of("zone " + location, locations, "Zone X");
        withUpdateableZone(client, draft, f);
    }

    private static void withUpdateableZone(final TestClient client, final ZoneDraft draft, final Function<Zone, Zone> f) {
        final ZoneCreateCommand createCommand = ZoneCreateCommand.of(draft);
        Zone zone = client.execute(createCommand);
        zone = f.apply(zone);//zone possibly has been updated
        client.execute(ZoneDeleteByIdCommand.of(zone));
    }

    public static void deleteZonesForCountries(final TestClient client, final CountryCode country, final CountryCode ... moreCountries) {
        final Set<CountryCode> countries = setOf(country, moreCountries);
        final ZoneQuery query = ZoneQuery.of();
        final Consumer<Zone> action = zone -> {
            try {
                client.execute(ZoneDeleteByIdCommand.of(zone));
            } catch (final Exception e) {
                if (e.getCause().getCause() instanceof ReferenceExistsException) {
                    final ShippingMethodQueryModel model = ShippingMethodQuery.model();
                    client.execute(ShippingMethodQuery.of().withPredicate(model.zoneRates().zone().is(zone)))
                            .head()
                            .ifPresent(sm -> {
                                client.execute(ShippingMethodDeleteByIdCommand.of(sm));
                                client.execute(ZoneDeleteByIdCommand.of(zone));
                            });
                } else {
                    throw e;
                }
            }
        };
        client.execute(query).getResults().stream()
                .filter(zone -> countries.stream().anyMatch(zone::contains))
                .forEach(action);

    }
}
