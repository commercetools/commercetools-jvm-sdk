package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.channels.ChannelRole.INVENTORY_SUPPLY;
import static org.assertj.core.api.Assertions.assertThat;

public class ChannelByIdFetchTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withChannelOfRole(client(), INVENTORY_SUPPLY, channel -> {
            final Optional<Channel> fetchedChannel = execute(ChannelByIdFetch.of(channel.getId()));
            assertThat(fetchedChannel.map(c -> c.getId())).contains(channel.getId());
        });
    }
}