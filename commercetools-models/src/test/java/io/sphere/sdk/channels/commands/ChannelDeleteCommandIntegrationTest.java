package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.queries.ChannelByIdGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.*;

public class ChannelDeleteCommandIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final Channel channel = client().executeBlocking(ChannelCreateCommand.of(ChannelDraft.of(randomKey())));

        client().executeBlocking(ChannelDeleteCommand.of(channel));

        assertThat(client().executeBlocking(ChannelByIdGet.of(channel.getId()))).isNull();
    }
}