package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.channels.queries.ChannelQuery;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Point;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import net.jcip.annotations.NotThreadSafe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.neovisionaries.i18n.CountryCode.DE;
import static io.sphere.sdk.channels.ChannelFixtures.withChannel;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public class ChannelCreateCommandIntegrationTest extends IntegrationTest {

    @Before
    @After
    public void cleanUp() throws Exception {
        final ChannelQuery query = ChannelQuery.of()
                .withPredicates(m -> m.key().isIn(asList("demo-key", "example-channel-key")));
        final PagedQueryResult<Channel> queryResult = client().executeBlocking(query);
        queryResult.getResults().forEach(c -> client().executeBlocking(ChannelDeleteCommand.of(c)));
    }

    @Test
    public void execution() throws Exception {
        final String key = "demo-key";
        final Address address = Address.of(DE).withCity("Berlin");
        final Point geoLocation = Point.of(52.5200070, 13.4049540);
        final ChannelDraft channelDraft = ChannelDraft.of(key)
                .withName(LocalizedString.of(ENGLISH, "name"))
                .withDescription(LocalizedString.of(ENGLISH, "description"))
                .withRoles(ChannelRole.INVENTORY_SUPPLY)
                .withAddress(address)
                .withGeoLocation(geoLocation);
        final Channel channel = client().executeBlocking(ChannelCreateCommand.of(channelDraft));
        assertThat(channel.getKey()).isEqualTo(key);
        assertThat(channel.getName()).isEqualTo(LocalizedString.of(ENGLISH, "name"));
        assertThat(channel.getDescription()).isEqualTo(LocalizedString.of(ENGLISH, "description"));
        assertThat(channel.getRoles()).isEqualTo(asSet(ChannelRole.INVENTORY_SUPPLY));
        assertThat(channel.getAddress()).isEqualTo(address);
        assertThat(channel.getGeoLocation()).isEqualTo(geoLocation);
    }

    @Test
    public void createByJson() {
        final ChannelDraft channelDraft = SphereJsonUtils.readObjectFromResource("drafts-tests/channel.json", ChannelDraft.class);
        withChannel(client(), channelDraft, channel -> {
            assertThat(channel.getKey()).isEqualTo("example-channel-key");
            assertThat(channel.getRoles()).containsOnly(ChannelRole.ORDER_IMPORT);
        });
    }
}