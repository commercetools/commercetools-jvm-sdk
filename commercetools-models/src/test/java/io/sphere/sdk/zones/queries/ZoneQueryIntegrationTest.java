package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.zones.Location;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneFixtures;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static com.neovisionaries.i18n.CountryCode.*;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ZoneQueryIntegrationTest extends IntegrationTest {
    @BeforeClass
    public static void deleteRemainingZone() throws Exception {
        ZoneFixtures.deleteZonesForCountries(client(), CC, CD, CF, CG, CI, CK);
    }

    @Test
    public void queryByName() throws Exception {
        ZoneFixtures.withUpdateableZone(client(), zoneA -> {
            ZoneFixtures.withUpdateableZone(client(), zoneB -> {
                final PagedQueryResult<Zone> result = client().executeBlocking(ZoneQuery.of().byName(zoneA.getName()));
                assertThat(result.getResults()).isEqualTo(asList(zoneA));
                return zoneB;
            }, CD);
            return zoneA;
        }, CC);
    }

    @Test
    public void byCountry() throws Exception {
        ZoneFixtures.withUpdateableZone(client(), zoneA -> {
            ZoneFixtures.withUpdateableZone(client(), zoneB -> {
                final Set<Location> locations = zoneA.getLocations();
                final PagedQueryResult<Zone> result = client().executeBlocking(ZoneQuery.of().byCountry(oneOf(locations).getCountry()));
                assertThat(result.getResults()).isEqualTo(asList(zoneA));
                return zoneB;
            }, CF);
            return zoneA;
        }, CG);
    }

    @Test
    public void byLocation() throws Exception {
        final Location bar = Location.of(CK, "state bar");
        final Location baz = Location.of(CI, "state baz");
        final Location foo = Location.of(CI, "state foo");
        ZoneFixtures.withUpdateableZone(client(), zoneA -> {
            ZoneFixtures.withUpdateableZone(client(), zoneB -> {
                //exact matches
                locationCheck(foo, zoneB);
                locationCheck(bar, zoneA);
                locationCheck(baz, zoneA);

                //provide only country finds nothing on exact search
                locationCheck(Location.of(CK));
                locationCheck(Location.of(CI));

                //mixing up state and country does not find anything
                locationCheck(Location.of(CI, "state bar"));
                return zoneB;
            }, foo);
            return zoneA;
        }, bar, baz);
    }

    private void locationCheck(final Location searchLocation, final Zone ... expected) {
        final PagedQueryResult<Zone> result = client().executeBlocking(ZoneQuery.of().byLocation(searchLocation));
        final Set<Zone> actual = new HashSet<>(result.getResults());
        assertThat(actual).isEqualTo(new HashSet<>(asList(expected)));
    }
}