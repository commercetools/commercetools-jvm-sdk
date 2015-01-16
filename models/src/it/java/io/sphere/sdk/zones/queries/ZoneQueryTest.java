package io.sphere.sdk.zones.queries;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneFixtures;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ZoneQueryTest extends IntegrationTest {
    @BeforeClass
    public static void deleteRemainingZone() throws Exception {
        ZoneFixtures.deleteZonesForCountries(client(), CountryCode.CC, CountryCode.CD);
    }

    @Test
    public void queryByName() throws Exception {
        ZoneFixtures.withZone(client(), zoneA -> {
            ZoneFixtures.withZone(client(), zoneB -> {
                final PagedQueryResult<Zone> result = execute(ZoneQuery.of().byName(zoneA.getName()));
                assertThat(result.getResults()).containsExactly(zoneA);
                return zoneB;
            }, CountryCode.CD);
            return zoneA;
        }, CountryCode.CC);
    }
}