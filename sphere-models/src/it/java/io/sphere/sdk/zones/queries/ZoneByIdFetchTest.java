package io.sphere.sdk.zones.queries;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneFixtures;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;

public class ZoneByIdFetchTest extends IntegrationTest {
    @BeforeClass
    public static void deleteRemainingZone() throws Exception {
        ZoneFixtures.deleteZonesForCountries(client(), CountryCode.BA);
    }

    @Test
    public void fetchById() throws Exception {
        ZoneFixtures.withUpdateableZone(client(), zone -> {
            final Optional<Zone> fetchedZone = execute(ZoneByIdFetch.of(zone.getId()));
            assertThat(fetchedZone.get().getId()).isEqualTo(zone.getId());
            return zone;
        }, CountryCode.BA);
    }
}