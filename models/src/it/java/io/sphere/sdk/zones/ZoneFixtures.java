package io.sphere.sdk.zones;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.zones.commands.ZoneCreateCommand;
import io.sphere.sdk.zones.commands.ZoneDeleteByIdCommand;
import io.sphere.sdk.zones.queries.ZoneQuery;

import java.util.Set;
import java.util.function.Function;

import static io.sphere.sdk.utils.SetUtils.setOf;

public class ZoneFixtures {
    public static synchronized void withZone(final TestClient client, final Function<Zone, Zone> f, final CountryCode country, final CountryCode ... moreCountries) {
        final Set<CountryCode> countries = setOf(country, moreCountries);
        final ZoneDraft draft = ZoneDraft.ofCountries("zone " + country, countries, "Zone X");
        final ZoneCreateCommand createCommand = ZoneCreateCommand.of(draft);
        final Zone zone1 = client.execute(createCommand);
        Zone zone = zone1;
        zone = f.apply(zone);//zone possibly has been updated
        client.execute(ZoneDeleteByIdCommand.of(zone));
    }

    public static void deleteZonesForCountries(final TestClient client, final CountryCode country, final CountryCode ... moreCountries) {
        final Set<CountryCode> countries = setOf(country, moreCountries);
        final ZoneQuery query = ZoneQuery.of();
        client.execute(query).getResults().stream()
                .filter(zone -> countries.stream().anyMatch(zone::contains))
                .forEach(zone -> client.execute(ZoneDeleteByIdCommand.of(zone)));
    }
}
