package io.sphere.sdk.zones.commands;


import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.zones.*;
import net.jcip.annotations.NotThreadSafe;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static com.neovisionaries.i18n.CountryCode.*;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static io.sphere.sdk.zones.ZoneFixtures.withZone;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static  io.sphere.sdk.test.SphereTestUtils.*;


@NotThreadSafe
public class ZoneCreateCommandIntegrationTest extends IntegrationTest {
    @Before
    public void deleteRemainingZone() throws Exception {
        ZoneFixtures.deleteZonesForCountries(client(), AT, BE, CH);
    }

    @Test
    public void execution() throws Exception {
        final Set<CountryCode> euAndSwissCountries = asSet(AT, BE, CH);//not complete, but you get the idea
        final String key = randomKey();
        final Set<Location> locations = euAndSwissCountries.stream().map(country -> Location.of(country)).collect(toSet());
        final ZoneDraft draft = ZoneDraftBuilder.of("zone1",locations ).description("EU and Swiss").key(key).build();
        final ZoneCreateCommand createCommand = ZoneCreateCommand.of(draft);
        final Zone zone = client().executeBlocking(createCommand);
        assertThat(zone.getKey()).isEqualTo(key);
        //end example parsing here
        client().executeBlocking(ZoneDeleteCommand.ofKey(zone.getKey(),zone.getVersion()));
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