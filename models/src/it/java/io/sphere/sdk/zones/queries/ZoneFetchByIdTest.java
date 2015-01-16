package io.sphere.sdk.zones.queries;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.zones.Zone;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.zones.ZoneFixtures.withZone;
import static org.fest.assertions.Assertions.assertThat;

public class ZoneFetchByIdTest extends IntegrationTest {
    @Test
    public void fetchById() throws Exception {
        withZone(client(), zone -> {
            final Optional<Zone> fetchedZone = execute(ZoneFetchById.of(zone.getId()));
            assertThat(fetchedZone.get().getId()).isEqualTo(zone.getId());
            return zone;
        }, CountryCode.BA);
    }
}