package io.sphere.sdk.zones.queries;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneFixtures;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ZoneByIdGetIntegrationTest extends IntegrationTest {
    @BeforeClass
    public static void deleteRemainingZone() throws Exception {
        ZoneFixtures.deleteZonesForCountries(client(), CountryCode.BA);
    }

    @Test
    public void fetchById() throws Exception {
        ZoneFixtures.withUpdateableZone(client(), zone -> {
            final Zone fetchedZone = client().executeBlocking(ZoneByIdGet.of(zone.getId()));
            assertThat(fetchedZone.getId()).isEqualTo(zone.getId());
            return zone;
        }, CountryCode.BA);
    }
}