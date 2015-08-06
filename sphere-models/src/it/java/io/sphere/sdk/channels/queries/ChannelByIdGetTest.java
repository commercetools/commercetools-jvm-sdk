package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.channels.ChannelRole.INVENTORY_SUPPLY;
import static org.assertj.core.api.Assertions.assertThat;

public class ChannelByIdGetTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withChannelOfRole(client(), INVENTORY_SUPPLY, channel -> {
            final Channel fetchedChannel = execute(ChannelByIdGet.of(channel.getId()));
            assertThat(fetchedChannel.getId()).contains(channel.getId());
        });
    }
}