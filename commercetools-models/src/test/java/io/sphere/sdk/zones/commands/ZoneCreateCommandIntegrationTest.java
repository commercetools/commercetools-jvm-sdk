package io.sphere.sdk.zones.commands;


import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.zones.Location;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneDraft;
import io.sphere.sdk.zones.ZoneFixtures;
import net.jcip.annotations.NotThreadSafe;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static com.neovisionaries.i18n.CountryCode.*;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static io.sphere.sdk.zones.ZoneFixtures.withZone;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public class ZoneCreateCommandIntegrationTest extends IntegrationTest {
    @Before
    public void deleteRemainingZone() throws Exception {
        ZoneFixtures.deleteZonesForCountries(client(), AT, BE, CH);
    }

    @Test
    public void execution() throws Exception {
        final Set<CountryCode> euAndSwissCountries = asSet(AT, BE, CH);//not complete, but you get the idea
        final ZoneDraft draft = ZoneDraft.ofCountries("zone1", euAndSwissCountries, "EU and Swiss");
        final ZoneCreateCommand createCommand = ZoneCreateCommand.of(draft);
        final Zone zone = client().executeBlocking(createCommand);
        //end example parsing here
        client().executeBlocking(ZoneDeleteCommand.of(zone));
    }

    @Test
    public void createByZone() {
        final ZoneDraft draft = SphereJsonUtils.readObjectFromResource("drafts-tests/zone.json", ZoneDraft.class);
        withZone(client(), draft, zone -> {
            assertThat(zone.getName()).isEqualTo("demo zone");
            assertThat(zone.getLocations()).isEqualTo(asSet(Location.of(CH, "Vaud"), Location.of(CH)));
        });
    }
}