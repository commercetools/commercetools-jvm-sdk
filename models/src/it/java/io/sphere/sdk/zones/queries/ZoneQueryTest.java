package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.zones.Location;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneFixtures;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static com.neovisionaries.i18n.CountryCode.*;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

public class ZoneQueryTest extends IntegrationTest {
    @BeforeClass
    public static void deleteRemainingZone() throws Exception {
        ZoneFixtures.deleteZonesForCountries(client(), CC, CD, CF, CG);
    }

    @Test
    public void queryByName() throws Exception {
        ZoneFixtures.withZone(client(), zoneA -> {
            ZoneFixtures.withZone(client(), zoneB -> {
                final PagedQueryResult<Zone> result = execute(ZoneQuery.of().byName(zoneA.getName()));
                assertThat(result.getResults()).containsExactly(zoneA);
                return zoneB;
            }, CD);
            return zoneA;
        }, CC);
    }

    @Test
    public void byCountry() throws Exception {
        ZoneFixtures.withZone(client(), zoneA -> {
            ZoneFixtures.withZone(client(), zoneB -> {
                final Set<Location> locations = zoneA.getLocations();
                final PagedQueryResult<Zone> result = execute(ZoneQuery.of().byCountry(oneOf(locations).getCountry()));
                assertThat(result.getResults()).containsExactly(zoneA);
                return zoneB;
            }, CF);
            return zoneA;
        }, CG);
    }
}