package io.sphere.sdk.zones;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.zones.commands.ZoneCreateCommand;
import io.sphere.sdk.zones.commands.ZoneDeleteByIdCommand;

import java.util.Set;
import java.util.function.Consumer;

import static com.neovisionaries.i18n.CountryCode.*;
import static io.sphere.sdk.utils.SetUtils.asSet;

public class ZonesFixtures {
    public static void withZone(final TestClient client, final Consumer<Zone> consumer) throws Exception {
        final Zone zone = createZone(client);
        try {
            consumer.accept(zone);
        } finally {
            client.execute(ZoneDeleteByIdCommand.of(zone));
        }
    }

    private static Zone createZone(final TestClient client) {
        final Set<CountryCode> euAndSwissCountries = asSet(AT, BE, CH);//not complete, but you get the idea
        final ZoneDraft draft = ZoneDraft.ofCountries("zone1", euAndSwissCountries, "EU and Swiss");
        final ZoneCreateCommand createCommand = ZoneCreateCommand.of(draft);
        final Zone zone = client.execute(createCommand);
        //end example parsing here
        return zone;
    }
}
